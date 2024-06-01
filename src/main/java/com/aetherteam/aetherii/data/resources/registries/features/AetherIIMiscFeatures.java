package com.aetherteam.aetherii.data.resources.registries.features;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.RockBlock;
import com.aetherteam.aetherii.data.resources.builders.AetherIIFeatureBuilders;
import com.aetherteam.aetherii.world.feature.AetherIIFeatures;
import com.aetherteam.nitrogen.data.resources.builders.NitrogenConfiguredFeatureBuilders;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

public class AetherIIMiscFeatures extends AetherIIFeatureBuilders {
    public static final ResourceKey<ConfiguredFeature<?, ?>> COAST_QUICKSOIL = AetherIIFeatureUtils.registerKey("coast_quicksoil");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COAST_FERROSITE_SAND = AetherIIFeatureUtils.registerKey("coast_ferrosite_sand");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HOLYSTONE_ROCKS = AetherIIFeatureUtils.registerKey("holystone_rocks");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOA_NEST = AetherIIFeatureUtils.registerKey("moa_nest");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COLD_AERCLOUD = AetherIIFeatureUtils.registerKey("cold_aercloud");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_AERCLOUD = AetherIIFeatureUtils.registerKey("blue_aercloud");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_AERCLOUD = AetherIIFeatureUtils.registerKey("golden_aercloud");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREEN_AERCLOUD = AetherIIFeatureUtils.registerKey("green_aercloud");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_AERCLOUD = AetherIIFeatureUtils.registerKey("purple_aercloud");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STORM_AERCLOUD = AetherIIFeatureUtils.registerKey("storm_aercloud");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        SimpleWeightedRandomList.Builder<BlockState> pebbles = new SimpleWeightedRandomList.Builder<>();
        for (Direction facing : RockBlock.FACING.getPossibleValues()) {
            for (int amount : RockBlock.AMOUNT.getPossibleValues()) {
                pebbles.add(AetherIIBlocks.HOLYSTONE_ROCK.get().defaultBlockState().setValue(RockBlock.FACING, facing).setValue(RockBlock.AMOUNT, amount), amount);
            }
        }

        AetherIIFeatureUtils.register(context, COAST_QUICKSOIL, AetherIIFeatures.COAST.get(), createCoast(AetherIIBlocks.QUICKSOIL.get().defaultBlockState()));
        AetherIIFeatureUtils.register(context, COAST_FERROSITE_SAND, AetherIIFeatures.COAST.get(), createCoast(AetherIIBlocks.FERROSITE_SAND.get().defaultBlockState()));

        AetherIIFeatureUtils.register(context, HOLYSTONE_ROCKS, Feature.RANDOM_PATCH, NitrogenConfiguredFeatureBuilders.grassPatch(new WeightedStateProvider(pebbles), 4));

        AetherIIFeatureUtils.register(context, MOA_NEST, AetherIIFeatures.MOA_NEST.get());

        AetherIIFeatureUtils.register(context, COLD_AERCLOUD, AetherIIFeatures.AERCLOUD.get(), AetherIIFeatureBuilders.aercloud(16, AetherIIBlocks.COLD_AERCLOUD.get().defaultBlockState()));
        AetherIIFeatureUtils.register(context, BLUE_AERCLOUD, AetherIIFeatures.AERCLOUD.get(), AetherIIFeatureBuilders.aercloud(8, AetherIIBlocks.BLUE_AERCLOUD.get().defaultBlockState()));
        AetherIIFeatureUtils.register(context, GOLDEN_AERCLOUD, AetherIIFeatures.AERCLOUD.get(), AetherIIFeatureBuilders.aercloud(4, AetherIIBlocks.GOLDEN_AERCLOUD.get().defaultBlockState()));
        AetherIIFeatureUtils.register(context, GREEN_AERCLOUD, AetherIIFeatures.AERCLOUD.get(), AetherIIFeatureBuilders.aercloud(8, AetherIIBlocks.GREEN_AERCLOUD.get().defaultBlockState()));
        AetherIIFeatureUtils.register(context, PURPLE_AERCLOUD, AetherIIFeatures.AERCLOUD.get(), AetherIIFeatureBuilders.aercloud(8, AetherIIBlocks.PURPLE_AERCLOUD.get().defaultBlockState()));
        AetherIIFeatureUtils.register(context, STORM_AERCLOUD, AetherIIFeatures.AERCLOUD.get(), AetherIIFeatureBuilders.aercloud(6, AetherIIBlocks.STORM_AERCLOUD.get().defaultBlockState()));
    }
}