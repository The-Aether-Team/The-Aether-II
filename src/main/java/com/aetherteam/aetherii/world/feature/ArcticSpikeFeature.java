package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.feature.configuration.ArcticIceSpikeConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.phys.Vec3;

import java.util.Set;

public class ArcticSpikeFeature extends AbstractArcticSpikeFeature {
    public ArcticSpikeFeature(Codec<ArcticIceSpikeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ArcticIceSpikeConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos pos = context.origin();
        ArcticIceSpikeConfiguration config = context.config();
        float baseRadius = random.nextInt(config.additionalRadius()) + config.baseRadius();
        float height = random.nextInt(config.additionalHeight()) + config.baseHeight();

        ChunkPos originChunk = ChunkPos.containing(pos);
        pos = originChunk.getBlockAt(8, pos.getY(), 8);

        BlockPos origin = AbstractCoastFeature.findOrigin(level, pos, config.validBlocks(), config.avoidBlocks());

        if (origin != null) {
            Vec3 originVec = Vec3.atCenterOf(origin);

            Vec3 baseOffset = new Vec3(0, 0, baseRadius);

            Vec3 point1 = Vec3.atCenterOf(level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BlockPos.containing(originVec.add(baseOffset.yRot(0 * Mth.DEG_TO_RAD)))).below());
            Vec3 point2 = Vec3.atCenterOf(level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BlockPos.containing(originVec.add(baseOffset.yRot(90 * Mth.DEG_TO_RAD)))).below());
            Vec3 point3 = Vec3.atCenterOf(level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BlockPos.containing(originVec.add(baseOffset.yRot(180 * Mth.DEG_TO_RAD)))).below());
            Vec3 point4 = Vec3.atCenterOf(level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BlockPos.containing(originVec.add(baseOffset.yRot(270 * Mth.DEG_TO_RAD)))).below());

            double farthestDist = Math.min(point1.distanceTo(originVec), Math.min(point2.distanceTo(originVec), Math.min(point3.distanceTo(originVec), point4.distanceTo(originVec))));

            if (farthestDist <= baseRadius * baseRadius
                    && level.getBlockState(BlockPos.containing(point1)).is(config.validBlocks())
                    && level.getBlockState(BlockPos.containing(point2)).is(config.validBlocks())
                    && level.getBlockState(BlockPos.containing(point3)).is(config.validBlocks())
                    && level.getBlockState(BlockPos.containing(point4)).is(config.validBlocks())) {

                Vec3 zLine = point3.subtract(point1);
                Vec3 xLine = point4.subtract(point2);

                Vec3 normal = zLine.cross(xLine);
                Vec3 perpendicular = normal.cross(xLine);

                Vec3 n = normal.scale(1 / normal.length());
                Vec3 u = xLine.scale(1 / xLine.length());
                Vec3 v = perpendicular.scale(1 / perpendicular.length());

                Vec3 endPoint = originVec.add(n.scale(height));

                Vec3 vertical = Vec3.Y_AXIS;
                double rotationDifference = Math.acos(vertical.dot(n) / (vertical.length() * n.length())) * Mth.RAD_TO_DEG;

                if (rotationDifference < 65.0F) {
                    Set<BlockPos> spherePoints = this.planIcestoneSphere(originChunk, baseRadius, originVec.add(n.reverse()));
                    if (!spherePoints.isEmpty()) {
                        Set<BlockPos> spikePoints = this.planSpike(level, originChunk, baseRadius, originVec, endPoint, u, v);
                        if (!spikePoints.isEmpty()) {
                            this.placeSpike(config, level, random, spikePoints);
                            this.placeIcestoneSphere(config, level, random, spherePoints);
                        }
                    }
                }
            }
        }
        return true;
    }
}
