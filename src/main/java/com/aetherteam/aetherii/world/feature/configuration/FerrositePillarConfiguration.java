package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record FerrositePillarConfiguration(BlockStateProvider baseBlock, BlockStateProvider strataBlock, DensityFunction strataNoise, UniformFloat radius, UniformInt height, TagKey<Block> validBlocks) implements FeatureConfiguration {
    public static final Codec<FerrositePillarConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("base_block").forGetter(FerrositePillarConfiguration::baseBlock),
            BlockStateProvider.CODEC.fieldOf("strata_block").forGetter(FerrositePillarConfiguration::strataBlock),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("strata_noise").forGetter(FerrositePillarConfiguration::strataNoise),
            UniformFloat.MAP_CODEC.fieldOf("radius").forGetter(FerrositePillarConfiguration::radius),
            UniformInt.MAP_CODEC.fieldOf("height").forGetter(FerrositePillarConfiguration::height),
            TagKey.codec(Registries.BLOCK).fieldOf("valid_blocks").forGetter(FerrositePillarConfiguration::validBlocks)
    ).apply(instance, FerrositePillarConfiguration::new));
}