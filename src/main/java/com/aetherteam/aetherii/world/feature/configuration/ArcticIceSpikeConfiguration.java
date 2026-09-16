package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record ArcticIceSpikeConfiguration(BlockStateProvider block, float baseRadius, float endRadius ,int additionalRadius, int baseHeight, int additionalHeight, TagKey<Block> validBlocks) implements FeatureConfiguration {
    public static final Codec<ArcticIceSpikeConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("block").forGetter(ArcticIceSpikeConfiguration::block),
            Codec.FLOAT.fieldOf("base_radius").forGetter(ArcticIceSpikeConfiguration::baseRadius),
            Codec.FLOAT.fieldOf("end_radius").forGetter(ArcticIceSpikeConfiguration::endRadius),
            Codec.INT.fieldOf("additional_radius").forGetter(ArcticIceSpikeConfiguration::additionalRadius),
            Codec.INT.fieldOf("base_height").forGetter(ArcticIceSpikeConfiguration::baseHeight),
            Codec.INT.fieldOf("additional_height").forGetter(ArcticIceSpikeConfiguration::additionalHeight),
            TagKey.codec(Registries.BLOCK).fieldOf("valid_blocks").forGetter(ArcticIceSpikeConfiguration::validBlocks)
    ).apply(instance, ArcticIceSpikeConfiguration::new));
}