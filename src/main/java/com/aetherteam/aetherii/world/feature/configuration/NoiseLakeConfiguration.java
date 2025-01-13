package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record NoiseLakeConfiguration(DensityFunction lakeNoise, DensityFunction lakeFloorNoise, DensityFunction lakeBarrierNoise, DensityFunction lakeWaterfallNoise, double noiseStartValue, ConstantInt height, BlockStateProvider underwaterBlock, double shoreStartValue, BlockStateProvider shoreBlock, DensityFunction shoreNoise, boolean frozen) implements FeatureConfiguration {
    public static final Codec<NoiseLakeConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("lake_noise").forGetter(NoiseLakeConfiguration::lakeNoise),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("lake_floor_noise").forGetter(NoiseLakeConfiguration::lakeFloorNoise),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("lake_barrier_noise").forGetter(NoiseLakeConfiguration::lakeBarrierNoise),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("lake_waterfall_noise").forGetter(NoiseLakeConfiguration::lakeWaterfallNoise),
            Codec.DOUBLE.fieldOf("noise_start_value").forGetter(NoiseLakeConfiguration::noiseStartValue),
            ConstantInt.CODEC.fieldOf("height").forGetter(NoiseLakeConfiguration::height),
            BlockStateProvider.CODEC.fieldOf("underwater_block").forGetter(NoiseLakeConfiguration::underwaterBlock),
            Codec.DOUBLE.fieldOf("shore_start_value").forGetter(NoiseLakeConfiguration::shoreStartValue),
            BlockStateProvider.CODEC.fieldOf("shore_block").forGetter(NoiseLakeConfiguration::shoreBlock),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("shore_noise").forGetter(NoiseLakeConfiguration::shoreNoise),
            Codec.BOOL.fieldOf("frozen").orElse(false).forGetter(NoiseLakeConfiguration::frozen)
    ).apply(instance, NoiseLakeConfiguration::new));
}