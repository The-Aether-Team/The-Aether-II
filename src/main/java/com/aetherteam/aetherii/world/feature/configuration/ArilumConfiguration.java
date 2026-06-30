package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record ArilumConfiguration(BlockStateProvider grassProvider, BlockStateProvider plantProvider, IntProvider height, IntProvider depth) implements FeatureConfiguration {
    public static final Codec<ArilumConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("grass_provider").forGetter(ArilumConfiguration::grassProvider),
            BlockStateProvider.CODEC.fieldOf("plant_provider").forGetter(ArilumConfiguration::plantProvider),
            IntProvider.CODEC.fieldOf("height").forGetter(ArilumConfiguration::height),
            IntProvider.CODEC.fieldOf("depth").forGetter(ArilumConfiguration::depth)
    ).apply(instance, ArilumConfiguration::new));
}
