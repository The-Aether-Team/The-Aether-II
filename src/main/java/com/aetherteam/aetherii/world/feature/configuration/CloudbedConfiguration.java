package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record CloudbedConfiguration(BlockStateProvider block, BlockPredicate predicate, int yLevel, DensityFunction cloudNoise, double cloudRadius, DensityFunction yOffset, double maxYOffset) implements FeatureConfiguration {
    public static final Codec<CloudbedConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("block").forGetter(CloudbedConfiguration::block),
            BlockPredicate.CODEC.fieldOf("predicate").forGetter(CloudbedConfiguration::predicate),
            Codec.INT.fieldOf("y_level").forGetter(CloudbedConfiguration::yLevel),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("cloud_noise").forGetter(CloudbedConfiguration::cloudNoise),
            Codec.DOUBLE.fieldOf("cloud_radius").forGetter(CloudbedConfiguration::cloudRadius),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("offset_noise").forGetter(CloudbedConfiguration::yOffset),
            Codec.DOUBLE.fieldOf("offset_max").forGetter(CloudbedConfiguration::maxYOffset)
    ).apply(instance, CloudbedConfiguration::new));
}