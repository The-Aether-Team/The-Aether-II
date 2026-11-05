package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record CrystalIslandConfiguration(BlockStateProvider baseState, BlockStateProvider oreState, DensityFunction noise, Holder<ConfiguredFeature<?, ?>> treeFeature, Holder<ConfiguredFeature<?, ?>> turfFeature, float radius, int height, float inclineFactor, float scatterFactor) implements FeatureConfiguration {
    public static final Codec<CrystalIslandConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("base_state").forGetter(CrystalIslandConfiguration::baseState),
            BlockStateProvider.CODEC.fieldOf("ore_state").forGetter(CrystalIslandConfiguration::oreState),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("noise").forGetter(CrystalIslandConfiguration::noise),
            ConfiguredFeature.CODEC.fieldOf("tree_feature").forGetter(CrystalIslandConfiguration::treeFeature),
            ConfiguredFeature.CODEC.fieldOf("turf_feature").forGetter(CrystalIslandConfiguration::turfFeature),
            Codec.FLOAT.fieldOf("radius").forGetter(CrystalIslandConfiguration::radius),
            Codec.INT.fieldOf("height").forGetter(CrystalIslandConfiguration::height),
            Codec.FLOAT.fieldOf("incline_factor").forGetter(CrystalIslandConfiguration::inclineFactor),
            Codec.FLOAT.fieldOf("scatter_factor").forGetter(CrystalIslandConfiguration::scatterFactor)
    ).apply(instance, CrystalIslandConfiguration::new));
}