package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.BlockPlacementUtil;
import com.aetherteam.aetherii.world.feature.configuration.ArcticIceSpikeConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class ArcticIceSpikeFeature extends Feature<ArcticIceSpikeConfiguration> {

    public ArcticIceSpikeFeature(Codec<ArcticIceSpikeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ArcticIceSpikeConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos pos = context.origin();
        ArcticIceSpikeConfiguration config = context.config();

        float radius = random.nextInt(config.additionalRadius()) + config.baseRadius() * 2;
        float slopeIntensity = random.nextInt(config.additionalSlopeIntensity()) + config.baseSlopeIntensity() / 10;

        if (random.nextBoolean()) { // Used for randomizing the rotation
            if (random.nextBoolean()) {
                for (int i = (int) radius; i > 0; --i) {  // Used for making the upper portion
                    BlockPlacementUtil.placeDisk(level, config.block(), new BlockPos((int) ((pos.getX() + i / slopeIntensity) - radius / slopeIntensity), (int) (pos.getY() - i + radius), (int) ((pos.getZ() + i / slopeIntensity) - radius / slopeIntensity)), (float) i / 2, random, false);
                }
                for (int i = (int) radius; i > 0; --i) {  // Used for making the lower portion by inverting the upper spike
                    BlockPlacementUtil.placeDisk(level, config.block(), new BlockPos((int) ((pos.getX() - i / slopeIntensity) + radius / slopeIntensity), (int) (pos.getY() + i - radius), (int) ((pos.getZ() - i / slopeIntensity) + radius / slopeIntensity)), (float) i / 2, random, false);
                }
            } else {
                for (int i = (int) radius; i > 0; --i) {
                    BlockPlacementUtil.placeDisk(level, config.block(), new BlockPos((int) ((pos.getX() - i / slopeIntensity) + radius / slopeIntensity), (int) (pos.getY() - i + radius), (int) ((pos.getZ() + i / slopeIntensity) - radius / slopeIntensity)), (float) i / 2, random, false);
                }
                for (int i = (int) radius; i > 0; --i) {
                    BlockPlacementUtil.placeDisk(level, config.block(), new BlockPos((int) ((pos.getX() + i / slopeIntensity) - radius / slopeIntensity), (int) (pos.getY() + i - radius), (int) ((pos.getZ() - i / slopeIntensity) + radius / slopeIntensity)), (float) i / 2, random, false);
                }
            }
        } else {
            if (random.nextBoolean()) {
                for (int i = (int) radius; i > 0; --i) {
                    BlockPlacementUtil.placeDisk(level, config.block(), new BlockPos((int) ((pos.getX() - i / slopeIntensity) + radius / slopeIntensity), (int) (pos.getY() - i + radius), (int) ((pos.getZ() - i / slopeIntensity) + radius / slopeIntensity)), (float) i / 2, random, false);
                }
                for (int i = (int) radius; i > 0; --i) {
                    BlockPlacementUtil.placeDisk(level, config.block(), new BlockPos((int) ((pos.getX() + i / slopeIntensity) - radius / slopeIntensity), (int) (pos.getY() + i - radius), (int) ((pos.getZ() + i / slopeIntensity) - radius / slopeIntensity)), (float) i / 2, random, false);
                }
            } else {
                for (int i = (int) radius; i > 0; --i) {
                    BlockPlacementUtil.placeDisk(level, config.block(), new BlockPos((int) ((pos.getX() - i / slopeIntensity) + radius / slopeIntensity), (int) (pos.getY() - i + radius), (int) ((pos.getZ() + i / slopeIntensity) - radius / slopeIntensity)), (float) i / 2, random, false);
                }
                for (int i = (int) radius; i > 0; --i) {
                    BlockPlacementUtil.placeDisk(level, config.block(), new BlockPos((int) ((pos.getX() + i / slopeIntensity) - radius / slopeIntensity), (int) (pos.getY() + i - radius), (int) ((pos.getZ() - i / slopeIntensity) + radius / slopeIntensity)), (float) i / 2, random, false);
                }
            }
        }
        return true;
    }
}