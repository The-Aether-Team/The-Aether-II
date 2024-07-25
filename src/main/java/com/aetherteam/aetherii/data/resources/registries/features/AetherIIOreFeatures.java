package com.aetherteam.aetherii.data.resources.registries.features;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.world.feature.AetherIIFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class AetherIIOreFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SCATTERGLASS = AetherIIFeatureUtils.registerKey("ore_scatterglass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_MOSSY_HOLYSTONE = AetherIIFeatureUtils.registerKey("ore_mossy_holystone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ARCTIC_PACKED_ICE = AetherIIFeatureUtils.registerKey("ore_arctic_packed_ice");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_AGIOSITE = AetherIIFeatureUtils.registerKey("ore_agiosite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ICESTONE = AetherIIFeatureUtils.registerKey("ore_icestone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ICESTONE_SMALL = AetherIIFeatureUtils.registerKey("ore_icestone_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_AMBROSIUM = AetherIIFeatureUtils.registerKey("ore_ambrosium");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ZANITE = AetherIIFeatureUtils.registerKey("ore_zanite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ARKENIUM = AetherIIFeatureUtils.registerKey("ore_arkenium");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GRAVITITE_BURIED = AetherIIFeatureUtils.registerKey("ore_gravitite_buried");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GRAVITITE = AetherIIFeatureUtils.registerKey("ore_gravitite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_HOLYSTONE_QUARTZ = AetherIIFeatureUtils.registerKey("ore_holystone_quartz");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_CORROBONITE = AetherIIFeatureUtils.registerKey("ore_corrobonite");

    public static final RuleTest HOLYSTONE = new TagMatchTest(AetherIITags.Blocks.HOLYSTONE);
    public static final RuleTest UNDERSHALE = new BlockMatchTest(AetherIIBlocks.UNDERSHALE.get());
    public static final RuleTest UNDERGROUND = new TagMatchTest(AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS);

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        List<OreConfiguration.TargetBlockState> ambrosium = List.of(
                OreConfiguration.target(HOLYSTONE, AetherIIBlocks.AMBROSIUM_ORE.get().defaultBlockState()),
                OreConfiguration.target(UNDERSHALE, AetherIIBlocks.UNDERSHALE_AMBROSIUM_ORE.get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> zanite = List.of(
                OreConfiguration.target(HOLYSTONE, AetherIIBlocks.ZANITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(UNDERSHALE, AetherIIBlocks.UNDERSHALE_ZANITE_ORE.get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> arkenium = List.of(
                OreConfiguration.target(HOLYSTONE, AetherIIBlocks.ARKENIUM_ORE.get().defaultBlockState()),
                OreConfiguration.target(UNDERSHALE, AetherIIBlocks.UNDERSHALE_ARKENIUM_ORE.get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> gravitite = List.of(
                OreConfiguration.target(HOLYSTONE, AetherIIBlocks.GRAVITITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(UNDERSHALE, AetherIIBlocks.UNDERSHALE_GRAVITITE_ORE.get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> quartz = List.of(
                OreConfiguration.target(HOLYSTONE, AetherIIBlocks.HOLYSTONE_QUARTZ_ORE.get().defaultBlockState())
        );

        AetherIIFeatureUtils.register(context, ORE_SCATTERGLASS, Feature.ORE, new OreConfiguration(UNDERGROUND, AetherIIBlocks.CRUDE_SCATTERGLASS.get().defaultBlockState(), 32));
        AetherIIFeatureUtils.register(context, ORE_MOSSY_HOLYSTONE, Feature.ORE, new OreConfiguration(HOLYSTONE, AetherIIBlocks.MOSSY_HOLYSTONE.get().defaultBlockState(), 48));
        AetherIIFeatureUtils.register(context, ORE_ARCTIC_PACKED_ICE, Feature.ORE, new OreConfiguration(HOLYSTONE, AetherIIBlocks.ARCTIC_PACKED_ICE.get().defaultBlockState(), 48));
        AetherIIFeatureUtils.register(context, ORE_AGIOSITE, Feature.ORE, new OreConfiguration(UNDERSHALE, AetherIIBlocks.AGIOSITE.get().defaultBlockState(), 64));
        AetherIIFeatureUtils.register(context, ORE_ICESTONE, Feature.ORE, new OreConfiguration(HOLYSTONE, AetherIIBlocks.ICESTONE.get().defaultBlockState(), 32));
        AetherIIFeatureUtils.register(context, ORE_ICESTONE_SMALL, Feature.ORE, new OreConfiguration(HOLYSTONE, AetherIIBlocks.ICESTONE.get().defaultBlockState(), 16));

        AetherIIFeatureUtils.register(context, ORE_AMBROSIUM, Feature.ORE, new OreConfiguration(ambrosium, 16));
        AetherIIFeatureUtils.register(context, ORE_ZANITE, Feature.ORE, new OreConfiguration(zanite, 5, 0.5F));
        AetherIIFeatureUtils.register(context, ORE_ARKENIUM, Feature.ORE, new OreConfiguration(arkenium, 5, 0.5F));
        AetherIIFeatureUtils.register(context, ORE_GRAVITITE_BURIED, Feature.ORE, new OreConfiguration(gravitite, 3, 0.5F));
        AetherIIFeatureUtils.register(context, ORE_GRAVITITE, Feature.ORE, new OreConfiguration(gravitite, 4));
        AetherIIFeatureUtils.register(context, ORE_HOLYSTONE_QUARTZ, Feature.ORE, new OreConfiguration(quartz, 6));

        AetherIIFeatureUtils.register(context, ORE_CORROBONITE, AetherIIFeatures.CORROBONITE_ORE.get(), new OreConfiguration(UNDERGROUND, AetherIIBlocks.CORROBONITE_ORE.get().defaultBlockState(), 4));
    }
}