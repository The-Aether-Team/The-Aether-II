package com.aetherteam.aetherii.world.feature.configuration;

import com.aetherteam.aetherii.world.feature.NoiseLakeFeature;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record NoiseLakeConfiguration(DensityFunction lakeNoise, DensityFunction lakeFloorNoise, DensityFunction lakeBarrierNoise, double noiseStartValue, ConstantInt height, BlockStateProvider underwaterBlock, NoiseLakeFeature.Type type, boolean frozen) implements FeatureConfiguration {
    public static final Codec<NoiseLakeConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("lake_noise").forGetter(NoiseLakeConfiguration::lakeNoise),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("lake_floor_noise").forGetter(NoiseLakeConfiguration::lakeFloorNoise),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("lake_barrier_noise").forGetter(NoiseLakeConfiguration::lakeBarrierNoise),
            Codec.DOUBLE.fieldOf("noise_start_value").forGetter(NoiseLakeConfiguration::noiseStartValue),
            ConstantInt.CODEC.fieldOf("height").forGetter(NoiseLakeConfiguration::height),
            BlockStateProvider.CODEC.fieldOf("underwater_block").forGetter(NoiseLakeConfiguration::underwaterBlock),
            NoiseLakeFeature.Type.CODEC.fieldOf("type").forGetter(NoiseLakeConfiguration::type),
            Codec.BOOL.fieldOf("frozen").orElse(false).forGetter(NoiseLakeConfiguration::frozen)
    ).apply(instance, NoiseLakeConfiguration::new));
}