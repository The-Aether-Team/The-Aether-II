package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.IceCrystalBlock;
import com.aetherteam.aetherii.world.feature.configuration.ArcticIceSpikeConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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

    public void placeSpike(WorldGenLevel level, RandomSource random, Set<BlockPos> points) {
        for (BlockPos point : points) { //todo use blocks from the feature config
            if (!points.contains(point.below()) && random.nextFloat() >= 0.25F) {
                level.setBlock(point, AetherIIBlocks.ARCTIC_ICE.get().defaultBlockState(), 3);
                if (random.nextBoolean() && level.getBlockState(point.below()).isAir()) {
                    level.setBlock(point.below(), AetherIIBlocks.LARGE_ARCTIC_ICE_CRYSTAL.get().defaultBlockState().setValue(IceCrystalBlock.FACING, Direction.DOWN), 3); //todo randomized ice crystal size
                }
            } else {
                level.setBlock(point, AetherIIBlocks.ARCTIC_PACKED_ICE.get().defaultBlockState(), 3);
            }
        }
    }
}
