package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBiomes;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDensityFunctions;
import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.aetherteam.aetherii.world.feature.configuration.CloudbedConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class CloudbedFeature extends Feature<CloudbedConfiguration> {

    public CloudbedFeature(Codec<CloudbedConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<CloudbedConfiguration> context) {
        CloudbedConfiguration config = context.config();
        WorldGenLevel level = context.level();

        DensityFunction cloudNoise = config.cloudNoise();
        DensityFunction yOffsetNoise = config.yOffset();

        DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(level.getSeed());

        cloudNoise.mapAll(visitor);
        yOffsetNoise.mapAll(visitor);




        HolderGetter<DensityFunction> function = context.level().registryAccess().lookupOrThrow(Registries.DENSITY_FUNCTION);
        DensityFunction bush = AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.ENVIRONMENTAL_BUSHES);
        bush.mapAll(visitor);

        // This should be placed, once per chunk
        int chunkX = context.origin().getX() - (context.origin().getX() % 16);
        int chunkZ = context.origin().getZ() - (context.origin().getZ() % 16);
        // Place blocks across the entire chunk
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int xCoord = chunkX + x;
                int zCoord = chunkZ + z;
                double calc = bush.compute(new DensityFunction.SinglePointContext(xCoord, 0, zCoord));

                Block state = null;
                if (calc < 0) {
                    state = AetherIIBlocks.BLACK_CLOUDWOOL.get();
                } else if (calc >= 0 && calc < 0.1) {
                    state = AetherIIBlocks.BROWN_CLOUDWOOL.get();
                } else if (calc >= 0.1 && calc < 0.2) {
                    state = AetherIIBlocks.RED_CLOUDWOOL.get();
                } else if (calc >= 0.2 && calc < 0.3) {
                    state = AetherIIBlocks.ORANGE_CLOUDWOOL.get();
                } else if (calc >= 0.3 && calc < 0.4) {
                    state = AetherIIBlocks.YELLOW_CLOUDWOOL.get();
                } else if (calc >= 0.4 && calc < 0.5) {
                    state = AetherIIBlocks.LIME_CLOUDWOOL.get();
                } else if (calc >= 0.5 && calc < 0.6) {
                    state = AetherIIBlocks.GREEN_CLOUDWOOL.get();
                } else if (calc >= 0.6 && calc < 0.7) {
                    state = AetherIIBlocks.CYAN_CLOUDWOOL.get();
                } else if (calc >= 0.7 && calc < 0.8) {
                    state = AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL.get();
                } else if (calc >= 0.8 && calc < 0.9) {
                    state = AetherIIBlocks.BLUE_CLOUDWOOL.get();
                } else if (calc >= 0.9 && calc < 1.0) {
                    state = AetherIIBlocks.PURPLE_CLOUDWOOL.get();
                } else {
                    state = AetherIIBlocks.MAGENTA_CLOUDWOOL.get();
                }

                BlockPos pos = new BlockPos(xCoord, context.level().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, xCoord, zCoord), zCoord);
//                this.setBlock(context.level(), pos, state.defaultBlockState());
            }
        }













        // This should be placed, once per chunk
//        int chunkX = context.origin().getX() - (context.origin().getX() % 16);
//        int chunkZ = context.origin().getZ() - (context.origin().getZ() % 16);
//        // Place blocks across the entire chunk
//        for (int x = 0; x < 16; x++) {
//            for (int z = 0; z < 16; z++) {
//                // calculate new coords based on the for loops' values
//                int xCoord = chunkX + x;
//                int zCoord = chunkZ + z;
//                // The main cloud noise is what is used for the distinction of gaps and non-gaps
//                double cloudCalc = cloudNoise.compute(new DensityFunction.SinglePointContext(xCoord, config.yLevel(), zCoord));
//                // A Y offset is then calculated and applied using a second, smoother and larger noise
//                double offsetCalc = yOffsetNoise.compute(new DensityFunction.SinglePointContext(xCoord, config.yLevel(), zCoord));
//                float realOffset =  cosineInterp((float) Mth.inverseLerp(offsetCalc, -0.5, 0.5), 0F, (float) config.maxYOffset());
//                // We don't need to, and shouldn't, generate anything if the cloud noise value is below zero
//                if (cloudCalc >= 0) {
//                    // Interpolate for some extra smoothness
//                    float realCloud = cosineInterp((float) Mth.clamp(cloudCalc, 0, 1), 0, 1);
//                    // Calculate how many blocks up from the main y offset plane should be generated
//                    float blocksUp = Mth.lerp(realCloud, 0F, (float) config.cloudRadius()) + realOffset;
//                    // Calculate how many blocks down from the main y offset plane should be generated
//                    float blocksDown = Mth.lerp(realCloud, 0F, (float) config.cloudRadius() - 1F) - realOffset;
//                    // Floor these values and then place the blocks
//                    BlockState state = config.block().getState(level, context.random(), new BlockPos(xCoord, config.yLevel(), zCoord));
//                    for (int i = Mth.floor(-blocksDown); i <= Mth.floor(blocksUp); i++) {
//                        int y = Mth.clamp(config.yLevel() + i, context.level().getMinY(), context.level().getMaxY());
//                        BlockPos pos = new BlockPos(xCoord, y, zCoord);
//                        if (config.predicate().test(context.level(), pos)) {
//                            this.setBlock(context.level(), pos, state);
//                        }
//                    }
//                }
//            }
//        }
        return false;
    }
    
    private static float cosineInterp(float progress, float start, float end) {
        return (-Mth.cos((float) (Math.PI * progress)) + 1F) * 0.5F * (end - start) + start;
    }
}