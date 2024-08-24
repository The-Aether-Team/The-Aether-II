package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.Optional;

public record FallenLogConfiguration(BlockStateProvider block, IntProvider length, Optional<Holder<PlacedFeature>> vegetationFeature, float vegetationChance, TagKey<Block> validBlocks) implements FeatureConfiguration {
    public static final Codec<FallenLogConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("block").forGetter(FallenLogConfiguration::block),
            IntProvider.CODEC.fieldOf("y_range").forGetter(FallenLogConfiguration::length),
            PlacedFeature.CODEC.optionalFieldOf("vegetation_feature").forGetter(FallenLogConfiguration::vegetationFeature),
            Codec.floatRange(0.0F, 1.0F).fieldOf("vegetation_chance").forGetter(FallenLogConfiguration::vegetationChance),
            TagKey.codec(Registries.BLOCK).fieldOf("valid_blocks").forGetter(FallenLogConfiguration::validBlocks)
    ).apply(instance, FallenLogConfiguration::new));
}
