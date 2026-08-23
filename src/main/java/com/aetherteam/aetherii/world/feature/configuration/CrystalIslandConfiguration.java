package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record CrystalIslandConfiguration(BlockStateProvider block, DensityFunction noise, float radius, int height, float inclineFactor, float scatterFactor) implements FeatureConfiguration {
    public static final Codec<CrystalIslandConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("block").forGetter(CrystalIslandConfiguration::block),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("noise").forGetter(CrystalIslandConfiguration::noise),
            Codec.FLOAT.fieldOf("radius").forGetter(CrystalIslandConfiguration::radius),
            Codec.INT.fieldOf("height").forGetter(CrystalIslandConfiguration::height),
            Codec.FLOAT.fieldOf("incline_factor").forGetter(CrystalIslandConfiguration::inclineFactor),
            Codec.FLOAT.fieldOf("scatter_factor").forGetter(CrystalIslandConfiguration::scatterFactor)
    ).apply(instance, CrystalIslandConfiguration::new));
}