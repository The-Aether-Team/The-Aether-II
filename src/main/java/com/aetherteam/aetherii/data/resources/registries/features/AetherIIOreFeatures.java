package com.aetherteam.aetherii.data.resources.registries.features;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.AetherIIFeatureRules;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

public class AetherIIOreFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SCATTERGLASS = AetherIIFeatureUtils.registerKey("ore_scatterglass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_MOSSY_HOLYSTONE = AetherIIFeatureUtils.registerKey("ore_mossy_holystone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_AGIOSITE = AetherIIFeatureUtils.registerKey("ore_agiosite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ICESTONE = AetherIIFeatureUtils.registerKey("ore_icestone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ICESTONE_SMALL = AetherIIFeatureUtils.registerKey("ore_icestone_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_AMBROSIUM = AetherIIFeatureUtils.registerKey("ore_ambrosium");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ZANITE = AetherIIFeatureUtils.registerKey("ore_zanite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ARKENIUM = AetherIIFeatureUtils.registerKey("ore_arkenium");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GRAVITITE_BURIED = AetherIIFeatureUtils.registerKey("ore_gravitite_buried");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GRAVITITE = AetherIIFeatureUtils.registerKey("ore_gravitite");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        AetherIIFeatureUtils.register(context, ORE_SCATTERGLASS, Feature.ORE, new OreConfiguration(AetherIIFeatureRules.UNDERGROUND, AetherIIBlocks.CRUDE_SCATTERGLASS.get().defaultBlockState(), 32));
        AetherIIFeatureUtils.register(context, ORE_MOSSY_HOLYSTONE, Feature.ORE, new OreConfiguration(AetherIIFeatureRules.HOLYSTONE, AetherIIBlocks.MOSSY_HOLYSTONE.get().defaultBlockState(), 64));
        AetherIIFeatureUtils.register(context, ORE_AGIOSITE, Feature.ORE, new OreConfiguration(AetherIIFeatureRules.UNDERSHALE, AetherIIBlocks.AGIOSITE.get().defaultBlockState(), 64));
        AetherIIFeatureUtils.register(context, ORE_ICESTONE, Feature.ORE, new OreConfiguration(AetherIIFeatureRules.HOLYSTONE, AetherIIBlocks.ICESTONE.get().defaultBlockState(), 32));
        AetherIIFeatureUtils.register(context, ORE_ICESTONE_SMALL, Feature.ORE, new OreConfiguration(AetherIIFeatureRules.HOLYSTONE, AetherIIBlocks.ICESTONE.get().defaultBlockState(), 16));
        AetherIIFeatureUtils.register(context, ORE_AMBROSIUM, Feature.ORE, new OreConfiguration(AetherIIFeatureRules.HOLYSTONE, AetherIIBlocks.AMBROSIUM_ORE.get().defaultBlockState(), 16));
        AetherIIFeatureUtils.register(context, ORE_ZANITE, Feature.ORE, new OreConfiguration(AetherIIFeatureRules.HOLYSTONE, AetherIIBlocks.ZANITE_ORE.get().defaultBlockState(), 5, 0.5F));
        AetherIIFeatureUtils.register(context, ORE_ARKENIUM, Feature.ORE, new OreConfiguration(AetherIIFeatureRules.HOLYSTONE, AetherIIBlocks.AMBROSIUM_ORE.get().defaultBlockState(), 5, 0.5F));
        AetherIIFeatureUtils.register(context, ORE_GRAVITITE_BURIED, Feature.ORE, new OreConfiguration(AetherIIFeatureRules.HOLYSTONE, AetherIIBlocks.GRAVITITE_ORE.get().defaultBlockState(), 3, 0.5F));
        AetherIIFeatureUtils.register(context, ORE_GRAVITITE, Feature.ORE, new OreConfiguration(AetherIIFeatureRules.HOLYSTONE, AetherIIBlocks.GRAVITITE_ORE.get().defaultBlockState(), 4));
    }
}