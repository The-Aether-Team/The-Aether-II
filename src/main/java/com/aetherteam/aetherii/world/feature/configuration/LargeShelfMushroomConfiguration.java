package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record LargeShelfMushroomConfiguration(BlockStateProvider block, float baseRadius, int additionalRadius, int minY) implements FeatureConfiguration {
    public static final Codec<LargeShelfMushroomConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("block").forGetter(LargeShelfMushroomConfiguration::block),
            Codec.FLOAT.fieldOf("base_radius").forGetter(LargeShelfMushroomConfiguration::baseRadius),
            Codec.INT.fieldOf("additional_radius").forGetter(LargeShelfMushroomConfiguration::additionalRadius),
            Codec.INT.fieldOf("min_y").forGetter(LargeShelfMushroomConfiguration::minY)
    ).apply(instance, LargeShelfMushroomConfiguration::new));
}