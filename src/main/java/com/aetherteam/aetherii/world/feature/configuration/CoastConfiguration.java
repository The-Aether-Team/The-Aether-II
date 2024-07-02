package com.aetherteam.aetherii.world.feature.configuration;

import com.aetherteam.aetherii.world.feature.CoastFeature;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record CoastConfiguration(BlockStateProvider block, CoastFeature.Type type, float size, DensityFunction distanceNoise, UniformInt yRange, HolderSet<Block> validBlocks) implements FeatureConfiguration {
    public static final Codec<CoastConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("block").forGetter(CoastConfiguration::block),
            CoastFeature.Type.CODEC.fieldOf("type").forGetter(CoastConfiguration::type),
            Codec.FLOAT.fieldOf("size").forGetter(CoastConfiguration::size),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("distance_noise").forGetter(CoastConfiguration::distanceNoise),
            UniformInt.CODEC.fieldOf("y_range").forGetter(CoastConfiguration::yRange),
            RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("valid_blocks").forGetter(CoastConfiguration::validBlocks)
    ).apply(instance, CoastConfiguration::new));
}