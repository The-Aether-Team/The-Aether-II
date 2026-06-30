package com.aetherteam.aetherii.block.dungeon;

import com.aetherteam.aetherii.blockentity.GuardianDonationBoxBlockEntity;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class GuardianDonationBoxBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final Map<Direction, VoxelShape> SHAPES = com.aetherteam.aetherii.block.AetherIIShapes.rotateHorizontal(Shapes.or(
            Block.box(5.0F, 0.0F, 3.0F, 11.0F, 1.0F, 13.0F),
            Block.box(4.0F, 1.0F, 3.0F, 12.0F, 4.0F, 13.0F),
            Block.box(3.0F, 4.0F, 2.0F, 13.0F, 12.0F, 14.0F)
    ));
public GuardianDonationBoxBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction directionToNeighbour, BlockState neighbourState, LevelAccessor level, BlockPos pos, BlockPos neighbourPos) {
        LevelAccessor ticks = level;
        if (directionToNeighbour.getOpposite() == state.getValue(FACING) && !state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            if (state.getValue(WATERLOGGED)) {
                ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
            }

            return super.updateShape(state, directionToNeighbour, neighbourState, level, pos, neighbourPos);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
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

        for (Direction direction : context.getNearestLookingDirections()) {
            if (direction.getAxis().isHorizontal()) {
                state = state.setValue(FACING, direction.getOpposite());
                if (state.canSurvive(level, pos)) {
                    return state.setValue(WATERLOGGED, replacedFluidState.is(Fluids.WATER));
                }
            }
        }

        return null;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GuardianDonationBoxBlockEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (level.getBlockEntity(pos) instanceof GuardianDonationBoxBlockEntity blockEntity) {
            if (level.isClientSide()) {
                return InteractionResult.SUCCESS;
            } else {
                ItemStack storedItem = blockEntity.getTheItem();
                if (itemStack.isEmpty() || !storedItem.isEmpty() && (!ItemStack.isSameItemSameTags(storedItem, itemStack) || storedItem.getCount() >= storedItem.getMaxStackSize())) {
                    level.playSound(null, pos, AetherIISoundEvents.BLOCK_GUARDIAN_DONATION_BOX_INSERT_FAIL.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                    return InteractionResult.CONSUME;
                } else {
                    player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
                    ItemStack itemstack = itemStack.copyWithCount(1);
                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }
                    float count;
                    if (blockEntity.isEmpty()) {
                        blockEntity.setTheItem(itemstack);
                        count = (float) itemstack.getCount() / (float) itemstack.getMaxStackSize();
                    } else {
                        storedItem.grow(1);
                        count = (float) storedItem.getCount() / (float) storedItem.getMaxStackSize();
                    }

                    level.playSound(null, pos, AetherIISoundEvents.BLOCK_GUARDIAN_DONATION_BOX_INSERT.get(), SoundSource.BLOCKS, 1.0F, 0.7F + 0.5F * count);
                    if (level instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.POOF, pos.getX() + 0.5, pos.getY() + 0.85, pos.getZ() + 0.5, 7, 0.0, 0.0, 0.0, 0.0);
                    }

                    blockEntity.setChanged();
                    level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.use(state, level, pos, player, hand, hitResult);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType pathComputationType) {
        return false;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
    }
}
