package com.aetherteam.aetherii.data.resources.registries.placement;

import com.aetherteam.aetherii.data.resources.registries.features.AetherIIMiscFeatures;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

public class AetherIIMiscPlacements {
    public static final ResourceKey<PlacedFeature> COAST_QUICKSOIL = AetherIIPlacementUtils.createKey("coast_quicksoil");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        AetherIIPlacementUtils.register(context, COAST_QUICKSOIL, configuredFeatures.getOrThrow(AetherIIMiscFeatures.COAST_QUICKSOIL),
                CountPlacement.of(127),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(32), VerticalAnchor.absolute(96)),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 16),
                BiomeFilter.biome()
        );
    }
}