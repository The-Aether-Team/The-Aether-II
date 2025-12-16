package com.aetherteam.aetherii.block.dungeon;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.blockentity.CopyBlockEntity;
import com.aetherteam.aetherii.blockentity.LockedBlockEntity;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
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
        ItemStack stack = context.getItemInHand();
        BlockState placementState = this.defaultBlockState().setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
        if (stack.get(AetherIIDataComponents.BLOCK_STATE) != null) {
            placementState = placementState.setValue(EMPTY, false);
        }
        return placementState;
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
        if (level.getBlockEntity(pos) instanceof CopyBlockEntity blockEntity) {
            return blockEntity.getItem();
        } else {
            return super.getCloneItemStack(level, pos, state, includeData);
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
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (!state.getValue(EMPTY)) {
            if (level.getBlockEntity(pos) instanceof CopyBlockEntity blockEntity && blockEntity.getCopyState() != null) {
                return blockEntity.getCopyState().getShape(level, pos);
            }
        }
        return super.getShape(state, level, pos, context);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        if (!state.getValue(EMPTY)) {
            if (level.getBlockEntity(pos) instanceof CopyBlockEntity blockEntity && blockEntity.getCopyState() != null) {
                return blockEntity.getCopyState().getLightEmission(level, pos);
            }
        }
        return super.getLightEmission(state, level, pos);
    }

    @Override
    public MapColor getMapColor(BlockState state, BlockGetter level, BlockPos pos, MapColor defaultColor) {
        if (!state.getValue(EMPTY)) {
            if (level.getBlockEntity(pos) instanceof CopyBlockEntity blockEntity && blockEntity.getCopyState() != null) {
                return blockEntity.getCopyState().getMapColor(level, pos);
            }
        }
        return super.getMapColor(state, level, pos, defaultColor);
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
    protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        if (!state.getValue(EMPTY)) {
            if (level.getBlockEntity(pos) instanceof CopyBlockEntity blockEntity && blockEntity.getCopyState() != null) {
                return blockEntity.getCopyState().getShadeBrightness(level, pos);
            }
        }
        return 1.0F;
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state) {
        if (!state.getValue(EMPTY)) {
            return super.getOcclusionShape(state);
        }
        return Shapes.empty();
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state) {
        if (!state.getValue(EMPTY)) {
            return super.propagatesSkylightDown(state);
        }
        return true;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        if (!state.getValue(EMPTY)) {
            return RenderShape.MODEL;
        }
        return RenderShape.INVISIBLE;
    }
}
