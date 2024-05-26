package com.aetherteam.aetherii.data.resources.builders.highlands;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDensityFunctions;
import com.aetherteam.aetherii.data.resources.registries.AetherIINoises;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;

public class HighlandsNoiseBuilders {
    private static final SurfaceRules.RuleSource GRASS_BLOCK = SurfaceRules.state(AetherIIBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState());
    private static final SurfaceRules.RuleSource DIRT = SurfaceRules.state(AetherIIBlocks.AETHER_DIRT.get().defaultBlockState());

    public static NoiseGeneratorSettings highlandsNoiseSettings(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noise) {
        BlockState holystone = AetherIIBlocks.HOLYSTONE.get().defaultBlockState();
        return new NoiseGeneratorSettings(
                new NoiseSettings(0, 256, 2, 1), // noiseSettings
                holystone, // defaultBlock
                Blocks.WATER.defaultBlockState(), // defaultFluid
                makeNoiseRouter(densityFunctions, noise), // noiseRouter
                highlandsSurfaceRules(), // surfaceRule
                List.of(), // spawnTarget
                -64, // seaLevel
                false, // disableMobGeneration
                false, // aquifersEnabled
                false, // oreVeinsEnabled
                false  // useLegacyRandomSource
        );
    }

    private static SurfaceRules.RuleSource highlandsSurfaceRules() {
        SurfaceRules.RuleSource surface = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), GRASS_BLOCK), DIRT);
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, surface),
                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DIRT),
                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("undershale", VerticalAnchor.absolute(40), VerticalAnchor.absolute(48)), SurfaceRules.state(AetherIIBlocks.UNDERSHALE.get().defaultBlockState()))
        );
    }

    private static NoiseRouter makeNoiseRouter(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noise) {
        return createNoiseRouter(densityFunctions, noise, buildFinalDensity(densityFunctions));
    }

    private static DensityFunction buildFinalDensity(HolderGetter<DensityFunction> densityFunctions) {
        DensityFunction density = getFunction(densityFunctions, AetherIIDensityFunctions.BASE_3D_NOISE_HIGHLANDS);
        density = DensityFunctions.add(density, DensityFunctions.constant(-0.13));
        density = DensityFunctions.add(density, DensityFunctions.constant(0.2));
        density = DensityFunctions.mul(density, DensityFunctions.yClampedGradient(56, 128, 1.0, 0.0));
        density = DensityFunctions.add(density, factorize(densityFunctions, -0.2));
        density = DensityFunctions.add(density, DensityFunctions.constant(0.1));
        density = DensityFunctions.mul(density, DensityFunctions.yClampedGradient(8, 40, 0.0, 1.0));
        density = DensityFunctions.add(density, factorize(densityFunctions, -0.1));
        density = DensityFunctions.add(density, DensityFunctions.constant(-0.05));
        density = DensityFunctions.blendDensity(density);
        density = DensityFunctions.interpolated(density);
        density = density.squeeze();
        return density;
    }

    public static DensityFunction factorize(HolderGetter<DensityFunction> densityFunctions, double value) {
        return DensityFunctions.mul(DensityFunctions.constant(value), getFunction(densityFunctions, AetherIIDensityFunctions.FACTOR));
    }

    private static NoiseRouter createNoiseRouter(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noise, DensityFunction finalDensity) {
        DensityFunction shiftX = getFunction(densityFunctions, ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("shift_x")));
        DensityFunction shiftZ = getFunction(densityFunctions, ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("shift_z")));
        DensityFunction temperature = getFunction(densityFunctions, AetherIIDensityFunctions.TEMPERATURE);
        DensityFunction vegetation = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.5D, noise.getOrThrow(AetherIINoises.VEGETATION));
        DensityFunction erosion = getFunction(densityFunctions, AetherIIDensityFunctions.EROSION);
        return new NoiseRouter(
                DensityFunctions.zero(), // barrier noise
                DensityFunctions.zero(), // fluid level floodedness noise
                DensityFunctions.zero(), // fluid level spread noise
                DensityFunctions.zero(), // lava noise
                temperature, // temperature
                vegetation, // vegetation
                DensityFunctions.zero(), // continentalness noise
                erosion, // erosion
                DensityFunctions.zero(), // depth
                DensityFunctions.zero(), // ridges
                DensityFunctions.zero(), // initial density without jaggedness, used for aquifers in the Overworld.
                finalDensity, // final density
                DensityFunctions.zero(), // veinToggle
                DensityFunctions.zero(), // veinRidged
                DensityFunctions.zero()); // veinGap
    }

    private static DensityFunction getFunction(HolderGetter<DensityFunction> densityFunctions, ResourceKey<DensityFunction> key) {
        return new DensityFunctions.HolderHolder(densityFunctions.getOrThrow(key));
    }
}