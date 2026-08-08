package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record HedgeConfiguration(BlockStateProvider block, IntProvider size) implements FeatureConfiguration {
    public static final Codec<HedgeConfiguration> CODEC = RecordCodecBuilder.create((i) -> i.group(
            BlockStateProvider.CODEC.fieldOf("block").forGetter((c) -> c.block),
            IntProviders.CODEC.fieldOf("size").forGetter((c) -> c.size)
    ).apply(i, HedgeConfiguration::new));
}
