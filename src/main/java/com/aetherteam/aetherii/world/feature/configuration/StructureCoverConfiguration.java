package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record StructureCoverConfiguration(BlockStateProvider block, BlockStateProvider secondaryBlock, int blockTransitionHeight, DensityFunction noise, float radius, int height, float inclineFactor, float scatterFactor) implements FeatureConfiguration {
    public static final Codec<StructureCoverConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("block").forGetter(StructureCoverConfiguration::block),
            BlockStateProvider.CODEC.fieldOf("secondary_block").forGetter(StructureCoverConfiguration::secondaryBlock),
            Codec.INT.fieldOf("block_transition_height").forGetter(StructureCoverConfiguration::blockTransitionHeight),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("noise").forGetter(StructureCoverConfiguration::noise),
            Codec.FLOAT.fieldOf("radius").forGetter(StructureCoverConfiguration::radius),
            Codec.INT.fieldOf("height").forGetter(StructureCoverConfiguration::height),
            Codec.FLOAT.fieldOf("incline_factor").forGetter(StructureCoverConfiguration::inclineFactor),
            Codec.FLOAT.fieldOf("scatter_factor").forGetter(StructureCoverConfiguration::scatterFactor)
    ).apply(instance, StructureCoverConfiguration::new));
}