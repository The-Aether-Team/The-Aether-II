package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.data.resources.registries.holyisles.HolyIslesPlacedFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class AetherIIPlacedFeatures {
    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolyIslesPlacedFeatures.bootstrap(context);
    }
}