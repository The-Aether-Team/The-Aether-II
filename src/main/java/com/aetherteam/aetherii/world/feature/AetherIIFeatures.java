package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.world.feature.configuration.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, AetherII.MODID);

    public static DeferredHolder<Feature<?>, Feature<CoastConfiguration>> COAST = FEATURES.register("coast", () -> new CoastFeature(CoastConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<NoiseLakeConfiguration>> NOISE_LAKE = FEATURES.register("noise_lake", () -> new NoiseLakeFeature(NoiseLakeConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<FerrositePillarConfiguration>> FERROSITE_PILLAR = FEATURES.register("ferrosite_pillar", () -> new FerrositePillarFeature(FerrositePillarConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> MOA_NEST = FEATURES.register("moa_nest", () -> new MoaNestFeature(NoneFeatureConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<SimpleBlockConfiguration>> ORANGE_TREE = FEATURES.register("orange_tree", () -> new OrangeTreeFeature(SimpleBlockConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<AercloudConfiguration>> AERCLOUD = FEATURES.register("aercloud", () -> new AercloudFeature(AercloudConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<CloudbedConfiguration>> CLOUDBED = FEATURES.register("cloudbed", () -> new CloudbedFeature(CloudbedConfiguration.CODEC));
}