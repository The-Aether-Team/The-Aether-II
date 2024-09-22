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

                placeCoast(context, layerPos.above());

                placeLakeLayer(context, layerPos, noiseStartValue, 1.0);
                placeLakeLayer(context, layerPos.below(), noiseStartValue + 0.025, 0.8);
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

        // Determinds the block to place at specific noise values
        WorldGenLevel level = context.level();
        if (density > noiseValue && density < 1.5) {
            if (floor < floorNoiseValue) {
                if (!level.isEmptyBlock(pos)
                        && !level.isEmptyBlock(pos.north())
                        && !level.isEmptyBlock(pos.east())
                        && !level.isEmptyBlock(pos.south())
                        && !level.isEmptyBlock(pos.west())
                        && !level.isEmptyBlock(pos.below())
                        && !level.isEmptyBlock(pos.east(barrierThickness(barrier)))
                        && !level.isEmptyBlock(pos.north(barrierThickness(barrier)))
                        && !level.isEmptyBlock(pos.south(barrierThickness(barrier)))
                        && !level.isEmptyBlock(pos.west(barrierThickness(barrier)))
                        && !level.isEmptyBlock(pos.below(barrierThickness(barrier)))
                        && (!level.getBlockState(pos.above()).isSolid()
                        || level.getBlockState(pos.above()).getBlock() instanceof HalfTransparentBlock
                        || level.getBlockState(pos.above()).getBlock() instanceof BushBlock
                )
                ) {
                    this.setBlock(level, pos, Blocks.WATER.defaultBlockState());
                    this.setBlock(level, pos.below(), config.underwaterBlock().getState(context.random(), pos.below()));
                    if (level.isEmptyBlock(pos.below(2))) {
                        this.setBlock(level, pos.below(2), AetherIIBlocks.HOLYSTONE.get().defaultBlockState());
                    }

                    // Removes Floating Grass above the lakes
                    if (level.getBlockState(pos.above()).getBlock() instanceof BushBlock || level.getBlockState(pos.above()).getBlock() instanceof TwigBlock || level.getBlockState(pos.above()).getBlock() instanceof RockBlock) {
                        this.setBlock(level, pos.above(), Blocks.AIR.defaultBlockState());
                    }
                }
            }

            // Generates waterfalls
            if (pos.getY() == config.height().getMinValue() && context.random().nextInt(12) == 0 && barrier > 0.25 && level.getBlockState(pos).is(AetherIIBlocks.AETHER_GRASS_BLOCK.get()) && !config.frozen()) {
                level.setBlock(pos, Fluids.WATER.defaultFluidState().createLegacyBlock(), 2);
                level.scheduleTick(pos, Fluids.WATER.defaultFluidState().getType(), 0);
            }

            // Freezes Top if "frozen" is true
            if (pos.getY() == config.height().getMinValue() && level.getBlockState(pos).is(Blocks.WATER) && config.frozen()) {
                this.setBlock(level, pos, AetherIIBlocks.ARCTIC_ICE.get().defaultBlockState());
            }
        }
    }

    public void placeCoast(FeaturePlaceContext<NoiseLakeConfiguration> context, BlockPos pos) {
        NoiseLakeConfiguration config = context.config();

        DensityFunction lakeNoise = config.lakeNoise();
        DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(context.level().getSeed());
        lakeNoise.mapAll(visitor);
        double density = lakeNoise.compute(new DensityFunction.SinglePointContext(pos.getX(), pos.getY(), pos.getZ()));

        // Determinds the block to place at specific noise values
        WorldGenLevel level = context.level();
        if (density > config.coastNoiseStartValue()) {
            if (level.getBlockState(pos).is(AetherIITags.Blocks.AETHER_DIRT)){
                this.setBlock(level, pos, config.coastBlock().getState(context.random(), pos));
                //this.setBlock(level, pos.below(), config.coastBlock().getState(context.random(), pos));

                // Removes Floating Grass above the lakes
                if (level.getBlockState(pos.above()).getBlock() instanceof BushBlock || level.getBlockState(pos.above()).getBlock() instanceof TwigBlock || level.getBlockState(pos.above()).getBlock() instanceof RockBlock) {
                    this.setBlock(level, pos.above(), Blocks.AIR.defaultBlockState());
                }
            }
        }
    }

    public int barrierThickness(double barrier) {
        return barrier < 0.25 ? 2 : 1;
    }
}