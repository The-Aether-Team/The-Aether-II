package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record MossVinesConfiguration(BlockStateProvider blockStateProvider) implements FeatureConfiguration {
    public static final Codec<MossVinesConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("blocks").forGetter(MossVinesConfiguration::blockStateProvider)
    ).apply(instance, MossVinesConfiguration::new));
}
