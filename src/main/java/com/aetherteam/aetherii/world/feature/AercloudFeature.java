package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.feature.configuration.AercloudConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import org.joml.Vector2i;

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
        BlockState state = context.config().block().getState(level, random, origin);

        int size = context.config().bounds();
        int edge = size / 2;

        Map<Vector2i, Column> centerRadiLine = new LinkedHashMap<>();

        double yMultiplier = (0.525 + (random.nextDouble() / 4.0F)) * (random.nextBoolean() ? -1 : 1);
        double zMultiplier = (1 + random.nextDouble()) * (random.nextBoolean() ? -1 : 1);

        double yOffset = 0;
        double zOffset = 0;
        int offsetMultiplier = random.nextBoolean() ? -1 : 1;

        double curve = 0;
        double curveIncrement = 1;
        boolean curveUp = true;

        for (int i = -edge; i < edge; i++) {
            if (curveUp) {
                curve = Math.min(curve + curveIncrement, edge);

                if (curve < edge / 2.0F) {
                    if (random.nextBoolean()) {
                        curveIncrement = Math.min(curveIncrement + 0.5, 2);
                    }
                } else {
                    if (curve == edge) {
                        curveUp = false;
                        curveIncrement = 1;
                    } else {
                        if (curveIncrement > 1 && random.nextBoolean()) {
                            curveIncrement--;
                        }
                    }
                }
            } else {
                curve = Math.max(curve - curveIncrement, 2);

                if (curve > edge / 2.0F) {
                    if (random.nextBoolean()) {
                        curveIncrement = Math.max(curveIncrement + 0.2, 1);
                    }
                } else {
                    if (curveIncrement > 1 && random.nextBoolean()) {
                        curveIncrement--;
                    }
                }
            }

            int x = origin.getX() + i;
            int z = origin.getZ() + (int) Math.round(Mth.sin(i) * zMultiplier) + (int) Math.round(zOffset);
            int bottomY = origin.getY() + (int) Math.round(yOffset);
            int bottomYCurve = (int) Math.round(Mth.sin(i / 1.75) * yMultiplier);
            int topYCurve = (int) Math.round(curve / 2.25F);

            int radiusCurve = -Mth.abs(i) + edge;
            float radius = Mth.floor(Math.pow(radiusCurve, 0.475)) + 0.5F;

            centerRadiLine.put(new Vector2i(x, z), new Column(bottomY + bottomYCurve, bottomY + topYCurve, radius));

            if (random.nextInt(5) == 0) {
                yOffset += 0.25 * offsetMultiplier;
            } else if (random.nextInt(6) == 0) {
                yOffset -= 0.25 * offsetMultiplier;
            }

            if (random.nextBoolean()) {
                zOffset += offsetMultiplier;
            } else if (random.nextInt(4) == 0) {
                zOffset -= offsetMultiplier;
            }
        }

        Set<BlockPos> blockPositions = new LinkedHashSet<>();

        for (Map.Entry<Vector2i, Column> entry : centerRadiLine.entrySet()) {
            Vector2i vec = entry.getKey();
            Column column = entry.getValue();
            float radius = column.radius();

            int min = column.bottomY();
            int max = column.topY();
            for (int y = min; y < max; y++) {
                int i = y - min;
                float radiusCurve = (float) Math.pow(i, 1.75) / 10.0F;
                this.planCircle(
                        new BlockPos(vec.x(), y, vec.y()),
                        radius - radiusCurve,
                        blockPositions
                );
            }
            this.planCircle(
                    new BlockPos(vec.x(), min - 1, vec.y()),
                    radius + 1,
                    blockPositions
            );
        }

        for (BlockPos pos : blockPositions) {
            if (level.getBlockState(pos).canBeReplaced()) {
                this.setBlock(level, pos, state);
            }
            if (random.nextInt(2) == 0) {
                BlockPos offsetPos = pos.offset(random.nextBoolean() ? 1 : -1, 0, 0);
                if (level.getBlockState(pos).canBeReplaced()) {
                    this.setBlock(level, offsetPos, state);
                }
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

    public record Column(int bottomY, int topY, float radius) { }
}