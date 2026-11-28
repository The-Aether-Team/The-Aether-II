package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record LoganRockConfiguration(BlockStateProvider state, UniformInt radius, TagKey<Block> validBlocks, DensityFunction noise, float inclineFactor, float scatterFactor) implements FeatureConfiguration {
    public static final Codec<LoganRockConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("state").forGetter(LoganRockConfiguration::state),
            UniformInt.MAP_CODEC.fieldOf("base_radius").forGetter(LoganRockConfiguration::radius),
            TagKey.codec(Registries.BLOCK).fieldOf("valid_blocks").forGetter(LoganRockConfiguration::validBlocks),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("noise").forGetter(LoganRockConfiguration::noise),
            Codec.FLOAT.fieldOf("incline_factor").forGetter(LoganRockConfiguration::inclineFactor),
            Codec.FLOAT.fieldOf("scatter_factor").forGetter(LoganRockConfiguration::scatterFactor)
    ).apply(instance, LoganRockConfiguration::new));
}