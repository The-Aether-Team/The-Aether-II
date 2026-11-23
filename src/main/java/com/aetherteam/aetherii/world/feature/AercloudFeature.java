package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.world.BlockPlacementUtil;
import com.aetherteam.aetherii.world.feature.configuration.AercloudConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.*;

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
        BlockPos origin = context.origin();


        Set<BlockPos> centerLine = new LinkedHashSet<>();


        //todo need more random sin wave variance.
        //  need it to trend in directions sometimes; i.e. not be centered around a perfectly straight forwards line
        //  need length variance
        //  need to have bigger radius circles near the center for better smoothness so the clouds dont look as rough as they do rn

        double zMultiplier = (1 + random.nextDouble()) * (random.nextBoolean() ? -1 : 1);
        double yMultiplier = (0.55 + (random.nextDouble() / 2.0F)) * (random.nextBoolean() ? -1 : 1);

        for (int x = -10; x < 10; x++) {
            for (int y = -3; y < 3; y++) {
                for (int z = -3; z < 3; z++) {

                    double zBound = Mth.sin(x) * zMultiplier;
                    double yBound = Mth.sin(x / 1.75) * yMultiplier;

                    centerLine.add(origin.offset((int) (x), (int) Math.round(yBound), (int) Math.round(zBound)));
                }
            }
        }

        Set<BlockPos> blockPositions = new LinkedHashSet<>();

//        int size = centerLine.size();
        int i = 0;
        for (BlockPos pos : centerLine) {
//            int value = -Mth.abs(i - (centerLine.size() / 2)) + (centerLine.size() / 2);

            if (i == 0) {
                this.planCircle(
                        pos.offset(-2, 0, 0),
                        1.25F,
                        blockPositions
                );
            } else if (i == centerLine.size() - 1) {
                this.planCircle(
                        pos.offset(2, 0, 0),
                        1.25F,
                        blockPositions
                );
            }

            this.planCircle(
                    pos.offset(0, -1, 0),
                    1.15F,
                    blockPositions
            );
            this.planCircle(
                    pos,
                    1.5F,
                    blockPositions
            );
            this.planCircle(
                    pos.offset(0, 1, 0),
                    1.25F,
                    blockPositions
            );
//            if (random.nextBoolean()) {
//                BlockPlacementUtil.placeDisk(
//                        level,
//                        context.config().block(),
//                        pos.offset(random.nextBoolean() ? -1 : 1, random.nextBoolean() ? -1 : 1, random.nextBoolean() ? -1 : 1),
//                        1.25F,
//                        random,
//                        true
//                );
//            }

            i++;
        }
        for (BlockPos pos : blockPositions) {
            this.setBlock(level, pos, context.config().block().getState(level, random, pos));
            if (random.nextInt(2) == 0 && pos.getY() == origin.getY()) {
                this.setBlock(level, pos.offset(0, 0, random.nextBoolean() ? 1 : -1), context.config().block().getState(level, random, pos));
            }

            if (random.nextInt(3) == 0) {
                this.setBlock(level, pos.offset(random.nextBoolean() ? 1 : -1, 0, 0), context.config().block().getState(level, random, pos));

            }

        }


        return true;
    }

    public void planCircle(BlockPos center, float radius, Set<BlockPos> positions) {
        float radiusSq = radius * radius;

        positions.add(center);
        for (int z = 0; z < radius; z++) {
            for (int x = 0; x < radius; x++) {
                if (x * x + z * z > radiusSq) continue;
                positions.add(center.offset(x, 0, z));
                positions.add(center.offset(-x, 0, -z));
                positions.add(center.offset(-z, 0, x));
                positions.add(center.offset(z, 0, -x));
            }
        }
    }
}