package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.data.resources.registries.highlands.HighlandsPlacedFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class AetherIIPlacedFeatures {
    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HighlandsPlacedFeatures.bootstrap(context);
    }
}
