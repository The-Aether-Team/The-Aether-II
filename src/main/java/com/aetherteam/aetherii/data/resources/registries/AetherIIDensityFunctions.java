package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
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
    public static final ResourceKey<DensityFunction> TEMPERATURE = createKey("highlands/temperature");
    public static final ResourceKey<DensityFunction> EROSION = createKey("highlands/erosion");
    public static final ResourceKey<DensityFunction> ELEVATION = createKey("highlands/elevation");
    public static final ResourceKey<DensityFunction> FACTOR = createKey("highlands/factor"); //TODO: Add to Datagen
    public static final ResourceKey<DensityFunction> BOTTOM_SLIDE = createKey("highlands/bottom_slide"); //TODO: Add to Datagen
    public static final ResourceKey<DensityFunction> TOP_SLIDE = createKey("highlands/top_slide"); //TODO: Add to Datagen
    public static final ResourceKey<DensityFunction> SLOPER = createKey("highlands/sloper"); //TODO: Add to Datagen
    public static final ResourceKey<DensityFunction> BASE_3D_NOISE = createKey("highlands/base_3d_noise");
    public static final ResourceKey<DensityFunction> AMPLIFICATION = createKey("highlands/amplification"); //TODO: Add to Datagen
    public static final ResourceKey<DensityFunction> TERRAIN_SHAPER = createKey("highlands/terrain_shaper"); //TODO: Add to Datagen

    public static final ResourceKey<DensityFunction> CLOUDBED_BASE = createKey("cloudbed");
    public static final ResourceKey<DensityFunction> CLOUDBED_Y_OFFSET = createKey("cloudbed_y_offset");

    private static ResourceKey<DensityFunction> createKey(String name) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(AetherII.MODID, name));
    }

    public static final ResourceKey<DensityFunction> SHIFT_X = createVanillaKey("shift_x");
    public static final ResourceKey<DensityFunction> SHIFT_Z = createVanillaKey("shift_z");
    public static final ResourceKey<DensityFunction> Y = createVanillaKey("y");

    private static ResourceKey<DensityFunction> createVanillaKey(String name) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(name));
    }

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
                8.0D // smear scale multiplier, capped at 8
        ));

        context.register(CLOUDBED_BASE, new PerlinNoiseFunction(new NormalNoise.NoiseParameters(0, 1, 1, 1, 1, 1), 0.375D, 0.0D, 42));
        context.register(CLOUDBED_Y_OFFSET, new PerlinNoiseFunction(new NormalNoise.NoiseParameters(0, 1, 1), 0.25D, 0.0D, 95));

        context.register(TERRAIN_SHAPER, makeTerrainShaper(function));
    }

    public static DensityFunction makeTerrainShaper(HolderGetter<DensityFunction> function) {
        DensityFunction density = getFunction(function, AMPLIFICATION);
        density = DensityFunctions.add(density, DensityFunctions.yClampedGradient(96, 128, 0.75, 0.35));
        density = DensityFunctions.mul(density, getFunction(function, SLOPER));
        return density.clamp(0, 1);
    }

    public static DensityFunction getFunction(HolderGetter<DensityFunction> densityFunctions, ResourceKey<DensityFunction> key) {
        return new DensityFunctions.HolderHolder(densityFunctions.getOrThrow(key));
    }
}