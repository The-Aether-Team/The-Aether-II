package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record AlkahestPoolConfiguration(IntProvider count, IntProvider radius, IntProvider offset) implements FeatureConfiguration {
    public static final Codec<AlkahestPoolConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            IntProviders.CODEC.fieldOf("count").forGetter(AlkahestPoolConfiguration::count),
            IntProviders.CODEC.fieldOf("radius").forGetter(AlkahestPoolConfiguration::radius),
            IntProviders.CODEC.fieldOf("offset").forGetter(AlkahestPoolConfiguration::offset)
    ).apply(instance, AlkahestPoolConfiguration::new));
}
