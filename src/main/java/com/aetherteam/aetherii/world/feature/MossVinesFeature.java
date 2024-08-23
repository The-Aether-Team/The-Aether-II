package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.BottomedVineBlock;
import com.aetherteam.aetherii.world.feature.configuration.MossVinesConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class MossVinesFeature extends Feature<MossVinesConfiguration> {
    public MossVinesFeature(Codec<MossVinesConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<MossVinesConfiguration> context) {
        WorldGenLevel worldgenlevel = context.level();
        BlockPos blockpos = context.origin();
        RandomSource random = context.random();
        if (worldgenlevel.isEmptyBlock(blockpos)) {
            Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
            if (BottomedVineBlock.isAcceptableNeighbour(worldgenlevel, blockpos.relative(direction), direction)) {
                BlockState aboveState = worldgenlevel.getBlockState(blockpos.above());
                BlockState blockState = context.config().blockStateProvider().getState(random, blockpos);
                blockState = blockState.setValue(VineBlock.getPropertyForFace(direction), true);
                if ((worldgenlevel.getBlockState(blockpos.relative(direction)).is(AetherIIBlocks.BRYALINN_MOSS_BLOCK)
                        || worldgenlevel.getBlockState(blockpos.above().relative(direction)).is(AetherIIBlocks.BRYALINN_MOSS_BLOCK)
                        || worldgenlevel.getBlockState(blockpos.above().relative(direction)).is(AetherIIBlocks.BRYALINN_MOSS_CARPET))
                        && random.nextInt(4) == 0) {
                    blockState = blockState.setValue(BottomedVineBlock.AGE, 25);
                } else {
                    blockState = blockState.setValue(BottomedVineBlock.AGE, 20 + random.nextInt(5));
                }
                if (!aboveState.is(blockState.getBlock()) || aboveState.getValue(BottomedVineBlock.AGE) < 25) {
                    addHangingVine(blockpos, blockState, worldgenlevel);
                    return true;
                }
            }
        }
        return false;
    }

    private static void addHangingVine(BlockPos pos, BlockState blockState, WorldGenLevel worldgenlevel) {
        worldgenlevel.setBlock(pos, blockState, 2);
        int i = 10;

        for (BlockPos blockpos = pos.below(); worldgenlevel.isEmptyBlock(blockpos) && i > 0; i--) {
            if (blockState.getValue(BottomedVineBlock.AGE) + 1 <= 25) {
                blockState = blockState.setValue(BottomedVineBlock.AGE, blockState.getValue(BottomedVineBlock.AGE) + 1);
                worldgenlevel.setBlock(blockpos, blockState, 2);
                blockpos = blockpos.below();
            } else {
                break;
            }
        }
    }
}
