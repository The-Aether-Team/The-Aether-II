package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.AetherIITags;
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
        BlockPos pos = context.origin();
        NoiseLakeConfiguration config = context.config();

        int chunkX = pos.getX() - (pos.getX() % 16);
        int chunkZ = pos.getZ() - (pos.getZ() % 16);
        int height = config.height().getMinValue();
        double noiseStartValue = config.noiseStartValue();

        // Generates this feature chunk-wise
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int xCoord = chunkX + x;
                int zCoord = chunkZ + z;

                BlockPos layerPos = new BlockPos(xCoord, height, zCoord);

                if (!config.frozen()) {
                    placeShore(context, layerPos);
                }

                placeShoreLayer(context, layerPos, noiseStartValue, 1.0);
                placeLakeLayer(context, layerPos.below(1), noiseStartValue + 0.025, 0.8);
                placeLakeLayer(context, layerPos.below(2), noiseStartValue + 0.04, 0.75);
                placeLakeLayer(context, layerPos.below(3), noiseStartValue + 0.045, 0.7);
                placeLakeLayer(context, layerPos.below(4), noiseStartValue + 0.05, 0.625);
                placeLakeLayer(context, layerPos.below(5), noiseStartValue + 0.055, 0.55);
                placeLakeLayer(context, layerPos.below(6), noiseStartValue + 0.06, 0.475);
                placeLakeLayer(context, layerPos.below(7), noiseStartValue + 0.065, 0.4);
                placeLakeLayer(context, layerPos.below(8), noiseStartValue + 0.07, 0.3);
                placeLakeLayer(context, layerPos.below(9), noiseStartValue + 0.075, 0.2);
                placeLakeLayer(context, layerPos.below(10), noiseStartValue + 0.082, 0.1);
                placeLakeLayer(context, layerPos.below(11), noiseStartValue + 0.05, 0.035);
            }
        }
        return true;
    }

    @SuppressWarnings("deprecation")
    public void placeLakeLayer(FeaturePlaceContext<NoiseLakeConfiguration> context, BlockPos pos, double noiseValue, double floorNoiseValue) {
        NoiseLakeConfiguration config = context.config();

        DensityFunction lakeNoise = config.lakeNoise();
        DensityFunction lakeFloorNoise = config.lakeFloorNoise();
        DensityFunction lakeBarrierNoise = config.lakeBarrierNoise();

        DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(context.level().getSeed());

        lakeNoise.mapAll(visitor);
        lakeFloorNoise.mapAll(visitor);
        lakeBarrierNoise.mapAll(visitor);

        double density = lakeNoise.compute(new DensityFunction.SinglePointContext(pos.getX(), pos.getY(), pos.getZ()));
        double floor = lakeFloorNoise.compute(new DensityFunction.SinglePointContext(pos.getX(), pos.getY(), pos.getZ()));
        double barrier = lakeBarrierNoise.compute(new DensityFunction.SinglePointContext(pos.getX(), pos.getY(), pos.getZ()));
        int thickness = calculateThickness(barrier, pos.getY(), config.height().getValue());

        // Determines the block to place at specific noise values
        WorldGenLevel level = context.level();
        if (density > noiseValue && density < 1.5) {
            if (floor < floorNoiseValue) {
                for (int i = 0; i < barrier; i++) {
                    if (!level.isEmptyBlock(pos)
                            && !level.isEmptyBlock(pos.east(thickness))
                            && !level.isEmptyBlock(pos.north(thickness))
                            && !level.isEmptyBlock(pos.south(thickness))
                            && !level.isEmptyBlock(pos.west(thickness))
                            && !level.isEmptyBlock(pos.below(2))
                            && !level.getBlockState(pos.above()).isSolid()
                    ) {
                        this.setBlock(level, pos, Blocks.WATER.defaultBlockState());
                        this.setBlock(level, pos.below(), config.underwaterBlock().getState(context.random(), pos.below()));
                        if (level.isEmptyBlock(pos.below(2))) {
                            this.setBlock(level, pos.below(2), AetherIIBlocks.HOLYSTONE.get().defaultBlockState());
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    public void placeShoreLayer(FeaturePlaceContext<NoiseLakeConfiguration> context, BlockPos pos, double noiseValue, double floorNoiseValue) {
        NoiseLakeConfiguration config = context.config();

        DensityFunction lakeNoise = config.lakeNoise();
        DensityFunction lakeFloorNoise = config.lakeFloorNoise();
        DensityFunction lakeBarrierNoise = config.lakeBarrierNoise();
        DensityFunction lakeWaterfallNoise = config.lakeWaterfallNoise();

        DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(context.level().getSeed());

        lakeNoise.mapAll(visitor);
        lakeFloorNoise.mapAll(visitor);
        lakeBarrierNoise.mapAll(visitor);
        lakeWaterfallNoise.mapAll(visitor);

        double density = lakeNoise.compute(new DensityFunction.SinglePointContext(pos.getX(), pos.getY(), pos.getZ()));
        double floor = lakeFloorNoise.compute(new DensityFunction.SinglePointContext(pos.getX(), pos.getY(), pos.getZ()));
        double barrier = lakeBarrierNoise.compute(new DensityFunction.SinglePointContext(pos.getX(), pos.getY(), pos.getZ()));
        double waterfalls = lakeWaterfallNoise.compute(new DensityFunction.SinglePointContext(pos.getX(), pos.getY(), pos.getZ()));
        int thickness = config.frozen() ? calculateThickness(barrier, pos.getY(), config.height().getValue()) : calculateShoreThickness(barrier, waterfalls, pos.getY(), config.height().getValue());

        // Determines the block to place at specific noise values
        WorldGenLevel level = context.level();
        if (density > noiseValue && density < 1.5) {
            if (floor < floorNoiseValue) {
                for (int i = 0; i < barrier; i++) {
                    if (!level.isEmptyBlock(pos)
                            && !level.isEmptyBlock(pos.below().east(thickness))
                            && !level.isEmptyBlock(pos.below().north(thickness))
                            && !level.isEmptyBlock(pos.below().south(thickness))
                            && !level.isEmptyBlock(pos.below().west(thickness))
                            && !level.isEmptyBlock(pos.below().below(2))
                            && !level.getBlockState(pos.above()).isSolid()
                    ) {
                        this.setBlock(level, pos, Blocks.AIR.defaultBlockState());
                        if (thickness > 1) {
                            this.setBlock(level, pos.below(), config.shoreBlock().getState(context.random(), pos.below()));
                        } else {
                            this.setBlock(level, pos, Blocks.AIR.defaultBlockState());
                            level.setBlock(pos.below(), Fluids.WATER.defaultFluidState().createLegacyBlock(), 2);
                            level.scheduleTick(pos.below(), Fluids.WATER.defaultFluidState().getType(), 0);
                        }
                        this.setBlock(level, pos.below(2), AetherIIBlocks.AETHER_DIRT.get().defaultBlockState());

                        // Removes Floating Grass above the lakes
                        if (level.getBlockState(pos.above()).getBlock() instanceof BushBlock || level.getBlockState(pos.above()).getBlock() instanceof TwigBlock || level.getBlockState(pos.above()).getBlock() instanceof RockBlock) {
                            this.setBlock(level, pos.above(), Blocks.AIR.defaultBlockState());
                        }
                    }
                }
            }

            // Freezes Top if "frozen" is true
            if (pos.getY() == config.height().getMinValue() - 1 && level.getBlockState(pos.below()).is(Blocks.WATER) && config.frozen()) {
                this.setBlock(level, pos.below(), AetherIIBlocks.ARCTIC_ICE.get().defaultBlockState());
            }
        }
    }

    public void placeShore(FeaturePlaceContext<NoiseLakeConfiguration> context, BlockPos pos) {
        NoiseLakeConfiguration config = context.config();

        DensityFunction lakeNoise = config.lakeNoise();
        DensityFunction shoreNoise = config.shoreNoise();

        DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(context.level().getSeed());

        lakeNoise.mapAll(visitor);
        shoreNoise.mapAll(visitor);

        double density = lakeNoise.compute(new DensityFunction.SinglePointContext(pos.getX(), pos.getY(), pos.getZ()));
        double shore = shoreNoise.compute(new DensityFunction.SinglePointContext(pos.getX(), pos.getY(), pos.getZ()));

        // Determinds the block to place at specific noise values
        WorldGenLevel level = context.level();
        if (density > config.shoreStartValue() + shore) {
            if (level.getBlockState(pos.below()).is(AetherIITags.Blocks.AETHER_DIRT) && level.getBlockState(pos.above()).is(AetherIITags.Blocks.AETHER_DIRT)) {
                this.setBlock(level, pos.below(), config.shoreBlock().getState(context.random(), pos.below()));
                for (int i = 0; i < 4; i++) {
                    this.setBlock(level, new BlockPos(pos.getX(), pos.getY() + i, pos.getZ()), Blocks.AIR.defaultBlockState());
                }
            }
        }

        // Blends the Shores with the surrounding Terrain
        if (density > config.shoreStartValue() + shore - 0.005) {
            if (level.getBlockState(pos.above()).is(AetherIIBlocks.AETHER_GRASS_BLOCK)) {
                this.setBlock(level, pos.below(), AetherIIBlocks.AETHER_DIRT.get().defaultBlockState());
                this.setBlock(level, pos, AetherIIBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState());
                this.setBlock(level, pos.above(), Blocks.AIR.defaultBlockState());
            }

            if (level.getBlockState(pos.above(2)).is(AetherIIBlocks.AETHER_GRASS_BLOCK)) {
                this.setBlock(level, pos.below(), AetherIIBlocks.AETHER_DIRT.get().defaultBlockState());
                this.setBlock(level, pos, AetherIIBlocks.AETHER_DIRT.get().defaultBlockState());
                this.setBlock(level, pos.above(), AetherIIBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState());
                this.setBlock(level, pos.above(2), Blocks.AIR.defaultBlockState());
            }
        }

        // Removes Floating Grass above the shores
        if (level.getBlockState(pos.above()).getBlock() instanceof BushBlock || level.getBlockState(pos.above()).getBlock() instanceof TwigBlock || level.getBlockState(pos.above()).getBlock() instanceof RockBlock) {
            this.setBlock(level, pos.above(), Blocks.AIR.defaultBlockState());
        }
    }

    public int calculateThickness(double barrier, int y, int height) {
        return (int) (y == height ? barrier / 2 : barrier);
    }

    public int calculateShoreThickness(double barrier, double waterfalls, int y, int height) {
        return waterfalls < 0.02 ? 0 : (int) (y == height ? barrier / 2 : barrier);
    }
}