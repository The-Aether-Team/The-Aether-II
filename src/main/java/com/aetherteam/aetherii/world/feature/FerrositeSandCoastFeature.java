package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.feature.configuration.CoastConfiguration;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
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

        BlockPos origin = this.findOrigin(level, pos);

        if (origin != null) {
            LinkedHashSet<BlockPos> coastPositions = new LinkedHashSet<>(List.of(origin));

            this.planCoastline(level, originChunk, origin, coastPositions, coastPositions::add, random);
            this.planCoastline(level, originChunk, origin, coastPositions, coastPositions::addFirst, random);

            Multimap<BlockPos, BlockPos> coastDiscs = Multimaps.newMultimap(new HashMap<>(), HashSet::new);

            this.prepareCoastDiscs(config, level, originChunk, coastPositions, coastDiscs, random);

            this.generateBlocksAndVegetation(context, config, level, random, coastDiscs);

            this.createFloatingFerrositeSand(config, level, origin, random, coastDiscs);
        }
        return true;
    }

    public void createFloatingFerrositeSand(CoastConfiguration config, WorldGenLevel level, BlockPos origin, RandomSource random, Multimap<BlockPos, BlockPos> coastDiscs) {
//        //todo possibly prevent the ferrosite coasts from generating on ferrosite so i can limit how they generate around pillars and spikes
//
//        for (BlockPos coastPos : coastDiscs.values()) {
//
//            if (coastPos.getY() == origin.getY() && (!level.getBlockState(coastPos.north()).isSolid()
//                    || !level.getBlockState(coastPos.east()).isSolid()
//                    || !level.getBlockState(coastPos.south()).isSolid()
//                    || !level.getBlockState(coastPos.west()).isSolid())) {
//
//                int startAngle = random.nextInt(360); //todo gotta see if theres a better way i can come up with the start angle based on orientation of the coast or something
//                if (random.nextInt(8) == 0) { //todo scale random chance with size of coast
//                    Set<BlockPos> extensionPositions = new HashSet<>();
//
//                    for (int theta = startAngle; theta < startAngle + 135 + random.nextInt(46); theta++) {
//                        for (int r = 3; r < 6; r++) {
//                            int x = (int) (r * Mth.cos(theta * Mth.DEG_TO_RAD));
//                            int z = (int) (r * Mth.sin(theta * Mth.DEG_TO_RAD));
//                            BlockPos offset = coastPos.offset(x, 0, z);
//
//                            if (!level.getBlockState(offset.north()).isSolid() //todo chunk distance check
//                                    && !level.getBlockState(offset.north().east()).isSolid()
//                                    && !level.getBlockState(offset.east()).isSolid()
//                                    && !level.getBlockState(offset.south().east()).isSolid()
//                                    && !level.getBlockState(offset.south()).isSolid()
//                                    && !level.getBlockState(offset.south().west()).isSolid()
//                                    && !level.getBlockState(offset.west()).isSolid()
//                                    && !level.getBlockState(offset.north().west()).isSolid()) {
//                                extensionPositions.add(offset);
//                            }
//                        }
//                    }
//
//                    if (extensionPositions.size() > 2) {
//                        for (BlockPos extensionPos : extensionPositions) {
//                            level.setBlock(extensionPos, config.block().getState(level, random, extensionPos), 1 | 2);
//                        }
//                    }
//                }
//            }
//        }
    }
}
