package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class AetherIINoises {
    public static final ResourceKey<NormalNoise.NoiseParameters> TEMPERATURE = createKey("temperature");
    public static final ResourceKey<NormalNoise.NoiseParameters> VEGETATION = createKey("vegetation");
    public static final ResourceKey<NormalNoise.NoiseParameters> VEGETATION_RARE = createKey("vegetation_rare");
    public static final ResourceKey<NormalNoise.NoiseParameters> CONTINENTALNESS = createKey("continentalness");
    public static final ResourceKey<NormalNoise.NoiseParameters> EROSION = createKey("erosion");
    public static final ResourceKey<NormalNoise.NoiseParameters> AMPLIFICATION = createKey("amplification");
    public static final ResourceKey<NormalNoise.NoiseParameters> RIDGES = createKey("ridges");
    public static final ResourceKey<NormalNoise.NoiseParameters> ELEVATION = createKey("elevation");
    public static final ResourceKey<NormalNoise.NoiseParameters> ELEVATION_SHATTERED = createKey("elevation_shattered");
    public static final ResourceKey<NormalNoise.NoiseParameters> CAVES = createKey("caves");
    public static final ResourceKey<NormalNoise.NoiseParameters> CAVE_THICKNESS = createKey("cave_thickness");
    public static final ResourceKey<NormalNoise.NoiseParameters> ARCTIC_SNOW = createKey("arctic_snow");

    private static ResourceKey<NormalNoise.NoiseParameters> createKey(String name) {
        return ResourceKey.create(Registries.NOISE, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void bootstrap(BootstrapContext<NormalNoise.NoiseParameters> context) {
        register(context, TEMPERATURE, -9, 1.5D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D);
        register(context, VEGETATION, -8, 1.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D);
        register(context, VEGETATION_RARE, -8, 1.5D, 0.0D, 0.0D, 0.0D);
        register(context, CONTINENTALNESS, -9, 1.5D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D);
        register(context, EROSION, -9, 1.5D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
        register(context, AMPLIFICATION, -7, 1.0D, 2.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D);
        register(context, RIDGES, -9, 1.5D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
        register(context, ELEVATION, -8, 1.0D, 0.2D, 0.0D, 0.0D, 0.0D);
        register(context, ELEVATION_SHATTERED, -7, 1.0D, 0.75D, 0.0D, 0.0D, 0.0D);
        register(context, CAVES, -6, 1.0, 2.0, 1.0, 0.0, 0.0, 0.0, 0.0);
        register(context, CAVE_THICKNESS, -7, 0.3, 0.0, 0.0);
        register(context, ARCTIC_SNOW, -5, 1.0, 0.0, 0.5, 0.5, 0.0, 0.0);
    }

    public static void register(BootstrapContext<NormalNoise.NoiseParameters> context, ResourceKey<NormalNoise.NoiseParameters> key, int firstOctave, double firstAmplitude, double... amplitudes) {
        context.register(key, new NormalNoise.NoiseParameters(firstOctave, firstAmplitude, amplitudes));
    }
}