package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.RockBlock;
import com.aetherteam.aetherii.block.natural.TwigBlock;
import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.aetherteam.aetherii.world.feature.configuration.NoiseLakeConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Fluids;

public class NoiseLakeFeature extends Feature<NoiseLakeConfiguration> {
    public NoiseLakeFeature(Codec<NoiseLakeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoiseLakeConfiguration> context) {
        int chunkX = context.origin().getX() - (context.origin().getX() % 16);
        int chunkZ = context.origin().getZ() - (context.origin().getZ() % 16);
        int height = context.config().height().getMinValue();

        // Generates this feature chunk-wise
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int xCoord = chunkX + x;
                int zCoord = chunkZ + z;

                // Determinds the noise value at each y-level
                placeLakeLayer(context, xCoord, height, zCoord, 0.3, 1.0);
                placeLakeLayer(context, xCoord, height - 1, zCoord, 0.325, 0.8);
                placeLakeLayer(context, xCoord, height - 2, zCoord, 0.34, 0.75);
                placeLakeLayer(context, xCoord, height - 3, zCoord, 0.345, 0.7);
                placeLakeLayer(context, xCoord, height - 4, zCoord, 0.35, 0.625);
                placeLakeLayer(context, xCoord, height - 5, zCoord, 0.365, 0.55);
                placeLakeLayer(context, xCoord, height - 6, zCoord, 0.37, 0.475);
                placeLakeLayer(context, xCoord, height - 7, zCoord, 0.375, 0.4);
                placeLakeLayer(context, xCoord, height - 8, zCoord, 0.38, 0.3);
                placeLakeLayer(context, xCoord, height - 9, zCoord, 0.385, 0.2);
                placeLakeLayer(context, xCoord, height - 10, zCoord, 0.391, 0.1);
                placeLakeLayer(context, xCoord, height - 11, zCoord, 0.45, 0.035);
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

        DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(context.level().getSeed());

        lakeNoise.mapAll(visitor);
        lakeFloorNoise.mapAll(visitor);
        lakeBarrierNoise.mapAll(visitor);

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
                        && (!level.getBlockState(new BlockPos(x, y + 1, z)).isSolid()
                        || level.getBlockState(new BlockPos(x, y + 1, z)).getBlock() instanceof HalfTransparentBlock
                        || level.getBlockState(new BlockPos(x, y + 1, z)).getBlock() instanceof BushBlock
                )
                ) {
                    this.setBlock(level, new BlockPos(x, y, z), Blocks.WATER.defaultBlockState());
                    this.setBlock(level, new BlockPos(x, y - 1, z), config.underwaterBlock().getState(context.random(), new BlockPos(x, y - 1, z)));

                    // Removes Floating Grass above the lakes
                    if (level.getBlockState(new BlockPos(x, y + 1, z)).getBlock() instanceof BushBlock || level.getBlockState(new BlockPos(x, y + 1, z)).getBlock() instanceof TwigBlock || level.getBlockState(new BlockPos(x, y + 1, z)).getBlock() instanceof RockBlock) {
                        this.setBlock(level, new BlockPos(x, y + 1, z), Blocks.AIR.defaultBlockState());
                    }
                }
            }

            // Generates waterfalls
            if (y == config.height().getMinValue() && context.random().nextInt(12) == 0 && barrier > 0.25 && level.getBlockState(new BlockPos(x, y, z)).is(AetherIIBlocks.AETHER_GRASS_BLOCK.get()) && !config.frozen()) {
                level.setBlock(new BlockPos(x, y, z), Fluids.WATER.defaultFluidState().createLegacyBlock(), 2);
                level.scheduleTick(new BlockPos(x, y, z), Fluids.WATER.defaultFluidState().getType(), 0);
            }

            // Freezes Top if "frozen" is true
            if (y == config.height().getMinValue() && level.getBlockState(new BlockPos(x, y, z)).is(Blocks.WATER) && config.frozen()) {
                this.setBlock(level, new BlockPos(x, y, z), AetherIIBlocks.ARCTIC_ICE.get().defaultBlockState());
            }
        }
    }

    public int barrierThickness(double barrier) {
        return barrier < 0.25 ? 2 : 1;
    }
}