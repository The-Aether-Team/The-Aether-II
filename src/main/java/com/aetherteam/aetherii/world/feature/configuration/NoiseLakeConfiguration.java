package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record NoiseLakeConfiguration(DensityFunction lakeNoise, ConstantInt height) implements FeatureConfiguration {
    public static final Codec<NoiseLakeConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("lake_noise").forGetter(NoiseLakeConfiguration::lakeNoise),
            ConstantInt.CODEC.fieldOf("height").forGetter(NoiseLakeConfiguration::height)
    ).apply(instance, NoiseLakeConfiguration::new));
}