package com.aetherteam.aetherii.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class NoiseLakeFeature extends Feature<NoneFeatureConfiguration> {
    public NoiseLakeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        int chunkX = context.origin().getX() - (context.origin().getX() % 16);
        int chunkZ = context.origin().getZ() - (context.origin().getZ() % 16);
        BlockPos pos = context.origin();

        //DensityFunction lakeNoise = config.cloudNoise();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {

                int xCoord = chunkX + x;
                int zCoord = chunkZ + z;

            }
        }
        return false;
    }
}