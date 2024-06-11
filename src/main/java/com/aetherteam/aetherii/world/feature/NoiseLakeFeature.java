package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.world.feature.configuration.NoiseLakeConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
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

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int xCoord = chunkX + x;
                int zCoord = chunkZ + z;

                placeLakeLayer(context, xCoord, 124, zCoord, 0.4, 1.0);
                placeLakeLayer(context, xCoord, 123, zCoord, 0.425, 0.75);
                placeLakeLayer(context, xCoord, 122, zCoord, 0.44, 0.6);
                placeLakeLayer(context, xCoord, 121, zCoord, 0.45, 0.5);
                placeLakeLayer(context, xCoord, 120, zCoord, 0.46, 0.47);
                placeLakeLayer(context, xCoord, 119, zCoord, 0.47, 0.43);
                placeLakeLayer(context, xCoord, 118, zCoord, 0.48, 0.4);
                placeLakeLayer(context, xCoord, 117, zCoord, 0.49, 0.37);
                placeLakeLayer(context, xCoord, 116, zCoord, 0.5, 0.35);
                placeLakeLayer(context, xCoord, 115, zCoord, 0.51, 0.325);
                placeLakeLayer(context, xCoord, 114, zCoord, 0.52, 0.3);
                placeLakeLayer(context, xCoord, 113, zCoord, 0.53, 0.275);
                placeLakeLayer(context, xCoord, 112, zCoord, 0.555, 0.225);
                placeLakeLayer(context, xCoord, 111, zCoord, 0.56, 0.18);
                placeLakeLayer(context, xCoord, 110, zCoord, 0.58, 0.13);
                placeLakeLayer(context, xCoord, 109, zCoord, 0.605, 0.08);
                placeLakeLayer(context, xCoord, 108, zCoord, 0.65, 0.02);
            }
        }
        return true;
    }

    @SuppressWarnings("deprecation")
    public void placeLakeLayer(FeaturePlaceContext<NoiseLakeConfiguration> context, int x, int y, int z, double noiseValue, double floorNoiseValue) {
        NoiseLakeConfiguration config = context.config();

        DensityFunction lakeNoise = config.lakeNoise();
        DensityFunction lakeFloorNoise = config.lakeFloorNoise();

        double density = lakeNoise.compute(new DensityFunction.SinglePointContext(x, y, z));
        double floor = lakeFloorNoise.compute(new DensityFunction.SinglePointContext(x, y, z));

        WorldGenLevel level = context.level();
        if (density > noiseValue && density < 1.5D) {
            if (!(floor > floorNoiseValue && floor < 1.0D)) {
                if (!level.isEmptyBlock(new BlockPos(x, y, z))
                        && !level.isEmptyBlock(new BlockPos(x + 1, y, z))
                        && !level.isEmptyBlock(new BlockPos(x - 1, y, z))
                        && !level.isEmptyBlock(new BlockPos(x, y - 1, z))
                        && !level.isEmptyBlock(new BlockPos(x, y, z + 1))
                        && !level.isEmptyBlock(new BlockPos(x, y, z - 1))
                        && !level.getBlockState(new BlockPos(x, y + 1, z)).isSolid()
                ) {
                    this.setBlock(level, new BlockPos(x, y, z), Blocks.WATER.defaultBlockState());
                    this.setBlock(level, new BlockPos(x, y - 1, z), config.underwaterBlock().getState(context.random(), new BlockPos(x, y - 1, z)));
                    if (level.getBlockState(new BlockPos(x, y + 1, z)).getBlock() instanceof BushBlock) {
                        this.setBlock(level, new BlockPos(x, y + 1, z), Blocks.AIR.defaultBlockState());
                    }
                }
            }
        }
    }
}