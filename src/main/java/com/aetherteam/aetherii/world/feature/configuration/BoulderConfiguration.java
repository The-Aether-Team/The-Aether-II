package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.Optional;

public record BoulderConfiguration(BlockStateProvider block, float radius, FloatProvider variation, Optional<Holder<PlacedFeature>> vegetationFeature, float vegetationChance, TagKey<Block> validBlocks) implements FeatureConfiguration {
    public static final Codec<BoulderConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("block").forGetter(BoulderConfiguration::block),
            Codec.FLOAT.fieldOf("radius").forGetter(BoulderConfiguration::radius),
            FloatProvider.CODEC.fieldOf("variation").forGetter(BoulderConfiguration::variation),
            PlacedFeature.CODEC.optionalFieldOf("vegetation_feature").forGetter(BoulderConfiguration::vegetationFeature),
            Codec.floatRange(0.0F, 1.0F).fieldOf("vegetation_chance").forGetter(BoulderConfiguration::vegetationChance),
            TagKey.codec(Registries.BLOCK).fieldOf("valid_blocks").forGetter(BoulderConfiguration::validBlocks)
    ).apply(instance, BoulderConfiguration::new));
}
