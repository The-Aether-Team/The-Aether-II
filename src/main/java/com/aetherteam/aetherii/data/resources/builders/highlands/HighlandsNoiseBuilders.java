package com.aetherteam.aetherii.data.resources.builders.highlands;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.builders.AetherIIDensityFunctionBuilders;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDensityFunctions;
import com.aetherteam.aetherii.data.resources.registries.AetherIINoises;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;

public class HighlandsNoiseBuilders extends AetherIIDensityFunctionBuilders {

    public static NoiseGeneratorSettings highlandsNoiseSettings(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noise) {
        BlockState holystone = AetherIIBlocks.HOLYSTONE.get().defaultBlockState();
        return new NoiseGeneratorSettings(
                new NoiseSettings(0, 384, 2, 1), // noiseSettings
                holystone, // defaultBlock
                Blocks.WATER.defaultBlockState(), // defaultFluid
                makeNoiseRouter(densityFunctions, noise), // noiseRouter
                HighlandsSurfaceBuilders.surfaceRules(), // surfaceRule
                List.of(), // spawnTarget
                -64, // seaLevel
                false, // disableMobGeneration
                false, // aquifersEnabled
                false, // oreVeinsEnabled
                false  // useLegacyRandomSource
        );
    }

    private static NoiseRouter makeNoiseRouter(HolderGetter<DensityFunction> function, HolderGetter<NormalNoise.NoiseParameters> noise) {
        return createNoiseRouter(function, noise, buildFinalDensity(function));
    }

    private static DensityFunction buildFinalDensity(HolderGetter<DensityFunction> function) {
        DensityFunction density = getFunction(function, AetherIIDensityFunctions.BASE_3D_NOISE);
        density = DensityFunctions.add(density, DensityFunctions.constant(-0.03));
        density = DensityFunctions.add(density, DensityFunctions.constant(0.2));
        density = DensityFunctions.mul(density, getFunction(function, AetherIIDensityFunctions.TOP_SLIDE));
        density = DensityFunctions.add(density, factorize(function, -0.21));
        density = DensityFunctions.add(density, DensityFunctions.constant(0.1));
        density = DensityFunctions.mul(density, getFunction(function, AetherIIDensityFunctions.BOTTOM_SLIDE));
        density = DensityFunctions.add(density, factorize(function, -0.21));
        density = DensityFunctions.blendDensity(density);
        density = DensityFunctions.interpolated(density);
        density = density.squeeze();
        return density;
    }

    private static NoiseRouter createNoiseRouter(HolderGetter<DensityFunction> function, HolderGetter<NormalNoise.NoiseParameters> noise, DensityFunction finalDensity) {
        DensityFunction shiftX = getFunction(function, SHIFT_X);
        DensityFunction shiftZ = getFunction(function, SHIFT_Z);
        DensityFunction temperature = getFunction(function, AetherIIDensityFunctions.TEMPERATURE);
        DensityFunction vegetation = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.5D, noise.getOrThrow(AetherIINoises.VEGETATION));
        DensityFunction erosion = getFunction(function, AetherIIDensityFunctions.EROSION);
        DensityFunction depth = getFunction(function, AetherIIDensityFunctions.DEPTH);
        return new NoiseRouter(
                DensityFunctions.zero(), // barrier noise
                DensityFunctions.zero(), // fluid level floodedness noise
                DensityFunctions.zero(), // fluid level spread noise
                DensityFunctions.zero(), // lava noise
                temperature, // temperature
                vegetation, // vegetation
                DensityFunctions.zero(), // continentalness noise
                erosion, // erosion
                depth, // depth
                DensityFunctions.zero(), // ridges
                DensityFunctions.zero(), // initial density without jaggedness, used for aquifers in the Overworld.
                finalDensity, // final density
                DensityFunctions.zero(), // veinToggle
                DensityFunctions.zero(), // veinRidged
                DensityFunctions.zero()); // veinGap
    }
}