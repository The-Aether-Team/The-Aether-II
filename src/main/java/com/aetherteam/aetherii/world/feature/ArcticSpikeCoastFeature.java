package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.world.feature.configuration.ArcticIceSpikeConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public class ArcticSpikeCoastFeature extends AbstractArcticSpikeFeature {
    public ArcticSpikeCoastFeature(Codec<ArcticIceSpikeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ArcticIceSpikeConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos pos = context.origin();
        ArcticIceSpikeConfiguration config = context.config();
        float baseRadius = random.nextInt(config.additionalRadius()) + config.baseRadius();

        ChunkPos originChunk = ChunkPos.containing(pos);
        pos = originChunk.getBlockAt(8, pos.getY(), 8);

        BlockPos origin = AbstractCoastFeature.findOrigin(level, pos, AetherIITags.Blocks.SHAPES_ARCTIC_COASTS, BlockTags.ICE);

        if (origin != null) {
            Vec3 originVec = Vec3.atCenterOf(origin);

            LinkedHashSet<BlockPos> coastPositions = new LinkedHashSet<>(List.of(origin));

            AbstractCoastFeature.planPath(level, originChunk, origin, coastPositions, coastPositions::add, AetherIITags.Blocks.SHAPES_ARCTIC_COASTS, (int) (baseRadius * 2));

            List<BlockPos> coastPositionList = new ArrayList<>(coastPositions);

            Vec3 point1 = Vec3.atCenterOf(coastPositionList.getFirst());
            Vec3 point2 = Vec3.atCenterOf(coastPositionList.getLast());

            Vec3 point3 = originVec.add(0, baseRadius, 0);
            Vec3 point4 = originVec.subtract(0, baseRadius, 0);

            Vec3 center = point1.add(point2).scale(0.5);

            Vec3 xLine = point2.subtract(point1);
            Vec3 unitX = xLine.scale(1 / xLine.length());
            Vec3 yLine = point4.subtract(point3);
            Vec3 unitY = yLine.scale(1 / yLine.length());

            if (xLine.length() >= baseRadius) {
                Vec3 normal1 = xLine.cross(yLine);
                Vec3 unit1 = normal1.scale(1 / normal1.length());
                Vec3 normal2 = normal1.reverse();
                Vec3 unit2 = unit1.reverse();

                Vec3 outwardsVector = null;
                Vec3 outwardsUnit = null;

                if (!level.getBlockState(BlockPos.containing(center.add(unit1))).isSolid()) {
                    outwardsVector = normal1;
                    outwardsUnit = unit1;
                } else if (!level.getBlockState(BlockPos.containing(center.add(unit2))).isSolid()) {
                    outwardsVector = normal2;
                    outwardsUnit = unit2;
                }
                if (outwardsVector != null) {
                    float height = random.nextInt(config.additionalHeight()) + config.baseHeight();

                    //todo icestone spike at the base underneath the spike going the same direction

                    Set<BlockPos> points = new HashSet<>();

                    int rotationAmount = 2 + random.nextInt(3);
                    int rotationIncrement = 30;
                    int rotationRange = rotationAmount * rotationIncrement;
                    int rotationCenter = rotationRange / 2;

                    int heightVariance = random.nextInt(5);

                    for (int i = -rotationCenter; i <= rotationCenter; i += rotationIncrement) {
                        Vec3 rotatedOutwardsUnit = outwardsUnit.yRot(i * Mth.DEG_TO_RAD);
                        Vec3 rotatedUnitX = unitX.yRot(i * Mth.DEG_TO_RAD);
                        Vec3 rotatedUnitY = unitY.yRot(i * Mth.DEG_TO_RAD);

                        Vec3 endPoint = center.add(rotatedOutwardsUnit
                                .scale(height - (Mth.abs(i / rotationIncrement) * 2))
                                .add(0, ((height / 3.0) - (Mth.abs(i / rotationIncrement) * 3)) - heightVariance, 0));

                        points.addAll(this.planSpike(level, originChunk, baseRadius, center, endPoint, rotatedUnitX, rotatedUnitY));
                    }

                    this.placeSpike(level, random, points);

//                    level.setBlock(BlockPos.containing(pointMid), Blocks.DIAMOND_BLOCK.defaultBlockState(), 1 | 2);
//                    level.setBlock(BlockPos.containing(center), Blocks.GOLD_BLOCK.defaultBlockState(), 1 | 2);
//                    level.setBlock(BlockPos.containing(point1), Blocks.REDSTONE_BLOCK.defaultBlockState(), 1 | 2);
//                    level.setBlock(BlockPos.containing(point2), Blocks.EMERALD_BLOCK.defaultBlockState(), 1 | 2);
                }
            }
        }
        return true;
    }
}