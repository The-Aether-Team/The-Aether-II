package com.aetherteam.aetherii.block.furniture;

import com.aetherteam.aetherii.blockentity.VaseBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
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
import org.jspecify.annotations.Nullable;

import java.util.List;

public class VaseBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<VaseBlock> CODEC = simpleCodec(VaseBlock::new);
    public static final EnumProperty<Direction> HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty CRACKED = BlockStateProperties.CRACKED;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape SHAPE = Block.column(10.0, 0.0, 11.0);

    public MapCodec<VaseBlock> codec() {
        return CODEC;
    }

    public VaseBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(WATERLOGGED, false).setValue(CRACKED, false));
    }

    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess p_374267_, BlockPos pos, Direction p_276322_, BlockPos p_276312_, BlockState p_276280_, RandomSource p_374464_) {
        if (state.getValue(WATERLOGGED)) {
            p_374267_.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(state, level, p_374267_, pos, p_276322_, p_276312_, p_276280_, p_374464_);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection()).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER).setValue(CRACKED, false);
    }

    protected InteractionResult useItemOn(ItemStack item, BlockState state, Level p_316177_, BlockPos p_316898_, Player p_316632_, InteractionHand p_316424_, BlockHitResult p_316345_) {
        BlockEntity var9 = p_316177_.getBlockEntity(p_316898_);
        if (var9 instanceof VaseBlockEntity blockEntity) {
            if (p_316177_.isClientSide()) {
                return InteractionResult.SUCCESS;
            } else {
                ItemStack itemstack1 = blockEntity.getTheItem();
                if (item.isEmpty() || !itemstack1.isEmpty() && (!ItemStack.isSameItemSameComponents(itemstack1, item) || itemstack1.getCount() >= itemstack1.getMaxStackSize())) {
                    return InteractionResult.TRY_WITH_EMPTY_HAND;
                } else {
                    blockEntity.wobble(VaseBlockEntity.WobbleStyle.POSITIVE);
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
        if (var7 instanceof VaseBlockEntity blockEntity) {
            p_316544_.playSound(null, p_316541_, SoundEvents.DECORATED_POT_INSERT_FAIL, SoundSource.BLOCKS, 1.0F, 1.0F);
            blockEntity.wobble(VaseBlockEntity.WobbleStyle.NEGATIVE);
            p_316544_.gameEvent(p_316732_, GameEvent.BLOCK_CHANGE, p_316541_);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    protected VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_273169_) {
        p_273169_.add(HORIZONTAL_FACING, WATERLOGGED, CRACKED);
    }

    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new VaseBlockEntity(pos, state);
    }

    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean p_393685_) {
        Containers.updateNeighboursAfterDestroy(state, level, pos);
    }

    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder lootParams) {
        return super.getDrops(state, lootParams);
    }

    public BlockState playerWillDestroy(Level p_273590_, BlockPos p_273343_, BlockState p_272869_, Player player) {
        ItemStack itemstack = player.getMainHandItem();
        BlockState blockstate = p_272869_;
        if (itemstack.is(ItemTags.BREAKS_DECORATED_POTS) && !EnchantmentHelper.hasTag(itemstack, EnchantmentTags.PREVENTS_DECORATED_POT_SHATTERING)) {
            blockstate = p_272869_.setValue(CRACKED, true);
            p_273590_.setBlock(p_273343_, blockstate, 260);
        }

        return super.playerWillDestroy(p_273590_, p_273343_, blockstate, player);
    }

    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    protected SoundType getSoundType(BlockState state) {
        return state.getValue(CRACKED) ? SoundType.DECORATED_POT_CRACKED : SoundType.DECORATED_POT;
    }

    protected void onProjectileHit(Level level, BlockState state, BlockHitResult hitResult, Projectile projectile) {
        BlockPos pos = hitResult.getBlockPos();
        if (level instanceof ServerLevel serverLevel) {
            if (projectile.mayInteract(serverLevel, pos) && projectile.mayBreak(serverLevel)) {
                level.setBlock(pos, state.setValue(CRACKED, true), 260);
                level.destroyBlock(pos, true, projectile);
            }
        }

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

    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(HORIZONTAL_FACING, rotation.rotate(state.getValue(HORIZONTAL_FACING)));
    }

    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(HORIZONTAL_FACING)));
    }
}
