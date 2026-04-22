package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record StructureCoverConfiguration(BlockStateProvider block, DensityFunction noise, float radius, int height) implements FeatureConfiguration {
    public static final Codec<StructureCoverConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("block").forGetter(StructureCoverConfiguration::block),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("noise").forGetter(StructureCoverConfiguration::noise),
            Codec.FLOAT.fieldOf("radius").forGetter(StructureCoverConfiguration::radius),
            Codec.INT.fieldOf("height").forGetter(StructureCoverConfiguration::height)
    ).apply(instance, StructureCoverConfiguration::new));
}