package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.BlockPlacementUtil;
import com.aetherteam.aetherii.world.feature.configuration.CoastConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class CoastFeature extends Feature<CoastConfiguration> {
    public CoastFeature(Codec<CoastConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<CoastConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos pos = context.origin();
        CoastConfiguration config = context.config();

        for (int x = pos.getX(); x < pos.getX() + 16; ++x) {
            for (int z = pos.getZ(); z < pos.getZ() + 16; ++z) {
                for (int y = config.yRange().getMinValue(); y < config.yRange().getMaxValue(); ++y) {
                    BlockPos placementPos = new BlockPos(x, y, z);
                    BlockPos voidPos = new BlockPos(x, y - 16, z);
                    if (level.getBlockState(placementPos).isAir() && level.getBlockState(voidPos).isAir() && level.getBlockState(placementPos.above()).is(config.validBlocks()) && level.getBlockState(placementPos.above(2)).isAir()) {
                        BlockPlacementUtil.placeDisk(level, config.block(), placementPos, config.radiusTop().sample(random), random);
                        BlockPlacementUtil.placeDisk(level, config.block(), placementPos.below(), config.radiusBottom().sample(random), random);
                        break;
                    }
                }
            }
        }
        return true;
    }
}