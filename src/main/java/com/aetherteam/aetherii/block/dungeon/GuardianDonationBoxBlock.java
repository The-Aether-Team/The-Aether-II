package com.aetherteam.aetherii.block.dungeon;

import com.aetherteam.aetherii.blockentity.GuardianDonationBoxBlockEntity;
import com.aetherteam.aetherii.blockentity.VaseBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class GuardianDonationBoxBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<GuardianDonationBoxBlock> CODEC = simpleCodec(GuardianDonationBoxBlock::new);
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final Map<Direction, VoxelShape> SHAPES = Shapes.rotateHorizontal(Block.box(3.0F, 0.0F, 2.0F, 13.0F, 12.0F, 14.0F));

    public MapCodec<GuardianDonationBoxBlock> codec() {
        return CODEC;
    }

    public GuardianDonationBoxBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        if (directionToNeighbour.getOpposite() == state.getValue(FACING) && !state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            if (state.getValue(WATERLOGGED)) {
                ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
            }

            return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
        }
    }

    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        if (!context.replacingClickedOnBlock()) {
            BlockState state = context.getLevel().getBlockState(context.getClickedPos().relative(context.getClickedFace().getOpposite()));
            if (state.is(this) && state.getValue(FACING) == context.getClickedFace()) {
                return null;
            }
        }

        BlockState state = this.defaultBlockState();
        LevelReader level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        FluidState replacedFluidState = context.getLevel().getFluidState(context.getClickedPos());

        for(Direction direction : context.getNearestLookingDirections()) {
            if (direction.getAxis().isHorizontal()) {
                state = state.setValue(FACING, direction.getOpposite());
                if (state.canSurvive(level, pos)) {
                    return state.setValue(WATERLOGGED, replacedFluidState.is(Fluids.WATER));
                }
            }
        }

        return null;
    }

    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GuardianDonationBoxBlockEntity(pos, state);
    }

    protected InteractionResult useItemOn(ItemStack item, BlockState state, Level p_316177_, BlockPos p_316898_, Player p_316632_, InteractionHand p_316424_, BlockHitResult p_316345_) {
        BlockEntity var9 = p_316177_.getBlockEntity(p_316898_);
        if (var9 instanceof GuardianDonationBoxBlockEntity blockEntity) {
            if (p_316177_.isClientSide()) {
                return InteractionResult.SUCCESS;
            } else {
                ItemStack itemstack1 = blockEntity.getTheItem();
                if (item.isEmpty() || !itemstack1.isEmpty() && (!ItemStack.isSameItemSameComponents(itemstack1, item) || itemstack1.getCount() >= itemstack1.getMaxStackSize())) {
                    return InteractionResult.TRY_WITH_EMPTY_HAND;
                } else {
                    p_316632_.awardStat(Stats.ITEM_USED.get(item.getItem()));
                    ItemStack itemstack = item.consumeAndReturn(1, p_316632_);
                    float f;
                    if (blockEntity.isEmpty()) {
                        blockEntity.setTheItem(itemstack);
                        f = (float)itemstack.getCount() / (float)itemstack.getMaxStackSize();
                    } else {
                        itemstack1.grow(1);
                        f = (float)itemstack1.getCount() / (float)itemstack1.getMaxStackSize();
                    }

                    p_316177_.playSound(null, p_316898_, SoundEvents.DECORATED_POT_INSERT, SoundSource.BLOCKS, 1.0F, 0.7F + 0.5F * f);
                    if (p_316177_ instanceof ServerLevel) {
                        ServerLevel serverLevel = (ServerLevel)p_316177_;
                        serverLevel.sendParticles(ParticleTypes.DUST_PLUME, (double)p_316898_.getX() + 0.5, (double)p_316898_.getY() + 0.85, (double)p_316898_.getZ() + 0.5, 7, 0.0, 0.0, 0.0, 0.0);
                    }

                    blockEntity.setChanged();
                    p_316177_.gameEvent(p_316632_, GameEvent.BLOCK_CHANGE, p_316898_);
                    return InteractionResult.SUCCESS;
                }
            }
        } else {
            return InteractionResult.PASS;
        }
    }

    protected InteractionResult useWithoutItem(BlockState state, Level p_316544_, BlockPos p_316541_, Player p_316732_, BlockHitResult p_316860_) {
        BlockEntity var7 = p_316544_.getBlockEntity(p_316541_);
        if (var7 instanceof GuardianDonationBoxBlockEntity) {
            p_316544_.playSound(null, p_316541_, SoundEvents.DECORATED_POT_INSERT_FAIL, SoundSource.BLOCKS, 1.0F, 1.0F);
            p_316544_.gameEvent(p_316732_, GameEvent.BLOCK_CHANGE, p_316541_);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean p_393685_) {
        Containers.updateNeighboursAfterDestroy(state, level, pos);
    }

    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder lootParams) {
        return super.getDrops(state, lootParams);
    }

    protected ItemStack getCloneItemStack(LevelReader p_304622_, BlockPos p_294412_, BlockState p_294723_, boolean p_387769_) {
        return super.getCloneItemStack(p_304622_, p_294412_, p_294723_, p_387769_);
    }

    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos, Direction direction) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
    }
}