package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record ArcticIceSpikeConfiguration(BlockStateProvider block, float baseRadius, int additionalRadius, float baseSlopeIntensity, int additionalSlopeIntensity, TagKey<Block> validBlocks) implements FeatureConfiguration {
    public static final Codec<ArcticIceSpikeConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("block").forGetter(ArcticIceSpikeConfiguration::block),
            Codec.FLOAT.fieldOf("base_radius").forGetter(ArcticIceSpikeConfiguration::baseRadius),
            Codec.INT.fieldOf("additional_radius").forGetter(ArcticIceSpikeConfiguration::additionalRadius),
            Codec.FLOAT.fieldOf("base_slope_intensity").forGetter(ArcticIceSpikeConfiguration::baseSlopeIntensity),
            Codec.INT.fieldOf("additional_slope_intensity").forGetter(ArcticIceSpikeConfiguration::additionalSlopeIntensity),
            TagKey.codec(Registries.BLOCK).fieldOf("valid_blocks").forGetter(ArcticIceSpikeConfiguration::validBlocks)
    ).apply(instance, ArcticIceSpikeConfiguration::new));
}