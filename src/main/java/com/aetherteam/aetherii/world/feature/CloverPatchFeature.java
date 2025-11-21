package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;

public class CloverPatchFeature extends Feature<CountConfiguration> {
    public CloverPatchFeature(Codec<CountConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<CountConfiguration> context) {
        int i = 0;
        RandomSource random = context.random();
        WorldGenLevel level = context.level();
        BlockPos originPos = context.origin();
        int j = context.config().count().sample(random);

        for (int k = 0; k < j; ++k) {
//            int xOffset = random.nextInt(8) - random.nextInt(8);
//            int zOffset = random.nextInt(8) - random.nextInt(8);
//            int j1 = level.getHeight(Heightmap.Types.OCEAN_FLOOR, originPos.getX() + xOffset, originPos.getZ() + zOffset);
//            BlockPos offsetPos = new BlockPos(originPos.getX() + xOffset, j1, originPos.getZ() + zOffset);
            BlockState state = AetherIIBlocks.AETHER_CLOVER_TALL.get().defaultBlockState();
            if (state.canSurvive(level, originPos)) {
                if (level.getBlockState(originPos).is(Blocks.WATER) && level.getBlockState(originPos.above()).isAir()) {
                    level.setBlock(originPos, state.setValue(BlockStateProperties.WATERLOGGED, true), 3);
                } else {
                    if (random.nextBoolean()) {
                        state = AetherIIBlocks.AETHER_CLOVER.get().defaultBlockState();
                    }
                    level.setBlock(originPos, state, 3);
                }
                ++i;
            }
        }
        return i > 0;
    }
}
