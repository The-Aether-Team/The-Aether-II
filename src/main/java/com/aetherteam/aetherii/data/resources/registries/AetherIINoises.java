package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class AetherIINoises {
    public static final ResourceKey<NormalNoise.NoiseParameters> TEMPERATURE = createKey("temperature");
    public static final ResourceKey<NormalNoise.NoiseParameters> VEGETATION = createKey("vegetation");
    public static final ResourceKey<NormalNoise.NoiseParameters> CONTINENTALNESS = createKey("continentalness");
    public static final ResourceKey<NormalNoise.NoiseParameters> EROSION = createKey("erosion");
    public static final ResourceKey<NormalNoise.NoiseParameters> ELEVATION = createKey("elevation");
    public static final ResourceKey<NormalNoise.NoiseParameters> AMPLIFICATION = createKey("amplification");
    public static final ResourceKey<NormalNoise.NoiseParameters> LAKES = createKey("lakes");
    public static final ResourceKey<NormalNoise.NoiseParameters> LAKE_FLOOR = createKey("lake_floor");
    public static final ResourceKey<NormalNoise.NoiseParameters> ARCTIC_SNOW = createKey("arctic_snow");

    private static ResourceKey<NormalNoise.NoiseParameters> createKey(String name) {
        return ResourceKey.create(Registries.NOISE, new ResourceLocation(AetherII.MODID, name));
    }

    public static void bootstrap(BootstapContext<NormalNoise.NoiseParameters> context) {
        register(context, TEMPERATURE, -9, 1.5D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D);
        register(context, VEGETATION, -8, 1.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D);
        register(context, CONTINENTALNESS, -8, 1.0D, 2.0D, 2.0D, 2.0D, 1.0D, 1.0D, 0.0D, 0.0D, 0.0D);
        register(context, EROSION, -9, 1.5, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        register(context, ELEVATION, -8, 1.0D, 0.2D, 0.0D, 0.0D, 0.0D);
        register(context, AMPLIFICATION, -7, 1.0, 2.0, 1.0, 0.0, 0.0, 0.0, 0.0);
        register(context, LAKES, -8, 1.25, 0.0, 0.25, 0.0);
        register(context, LAKE_FLOOR, -6, 1.5, 0.5, 0.0, 0.0, 0.0);
        register(context, ARCTIC_SNOW, -5, 1.0, 0.0, 0.5, 0.5, 0.0);
    }

    public static void register(BootstapContext<NormalNoise.NoiseParameters> context, ResourceKey<NormalNoise.NoiseParameters> key, int firstOctave, double firstAmplitude, double... amplitudes) {
        context.register(key, new NormalNoise.NoiseParameters(firstOctave, firstAmplitude, amplitudes));
    }
}