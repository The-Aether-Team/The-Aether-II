package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.FloatProviders;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

/**
 * [CODE COPY] - {@link net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration}.
 */

public record LargeSpeleothemConfiguration(BlockStateProvider block, BlockStateProvider pointedBlock, int floorToCeilingSearchRange, IntProvider columnRadius, FloatProvider heightScale, float maxColumnRadiusToCaveHeightRatio, FloatProvider stalactiteBluntness, FloatProvider stalagmiteBluntness, FloatProvider windSpeed, int minRadiusForWind, float minBluntnessForWind) implements FeatureConfiguration {
    public static final Codec<LargeSpeleothemConfiguration> CODEC = RecordCodecBuilder.create((i) -> i.group(
            BlockStateProvider.CODEC.fieldOf("block").forGetter(LargeSpeleothemConfiguration::block),
            BlockStateProvider.CODEC.fieldOf("pointed_block").forGetter(LargeSpeleothemConfiguration::pointedBlock),
            Codec.intRange(1, 512).fieldOf("floor_to_ceiling_search_range").orElse(30).forGetter((c) -> c.floorToCeilingSearchRange),
            IntProviders.codec(1, 60).fieldOf("column_radius").forGetter((c) -> c.columnRadius),
            FloatProviders.codec(0.0F, 20.0F).fieldOf("height_scale").forGetter((c) -> c.heightScale),
            Codec.floatRange(0.1F, 1.0F).fieldOf("max_column_radius_to_cave_height_ratio").forGetter((c) -> c.maxColumnRadiusToCaveHeightRatio),
            FloatProviders.codec(0.1F, 10.0F).fieldOf("stalactite_bluntness").forGetter((c) -> c.stalactiteBluntness),
            FloatProviders.codec(0.1F, 10.0F).fieldOf("stalagmite_bluntness").forGetter((c) -> c.stalagmiteBluntness),
            FloatProviders.codec(0.0F, 2.0F).fieldOf("wind_speed").forGetter((c) -> c.windSpeed),
            Codec.intRange(0, 100).fieldOf("min_radius_for_wind").forGetter((c) -> c.minRadiusForWind),
            Codec.floatRange(0.0F, 5.0F).fieldOf("min_bluntness_for_wind").forGetter((c) -> c.minBluntnessForWind)
    ).apply(i, LargeSpeleothemConfiguration::new));
}