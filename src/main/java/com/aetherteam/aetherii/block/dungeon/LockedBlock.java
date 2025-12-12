package com.aetherteam.aetherii.block.dungeon;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.blockentity.LockedBlockEntity;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class LockedBlock extends BaseEntityBlock {
    public static final MapCodec<LockedBlock> CODEC = simpleCodec(LockedBlock::new);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty EMPTY = AetherIIBlockStateProperties.EMPTY;

    @Override
    protected MapCodec<LockedBlock> codec() {
        return CODEC;
    }

    public LockedBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(EMPTY, true));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new LockedBlockEntity(blockPos, blockState);
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
        if (level.getBlockEntity(pos) instanceof LockedBlockEntity blockEntity) {
            return blockEntity.getItem();
        } else {
            return super.getCloneItemStack(level, pos, state, includeData);
        }
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.getItem() instanceof BlockItem blockItem) {
            BlockState mimicState = blockItem.getBlock().getStateForPlacement(new BlockPlaceContext(player, hand, stack, hitResult));
            if (mimicState != null && mimicState.getBlock() != this && mimicState.is(AetherIITags.Blocks.LOCKABLE_BLOCKS)) {
                if (level.getBlockEntity(pos) instanceof LockedBlockEntity lockedBlockEntity) {
                    lockedBlockEntity.setMimicState(mimicState);
                    lockedBlockEntity.requestModelDataUpdate();
                    level.setBlockAndUpdate(pos, state.setValue(EMPTY, false));
//                    level.sendBlockUpdated(pos, state, newState, Block.UPDATE_ALL);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos currentPos, Direction facing, BlockPos facingPos, BlockState facingState, RandomSource randomSource) {
        if (state.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }
        return super.updateShape(state, levelReader, scheduledTickAccess, currentPos, facing, facingPos, facingState, randomSource);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, EMPTY);
    }

    @Override
    public boolean hidesNeighborFace(BlockGetter level, BlockPos pos, BlockState state, BlockState neighborState, Direction dir) {
        if (!state.getValue(EMPTY)) {
            if (level.getBlockEntity(pos) instanceof LockedBlockEntity lockedBlockEntity && lockedBlockEntity.getMimicState() != null) {
                return lockedBlockEntity.getMimicState().hidesNeighborFace(level, pos, neighborState, dir);
            }
        }
        return super.hidesNeighborFace(level, pos, state, neighborState, dir);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (!state.getValue(EMPTY)) {
            if (level.getBlockEntity(pos) instanceof LockedBlockEntity lockedBlockEntity && lockedBlockEntity.getMimicState() != null) {
                return lockedBlockEntity.getMimicState().getShape(level, pos);
            }
        }
        return super.getShape(state, level, pos, context);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        if (!state.getValue(EMPTY)) {
            if (level.getBlockEntity(pos) instanceof LockedBlockEntity lockedBlockEntity && lockedBlockEntity.getMimicState() != null) {
                return lockedBlockEntity.getMimicState().getLightEmission(level, pos);
            }
        }
        return super.getLightEmission(state, level, pos);
    }

    @Override
    public MapColor getMapColor(BlockState state, BlockGetter level, BlockPos pos, MapColor defaultColor) {
        if (!state.getValue(EMPTY)) {
            if (level.getBlockEntity(pos) instanceof LockedBlockEntity lockedBlockEntity && lockedBlockEntity.getMimicState() != null) {
                return lockedBlockEntity.getMimicState().getMapColor(level, pos);
            }
        }
        return super.getMapColor(state, level, pos, defaultColor);
    }

    @Override
    protected void spawnDestroyParticles(Level level, Player player, BlockPos pos, BlockState state) {
        if (!state.getValue(EMPTY)) {
            if (level.getBlockEntity(pos) instanceof LockedBlockEntity lockedBlockEntity && lockedBlockEntity.getMimicState() != null) {
                super.spawnDestroyParticles(level, player, pos, state);
            }
        }
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state) {
        if (!state.getValue(EMPTY)) {
            return super.getOcclusionShape(state);
        }
        return Shapes.empty();
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.gameMode != null && minecraft.gameMode.getPlayerMode() == GameType.CREATIVE && minecraft.player != null && minecraft.level != null) {
            ItemStack itemStack = minecraft.player.getMainHandItem();
            Item item = itemStack.getItem();
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (item instanceof BlockItem blockItem && (blockEntity == null || !blockEntity.collectComponents().has(AetherIIDataComponents.BLOCK_STATE))) {
                if (blockItem.getBlock() == this) {
                    minecraft.level.addParticle(AetherIIParticleTypes.LOCKED_BLOCK.get(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0.0, 0.0, 0.0);
                }
            }
        }
    }
}
