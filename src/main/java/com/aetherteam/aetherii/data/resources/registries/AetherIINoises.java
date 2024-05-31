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
    public static final ResourceKey<NormalNoise.NoiseParameters> EROSION = createKey("erosion");

    public static final ResourceKey<NormalNoise.NoiseParameters> CLOUDBED_BASE = createKey("cloudbed");
    public static final ResourceKey<NormalNoise.NoiseParameters> CLOUDBED_Y_OFFSET = createKey("cloudbed_y_offset");

    private static ResourceKey<NormalNoise.NoiseParameters> createKey(String name) {
        return ResourceKey.create(Registries.NOISE, new ResourceLocation(AetherII.MODID, name));
    }

    public static void bootstrap(BootstapContext<NormalNoise.NoiseParameters> ctx) {
        register(ctx, TEMPERATURE, -9, 1.5D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D);
        register(ctx, VEGETATION, -8, 1.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D);
        register(ctx, EROSION, -9, 1.5, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

        register(ctx, CLOUDBED_BASE, 0, 1, 1, 1, 1, 1);
        register(ctx, CLOUDBED_Y_OFFSET, 0, 1, 1);
    }

    public static void register(BootstapContext<NormalNoise.NoiseParameters> ctx, ResourceKey<NormalNoise.NoiseParameters> key, int firstOctave, double firstAmplitude, double... amplitudes) {
        ctx.register(key, new NormalNoise.NoiseParameters(firstOctave, firstAmplitude, amplitudes));
    }
}