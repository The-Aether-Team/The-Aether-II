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

    public static DeferredHolder<Feature<?>, Feature<SimpleBlockConfiguration>> AETHER_GRASS = FEATURES.register("aether_grass", () -> new AetherGrassFeature(SimpleBlockConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<CoastConfiguration>> COAST = FEATURES.register("coast", () -> new CoastFeature(CoastConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<AetherLakeConfiguration>> LAKE = FEATURES.register("lake", () -> new AetherLakeFeature(AetherLakeConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<NoiseLakeConfiguration>> NOISE_LAKE = FEATURES.register("noise_lake", () -> new NoiseLakeFeature(NoiseLakeConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<FerrositePillarConfiguration>> FERROSITE_PILLAR = FEATURES.register("ferrosite_pillar", () -> new FerrositePillarFeature(FerrositePillarConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<ArcticIceSpikeConfiguration>> ARCTIC_ICE_SPIKE = FEATURES.register("arctic_ice_spike", () -> new ArcticIceSpikeFeature(ArcticIceSpikeConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<MoaNestConfiguration>> MOA_NEST = FEATURES.register("moa_nest", () -> new MoaNestFeature(MoaNestConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<SimpleBlockConfiguration>> ORANGE_TREE = FEATURES.register("orange_tree", () -> new OrangeTreeFeature(SimpleBlockConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> BRETTL_PLANT = FEATURES.register("brettl_plant", () -> new BrettlPlantFeature(NoneFeatureConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<AercloudConfiguration>> AERCLOUD = FEATURES.register("aercloud", () -> new AercloudFeature(AercloudConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> FREEZE_TOP_LAYER_ARCTIC = FEATURES.register("freeze_top_layer_arctic", () -> new ArcticSnowAndFreezeFeature(NoneFeatureConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<CloudbedConfiguration>> CLOUDBED = FEATURES.register("cloudbed", () -> new CloudbedFeature(CloudbedConfiguration.CODEC));
}