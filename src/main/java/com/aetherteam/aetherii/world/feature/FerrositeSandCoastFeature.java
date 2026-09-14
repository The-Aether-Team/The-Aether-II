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

            this.createFerrositeSandArcs(config, level, originChunk, origin, random, coastDiscs);
        }
        return true;
    }

    public void createFerrositeSandArcs(CoastConfiguration config, WorldGenLevel level, ChunkPos originChunk, BlockPos origin, RandomSource random, Multimap<BlockPos, BlockPos> coastDiscs) {
        //todo optimize and improve
        //  possibly rework how im getting the coastline positions to use the trail path method again
        //  possibly figure out a way to cut out pieces of these coast arcs to make the lines less continuous
        //  possibly make the coast radius a bit less random and see if its possible to have multiple sections of trails using a multimap and those have their own radius curves using the decimal power method

        Set<BlockPos> extensionPositions = new HashSet<>();

        for (BlockPos coastPos : coastDiscs.values()) {
            if (coastPos.getY() == origin.getY()) {
                Set<BlockPos> temporaryPositions = this.prepareCoast(level, originChunk, coastPos, random.nextInt(5));
                for (BlockPos temporaryPos : temporaryPositions) {
                    if (!level.getBlockState(temporaryPos.north()).isSolid()
                            && !level.getBlockState(temporaryPos.north().east()).isSolid()
                            && !level.getBlockState(temporaryPos.east()).isSolid()
                            && !level.getBlockState(temporaryPos.south().east()).isSolid()
                            && !level.getBlockState(temporaryPos.south()).isSolid()
                            && !level.getBlockState(temporaryPos.south().west()).isSolid()
                            && !level.getBlockState(temporaryPos.west()).isSolid()
                            && !level.getBlockState(temporaryPos.north().west()).isSolid()) {
                        extensionPositions.add(temporaryPos);
                    }
                }
            }
        }

        if (extensionPositions.size() > 2) {
            for (BlockPos extensionPos : extensionPositions) {
                level.setBlock(extensionPos, config.block().getState(level, random, extensionPos), 1 | 2);
            }
        }
    }
}
