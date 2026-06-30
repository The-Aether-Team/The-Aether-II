package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.data.resources.registries.holyisles.HolyIslesPlacedFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class AetherIIPlacedFeatures {
    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolyIslesPlacedFeatures.bootstrap(context);
    }
}