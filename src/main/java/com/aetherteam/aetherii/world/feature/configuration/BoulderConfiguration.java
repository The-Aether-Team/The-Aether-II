package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.Optional;

public record BoulderConfiguration(BlockStateProvider block, float radius, FloatProvider variation, Optional<Holder<PlacedFeature>> vegetationFeature, float vegetationChance) implements FeatureConfiguration {
    public static final Codec<BoulderConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("block").forGetter(BoulderConfiguration::block),
            Codec.FLOAT.fieldOf("radius").forGetter(BoulderConfiguration::radius),
            FloatProvider.CODEC.fieldOf("variation").forGetter(BoulderConfiguration::variation),
            PlacedFeature.CODEC.optionalFieldOf("vegetation_feature").forGetter(BoulderConfiguration::vegetationFeature),
            Codec.floatRange(0.0F, 1.0F).fieldOf("vegetation_chance").forGetter(BoulderConfiguration::vegetationChance)
    ).apply(instance, BoulderConfiguration::new));
}
