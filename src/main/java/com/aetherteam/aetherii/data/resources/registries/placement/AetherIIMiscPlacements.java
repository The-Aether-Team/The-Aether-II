package com.aetherteam.aetherii.data.resources.registries.placement;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.builders.AetherIIPlacementBuilders;
import com.aetherteam.aetherii.data.resources.registries.features.AetherIIMiscFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

public class AetherIIMiscPlacements {
    public static final ResourceKey<PlacedFeature> MOA_NEST = AetherIIPlacementUtils.createKey("moa_nest");
    public static final ResourceKey<PlacedFeature> COLD_AERCLOUD = AetherIIPlacementUtils.createKey("cold_aercloud");
    public static final ResourceKey<PlacedFeature> BLUE_AERCLOUD = AetherIIPlacementUtils.createKey("blue_aercloud");
    public static final ResourceKey<PlacedFeature> GOLDEN_AERCLOUD = AetherIIPlacementUtils.createKey("golden_aercloud");
    public static final ResourceKey<PlacedFeature> GREEN_AERCLOUD = AetherIIPlacementUtils.createKey("green_aercloud");
    public static final ResourceKey<PlacedFeature> PURPLE_AERCLOUD = AetherIIPlacementUtils.createKey("purple_aercloud");
    public static final ResourceKey<PlacedFeature> STORM_AERCLOUD = AetherIIPlacementUtils.createKey("storm_aercloud");

    @SuppressWarnings("deprecation")
    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        AetherIIPlacementUtils.register(context, MOA_NEST, configuredFeatures.getOrThrow(AetherIIMiscFeatures.MOA_NEST),
                RarityFilter.onAverageOnceEvery(6),
                InSquarePlacement.spread(),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(128), VerticalAnchor.absolute(200)),
                PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get()),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BiomeFilter.biome()
        );

        AetherIIPlacementUtils.register(context, COLD_AERCLOUD, configuredFeatures.getOrThrow(AetherIIMiscFeatures.COLD_AERCLOUD), AetherIIPlacementBuilders.aercloudPlacement(32, 256, 12));
        AetherIIPlacementUtils.register(context, BLUE_AERCLOUD, configuredFeatures.getOrThrow(AetherIIMiscFeatures.BLUE_AERCLOUD), AetherIIPlacementBuilders.aercloudPlacement(32, 256, 28));
        AetherIIPlacementUtils.register(context, GOLDEN_AERCLOUD, configuredFeatures.getOrThrow(AetherIIMiscFeatures.GOLDEN_AERCLOUD), AetherIIPlacementBuilders.aercloudPlacement(180, 272, 32));
        AetherIIPlacementUtils.register(context, GREEN_AERCLOUD, configuredFeatures.getOrThrow(AetherIIMiscFeatures.GREEN_AERCLOUD), AetherIIPlacementBuilders.aercloudPlacement(32, 256, 28));
        AetherIIPlacementUtils.register(context, PURPLE_AERCLOUD, configuredFeatures.getOrThrow(AetherIIMiscFeatures.PURPLE_AERCLOUD), AetherIIPlacementBuilders.aercloudPlacement(32, 256, 28));
        AetherIIPlacementUtils.register(context, STORM_AERCLOUD, configuredFeatures.getOrThrow(AetherIIMiscFeatures.STORM_AERCLOUD), AetherIIPlacementBuilders.aercloudPlacement(16, 96, 30));
    }
}