package com.aetherteam.aetherii.data.resources.registries.features;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class AetherIIFeatureUtils {
    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> configuredFeature) {
        AetherIIOreFeatures.bootstrap(configuredFeature);
        AetherIIMiscFeatures.bootstrap(configuredFeature);
        AetherIITreeFeatures.bootstrap(configuredFeature);
        //AetherIIVegetationFeatures.bootstrap(configuredFeature);
    }

    /**
    Separation of Configured Features Datagen into Sub-Classes, this helps with code cleansity,
    especially later on once more Features are added.
    Based on {@link net.minecraft.data.worldgen.features.FeatureUtils}
    */

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(AetherII.MODID, name));
    }

    static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    public static void register(BootstapContext<ConfiguredFeature<?, ?>> bootstapContext, ResourceKey<ConfiguredFeature<?, ?>> resourceKey, Feature<NoneFeatureConfiguration> feature) {
        register(bootstapContext, resourceKey, feature, FeatureConfiguration.NONE);
    }
}