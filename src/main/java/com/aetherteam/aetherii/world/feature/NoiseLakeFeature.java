package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.world.feature.configuration.NoiseLakeConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class NoiseLakeFeature extends Feature<NoiseLakeConfiguration> {
    public NoiseLakeFeature(Codec<NoiseLakeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoiseLakeConfiguration> context) {
        int chunkX = context.origin().getX() - (context.origin().getX() % 16);
        int chunkZ = context.origin().getZ() - (context.origin().getZ() % 16);

        DensityFunction lakeNoise = context.config().lakeNoise();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int xCoord = chunkX + x;
                int zCoord = chunkZ + z;

                placeLakeLayer(context, lakeNoise, xCoord, 124, zCoord, 0.4);
                placeLakeLayer(context, lakeNoise, xCoord, 123, zCoord, 0.425);
                placeLakeLayer(context, lakeNoise, xCoord, 122, zCoord, 0.44);
                placeLakeLayer(context, lakeNoise, xCoord, 121, zCoord, 0.45);
                placeLakeLayer(context, lakeNoise, xCoord, 120, zCoord, 0.46);
                placeLakeLayer(context, lakeNoise, xCoord, 119, zCoord, 0.47);
                placeLakeLayer(context, lakeNoise, xCoord, 118, zCoord, 0.48);
                placeLakeLayer(context, lakeNoise, xCoord, 117, zCoord, 0.49);
                placeLakeLayer(context, lakeNoise, xCoord, 116, zCoord, 0.5);
                placeLakeLayer(context, lakeNoise, xCoord, 115, zCoord, 0.515);
                placeLakeLayer(context, lakeNoise, xCoord, 114, zCoord, 0.53);
                placeLakeLayer(context, lakeNoise, xCoord, 113, zCoord, 0.55);
                placeLakeLayer(context, lakeNoise, xCoord, 112, zCoord, 0.575);
                placeLakeLayer(context, lakeNoise, xCoord, 111, zCoord, 0.62);
            }
        }
        return true;
    }

    @SuppressWarnings("deprecation")
    public void placeLakeLayer(FeaturePlaceContext<NoiseLakeConfiguration> context, DensityFunction lakeNoise, int x, int y, int z, double noiseValue) {
        double density = lakeNoise.compute(new DensityFunction.SinglePointContext(x, y, z));
        WorldGenLevel level = context.level();
        if (density > noiseValue && density < 1.5D) {
            if (!level.isEmptyBlock(new BlockPos(x, y, z))
                    && !level.isEmptyBlock(new BlockPos(x + 1, y, z))
                    && !level.isEmptyBlock(new BlockPos(x - 1, y, z))
                    && !level.isEmptyBlock(new BlockPos(x, y - 1, z))
                    && !level.isEmptyBlock(new BlockPos(x, y, z + 1))
                    && !level.isEmptyBlock(new BlockPos(x, y, z - 1))
                    && !level.getBlockState(new BlockPos(x, y + 1, z)).isSolid()
            ) {
                this.setBlock(level, new BlockPos(x, y, z), Blocks.WATER.defaultBlockState());
                this.setBlock(level, new BlockPos(x, y - 1, z), AetherIIBlocks.AETHER_DIRT.get().defaultBlockState());
            }
        }
    }
}