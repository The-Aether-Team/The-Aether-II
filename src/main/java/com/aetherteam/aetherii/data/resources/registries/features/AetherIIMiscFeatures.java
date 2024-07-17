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
import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.material.Fluids;

import java.util.List;
import java.util.Optional;

public class AetherIIMiscFeatures extends AetherIIFeatureBuilders {
    public static final ResourceKey<ConfiguredFeature<?, ?>> COAST_QUICKSOIL = AetherIIFeatureUtils.registerKey("coast_quicksoil");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COAST_FERROSITE_SAND = AetherIIFeatureUtils.registerKey("coast_ferrosite_sand");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COAST_ARCTIC_PACKED_ICE = AetherIIFeatureUtils.registerKey("coast_arctic_packed_ice");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NOISE_LAKE = AetherIIFeatureUtils.registerKey("noise_lake");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NOISE_LAKE_ARCTIC = AetherIIFeatureUtils.registerKey("noise_lake_arctic");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FERROSITE_PILLAR = AetherIIFeatureUtils.registerKey("ferrosite_pillar");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AETHER_WATER_LAKE = AetherIIFeatureUtils.registerKey("aether_water_lake");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AETHER_WATER_SPRING = AetherIIFeatureUtils.registerKey("aether_water_spring");
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
    public static final ResourceKey<ConfiguredFeature<?, ?>> CLOUDBED = AetherIIFeatureUtils.registerKey("cloudbed");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FREEZE_TOP_LAYER_ARCTIC = AetherIIFeatureUtils.registerKey("freeze_top_layer_arctic");

    @SuppressWarnings("deprecation")
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<DensityFunction> function = context.lookup(Registries.DENSITY_FUNCTION);

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

        AetherIIFeatureUtils.register(context, COAST_QUICKSOIL, AetherIIFeatures.COAST.get(), new CoastConfiguration(
                BlockStateProvider.simple(AetherIIBlocks.QUICKSOIL.get()),
                CoastFeature.Type.HIGHFIELDS,
                16.35F,
                AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.COASTS_HIGHFIELDS),
                Optional.empty(),
                UniformInt.of(112, 156),
                0.75F,
                HolderSet.direct(Block::builtInRegistryHolder, AetherIIBlocks.AETHER_GRASS_BLOCK.get())
        ));
        AetherIIFeatureUtils.register(context, COAST_FERROSITE_SAND, AetherIIFeatures.COAST.get(), new CoastConfiguration(
                BlockStateProvider.simple(AetherIIBlocks.FERROSITE_SAND.get()),
                CoastFeature.Type.MAGNETIC,
                16.35F,
                AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.COASTS_HIGHFIELDS),
                Optional.of(DensityFunctions.zero()),
                UniformInt.of(112, 156),
                0.0F,
                HolderSet.direct(Block::builtInRegistryHolder, AetherIIBlocks.AETHER_GRASS_BLOCK.get())
        ));
        AetherIIFeatureUtils.register(context, COAST_ARCTIC_PACKED_ICE, AetherIIFeatures.COAST.get(), new CoastConfiguration(
                BlockStateProvider.simple(AetherIIBlocks.ARCTIC_PACKED_ICE.get()),
                CoastFeature.Type.ARCTIC,
                16.35F,
                AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.COASTS_ARCTIC),
                Optional.empty(),
                UniformInt.of(120, 180),
                0.0F,
                HolderSet.direct(Block::builtInRegistryHolder, AetherIIBlocks.AETHER_GRASS_BLOCK.get())
        ));

        AetherIIFeatureUtils.register(context, NOISE_LAKE, AetherIIFeatures.NOISE_LAKE.get(),
                new NoiseLakeConfiguration(
                        AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.LAKES_NOISE),
                        AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.LAKES_FLOOR),
                        AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.LAKES_BARRIER),
                        ConstantInt.of(124),
                        new NoiseProvider(
                                100L,
                                new NormalNoise.NoiseParameters(0, 1.0),
                                0.075F,
                                List.of(
                                        AetherIIBlocks.AETHER_DIRT.get().defaultBlockState(),
                                        AetherIIBlocks.HOLYSTONE.get().defaultBlockState()
                                )
                        ),
                        false
                ));
        AetherIIFeatureUtils.register(context, NOISE_LAKE_ARCTIC, AetherIIFeatures.NOISE_LAKE.get(),
                new NoiseLakeConfiguration(
                        AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.LAKES_NOISE),
                        AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.LAKES_FLOOR),
                        AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.LAKES_BARRIER),
                        ConstantInt.of(124),
                        new NoiseProvider(
                                100L,
                                new NormalNoise.NoiseParameters(0, 1.0),
                                0.075F,
                                List.of(
                                        AetherIIBlocks.AETHER_DIRT.get().defaultBlockState(),
                                        AetherIIBlocks.HOLYSTONE.get().defaultBlockState()
                                )
                        ),
                        true
                ));

        AetherIIFeatureUtils.register(context, FERROSITE_PILLAR, AetherIIFeatures.FERROSITE_PILLAR.get(), new FerrositePillarConfiguration(
                new NoiseProvider(
                        200L,
                        new NormalNoise.NoiseParameters(0, 1.0),
                        0.0633F,
                        List.of(
                                AetherIIBlocks.FERROSITE.get().defaultBlockState(),
                                AetherIIBlocks.FERROSITE.get().defaultBlockState(),
                                AetherIIBlocks.RUSTED_FERROSITE.get().defaultBlockState()
                        )
                ),
                4.5F,
                6,
                24,
                16,
                HolderSet.direct(Block::builtInRegistryHolder, AetherIIBlocks.AETHER_GRASS_BLOCK.get())
        ));

        AetherIIFeatureUtils.register(context, AETHER_WATER_LAKE, AetherIIFeatures.LAKE.get(),
                new AetherLakeConfiguration(BlockStateProvider.simple(Blocks.WATER), BlockStateProvider.simple(AetherIIBlocks.AETHER_GRASS_BLOCK.get())));
        AetherIIFeatureUtils.register(context, AETHER_WATER_SPRING, Feature.SPRING,
                new SpringConfiguration(Fluids.WATER.defaultFluidState(), true, 4, 1, HolderSet.direct(Block::builtInRegistryHolder, AetherIIBlocks.UNDERSHALE.get(), AetherIIBlocks.HOLYSTONE.get(), AetherIIBlocks.AETHER_DIRT.get())));

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

        AetherIIFeatureUtils.register(context, CLOUDBED, AetherIIFeatures.CLOUDBED.get(),
                new CloudbedConfiguration(
                        BlockStateProvider.simple(AetherIIBlocks.COLD_AERCLOUD.get().defaultBlockState()),
                        BlockPredicate.ONLY_IN_AIR_PREDICATE,
                        96,
                        AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.CLOUDBED_NOISE),
                        10D,
                        AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.CLOUDBED_Y_OFFSET),
                        15D
                ));

        AetherIIFeatureUtils.register(context, FREEZE_TOP_LAYER_ARCTIC, AetherIIFeatures.FREEZE_TOP_LAYER_ARCTIC.get());
    }
}