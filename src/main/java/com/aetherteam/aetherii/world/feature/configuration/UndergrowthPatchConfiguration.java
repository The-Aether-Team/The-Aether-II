package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record UndergrowthPatchConfiguration(BlockStateProvider block, UniformFloat radius, UniformFloat radiusBelow, Holder<PlacedFeature> vegetationFeature, float vegetationChance) implements FeatureConfiguration {
    public static final Codec<UndergrowthPatchConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("block").forGetter(UndergrowthPatchConfiguration::block),
            UniformFloat.MAP_CODEC.fieldOf("radius").forGetter(UndergrowthPatchConfiguration::radius),
            UniformFloat.MAP_CODEC.fieldOf("radius_below").forGetter(UndergrowthPatchConfiguration::radiusBelow),
            PlacedFeature.CODEC.fieldOf("vegetation_feature").forGetter(UndergrowthPatchConfiguration::vegetationFeature),
            Codec.floatRange(0.0F, 1.0F).fieldOf("vegetation_chance").forGetter(UndergrowthPatchConfiguration::vegetationChance)
    ).apply(instance, UndergrowthPatchConfiguration::new));
}