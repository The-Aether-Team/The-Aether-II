package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AetherTallGrassBlock extends TallGrassBlock {
    protected static final VoxelShape SHORT_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 7.0, 14.0);
    protected static final VoxelShape MEDIUM_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 10.0, 14.0);
    protected static final VoxelShape LONG_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

    public AetherTallGrassBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.is(AetherIIBlocks.AETHER_SHORT_GRASS)) {
            return SHORT_SHAPE;
        } else if (state.is(AetherIIBlocks.AETHER_MEDIUM_GRASS)) {
            return MEDIUM_SHAPE;
        }
        return LONG_SHAPE;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        if (state.is(AetherIIBlocks.AETHER_SHORT_GRASS)) {
            level.setBlock(pos, AetherIIBlocks.AETHER_MEDIUM_GRASS.get().defaultBlockState(), 2);
        } else if (state.is(AetherIIBlocks.AETHER_MEDIUM_GRASS)) {
            level.setBlock(pos, AetherIIBlocks.AETHER_LONG_GRASS.get().defaultBlockState(), 2);
        }
    }
}
