package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.world.feature.configuration.CoastConfiguration;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.*;

public class FerrositeSandCoastFeature extends AbstractCoastFeature {
    public FerrositeSandCoastFeature(Codec<CoastConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<CoastConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos pos = context.origin();
        CoastConfiguration config = context.config();

        ChunkPos originChunk = ChunkPos.containing(pos);
        pos = originChunk.getBlockAt(8, pos.getY(), 8);

        BlockPos origin = AbstractCoastFeature.findOrigin(level, pos, config.validBlocks(), config.avoidBlocks());

        if (origin != null) {
            LinkedHashSet<BlockPos> coastPositions = new LinkedHashSet<>(List.of(origin));

            AbstractCoastFeature.planPath(level, originChunk, origin, coastPositions, coastPositions::add, config.validBlocks(), 24 + random.nextInt(9));
            AbstractCoastFeature.planPath(level, originChunk, origin, coastPositions, coastPositions::addFirst, config.validBlocks(), 24 + random.nextInt(9));

            Multimap<BlockPos, BlockPos> coastDiscs = Multimaps.newMultimap(new HashMap<>(), HashSet::new);

            this.prepareCoastDiscs(config, level, originChunk, coastPositions, coastDiscs, random);

            this.generateBlocksAndVegetation(context, config, level, random, coastDiscs);

            this.createFerrositeSandArcs(context, config, level, originChunk, origin, random, coastDiscs);
        }
        return true;
    }

    public void createFerrositeSandArcs(FeaturePlaceContext<CoastConfiguration> context, CoastConfiguration config, WorldGenLevel level, ChunkPos originChunk, BlockPos originPos, RandomSource random, Multimap<BlockPos, BlockPos> coastDiscs) {
        if (!coastDiscs.values().isEmpty()) {
            BlockPos arcOrigin = null;

            for (BlockPos coastPos : coastDiscs.values()) {
                if (coastPos.getY() == originPos.getY()
                        && (!level.getBlockState(coastPos.north()).isSolid()
                        || !level.getBlockState(coastPos.east()).isSolid()
                        || !level.getBlockState(coastPos.south()).isSolid()
                        || !level.getBlockState(coastPos.west()).isSolid())) {
                    arcOrigin = coastPos;
                    break;
                }
            }

            if (arcOrigin != null && random.nextInt(8) != 0) {
                LinkedHashSet<BlockPos> arcPositionSet = new LinkedHashSet<>(List.of(arcOrigin));

                AbstractCoastFeature.planPath(level, originChunk, arcOrigin, arcPositionSet, arcPositionSet::add, config.validBlocks(), coastDiscs.keys().size() + 8);
                AbstractCoastFeature.planPath(level, originChunk, arcOrigin, arcPositionSet, arcPositionSet::addFirst, config.validBlocks(), coastDiscs.keys().size() + 8);

                List<BlockPos> arcPositionList = new ArrayList<>(arcPositionSet);
                List<List<BlockPos>> segmentedArcPositions = new ArrayList<>();

                int segmentCount = 1 + random.nextInt(3);
                if (arcPositionList.size() > 30) {
                    segmentCount += 1;
                }

                int fromIndex = 0;
                for (int i = 0; i < segmentCount; i++) {
                    int toIndex = fromIndex + random.nextInt(arcPositionList.size() - fromIndex);
                    if (toIndex == arcPositionList.size()) {
                        toIndex -= random.nextInt(arcPositionList.size() / 4);
                    }
                    if (i == segmentCount - 1) {
                        toIndex = arcPositionList.size();
                    }
                    List<BlockPos> subList = arcPositionList.subList(fromIndex, toIndex);
                    segmentedArcPositions.add(subList);
                    fromIndex = toIndex;
                }

                if (segmentedArcPositions.size() > 1 && random.nextInt(6) == 0) {
                    segmentedArcPositions.remove(random.nextInt(segmentedArcPositions.size()));
                }

                for (List<BlockPos> arcSegment : segmentedArcPositions) {
                    Multimap<BlockPos, BlockPos> arcDiscs = Multimaps.newMultimap(new HashMap<>(), HashSet::new);

                    this.prepareArcDiscs(level, originChunk, arcSegment, arcDiscs);

                    this.generateBlocksAndVegetation(context, config, level, random, arcDiscs);
                }
            }
        }
    }

    protected void prepareArcDiscs(WorldGenLevel level, ChunkPos originChunk, List<BlockPos> arcSegment, Multimap<BlockPos, BlockPos> arcDiscs) {
        float power = 0.9F - ((Mth.ceil(arcSegment.size() / 7.0F)) * 0.05F);
        int i = 0;
        int max = arcSegment.size() - 1;
        float half = max / 2.0F;
        for (BlockPos coastPos : arcSegment) {
            int mainDistToCenter = Mth.ceil(Math.abs(i - half));
            int scale = Mth.floor((arcSegment.size() / 2.0F) - mainDistToCenter);
            float radius = Mth.floor(Math.pow(scale + 1, power)) + 0.25F;
            arcDiscs.putAll(coastPos, this.prepareArc(level, originChunk, coastPos, radius)) ;
            i += 1;
        }
    }

    protected Set<BlockPos> prepareArc(WorldGenLevel level, ChunkPos originChunk, BlockPos center, float radius) {
        Set<BlockPos> positions = new HashSet<>();
        float radiusSq = radius * radius;
        boolean placed = true;
        for (int z = 0; z <= radius; z++) {
            for (int x = 0; x <= radius; x++) {
                if (x * x + z * z <= radiusSq) {
                    placed = placed && this.prepareArcBlock(level, originChunk, center.offset(x, 0, z), positions);
                    placed = placed && this.prepareArcBlock(level, originChunk, center.offset(-x, 0, -z), positions);
                    placed = placed && this.prepareArcBlock(level, originChunk, center.offset(-z, 0, x), positions);
                    placed = placed && this.prepareArcBlock(level, originChunk, center.offset(z, 0, -x), positions);
                    if (!placed) {
                        return new HashSet<>();
                    }
                }
            }
        }
        return positions;
    }

    protected boolean prepareArcBlock(WorldGenLevel level, ChunkPos originChunk, BlockPos pos, Set<BlockPos> positions) {
        if (originChunk.getChessboardDistance(ChunkPos.containing(pos)) <= 1) {
            if (!level.getBlockState(pos.north()).isSolid()
                    && !level.getBlockState(pos.north().east()).isSolid()
                    && !level.getBlockState(pos.east()).isSolid()
                    && !level.getBlockState(pos.south().east()).isSolid()
                    && !level.getBlockState(pos.south()).isSolid()
                    && !level.getBlockState(pos.south().west()).isSolid()
                    && !level.getBlockState(pos.west()).isSolid()
                    && !level.getBlockState(pos.north().west()).isSolid()) {
                positions.add(pos);
            }
            return true;
        }
        return false;
    }
}
