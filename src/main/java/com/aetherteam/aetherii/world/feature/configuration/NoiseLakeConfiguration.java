package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record NoiseLakeConfiguration(DensityFunction lakeNoise, DensityFunction lakeFloorNoise, DensityFunction lakeBarrierNoise, ConstantInt height, BlockStateProvider underwaterBlock, boolean waterfalls) implements FeatureConfiguration {
    public static final Codec<NoiseLakeConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("lake_noise").forGetter(NoiseLakeConfiguration::lakeNoise),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("lake_floor_noise").forGetter(NoiseLakeConfiguration::lakeFloorNoise),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("lake_barrier_noise").forGetter(NoiseLakeConfiguration::lakeBarrierNoise),
            ConstantInt.CODEC.fieldOf("height").forGetter(NoiseLakeConfiguration::height),
            BlockStateProvider.CODEC.fieldOf("underwater_block").forGetter(NoiseLakeConfiguration::underwaterBlock),
            Codec.BOOL.fieldOf("generate_waterfalls").orElse(false).forGetter(NoiseLakeConfiguration::waterfalls)
    ).apply(instance, NoiseLakeConfiguration::new));
}