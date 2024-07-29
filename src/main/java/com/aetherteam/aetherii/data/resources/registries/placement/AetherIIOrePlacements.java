package com.aetherteam.aetherii.data.resources.registries.placement;

import com.aetherteam.aetherii.data.resources.registries.features.AetherIIOreFeatures;
import com.aetherteam.nitrogen.data.resources.builders.NitrogenPlacedFeatureBuilders;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class AetherIIOrePlacements {
    public static final ResourceKey<PlacedFeature> ORE_SCATTERGLASS = AetherIIPlacementUtils.createKey("ore_scatterglass");
    public static final ResourceKey<PlacedFeature> ORE_MOSSY_HOLYSTONE = AetherIIPlacementUtils.createKey("ore_mossy_holystone");
    public static final ResourceKey<PlacedFeature> ORE_ARCTIC_PACKED_iCE = AetherIIPlacementUtils.createKey("ore_arctic_packed_ice");
    public static final ResourceKey<PlacedFeature> ORE_AGIOSITE = AetherIIPlacementUtils.createKey("ore_agiosite");
    public static final ResourceKey<PlacedFeature> ORE_ICESTONE = AetherIIPlacementUtils.createKey("ore_icestone");
    public static final ResourceKey<PlacedFeature> ORE_ICESTONE_SMALL = AetherIIPlacementUtils.createKey("ore_icestone_small");
    public static final ResourceKey<PlacedFeature> ORE_AMBROSIUM = AetherIIPlacementUtils.createKey("ore_ambrosium");
    public static final ResourceKey<PlacedFeature> ORE_ZANITE = AetherIIPlacementUtils.createKey("ore_zanite");
    public static final ResourceKey<PlacedFeature> ORE_ARKENIUM = AetherIIPlacementUtils.createKey("ore_arkenium");
    public static final ResourceKey<PlacedFeature> ORE_GRAVITITE_BURIED = AetherIIPlacementUtils.createKey("ore_gravitite_buried");
    public static final ResourceKey<PlacedFeature> ORE_GRAVITITE = AetherIIPlacementUtils.createKey("ore_gravitite");
    public static final ResourceKey<PlacedFeature> ORE_HOLYSTONE_QUARTZ = AetherIIPlacementUtils.createKey("ore_holystone_quartz");
    public static final ResourceKey<PlacedFeature> ORE_CORROBONITE = AetherIIPlacementUtils.createKey("ore_corrobonite");
    public static final ResourceKey<PlacedFeature> ORE_GLINT = AetherIIPlacementUtils.createKey("ore_glint");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        AetherIIPlacementUtils.register(context, ORE_SCATTERGLASS, configuredFeatures.getOrThrow(AetherIIOreFeatures.ORE_SCATTERGLASS),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(256))));
        AetherIIPlacementUtils.register(context, ORE_MOSSY_HOLYSTONE, configuredFeatures.getOrThrow(AetherIIOreFeatures.ORE_MOSSY_HOLYSTONE),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(3, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(72), VerticalAnchor.aboveBottom(256))));
        AetherIIPlacementUtils.register(context, ORE_ARCTIC_PACKED_iCE, configuredFeatures.getOrThrow(AetherIIOreFeatures.ORE_ARCTIC_PACKED_ICE),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(1, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(72), VerticalAnchor.aboveBottom(256))));
        AetherIIPlacementUtils.register(context, ORE_AGIOSITE, configuredFeatures.getOrThrow(AetherIIOreFeatures.ORE_AGIOSITE),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(1, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(72))));
        AetherIIPlacementUtils.register(context, ORE_ICESTONE, configuredFeatures.getOrThrow(AetherIIOreFeatures.ORE_ICESTONE),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(256))));
        AetherIIPlacementUtils.register(context, ORE_ICESTONE_SMALL, configuredFeatures.getOrThrow(AetherIIOreFeatures.ORE_ICESTONE_SMALL),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(5, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(256))));

        AetherIIPlacementUtils.register(context, ORE_AMBROSIUM, configuredFeatures.getOrThrow(AetherIIOreFeatures.ORE_AMBROSIUM),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(16, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(88), VerticalAnchor.aboveBottom(256))));
        AetherIIPlacementUtils. register(context, ORE_ZANITE, configuredFeatures.getOrThrow(AetherIIOreFeatures.ORE_ZANITE),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(64), VerticalAnchor.aboveBottom(144))));
        AetherIIPlacementUtils. register(context, ORE_ARKENIUM, configuredFeatures.getOrThrow(AetherIIOreFeatures.ORE_ARKENIUM),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.aboveBottom(96))));
        AetherIIPlacementUtils.register(context, ORE_GRAVITITE_BURIED, configuredFeatures.getOrThrow(AetherIIOreFeatures.ORE_GRAVITITE_BURIED),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.aboveBottom(72))));
        AetherIIPlacementUtils.register(context, ORE_GRAVITITE, configuredFeatures.getOrThrow(AetherIIOreFeatures.ORE_GRAVITITE),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-58), VerticalAnchor.aboveBottom(88))));
        AetherIIPlacementUtils.register(context, ORE_HOLYSTONE_QUARTZ, configuredFeatures.getOrThrow(AetherIIOreFeatures.ORE_HOLYSTONE_QUARTZ),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(96), VerticalAnchor.aboveBottom(180))));
        AetherIIPlacementUtils.register(context, ORE_CORROBONITE, configuredFeatures.getOrThrow(AetherIIOreFeatures.ORE_CORROBONITE),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(4, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.aboveBottom(56))));
        AetherIIPlacementUtils.register(context, ORE_GLINT, configuredFeatures.getOrThrow(AetherIIOreFeatures.ORE_GLINT),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.aboveBottom(144))));
    }
}