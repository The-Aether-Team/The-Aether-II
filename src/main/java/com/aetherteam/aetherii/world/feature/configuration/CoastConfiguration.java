package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.Optional;

public record CoastConfiguration(BlockStateProvider block, float widthPower, Optional<Holder<PlacedFeature>> vegetationFeature, float vegetationChance) implements FeatureConfiguration {
    public static final Codec<CoastConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("block").forGetter(CoastConfiguration::block),
            Codec.floatRange(0.0F, 1.0F).fieldOf("width_power").forGetter(CoastConfiguration::widthPower),
            PlacedFeature.CODEC.optionalFieldOf("vegetation_feature").forGetter(CoastConfiguration::vegetationFeature),
            Codec.floatRange(0.0F, 1.0F).fieldOf("vegetation_chance").forGetter(CoastConfiguration::vegetationChance)
    ).apply(instance, CoastConfiguration::new));
}