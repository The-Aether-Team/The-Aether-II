package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.data.resources.builders.highlands.HighlandsNoiseBuilders;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class AetherIINoiseSettings {
    public static final ResourceKey<NoiseGeneratorSettings> HIGHLANDS = createKey("highlands");

    private static ResourceKey<NoiseGeneratorSettings> createKey(String name) {
        return ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation(AetherII.MODID, name));
    }

    public static void bootstrap(BootstapContext<NoiseGeneratorSettings> context) {
        HolderGetter<DensityFunction> densityFunctions = context.lookup(Registries.DENSITY_FUNCTION);
        HolderGetter<NormalNoise.NoiseParameters> noise = context.lookup(Registries.NOISE);
        context.register(HIGHLANDS, HighlandsNoiseBuilders.highlandsNoiseSettings(densityFunctions, noise));
    }
}