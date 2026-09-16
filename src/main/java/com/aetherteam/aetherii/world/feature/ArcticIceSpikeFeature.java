package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.Set;

public class ArcticIceSpikeFeature extends Feature<ArcticIceSpikeConfiguration> { //todo give this more of an upward curved shape by shifting positions upwards to the focal point at the top
    public ArcticIceSpikeFeature(Codec<ArcticIceSpikeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ArcticIceSpikeConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos pos = context.origin();
        ArcticIceSpikeConfiguration config = context.config();

        ChunkPos originChunk = ChunkPos.containing(pos);
        pos = originChunk.getBlockAt(8, pos.getY(), 8);

        BlockPos origin = this.findOrigin(level, pos);

        if (origin != null) {
            Vec3 originVec = Vec3.atCenterOf(origin);

            //todo have a loop that can create multiple spikes jutting from the same location
            //  or use the pathing system to generate positions to spawn spikes from. and generate maybbe with increasing angles to create cool patterns

            float baseRadius = random.nextInt(config.additionalRadius()) + config.baseRadius();
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

                float randomRotation = random.nextInt(360);
                float endRadius = random.nextInt(config.additionalRadius()) + config.endRadius();

                float height = random.nextInt(config.additionalHeight()) + config.baseHeight();

                Vec3 endPoint = originVec.add(n.scale(height))
                        .add(u.scale(endRadius).scale(Mth.cos(randomRotation)))
                        .add(v.scale(endRadius).scale(Mth.sin(randomRotation)));

                Set<BlockPos> points = new HashSet<>();

                //todo icestone spike at the base underneath the spike going the same direction

                for (int i = 0; i <= 360; i += 10) {
                    for (float r = baseRadius; r > 0; r -= 0.5F) {
                        Vec3 startPoint = originVec.subtract(n.scale(farthestDist / 2))
                                .add(u.scale(r).scale(Mth.cos(i)))
                                .add(v.scale(r).scale(Mth.sin(i)));
                        int length = Math.round((float) startPoint.distanceTo(endPoint));
                        Vec3 step = endPoint.subtract(startPoint).scale(1.0 / length);
                        for (int l = 0; l < length; l++) {
                            points.add(BlockPos.containing(startPoint.add(step.scale(l))));
                        }
                    }
                }

                for (BlockPos point : points) { //todo use blocks from the feature config
                    if (!points.contains(point.below()) && random.nextFloat() >= 0.25F) {
                        level.setBlock(point, AetherIIBlocks.ARCTIC_ICE.get().defaultBlockState(), 3);
                        if (random.nextBoolean() && level.getBlockState(point.below()).isAir()) {
                            level.setBlock(point.below(), AetherIIBlocks.LARGE_ARCTIC_ICE_CRYSTAL.get().defaultBlockState().setValue(IceCrystalBlock.FACING, Direction.DOWN), 3);
                        }
                    } else {
                        level.setBlock(point, AetherIIBlocks.ARCTIC_PACKED_ICE.get().defaultBlockState(), 3);
                    }
                }
            }
        }
        return true;
    }

    protected BlockPos findOrigin(WorldGenLevel level, BlockPos pos) {
        for (BlockPos offset : BlockPos.spiralAround(pos, 7, Direction.SOUTH, Direction.EAST)) {
            offset = offset.immutable();
            if (level.getBlockState(offset).is(AetherIITags.Blocks.ARCTIC_ICE_SPIKE_GENERATES_ON)
                    && level.getBlockState(offset.above()).is(AetherIITags.Blocks.ARCTIC_ICE_SPIKE_GENERATES_ON)
                    && level.getBlockState(offset.below()).is(AetherIITags.Blocks.ARCTIC_ICE_SPIKE_GENERATES_ON)
                    && (!level.getBlockState(offset.north()).isSolid()
                    || !level.getBlockState(offset.east()).isSolid()
                    || !level.getBlockState(offset.south()).isSolid()
                    || !level.getBlockState(offset.west()).isSolid())) {
                return offset;
            }
        }
        return null;
    }
}