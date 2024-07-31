package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record FerrositeSpikeConfiguration(BlockStateProvider block, float baseRadius, int additionalRadius, HolderSet<Block> validBlocks) implements FeatureConfiguration {
    public static final Codec<FerrositeSpikeConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("block").forGetter(FerrositeSpikeConfiguration::block),
            Codec.FLOAT.fieldOf("base_radius").forGetter(FerrositeSpikeConfiguration::baseRadius),
            Codec.INT.fieldOf("additional_radius").forGetter(FerrositeSpikeConfiguration::additionalRadius),
            RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("valid_blocks").forGetter(FerrositeSpikeConfiguration::validBlocks)
    ).apply(instance, FerrositeSpikeConfiguration::new));
}