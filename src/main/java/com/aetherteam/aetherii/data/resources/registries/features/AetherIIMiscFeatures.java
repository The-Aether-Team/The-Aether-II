package com.aetherteam.aetherii.data.resources.registries.features;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.RockBlock;
import com.aetherteam.aetherii.block.natural.TwigBlock;
import com.aetherteam.aetherii.data.resources.builders.AetherIIFeatureBuilders;
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

public class AetherIIMiscFeatures extends AetherIIFeatureBuilders {
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYROOT_TWIGS = AetherIIFeatureUtils.registerKey("skyroot_twigs");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HOLYSTONE_ROCKS = AetherIIFeatureUtils.registerKey("holystone_rocks");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOA_NEST = AetherIIFeatureUtils.registerKey("moa_nest");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOA_NEST_TREE = AetherIIFeatureUtils.registerKey("moa_nest_tree");

    public static final ResourceKey<ConfiguredFeature<?, ?>> COLD_AERCLOUD = AetherIIFeatureUtils.registerKey("cold_aercloud");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_AERCLOUD = AetherIIFeatureUtils.registerKey("blue_aercloud");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_AERCLOUD = AetherIIFeatureUtils.registerKey("golden_aercloud");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREEN_AERCLOUD = AetherIIFeatureUtils.registerKey("green_aercloud");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_AERCLOUD = AetherIIFeatureUtils.registerKey("purple_aercloud");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STORM_AERCLOUD = AetherIIFeatureUtils.registerKey("storm_aercloud");

    @SuppressWarnings("deprecation")
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {

        SimpleWeightedRandomList.Builder<BlockState> twigs = new SimpleWeightedRandomList.Builder<>();
        for (Direction facing : TwigBlock.FACING.getPossibleValues()) {
            for (int amount : TwigBlock.AMOUNT.getPossibleValues()) {
                twigs.add(AetherIIBlocks.SKYROOT_TWIG.get().defaultBlockState().setValue(TwigBlock.FACING, facing).setValue(TwigBlock.AMOUNT, amount), amount);
            }
        }

        SimpleWeightedRandomList.Builder<BlockState> rocks = new SimpleWeightedRandomList.Builder<>();
        for (Direction facing : RockBlock.FACING.getPossibleValues()) {
            for (int amount : RockBlock.AMOUNT.getPossibleValues()) {
                rocks.add(AetherIIBlocks.HOLYSTONE_ROCK.get().defaultBlockState().setValue(RockBlock.FACING, facing).setValue(RockBlock.AMOUNT, amount), amount);
            }
        }


        AetherIIFeatureUtils.register(context, SKYROOT_TWIGS, Feature.RANDOM_PATCH, NitrogenConfiguredFeatureBuilders.grassPatch(new WeightedStateProvider(twigs), 4));
        AetherIIFeatureUtils.register(context, HOLYSTONE_ROCKS, Feature.RANDOM_PATCH, NitrogenConfiguredFeatureBuilders.grassPatch(new WeightedStateProvider(rocks), 4));

        AetherIIFeatureUtils.register(context, MOA_NEST, AetherIIFeatures.MOA_NEST.get(), new MoaNestConfiguration(BlockStateProvider.simple(AetherIIBlocks.WOVEN_SKYROOT_STICKS.get()), 1.5F, 2, true));
        AetherIIFeatureUtils.register(context, MOA_NEST_TREE, AetherIIFeatures.MOA_NEST.get(), new MoaNestConfiguration(BlockStateProvider.simple(AetherIIBlocks.WOVEN_SKYROOT_STICKS.get()), 1.5F, 2, true));

        AetherIIFeatureUtils.register(context, COLD_AERCLOUD, AetherIIFeatures.AERCLOUD.get(), AetherIIFeatureBuilders.aercloud(16, AetherIIBlocks.COLD_AERCLOUD.get().defaultBlockState()));
        AetherIIFeatureUtils.register(context, BLUE_AERCLOUD, AetherIIFeatures.AERCLOUD.get(), AetherIIFeatureBuilders.aercloud(8, AetherIIBlocks.BLUE_AERCLOUD.get().defaultBlockState()));
        AetherIIFeatureUtils.register(context, GOLDEN_AERCLOUD, AetherIIFeatures.AERCLOUD.get(), AetherIIFeatureBuilders.aercloud(4, AetherIIBlocks.GOLDEN_AERCLOUD.get().defaultBlockState()));
        AetherIIFeatureUtils.register(context, GREEN_AERCLOUD, AetherIIFeatures.AERCLOUD.get(), AetherIIFeatureBuilders.aercloud(8, AetherIIBlocks.GREEN_AERCLOUD.get().defaultBlockState()));
        AetherIIFeatureUtils.register(context, PURPLE_AERCLOUD, AetherIIFeatures.AERCLOUD.get(), AetherIIFeatureBuilders.aercloud(8, AetherIIBlocks.PURPLE_AERCLOUD.get().defaultBlockState()));
        AetherIIFeatureUtils.register(context, STORM_AERCLOUD, AetherIIFeatures.AERCLOUD.get(), AetherIIFeatureBuilders.aercloud(6, AetherIIBlocks.STORM_AERCLOUD.get().defaultBlockState()));
    }
}