package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record HugeMagneticShroomConfiguration(BlockStateProvider capProvider, BlockStateProvider bottomCapProvider, BlockStateProvider stemProvider) implements FeatureConfiguration {
    public static final Codec<HugeMagneticShroomConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("cap_provider").forGetter(HugeMagneticShroomConfiguration::capProvider),
            BlockStateProvider.CODEC.fieldOf("bottom_cap_provider").forGetter(HugeMagneticShroomConfiguration::bottomCapProvider),
            BlockStateProvider.CODEC.fieldOf("stem_provider").forGetter(HugeMagneticShroomConfiguration::stemProvider)
    ).apply(instance, HugeMagneticShroomConfiguration::new));
}
