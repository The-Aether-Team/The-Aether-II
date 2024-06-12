package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.world.feature.configuration.NoiseLakeConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class NoiseLakeFeature extends Feature<NoiseLakeConfiguration> {
    public NoiseLakeFeature(Codec<NoiseLakeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoiseLakeConfiguration> context) {
        int chunkX = context.origin().getX() - (context.origin().getX() % 16);
        int chunkZ = context.origin().getZ() - (context.origin().getZ() % 16);

        // Generates this feature chunk-wise
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int xCoord = chunkX + x;
                int zCoord = chunkZ + z;

                // Determinds the noise value at each y-level
                placeLakeLayer(context, xCoord, 124, zCoord, 0.4, 1.0);
                placeLakeLayer(context, xCoord, 123, zCoord, 0.425, 0.75);
                placeLakeLayer(context, xCoord, 122, zCoord, 0.44, 0.6);
                placeLakeLayer(context, xCoord, 121, zCoord, 0.45, 0.5);
                placeLakeLayer(context, xCoord, 120, zCoord, 0.46, 0.47);
                placeLakeLayer(context, xCoord, 119, zCoord, 0.47, 0.43);
                placeLakeLayer(context, xCoord, 118, zCoord, 0.48, 0.39);
                placeLakeLayer(context, xCoord, 117, zCoord, 0.49, 0.35);
                placeLakeLayer(context, xCoord, 116, zCoord, 0.5, 0.3);
                placeLakeLayer(context, xCoord, 115, zCoord, 0.51, 0.25);
                placeLakeLayer(context, xCoord, 114, zCoord, 0.54, 0.175);
                placeLakeLayer(context, xCoord, 113, zCoord, 0.57, 0.08);
            }
        }
        return true;
    }

    @SuppressWarnings("deprecation")
    public void placeLakeLayer(FeaturePlaceContext<NoiseLakeConfiguration> context, int x, int y, int z, double noiseValue, double floorNoiseValue) {
        NoiseLakeConfiguration config = context.config();

        DensityFunction lakeNoise = config.lakeNoise();
        DensityFunction lakeFloorNoise = config.lakeFloorNoise();
        DensityFunction lakeBarrierNoise = config.lakeBarrierNoise();

        double density = lakeNoise.compute(new DensityFunction.SinglePointContext(x, y, z));
        double floor = lakeFloorNoise.compute(new DensityFunction.SinglePointContext(x, y, z));
        double barrier = lakeBarrierNoise.compute(new DensityFunction.SinglePointContext(x, y, z));

        // Determinds the block to place at specific noise values
        WorldGenLevel level = context.level();
        if (density > noiseValue && density < 1.5) {
            if (floor < floorNoiseValue) {
                if (!level.isEmptyBlock(new BlockPos(x, y, z))
                        && !level.isEmptyBlock(new BlockPos(x + 1, y, z))
                        && !level.isEmptyBlock(new BlockPos(x - 1, y, z))
                        && !level.isEmptyBlock(new BlockPos(x, y - 1, z))
                        && !level.isEmptyBlock(new BlockPos(x, y, z + 1))
                        && !level.isEmptyBlock(new BlockPos(x, y, z - 1))
                        && !level.isEmptyBlock(new BlockPos(x + barrierThickness(barrier), y, z))
                        && !level.isEmptyBlock(new BlockPos(x - barrierThickness(barrier), y, z))
                        && !level.isEmptyBlock(new BlockPos(x, y - barrierThickness(barrier), z))
                        && !level.isEmptyBlock(new BlockPos(x, y, z + barrierThickness(barrier)))
                        && !level.isEmptyBlock(new BlockPos(x, y, z - barrierThickness(barrier)))
                        && !level.getBlockState(new BlockPos(x, y + 1, z)).isSolid()
                ) {
                    this.setBlock(level, new BlockPos(x, y, z), Blocks.WATER.defaultBlockState());
                    this.setBlock(level, new BlockPos(x, y - 1, z), config.underwaterBlock().getState(context.random(), new BlockPos(x, y - 1, z)));

                    // Removes Floating Grass above the lakes
                    if (level.getBlockState(new BlockPos(x, y + 1, z)).getBlock() instanceof BushBlock) {
                        this.setBlock(level, new BlockPos(x, y + 1, z), Blocks.AIR.defaultBlockState());
                    }
                }
            }

            // Generates waterfalls
            if (y == 124 && context.random().nextInt(12) == 0 && barrier > 0.25 && level.getBlockState(new BlockPos(x, y, z)).is(AetherIIBlocks.AETHER_GRASS_BLOCK.get())) {
                level.setBlock(new BlockPos(x, y, z), Fluids.WATER.defaultFluidState().createLegacyBlock(), 2);
                level.scheduleTick(new BlockPos(x, y, z), Fluids.WATER.defaultFluidState().getType(), 0);
            }
        }
    }

    public int barrierThickness(double barrier) {
        return barrier < 0.25 ? 2 : 1;
    }
}