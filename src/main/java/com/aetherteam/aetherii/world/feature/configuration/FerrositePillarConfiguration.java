package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record FerrositePillarConfiguration(BlockStateProvider block, float baseRadius, int additionalRadius, int baseHeight, int additionalHeight, TagKey<Block> validBlocks) implements FeatureConfiguration {
    public static final Codec<FerrositePillarConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("block").forGetter(FerrositePillarConfiguration::block),
            Codec.FLOAT.fieldOf("base_radius").forGetter(FerrositePillarConfiguration::baseRadius),
            Codec.INT.fieldOf("additional_radius").forGetter(FerrositePillarConfiguration::additionalRadius),
            Codec.INT.fieldOf("base_height").forGetter(FerrositePillarConfiguration::baseHeight),
            Codec.INT.fieldOf("additional_height").forGetter(FerrositePillarConfiguration::additionalHeight),
            TagKey.codec(Registries.BLOCK).fieldOf("valid_blocks").forGetter(FerrositePillarConfiguration::validBlocks)
    ).apply(instance, FerrositePillarConfiguration::new));
}