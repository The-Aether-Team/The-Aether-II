package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.world.feature.configuration.CoastConfiguration;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
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



//        float radius = 3.15F;
//
//        float radiusSq = radius * radius;
//        for (int z = 0; z <= radius; z++) {
//            for (int x = 0; x <= radius; x++) {
//                if (x * x + z * z <= radiusSq) {
//                    level.setBlock(pos.offset(x, 0, z), config.block().getState(level, random, pos.offset(x, 0, z)), 1 | 2);
//                    level.setBlock(pos.offset(-x, 0, -z), config.block().getState(level, random, pos.offset(-x, 0, -z)), 1 | 2);
//                    level.setBlock(pos.offset(-z, 0, x), config.block().getState(level, random, pos.offset(-z, 0, x)), 1 | 2);
//                    level.setBlock(pos.offset(z, 0, -x), config.block().getState(level, random, pos.offset(z, 0, -x)), 1 | 2);
//                }
//            }
//        }



        ChunkPos originChunk = ChunkPos.containing(pos);
        pos = originChunk.getBlockAt(8, pos.getY(), 8);

        BlockPos origin = this.findOrigin(level, pos);

        if (origin != null) {
            LinkedHashSet<BlockPos> coastPositions = new LinkedHashSet<>(List.of(origin));

            this.planPath(level, originChunk, origin, coastPositions, coastPositions::add, AetherIITags.Blocks.SHAPES_COASTS, 24 + random.nextInt(9));
            this.planPath(level, originChunk, origin, coastPositions, coastPositions::addFirst, AetherIITags.Blocks.SHAPES_COASTS, 24 + random.nextInt(9));

            Multimap<BlockPos, BlockPos> coastDiscs = Multimaps.newMultimap(new HashMap<>(), HashSet::new);

            this.prepareCoastDiscs(config, level, originChunk, coastPositions, coastDiscs, random);

            this.generateBlocksAndVegetation(context, config, level, random, coastDiscs);

            this.createFerrositeSandArcs(context, config, level, originChunk, origin, random, coastDiscs);
        }
        return true;
    }

    public void createFerrositeSandArcs(FeaturePlaceContext<CoastConfiguration> context, CoastConfiguration config, WorldGenLevel level, ChunkPos originChunk, BlockPos originPos, RandomSource random, Multimap<BlockPos, BlockPos> coastDiscs) {
        //todo optimize and improve
        //         draw a path, go from the start of the list and at random after a certain length stop. this section will consist of an arc. the arc can have its own radius curve where it gets bigger towards the center.
        //              maybe even have a random amount of positions to remove off the list from the start before the loop starts again to give it some distance
        //         the path positions from that section will be removed from the original list, and the loop will go through the rest of the list again to get another segment to do the same thing to as before.
        //          important to note that the next section cannot have any blocks that border another arc. the arc will be placed down before the next one is planned so that allows for checking this easily.


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

            if (arcOrigin != null) {
                LinkedHashSet<BlockPos> arcPositionSet = new LinkedHashSet<>(List.of(arcOrigin));

                //todo this can fail sometimes if the coast has two sides. might just be an acceptable edge case
                //  though it also gets cut off if like, smth intersects the coast. and at corners.
                //    maybe i need to trace a path along air bordering the sand? thatd only fix the corner issue though
                //         maybe starting the other path from the reverse side could help, id just need to figure out how to properly add the values in order
                this.planPath(level, originChunk, arcOrigin, arcPositionSet, arcPositionSet::add, AetherIITags.Blocks.COAST_SOILS, coastDiscs.keys().size() + 8);
                this.planPath(level, originChunk, arcOrigin, arcPositionSet, arcPositionSet::addFirst, AetherIITags.Blocks.COAST_SOILS, coastDiscs.keys().size() + 8);


                List<BlockPos> arcPositionList = new ArrayList<>(arcPositionSet);
                List<List<BlockPos>> segmentedArcPositions = new ArrayList<>();

                int segmentCount = 1 + random.nextInt(3);
                if (arcPositionList.size() > 30) {
                    segmentCount += 1;
                }

                int fromIndex = 0;
                for (int i = 0; i < segmentCount; i++) {
                    int toIndex = fromIndex + random.nextInt(arcPositionList.size() - fromIndex);
                    if (i == segmentCount - 1) {
                        toIndex = arcPositionList.size();
                    }
                    List<BlockPos> subList = arcPositionList.subList(fromIndex, toIndex);
                    segmentedArcPositions.add(subList);
                    fromIndex = toIndex;
                }

                for (List<BlockPos> arcSegment : segmentedArcPositions) {
                    Multimap<BlockPos, BlockPos> arcDiscs = Multimaps.newMultimap(new HashMap<>(), HashSet::new);

                    this.prepareArcDiscs(config, level, originChunk, arcSegment, arcDiscs, random);

                    this.generateBlocksAndVegetation(context, config, level, random, arcDiscs);
                }

                for (BlockPos arcPos : arcPositionList) { //debug
                    level.setBlock(arcPos, Blocks.DIAMOND_BLOCK.defaultBlockState(), 1 | 2);
                }
            }
        }
    }

    protected void prepareArcDiscs(CoastConfiguration config, WorldGenLevel level, ChunkPos originChunk, List<BlockPos> arcSegment, Multimap<BlockPos, BlockPos> arcDiscs, RandomSource random) {
        //todo implement some spikiness; might be able to do that by changing the +0.25F to get a spikier circle shape
        float power = 0.85F - ((Mth.ceil(arcSegment.size() / 10.0F)) * 0.05F); //todo balance further

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
