package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record AlkahestNoiseLakeConfiguration(DensityFunction noise, UniformInt radius, int height, float inclineFactor, float scatterFactor) implements FeatureConfiguration {
    public static final Codec<AlkahestNoiseLakeConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("noise").forGetter(AlkahestNoiseLakeConfiguration::noise),
            UniformInt.MAP_CODEC.fieldOf("radius").forGetter(AlkahestNoiseLakeConfiguration::radius),
            Codec.INT.fieldOf("height").forGetter(AlkahestNoiseLakeConfiguration::height),
            Codec.FLOAT.fieldOf("incline_factor").forGetter(AlkahestNoiseLakeConfiguration::inclineFactor),
            Codec.FLOAT.fieldOf("scatter_factor").forGetter(AlkahestNoiseLakeConfiguration::scatterFactor)
    ).apply(instance, AlkahestNoiseLakeConfiguration::new));
}