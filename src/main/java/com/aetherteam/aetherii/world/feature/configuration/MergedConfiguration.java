package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;
import java.util.stream.Stream;

public record MergedConfiguration(List<Holder<PlacedFeature>> features) implements FeatureConfiguration {
    public static final Codec<MergedConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            PlacedFeature.CODEC.listOf().fieldOf("features").forGetter(MergedConfiguration::features)
    ).apply(instance, MergedConfiguration::new));

    @Override
    public Stream<Holder<ConfiguredFeature<?, ?>>> getSubFeatures() {
        return this.features.stream().flatMap((weighted) -> weighted.value().getFeatures());
    }
}
