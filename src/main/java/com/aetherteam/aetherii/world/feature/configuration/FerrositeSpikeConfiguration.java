package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record FerrositeSpikeConfiguration(BlockStateProvider state, UniformInt radius, TagKey<Block> validBlocks, DensityFunction noise, float inclineFactor, float scatterFactor) implements FeatureConfiguration {
    public static final Codec<FerrositeSpikeConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("state").forGetter(FerrositeSpikeConfiguration::state),
            UniformInt.MAP_CODEC.fieldOf("radius").forGetter(FerrositeSpikeConfiguration::radius),
            TagKey.codec(Registries.BLOCK).fieldOf("valid_blocks").forGetter(FerrositeSpikeConfiguration::validBlocks),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("noise").forGetter(FerrositeSpikeConfiguration::noise),
            Codec.FLOAT.fieldOf("incline_factor").forGetter(FerrositeSpikeConfiguration::inclineFactor),
            Codec.FLOAT.fieldOf("scatter_factor").forGetter(FerrositeSpikeConfiguration::scatterFactor)
    ).apply(instance, FerrositeSpikeConfiguration::new));
}