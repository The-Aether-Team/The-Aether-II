package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record AcidPoolConfiguration(IntProvider count, IntProvider radius, IntProvider offset) implements FeatureConfiguration {
    public static final Codec<AcidPoolConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            IntProvider.CODEC.fieldOf("count").forGetter(AcidPoolConfiguration::count),
            IntProvider.CODEC.fieldOf("radius").forGetter(AcidPoolConfiguration::radius),
            IntProvider.CODEC.fieldOf("offset").forGetter(AcidPoolConfiguration::offset)
    ).apply(instance, AcidPoolConfiguration::new));
}
