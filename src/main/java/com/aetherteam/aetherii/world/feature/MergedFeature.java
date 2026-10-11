package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.feature.configuration.MergedConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class MergedFeature extends Feature<MergedConfiguration> {
    public MergedFeature(Codec<MergedConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<MergedConfiguration> context) {
        MergedConfiguration config = context.config();
        RandomSource random = context.random();
        WorldGenLevel level = context.level();
        ChunkGenerator chunkGenerator = context.chunkGenerator();
        BlockPos origin = context.origin();

        boolean flag = false;
        for (Holder<PlacedFeature> feature : config.features()) {
            if (feature.value().place(level, chunkGenerator, random, origin)) {
                flag = true;
            }
        }
        return flag;
    }
}
