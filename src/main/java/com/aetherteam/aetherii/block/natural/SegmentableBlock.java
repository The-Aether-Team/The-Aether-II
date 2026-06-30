package com.aetherteam.aetherii.block.natural;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Function;

public interface SegmentableBlock {
    int MAX_SEGMENTS = 4;

    IntegerProperty getSegmentAmountProperty();

    default boolean canBeReplaced(BlockState state, BlockPlaceContext context, IntegerProperty amountProperty) {
        return !context.isSecondaryUseActive() && context.getItemInHand().is(state.getBlock().asItem()) && state.getValue(amountProperty) < MAX_SEGMENTS;
    }

    default BlockState getStateForPlacement(BlockPlaceContext context, Block block, IntegerProperty amountProperty, EnumProperty<Direction> facingProperty) {
        BlockState existingState = context.getLevel().getBlockState(context.getClickedPos());
        if (existingState.is(block)) {
            return existingState.setValue(amountProperty, Math.min(MAX_SEGMENTS, existingState.getValue(amountProperty) + 1));
        }
        return block.defaultBlockState().setValue(facingProperty, context.getHorizontalDirection().getOpposite()).setValue(amountProperty, 1);
    }

    default Function<BlockState, VoxelShape> getShapeCalculator(EnumProperty<Direction> facingProperty, IntegerProperty amountProperty) {
        return state -> {
            Direction direction = state.getValue(facingProperty);
            int amount = state.getValue(amountProperty);
            return switch (direction) {
                case SOUTH -> Block.box(2.0, 0.0, 2.0, amount >= 2 ? 14.0 : 8.0, 3.0, amount >= 3 ? 14.0 : 8.0);
                case WEST -> Block.box(amount >= 3 ? 2.0 : 8.0, 0.0, 2.0, 14.0, 3.0, amount >= 2 ? 14.0 : 8.0);
                case EAST -> Block.box(2.0, 0.0, amount >= 2 ? 2.0 : 8.0, amount >= 3 ? 14.0 : 8.0, 3.0, 14.0);
                default -> Block.box(amount >= 2 ? 2.0 : 8.0, 0.0, amount >= 3 ? 2.0 : 8.0, 14.0, 3.0, 14.0);
            };
        };
    }
}
