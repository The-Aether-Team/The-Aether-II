package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record FerrositeSpikeConfiguration(BlockStateProvider baseBlock, BlockStateProvider strataBlock, DensityFunction strataNoise, float baseRadius, int additionalRadius, TagKey<Block> validBlocks) implements FeatureConfiguration {
    public static final Codec<FerrositeSpikeConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("base_block").forGetter(FerrositeSpikeConfiguration::baseBlock),
            BlockStateProvider.CODEC.fieldOf("strata_block").forGetter(FerrositeSpikeConfiguration::strataBlock),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("strata_noise").forGetter(FerrositeSpikeConfiguration::strataNoise),
            Codec.FLOAT.fieldOf("base_radius").forGetter(FerrositeSpikeConfiguration::baseRadius),
            Codec.INT.fieldOf("additional_radius").forGetter(FerrositeSpikeConfiguration::additionalRadius),
            TagKey.codec(Registries.BLOCK).fieldOf("valid_blocks").forGetter(FerrositeSpikeConfiguration::validBlocks)
    ).apply(instance, FerrositeSpikeConfiguration::new));
}