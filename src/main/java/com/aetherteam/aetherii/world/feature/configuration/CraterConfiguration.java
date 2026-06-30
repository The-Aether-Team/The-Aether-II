package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record CraterConfiguration(UniformInt radius, DensityFunction noise, BlockStateProvider exteriorBlock, BlockStateProvider interiorBlock, BlockStateProvider craterBlock) implements FeatureConfiguration {
    public static final Codec<CraterConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            UniformInt.CODEC.fieldOf("radius").forGetter(CraterConfiguration::radius),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("noise").forGetter(CraterConfiguration::noise),
            BlockStateProvider.CODEC.fieldOf("exterior_block").forGetter(CraterConfiguration::exteriorBlock),
            BlockStateProvider.CODEC.fieldOf("interior_block").forGetter(CraterConfiguration::interiorBlock),
            BlockStateProvider.CODEC.fieldOf("crater_block").forGetter(CraterConfiguration::craterBlock)
    ).apply(instance, CraterConfiguration::new));
}
