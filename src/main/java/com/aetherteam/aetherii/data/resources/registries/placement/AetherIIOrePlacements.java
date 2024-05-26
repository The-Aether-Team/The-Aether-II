package com.aetherteam.aetherii.data.resources.registries.placement;

import com.aetherteam.aetherii.data.resources.registries.features.AetherIIOreFeatures;
import com.aetherteam.nitrogen.data.resources.builders.NitrogenPlacedFeatureBuilders;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class AetherIIOrePlacements {
    public static final ResourceKey<PlacedFeature> ORE_AGIOSITE = AetherIIPlacementUtils.createKey("ore_agiosite");
    public static final ResourceKey<PlacedFeature> ORE_AMBROSIUM = AetherIIPlacementUtils.createKey("ore_ambrosium");
    public static final ResourceKey<PlacedFeature> ORE_ZANITE = AetherIIPlacementUtils.createKey("ore_zanite");
    public static final ResourceKey<PlacedFeature> ORE_ARKENIUM = AetherIIPlacementUtils.createKey("ore_arkenium");
    public static final ResourceKey<PlacedFeature> ORE_GRAVITITE_BURIED = AetherIIPlacementUtils.createKey("ore_gravitite_buried");
    public static final ResourceKey<PlacedFeature> ORE_GRAVITITE = AetherIIPlacementUtils.createKey("ore_gravitite");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        AetherIIPlacementUtils.register(context, ORE_AGIOSITE, configuredFeatures.getOrThrow(AetherIIOreFeatures.ORE_AGIOSITE),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(1, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(40))));
        AetherIIPlacementUtils.register(context, ORE_AMBROSIUM, configuredFeatures.getOrThrow(AetherIIOreFeatures.ORE_AMBROSIUM),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(20, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(256))));
        AetherIIPlacementUtils. register(context, ORE_ZANITE, configuredFeatures.getOrThrow(AetherIIOreFeatures.ORE_ZANITE),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(14, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(75))));
        AetherIIPlacementUtils. register(context, ORE_ARKENIUM, configuredFeatures.getOrThrow(AetherIIOreFeatures.ORE_ZANITE),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(64))));
        AetherIIPlacementUtils.register(context, ORE_GRAVITITE_BURIED, configuredFeatures.getOrThrow(AetherIIOreFeatures.ORE_GRAVITITE_BURIED),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(5, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(74))));
        AetherIIPlacementUtils.register(context, ORE_GRAVITITE, configuredFeatures.getOrThrow(AetherIIOreFeatures.ORE_GRAVITITE),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-58), VerticalAnchor.aboveBottom(74))));
    }
}