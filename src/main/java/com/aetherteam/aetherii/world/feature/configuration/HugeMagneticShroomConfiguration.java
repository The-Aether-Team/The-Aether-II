package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.Optional;

public record HugeMagneticShroomConfiguration(BlockStateProvider capProvider, BlockStateProvider bottomCapProvider, BlockStateProvider stemProvider, Optional<Holder<PlacedFeature>> groundFeature, FeatureSize minimumSize, boolean large) implements FeatureConfiguration {
    public static final Codec<HugeMagneticShroomConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("cap_provider").forGetter(HugeMagneticShroomConfiguration::capProvider),
            BlockStateProvider.CODEC.fieldOf("bottom_cap_provider").forGetter(HugeMagneticShroomConfiguration::bottomCapProvider),
            BlockStateProvider.CODEC.fieldOf("stem_provider").forGetter(HugeMagneticShroomConfiguration::stemProvider),
            PlacedFeature.CODEC.optionalFieldOf("ground_feature").forGetter(HugeMagneticShroomConfiguration::groundFeature),
            FeatureSize.CODEC.fieldOf("minimum_size").forGetter(HugeMagneticShroomConfiguration::minimumSize),
            Codec.BOOL.fieldOf("large").forGetter(HugeMagneticShroomConfiguration::large)
    ).apply(instance, HugeMagneticShroomConfiguration::new));
}
