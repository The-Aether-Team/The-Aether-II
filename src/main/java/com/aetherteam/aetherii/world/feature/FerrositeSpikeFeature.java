package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.BlockPlacementUtil;
import com.aetherteam.aetherii.world.feature.configuration.FerrositeSpikeConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class FerrositeSpikeFeature extends Feature<FerrositeSpikeConfiguration> {

    public FerrositeSpikeFeature(Codec<FerrositeSpikeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<FerrositeSpikeConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos pos = context.origin().below(2);
        FerrositeSpikeConfiguration config = context.config();

        float radius = random.nextInt(config.additionalRadius()) + config.baseRadius();
        float heightFactor = 5.5F + random.nextInt(4);

        for (int i = 0; i < radius * heightFactor; ++i) {
            if (i < radius * heightFactor - heightFactor * 0.5F) {
                BlockPlacementUtil.placeDisk(level, config.block(), new BlockPos(pos.getX(), pos.getY() + i, pos.getZ()), radius - (float) i / 20 - shapeVariator(random, 0.05F), random, true);
            }
            if (i == radius * heightFactor - heightFactor * 0.5F) {
                BlockPlacementUtil.placeDisk(level, config.block(), new BlockPos(pos.getX() + random.nextIntBetweenInclusive(-1, 1), pos.getY() + i, pos.getZ() + random.nextIntBetweenInclusive(-1, 1)), radius - (float) i / 10, random, true);
            }
        }

        if (random.nextBoolean()) {
            placeSideSpike(context, new BlockPos(pos.getX() + random.nextInt(3) + 2, pos.getY() - random.nextInt(2) - 1, pos.getZ() + random.nextInt(3) + 2));
        }
        if (random.nextBoolean()) {
            placeSideSpike(context, new BlockPos(pos.getX() - random.nextInt(3) + 2, pos.getY() - random.nextInt(2) - 1, pos.getZ() - random.nextInt(3) + 2));
        }
        if (random.nextInt(2) == 0) {
            placeSideSpike(context, new BlockPos(pos.getX() + random.nextInt(3) + 2, pos.getY() - random.nextInt(2) - 1, pos.getZ() + random.nextInt(3) + 2));
        }
        if (random.nextInt(2) == 0) {
            placeSideSpike(context, new BlockPos(pos.getX() - random.nextInt(3) + 2, pos.getY() - random.nextInt(2) - 1, pos.getZ() - random.nextInt(3) + 2));
        }


        return true;
    }

    public void placeSideSpike(FeaturePlaceContext<FerrositeSpikeConfiguration> context, BlockPos pos) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        FerrositeSpikeConfiguration config = context.config();

        float radius = random.nextInt(config.additionalRadius()) + config.baseRadius();
        float heightFactor = 3.5F + random.nextInt(2);

        for (int i = 0; i < radius * heightFactor; ++i) {
            if (i < radius * heightFactor - heightFactor * 0.5F) {
                BlockPlacementUtil.placeDisk(level, config.block(), new BlockPos(pos.getX(), pos.getY() + i, pos.getZ()), radius - (float) i / 12 - shapeVariator(random, 0.035F), random, true);
            }
            if (i == radius * heightFactor - heightFactor * 0.5F) {
                BlockPlacementUtil.placeDisk(level, config.block(), new BlockPos(pos.getX() + random.nextIntBetweenInclusive(-1, 1), pos.getY() + i, pos.getZ() + random.nextIntBetweenInclusive(-1, 1)), radius - (float) i / 6, random, true);
            }
        }
    }

    private float shapeVariator(RandomSource random, float value) {
        if (random.nextInt(16) == 5) {
            return value * 1.5F;
        }
        else if (random.nextInt(12) == 5) {
            return value * 1.25F;
        } else {
            return value;
        }
    }
}