package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.feature.configuration.AercloudConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class AercloudFeature extends Feature<AercloudConfiguration> {
    public AercloudFeature(Codec<AercloudConfiguration> codec) {
        super(codec);
    }

    /**
     * Randomly places an area blocks in a direction to create a cloud.
     * The code is taken from older versions.
     *
     * @param context The {@link FeaturePlaceContext} with a {@link AercloudConfiguration}.
     * @return Whether the placement was successful, as a {@link Boolean}.
     */
    @Override
    public boolean place(FeaturePlaceContext<AercloudConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        boolean positiveZAngle = random.nextBoolean();
        BlockPos blockPos = context.origin().offset(-random.nextInt(8), 0, (positiveZAngle ? 0 : 8) - random.nextInt(8));
        AercloudConfiguration config = context.config();
        BlockState blockState = config.block().getState(random, blockPos);

        int width = 3;
        int height = 2;

        for (int lengthCount = 0; lengthCount < config.bounds(); ++lengthCount) {
            blockPos = blockPos.offset(random.nextInt(2), (random.nextBoolean() ? random.nextInt(3) - 1 : 0), random.nextInt(2) * (positiveZAngle ? 1 : -1));

            for (int x = 0; x < random.nextInt(2) + width; ++x) {
                for (int y = 0; y < random.nextInt(1) + height; ++y) {
                    for (int z = 0; z < random.nextInt(2) + width; ++z) {
                        BlockPos newPosition = blockPos.offset(x, y, z);
                        if (level.isEmptyBlock(newPosition)) {
                            if (x + y + z < 4 + random.nextInt(2)) {
                                this.setBlock(level, newPosition, blockState);
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
}