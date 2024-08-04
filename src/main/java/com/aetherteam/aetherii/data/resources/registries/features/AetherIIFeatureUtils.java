package com.aetherteam.aetherii.data.resources.registries.features;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class AetherIIFeatureUtils {
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> configuredFeature) {
        AetherIIOreFeatures.bootstrap(configuredFeature);
        AetherIIMiscFeatures.bootstrap(configuredFeature);
    }

    /**
    Separation of Configured Features Datagen into Sub-Classes, this helps with code cleansity,
    especially later on once more Features are added.
    Based on {@link net.minecraft.data.worldgen.features.FeatureUtils}
    */

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }

    static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    public static void register(BootstrapContext<ConfiguredFeature<?, ?>> BootstrapContext, ResourceKey<ConfiguredFeature<?, ?>> resourceKey, Feature<NoneFeatureConfiguration> feature) {
        register(BootstrapContext, resourceKey, feature, FeatureConfiguration.NONE);
    }
}