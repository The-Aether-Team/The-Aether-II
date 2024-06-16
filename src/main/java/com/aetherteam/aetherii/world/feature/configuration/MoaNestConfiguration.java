package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record MoaNestConfiguration(BlockStateProvider nestBlock, float baseRadius, int additionalRadius, boolean spawnMoas) implements FeatureConfiguration {
    public static final Codec<MoaNestConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("nest_block").forGetter(MoaNestConfiguration::nestBlock),
            Codec.FLOAT.fieldOf("base_radius").forGetter(MoaNestConfiguration::baseRadius),
            Codec.INT.fieldOf("additional_radius").forGetter(MoaNestConfiguration::additionalRadius),
            Codec.BOOL.fieldOf("spawn_moas").orElse(false).forGetter(MoaNestConfiguration::spawnMoas)
    ).apply(instance, MoaNestConfiguration::new));
}