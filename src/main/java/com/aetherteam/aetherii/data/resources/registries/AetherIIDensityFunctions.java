package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.data.resources.builders.AetherIIDensityFunctionBuilders;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class AetherIIDensityFunctions extends AetherIIDensityFunctionBuilders {

    public static void bootstrap(BootstapContext<DensityFunction> context) {
        HolderGetter<DensityFunction> function = context.lookup(Registries.DENSITY_FUNCTION);
        HolderGetter<NormalNoise.NoiseParameters> noise = context.lookup(Registries.NOISE);
        DensityFunction shiftX = getFunction(function, SHIFT_X);
        DensityFunction shiftZ = getFunction(function, SHIFT_Z);

        context.register(TEMPERATURE, DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.5D, noise.getOrThrow(AetherIINoises.TEMPERATURE)));
        context.register(EROSION, DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.5D, noise.getOrThrow(AetherIINoises.EROSION)).abs());
        context.register(ELEVATION, DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.75D, noise.getOrThrow(AetherIINoises.ELEVATION)).abs());

        context.register(BASE_3D_NOISE, BlendedNoise.createUnseeded(
                0.1D, // xz scale
                0.02D, // y scale
                80D, // xz factor
                160D, // y factor
                1.0D // smear scale multiplier, capped at 8
        ));

        context.register(TERRAIN_SHAPER, makeTerrainShaper(function));
    }
}