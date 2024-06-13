package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record MoaNestConfiguration(BlockStateProvider nestBlock, ConstantFloat baseRadius, ConstantFloat additionalRadius) implements FeatureConfiguration {
    public static final Codec<MoaNestConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("nest_block").forGetter(MoaNestConfiguration::nestBlock),
            ConstantFloat.CODEC.fieldOf("base_radius").forGetter(MoaNestConfiguration::baseRadius),
            ConstantFloat.CODEC.fieldOf("additional_radius").forGetter(MoaNestConfiguration::additionalRadius)
    ).apply(instance, MoaNestConfiguration::new));
}