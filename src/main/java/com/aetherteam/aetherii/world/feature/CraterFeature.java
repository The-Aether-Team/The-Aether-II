package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.aetherteam.aetherii.world.feature.configuration.CraterConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class CraterFeature extends Feature<CraterConfiguration> {
    public CraterFeature(Codec<CraterConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<CraterConfiguration> context) {
        BlockPos pos = context.origin();
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        CraterConfiguration config = context.config();

        int radius = config.radius().sample(random);
        for (int x = -radius; x < radius; x++) {
            for (int z = -radius; z < radius; z++) {
                for (int y = -radius; y < radius; y++) {
                    int volume = x * x + y * y + z * z;
                    int radiusSquared = (radius - 1) * (radius - 1);
                    int radiusOutlineSquared = radius * radius;

                    DensityFunction noise = config.noise();
                    DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(context.level().getSeed());
                    noise.mapAll(visitor);

                    int density = (int) noise.compute(new DensityFunction.SinglePointContext(x, y, z));

                    if (volume <= radiusOutlineSquared) {
                        BlockPos offsetPos = pos.offset(x, y, z);
                        if (y < 0) {
                            if (volume >= radiusSquared) {
                                if (!level.getBlockState(offsetPos).is(config.interiorBlock().getState(random, offsetPos).getBlock())) {
                                    level.setBlock(offsetPos, config.exteriorBlock().getState(random, offsetPos), 3);
                                }
                            } else {
                                level.setBlock(offsetPos, config.interiorBlock().getState(random, offsetPos), 3);
                            }
                        } else {
                            level.setBlock(offsetPos, Blocks.AIR.defaultBlockState(), 3);
                        }
                        if (x == 0 && z == 0 && y == -radius + 2) {
                            level.setBlock(offsetPos, config.craterBlock().getState(random, offsetPos), 3);
                        }
                    }
                }
            }
        }

        return true;
    }
}