package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record CraterConfiguration(UniformInt radius) implements FeatureConfiguration {
    public static final Codec<CraterConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            UniformInt.CODEC.fieldOf("radius").forGetter(CraterConfiguration::radius)
    ).apply(instance, CraterConfiguration::new));
}