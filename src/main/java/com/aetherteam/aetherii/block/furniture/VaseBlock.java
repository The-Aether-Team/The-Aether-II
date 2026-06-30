package com.aetherteam.aetherii.block.furniture;

import com.aetherteam.aetherii.blockentity.VaseBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
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
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class VaseBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final EnumProperty<Direction> HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty CRACKED = BlockStateProperties.CRACKED;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape SHAPE = Block.box(3, 0.0, 3, 13, 11.0, 13);
public VaseBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(WATERLOGGED, false).setValue(CRACKED, false));
    }

    public BlockState updateShape(BlockState state, Direction p_276322_, BlockState p_276280_, LevelAccessor level, BlockPos pos, BlockPos p_276312_) {
        LevelAccessor p_374267_ = level;
        if (state.getValue(WATERLOGGED)) {
            p_374267_.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(state, p_276322_, p_276280_, level, pos, p_276312_);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection()).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER).setValue(CRACKED, false);
    }

    public InteractionResult use(BlockState state, Level p_316177_, BlockPos p_316898_, Player p_316632_, InteractionHand p_316424_, BlockHitResult p_316345_) {
        ItemStack item = p_316632_.getItemInHand(p_316424_);
        BlockEntity var9 = p_316177_.getBlockEntity(p_316898_);
        if (var9 instanceof VaseBlockEntity blockEntity) {
            if (p_316177_.isClientSide()) {
                return InteractionResult.SUCCESS;
            } else {
                ItemStack itemstack1 = blockEntity.getTheItem();
                if (item.isEmpty() || !itemstack1.isEmpty() && (!ItemStack.isSameItemSameTags(itemstack1, item) || itemstack1.getCount() >= itemstack1.getMaxStackSize())) {
                    p_316177_.playSound(null, p_316898_, SoundEvents.DECORATED_POT_HIT, SoundSource.BLOCKS, 1.0F, 1.0F);
                    blockEntity.wobble(VaseBlockEntity.WobbleStyle.NEGATIVE);
                    p_316177_.gameEvent(p_316632_, GameEvent.BLOCK_CHANGE, p_316898_);
                    return InteractionResult.CONSUME;
                } else {
                    blockEntity.wobble(VaseBlockEntity.WobbleStyle.POSITIVE);
                    p_316632_.awardStat(Stats.ITEM_USED.get(item.getItem()));
                    ItemStack itemstack = item.copyWithCount(1);
                    if (!p_316632_.getAbilities().instabuild) {
                        item.shrink(1);
                    }
                    float f;
                    if (blockEntity.isEmpty()) {
                        blockEntity.setTheItem(itemstack);
                        f = (float)itemstack.getCount() / (float)itemstack.getMaxStackSize();
                    } else {
                        itemstack1.grow(1);
                        f = (float)itemstack1.getCount() / (float)itemstack1.getMaxStackSize();
                    }

                    p_316177_.playSound(null, p_316898_, SoundEvents.CHISELED_BOOKSHELF_INSERT, SoundSource.BLOCKS, 1.0F, 0.7F + 0.5F * f);
                    if (p_316177_ instanceof ServerLevel) {
                        ServerLevel serverLevel = (ServerLevel)p_316177_;
                        serverLevel.sendParticles(ParticleTypes.POOF, (double)p_316898_.getX() + 0.5, (double)p_316898_.getY() + 0.85, (double)p_316898_.getZ() + 0.5, 7, 0.0, 0.0, 0.0, 0.0);
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

    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType pathComputationType) {
        return false;
    }

    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_273169_) {
        p_273169_.add(HORIZONTAL_FACING, WATERLOGGED, CRACKED);
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new VaseBlockEntity(pos, state);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            if (level.getBlockEntity(pos) instanceof VaseBlockEntity blockEntity) {
                Containers.dropContents(level, pos, blockEntity);
            }
            level.updateNeighbourForOutputSignal(pos, this);
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    public List<ItemStack> getDrops(BlockState state, LootParams.Builder lootParams) {
        return super.getDrops(state, lootParams);
    }

    public void playerWillDestroy(Level p_273590_, BlockPos p_273343_, BlockState p_272869_, Player player) {
        ItemStack itemstack = player.getMainHandItem();
        BlockState blockstate = p_272869_;
        if (itemstack.is(ItemTags.BREAKS_DECORATED_POTS)) {
            blockstate = p_272869_.setValue(CRACKED, true);
            p_273590_.setBlock(p_273343_, blockstate, 260);
        }

        super.playerWillDestroy(p_273590_, p_273343_, blockstate, player);
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public SoundType getSoundType(BlockState state) {
        return state.getValue(CRACKED) ? SoundType.DECORATED_POT_CRACKED : SoundType.DECORATED_POT;
    }

    public void onProjectileHit(Level level, BlockState state, BlockHitResult hitResult, Projectile projectile) {
        BlockPos pos = hitResult.getBlockPos();
        if (level instanceof ServerLevel serverLevel) {
            if (projectile.mayInteract(serverLevel, pos)) {
                level.setBlock(pos, state.setValue(CRACKED, true), 260);
                level.destroyBlock(pos, true, projectile);
            }
        }

    }

    public ItemStack getCloneItemStack(BlockGetter p_304622_, BlockPos p_294412_, BlockState p_294723_) {
        return super.getCloneItemStack(p_304622_, p_294412_, p_294723_);
    }

    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(HORIZONTAL_FACING, rotation.rotate(state.getValue(HORIZONTAL_FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(HORIZONTAL_FACING)));
    }
}
