package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.data.resources.registries.highlands.HighlandsConfiguredFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class AetherIIConfiguredFeatures {
    /**
     Separation of Configured Features Datagen into Sub-Classes, this helps with code cleansity,
     especially later on once more Features are added.
     Based on {@link net.minecraft.data.worldgen.features.FeatureUtils}
     */
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HighlandsConfiguredFeatures.bootstrap(context);
    }
}
