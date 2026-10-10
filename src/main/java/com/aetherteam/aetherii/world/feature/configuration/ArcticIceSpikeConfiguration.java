package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record ArcticIceSpikeConfiguration(BlockStateProvider mainBlock, BlockStateProvider underBlock, BlockStateProvider crystalBlock, BlockStateProvider sphereBlock, float baseRadius, int additionalRadius, int baseHeight, int additionalHeight, TagKey<Block> validBlocks, TagKey<Block> avoidBlocks) implements FeatureConfiguration {
    public static final Codec<ArcticIceSpikeConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("main_block").forGetter(ArcticIceSpikeConfiguration::mainBlock),
            BlockStateProvider.CODEC.fieldOf("under_block").forGetter(ArcticIceSpikeConfiguration::underBlock),
            BlockStateProvider.CODEC.fieldOf("crystal_block").forGetter(ArcticIceSpikeConfiguration::crystalBlock),
            BlockStateProvider.CODEC.fieldOf("sphere_block").forGetter(ArcticIceSpikeConfiguration::sphereBlock),
            Codec.FLOAT.fieldOf("base_radius").forGetter(ArcticIceSpikeConfiguration::baseRadius),
            Codec.INT.fieldOf("additional_radius").forGetter(ArcticIceSpikeConfiguration::additionalRadius),
            Codec.INT.fieldOf("base_height").forGetter(ArcticIceSpikeConfiguration::baseHeight),
            Codec.INT.fieldOf("additional_height").forGetter(ArcticIceSpikeConfiguration::additionalHeight),
            TagKey.codec(Registries.BLOCK).fieldOf("valid_blocks").forGetter(ArcticIceSpikeConfiguration::validBlocks),
            TagKey.codec(Registries.BLOCK).fieldOf("avoid_blocks").forGetter(ArcticIceSpikeConfiguration::avoidBlocks)
    ).apply(instance, ArcticIceSpikeConfiguration::new));
}