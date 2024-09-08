package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.Optional;

public record CoastConfiguration(BlockStateProvider block, float size, DensityFunction distanceNoise, Optional<DensityFunction> patternNoise, UniformInt yRange, Optional<Holder<PlacedFeature>> vegetationFeature, float vegetationChance, TagKey<Block> validBlocks) implements FeatureConfiguration {
    public static final Codec<CoastConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("block").forGetter(CoastConfiguration::block),
            Codec.FLOAT.fieldOf("size").forGetter(CoastConfiguration::size),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("distance_noise").forGetter(CoastConfiguration::distanceNoise),
            DensityFunction.HOLDER_HELPER_CODEC.optionalFieldOf("pattern_noise").forGetter(CoastConfiguration::patternNoise),
            UniformInt.CODEC.fieldOf("y_range").forGetter(CoastConfiguration::yRange),
            PlacedFeature.CODEC.optionalFieldOf("vegetation_feature").forGetter(CoastConfiguration::vegetationFeature),
            Codec.floatRange(0.0F, 1.0F).fieldOf("vegetation_chance").forGetter(CoastConfiguration::vegetationChance),
            TagKey.codec(Registries.BLOCK).fieldOf("valid_blocks").forGetter(CoastConfiguration::validBlocks)
    ).apply(instance, CoastConfiguration::new));
}