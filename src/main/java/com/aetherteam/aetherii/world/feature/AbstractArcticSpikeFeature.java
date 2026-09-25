package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.feature.configuration.ArcticIceSpikeConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractArcticSpikeFeature extends Feature<ArcticIceSpikeConfiguration> {
    public AbstractArcticSpikeFeature(Codec<ArcticIceSpikeConfiguration> codec) {
        super(codec);
    }

    public Set<BlockPos> planSpike(WorldGenLevel level, ChunkPos originChunk, float radius, Vec3 center, Vec3 end, Vec3 unitX, Vec3 unitY) {
        Set<BlockPos> points = new HashSet<>();
        for (int i = 0; i <= 360; i += 10) {
            for (float r = radius; r > 0; r -= 0.5F) {
                Vec3 startPoint = center
                        .add(unitX.scale(r).scale(Mth.cos(i)))
                        .add(unitY.scale(r).scale(Mth.sin(i)));
                int originalLength = Math.round((float) startPoint.distanceTo(end));

                for (int l = 0; l < originalLength; l++) {
                    Vec3 curvedEndPoint = end.add(0, Mth.square(originalLength) / 35.0F, 0);
                    int curvedLength = Math.round((float) startPoint.distanceTo(curvedEndPoint));
                    Vec3 step = curvedEndPoint.subtract(startPoint).scale(1.0 / curvedLength);
                    BlockPos offset = BlockPos.containing(startPoint.add(step.scale(l)));

                    if (originChunk.getChessboardDistance(ChunkPos.containing(offset)) <= 1 && (l < originalLength / 2 || !level.getBlockState(offset).isSolid())) {
                        points.add(offset);
                    } else {
                        return Set.of();
                    }
                }
            }
        }
        return points;
    }

    public void placeSpike(ArcticIceSpikeConfiguration config, WorldGenLevel level, RandomSource random, Set<BlockPos> points) {
        for (BlockPos point : points) {
            if (!points.contains(point.below())) {
                level.setBlock(point, config.underBlock().getState(level, random, point), 3);
                if (random.nextBoolean() && level.getBlockState(point.below()).isAir()) {
                    level.setBlock(point.below(), config.crystalBlock().getState(level, random, point.below()), 3);
                }
            } else {
                level.setBlock(point, config.mainBlock().getState(level, random, point), 3);
            }
        }
    }

    public Set<BlockPos> planIcestoneSphere(ChunkPos originChunk, float radius, Vec3 center) {
        Set<BlockPos> points = new HashSet<>();
        int sphereRadius = Mth.floor(radius);
        for (int x = -sphereRadius; x < radius; x++) {
            for (int z = -sphereRadius; z < radius; z++) {
                for (int y = -sphereRadius; y < radius; y++) {
                    int volume = x * x + y * y + z * z;
                    int radiusSquared = sphereRadius * sphereRadius;
                    BlockPos offset = BlockPos.containing(center).offset(x, y, z);
                    if (originChunk.getChessboardDistance(ChunkPos.containing(offset)) <= 1) {
                        if (volume <= radiusSquared) {
                            points.add(offset);
                        }
                    } else {
                        return Set.of();
                    }
                }
            }
        }
        return points;

    }

    public void placeIcestoneSphere(ArcticIceSpikeConfiguration config, WorldGenLevel level, RandomSource random, Set<BlockPos> points) {
        for (BlockPos point : points) {
            level.setBlock(point, config.sphereBlock().getState(level, random, point), 1 | 2);
        }
    }
}
