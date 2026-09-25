package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.AetherIITags;
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

public class QuicksoilCoastFeature extends AbstractCoastFeature {
    public QuicksoilCoastFeature(Codec<CoastConfiguration> codec) {
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
        }
        return true;
    }
}