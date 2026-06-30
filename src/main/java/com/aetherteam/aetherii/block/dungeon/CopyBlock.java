package com.aetherteam.aetherii.block.dungeon;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.blockentity.CopyBlockEntity;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
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

public abstract class CopyBlock extends BaseEntityBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty EMPTY = AetherIIBlockStateProperties.EMPTY;

    public CopyBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(EMPTY, true));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.isCreative()) {
            if (stack.getItem() instanceof BlockItem blockItem) {
                BlockState copyState = blockItem.getBlock().getStateForPlacement(new BlockPlaceContext(player, hand, stack, hitResult));
                if (copyState != null && copyState.getBlock() != this && copyState.is(AetherIITags.Blocks.COPYABLE_DUNGEON_BLOCKS)) {
                    if (level.getBlockEntity(pos) instanceof CopyBlockEntity blockEntity) {
                        BlockState newState = state.setValue(CopyBlock.EMPTY, false);
                        this.setCopyBlocksInfo(level, pos, state, copyState, newState, blockEntity);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return super.use(state, level, pos, player, hand, hitResult);
    }

    protected void setCopyBlocksInfo(Level level, BlockPos pos, BlockState state, BlockState copyState, BlockState newState, CopyBlockEntity blockEntity) {
        blockEntity.setCopyState(copyState);
        level.setBlockAndUpdate(pos, newState);
        level.blockEvent(pos, newState.getBlock(), 1, 0);
        state.initCache();
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor levelReader, BlockPos currentPos, BlockPos facingPos) {
        LevelAccessor scheduledTickAccess = levelReader;
        if (!state.getValue(EMPTY)) {
            if (levelReader.getBlockEntity(currentPos) instanceof CopyBlockEntity blockEntity && blockEntity.getCopyState() != null) {
                blockEntity.setChanged();
            }
        } else {
            if (state.getValue(WATERLOGGED)) {
                scheduledTickAccess.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
            }
        }
        return super.updateShape(state, facing, facingState, levelReader, currentPos, facingPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(EMPTY) && state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        ItemStack stack = context.getItemInHand();
        BlockState placementState = this.defaultBlockState();
        if (AetherIIDataComponents.get(stack, AetherIIDataComponents.BLOCK_STATE) != null) {
            placementState = placementState.setValue(EMPTY, false);
        } else {
            placementState = placementState.setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
        }
        return placementState;
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        if (level.getBlockEntity(pos) instanceof CopyBlockEntity blockEntity) {
            return blockEntity.getItem();
        } else {
            return super.getCloneItemStack(level, pos, state);
        }
    }

    @Override
    protected void spawnDestroyParticles(Level level, Player player, BlockPos pos, BlockState state) {
        if (!state.getValue(EMPTY)) {
            if (level.getBlockEntity(pos) instanceof CopyBlockEntity blockEntity && blockEntity.getCopyState() != null) {
                super.spawnDestroyParticles(level, player, pos, state);
            }
        }
    }

    @Override
    public boolean hidesNeighborFace(BlockGetter level, BlockPos pos, BlockState state, BlockState neighborState, Direction dir) {
        if (!state.getValue(EMPTY)) {
            if (level.getBlockEntity(pos) instanceof CopyBlockEntity blockEntity && blockEntity.getCopyState() != null) {
                return blockEntity.getCopyState().hidesNeighborFace(level, pos, neighborState, dir);
            }
        }
        return super.hidesNeighborFace(level, pos, state, neighborState, dir);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (!state.getValue(EMPTY)) {
            if (level.getBlockEntity(pos) instanceof CopyBlockEntity blockEntity && blockEntity.getCopyState() != null) {
                return blockEntity.getCopyState().getShape(level, pos);
            }
        }
        return super.getShape(state, level, pos, context);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        if (!state.getValue(EMPTY)) {
            return super.getOcclusionShape(state, level, pos);
        }
        return Shapes.empty();
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        if (!state.getValue(EMPTY)) {
            return RenderShape.MODEL;
        }
        return RenderShape.INVISIBLE;
    }

    public boolean hasDynamicLightEmission(BlockState state) {
        return true;
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        if (!state.getValue(EMPTY)) {
            if (level.getBlockEntity(pos) instanceof CopyBlockEntity blockEntity && blockEntity.getCopyState() != null) {
                return blockEntity.getCopyState().getLightEmission();
            }
        }
        return 0;
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        if (!state.getValue(EMPTY)) {
            if (level.getBlockEntity(pos) instanceof CopyBlockEntity blockEntity && blockEntity.getCopyState() != null) {
                return blockEntity.getCopyState().getShadeBrightness(level, pos);
            }
        }
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, net.minecraft.world.level.BlockGetter level, net.minecraft.core.BlockPos pos) {
        if (!state.getValue(EMPTY)) {
            return super.propagatesSkylightDown(state, level, pos);
        }
        return true;
    }

    @Override
    public MapColor getMapColor(BlockState state, BlockGetter level, BlockPos pos, MapColor defaultColor) {
        if (!state.getValue(EMPTY)) {
            if (level.getBlockEntity(pos) instanceof CopyBlockEntity blockEntity && blockEntity.getCopyState() != null) {
                return blockEntity.getCopyState().getMapColor(level, pos);
            }
        }
        return defaultColor;
    }

    @Override
    public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        if (!state.getValue(EMPTY)) {
            if (level.getBlockEntity(pos) instanceof CopyBlockEntity blockEntity && blockEntity.getCopyState() != null) {
                return blockEntity.getCopyState().getSoundType(level, pos, entity);
            }
        }
        return super.getSoundType(state, level, pos, entity);
    }
}
