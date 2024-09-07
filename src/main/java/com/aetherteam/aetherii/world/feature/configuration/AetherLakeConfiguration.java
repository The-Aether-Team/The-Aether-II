package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record AetherLakeConfiguration(IntProvider shrinkScale, BlockStateProvider fluid, BlockStateProvider top) implements FeatureConfiguration {
    public static final Codec<AetherLakeConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            IntProvider.CODEC.fieldOf("shrink_scale").forGetter(AetherLakeConfiguration::shrinkScale),
            BlockStateProvider.CODEC.fieldOf("fluid").forGetter(AetherLakeConfiguration::fluid),
            BlockStateProvider.CODEC.fieldOf("top").forGetter(AetherLakeConfiguration::top)
    ).apply(instance, AetherLakeConfiguration::new));
}