package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record FerrositePillarConfiguration(BlockStateProvider block, FloatProvider radius, IntProvider height, IntProvider floatingHeight, HolderSet<Block> validBlocks) implements FeatureConfiguration {
    public static final Codec<FerrositePillarConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("block").forGetter(FerrositePillarConfiguration::block),
            FloatProvider.CODEC.fieldOf("radius").forGetter(FerrositePillarConfiguration::radius),
            IntProvider.CODEC.fieldOf("height").forGetter(FerrositePillarConfiguration::height),
            IntProvider.CODEC.fieldOf("floating_height").forGetter(FerrositePillarConfiguration::floatingHeight),
            RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("valid_blocks").forGetter(FerrositePillarConfiguration::validBlocks)
    ).apply(instance, FerrositePillarConfiguration::new));
}