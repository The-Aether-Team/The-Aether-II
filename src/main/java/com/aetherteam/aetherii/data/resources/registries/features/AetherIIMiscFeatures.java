package com.aetherteam.aetherii.data.resources.registries.features;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.RockBlock;
import com.aetherteam.aetherii.block.natural.TwigBlock;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDensityFunctions;
import com.aetherteam.aetherii.world.feature.AetherIIFeatures;
import com.aetherteam.aetherii.world.feature.CoastFeature;
import com.aetherteam.aetherii.world.feature.configuration.*;
import com.aetherteam.nitrogen.data.resources.builders.NitrogenConfiguredFeatureBuilders;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.material.Fluids;

import java.util.List;
import java.util.Optional;

public class AetherIIMiscFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOA_NEST = AetherIIFeatureUtils.registerKey("moa_nest");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOA_NEST_TREE = AetherIIFeatureUtils.registerKey("moa_nest_tree");

    @SuppressWarnings("deprecation")
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        AetherIIFeatureUtils.register(context, MOA_NEST, AetherIIFeatures.MOA_NEST.get(), new MoaNestConfiguration(BlockStateProvider.simple(AetherIIBlocks.WOVEN_SKYROOT_STICKS.get()), 1.5F, 2, true));
        AetherIIFeatureUtils.register(context, MOA_NEST_TREE, AetherIIFeatures.MOA_NEST.get(), new MoaNestConfiguration(BlockStateProvider.simple(AetherIIBlocks.WOVEN_SKYROOT_STICKS.get()), 1.5F, 2, true));
    }
}