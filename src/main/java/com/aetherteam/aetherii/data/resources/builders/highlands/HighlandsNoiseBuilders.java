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
        return createNoiseRouter(function, noise, getFunction(function, AetherIIDensityFunctions.FINAL_DENSITY));
    }

    private static NoiseRouter createNoiseRouter(HolderGetter<DensityFunction> function, HolderGetter<NormalNoise.NoiseParameters> noise, DensityFunction finalDensity) {
        DensityFunction shiftX = getFunction(function, SHIFT_X);
        DensityFunction shiftZ = getFunction(function, SHIFT_Z);
        DensityFunction temperature = getFunction(function, AetherIIDensityFunctions.TEMPERATURE);
        DensityFunction vegetation = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.5D, noise.getOrThrow(AetherIINoises.VEGETATION));
        DensityFunction continents = getFunction(function, AetherIIDensityFunctions.CONTINENTS);
        DensityFunction erosion = getFunction(function, AetherIIDensityFunctions.EROSION);
        DensityFunction depth = getFunction(function, AetherIIDensityFunctions.DEPTH);
        DensityFunction ridges = getFunction(function, AetherIIDensityFunctions.LAKES_ISLAND_CHECKER);
        return new NoiseRouter(
                DensityFunctions.zero(), // barrier
                DensityFunctions.zero(), // fluid level floodedness
                DensityFunctions.zero(), // fluid level spread
                DensityFunctions.zero(), // lava
                temperature, // temperature
                vegetation, // vegetation
                continents, // continentalness
                erosion, // erosion
                depth, // depth
                ridges, // ridges
                DensityFunctions.zero(), // initial density without jaggedness
                finalDensity, // final density
                DensityFunctions.zero(), // vein toggle
                DensityFunctions.zero(), // vein ridged
                DensityFunctions.zero()); // vein gap
    }
}