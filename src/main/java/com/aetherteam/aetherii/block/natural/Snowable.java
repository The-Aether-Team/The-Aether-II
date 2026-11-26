package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface Snowable {
    IntegerProperty SNOW_LAYERS = AetherIIBlockStateProperties.SNOW_LAYERS;
    VoxelShape[] SHAPES = Block.boxes(8, (height) -> Block.column(16.0F, 0.0F, height * 2));

    default InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult, InteractionResult interactionResult) {
        if (itemStack.getItem() instanceof BlockItem blockItem && level.isUnobstructed(state, pos, CollisionContext.placementContext(player))) {
            if (blockItem.getBlock() == AetherIIBlocks.ARCTIC_SNOW.get() && state.getBlock() instanceof Snowable snowable && state.getValue(Snowable.SNOW_LAYERS) < 8 && (state.getValue(Snowable.SNOW_LAYERS) == 0 || hitResult.getDirection() == Direction.UP)) {
                level.setBlock(pos, snowable.setSnowy(state, Math.min(state.getValue(Snowable.SNOW_LAYERS) + 1, 8)), 1 | 2);
                level.playSound(null, pos, blockItem.getBlock().getSoundType(blockItem.getBlock().defaultBlockState(), level, pos, player).getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            } else if (blockItem.getBlock() instanceof Snowable snowable && AetherGrassBlock.plantIsSnowed(state) && state.canBeReplaced() && state.getBlock() != blockItem.getBlock()) {
                BlockState blockItemState = blockItem.getBlock().getStateForPlacement(new BlockPlaceContext(level, player, hand, itemStack, hitResult));
                if (blockItemState != null) {
                    level.setBlock(pos, snowable.setSnowy(blockItemState, state.getValue(Snowable.SNOW_LAYERS)), 1 | 2);
                    level.playSound(null, pos, blockItem.getBlock().getSoundType(blockItemState, level, pos, player).getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return interactionResult;
    }

    default boolean isRandomlyTicking(BlockState state, boolean ticking) {
        return ticking && this.isSnowy(state);
    }

    default void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.getBrightness(LightLayer.BLOCK, pos) > 11) {
            level.setBlock(pos, state.setValue(SNOW_LAYERS, 0), 1 | 2);
        }
    }

    default void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        if (this.isSnowy(state)) {
            level.setBlock(pos, AetherIIBlocks.ARCTIC_SNOW.get().defaultBlockState().setValue(ArcticSnowLayerBlock.LAYERS, state.getValue(SNOW_LAYERS)), 1 | 2);
        }
    }

    default void onBlockExploded(BlockState state, ServerLevel level, BlockPos pos, Explosion explosion) {
        if (this.isSnowy(state)) {
            level.setBlock(pos, AetherIIBlocks.ARCTIC_SNOW.get().defaultBlockState().setValue(ArcticSnowLayerBlock.LAYERS, state.getValue(SNOW_LAYERS)), 1 | 2);
        }
    }

    default boolean isPathfindable(BlockState state, PathComputationType type, boolean pathfindable) {
        return pathfindable && type == PathComputationType.LAND && state.getValue(SNOW_LAYERS) < 5;
    }

    default VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, VoxelShape shape) {
        return Shapes.or(shape, SHAPES[state.getValue(SNOW_LAYERS)]);
    }

    default VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, VoxelShape shape) {
        return Shapes.or(shape, SHAPES[Math.max(0, state.getValue(SNOW_LAYERS) - 1)]);
    }

    default VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos, VoxelShape shape) {
        return Shapes.or(shape, SHAPES[state.getValue(SNOW_LAYERS)]);
    }

    default VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, VoxelShape shape) {
        return Shapes.or(shape, SHAPES[state.getValue(SNOW_LAYERS)]);
    }

    default float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getValue(SNOW_LAYERS) == 8 ? 0.2F : 1.0F;
    }

    default boolean isSnowy(BlockState blockState) {
        return blockState.getValue(SNOW_LAYERS) > 0;
    }

    default BlockState setSnowy(BlockState blockState, int layer) {
        return blockState.setValue(SNOW_LAYERS, layer);
    }
}
