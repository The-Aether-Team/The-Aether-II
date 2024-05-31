package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class AetherIIDensityFunctions {
    public static final ResourceKey<DensityFunction> TEMPERATURE = createKey("temperature");
    public static final ResourceKey<DensityFunction> EROSION = createKey("erosion");
    public static final ResourceKey<DensityFunction> FACTOR = createKey("factor"); //TODO: Add to Datagen
    public static final ResourceKey<DensityFunction> BASE_3D_NOISE_HIGHLANDS = createKey("base_3d_noise_highlands");
    public static final ResourceKey<DensityFunction> CLOUDBED_BASE = createKey("cloudbed");
    public static final ResourceKey<DensityFunction> CLOUDBED_Y_OFFSET = createKey("cloudbed_y_offset");

    private static ResourceKey<DensityFunction> createKey(String name) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(AetherII.MODID, name));
    }

    public static void bootstrap(BootstapContext<DensityFunction> context) {
        HolderGetter<DensityFunction> densityFunctions = context.lookup(Registries.DENSITY_FUNCTION);
        HolderGetter<NormalNoise.NoiseParameters> noise = context.lookup(Registries.NOISE);
        DensityFunction shiftX = getFunction(densityFunctions, ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("shift_x")));
        DensityFunction shiftZ = getFunction(densityFunctions, ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("shift_z")));

        context.register(TEMPERATURE, DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.5D, noise.getOrThrow(AetherIINoises.TEMPERATURE)));
        context.register(EROSION, DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.5D, noise.getOrThrow(AetherIINoises.EROSION)).abs());

        context.register(BASE_3D_NOISE_HIGHLANDS, BlendedNoise.createUnseeded(
                0.25D, // xz scale
                0.25D, // y scale
                80D, // xz factor
                160D, // y factor
                8.0D // smear scale multiplier, capped at 8
        ));

        context.register(CLOUDBED_BASE, DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.375D, noise.getOrThrow(AetherIINoises.CLOUDBED_BASE)));
        context.register(CLOUDBED_Y_OFFSET, DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25D, noise.getOrThrow(AetherIINoises.CLOUDBED_Y_OFFSET)));
    }

    public static DensityFunction getFunction(HolderGetter<DensityFunction> densityFunctions, ResourceKey<DensityFunction> key) {
        return new DensityFunctions.HolderHolder(densityFunctions.getOrThrow(key));
    }
}