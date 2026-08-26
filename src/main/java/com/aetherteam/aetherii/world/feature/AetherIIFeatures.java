package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.world.feature.configuration.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, AetherII.MODID);

    public static DeferredHolder<Feature<?>, Feature<MergedConfiguration>> MERGED = FEATURES.register("merged", () -> new MergedFeature(MergedConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<SimpleBlockConfiguration>> AETHER_GRASS = FEATURES.register("aether_grass", () -> new AetherGrassFeature(SimpleBlockConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<SimpleBlockConfiguration>> AETHER_FLOWER = FEATURES.register("aether_flower", () -> new AetherFlowerFeature(SimpleBlockConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<CoastConfiguration>> COAST = FEATURES.register("coast", () -> new CoastFeature(CoastConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<AetherLakeConfiguration>> LAKE = FEATURES.register("lake", () -> new AetherLakeFeature(AetherLakeConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<NoiseLakeConfiguration>> NOISE_LAKE = FEATURES.register("noise_lake", () -> new NoiseLakeFeature(NoiseLakeConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<FerrositeSpikeConfiguration>> FERROSITE_SPIKE = FEATURES.register("ferrosite_spike", () -> new FerrositeSpikeFeature(FerrositeSpikeConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<FerrositePillarConfiguration>> FERROSITE_PILLAR = FEATURES.register("ferrosite_pillar", () -> new FerrositePillarFeature(FerrositePillarConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<ArcticIceSpikeConfiguration>> ARCTIC_ICE_SPIKE = FEATURES.register("arctic_ice_spike", () -> new ArcticIceSpikeFeature(ArcticIceSpikeConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<MoaNestConfiguration>> MOA_NEST = FEATURES.register("moa_nest", () -> new MoaNestFeature(MoaNestConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<SimpleBlockConfiguration>> ORANGE_TREE = FEATURES.register("orange_tree", () -> new OrangeTreeFeature(SimpleBlockConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> BRETTL_PLANT = FEATURES.register("brettl_plant", () -> new BrettlPlantFeature(NoneFeatureConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<AercloudConfiguration>> AERCLOUD = FEATURES.register("aercloud", () -> new AercloudFeature(AercloudConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> FREEZE_TOP_LAYER_ARCTIC = FEATURES.register("freeze_top_layer_arctic", () -> new ArcticSnowAndFreezeFeature(NoneFeatureConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> FREEZE_TOP_LAYER_TUNDRA = FEATURES.register("freeze_top_layer_tundra", () -> new TundraSnowAndFreezeFeature(NoneFeatureConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<CloudbedConfiguration>> CLOUDBED = FEATURES.register("cloudbed", () -> new CloudbedFeature(CloudbedConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<OreConfiguration>> CORROBONITE_ORE = FEATURES.register("corrobonite_ore", () -> new CorroboniteOreFeature(OreConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<BoulderConfiguration>> BOULDER = FEATURES.register("boulder", () -> new BoulderFeature(BoulderConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<FallenLogConfiguration>> FALLEN_LOG = FEATURES.register("fallen_log", () -> new FallenLogFeature(FallenLogConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<MossVinesConfiguration>> MOSS_VINES = FEATURES.register("moss_vines", () -> new MossVinesFeature(MossVinesConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<ArilumConfiguration>> ARILUM = FEATURES.register("arilum", () -> new ArilumFeature(ArilumConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<AlkahestPoolConfiguration>> ALKAHEST_POOL = FEATURES.register("alkahest_pool", () -> new AlkahestPoolFeature(AlkahestPoolConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> HESTVEIL = FEATURES.register("hestveil", () -> new HestveilFeature(NoneFeatureConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<PointedStoneConfiguration>> POINTED_STONE = FEATURES.register("pointed_stone", () -> new PointedStoneFeature(PointedStoneConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<CraterConfiguration>> CRATER = FEATURES.register("crater", () -> new CraterFeature(CraterConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> TREE_MOSS_COVER = FEATURES.register("tree_moss_cover", () -> new TreeMossCoverFeature(NoneFeatureConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<BigMagneticShroomConfiguration>> SMALL_MAGNETIC_SHROOM = FEATURES.register("small_magnetic_shroom", () -> new SmallMagneticShroomFeature(BigMagneticShroomConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<BigMagneticShroomConfiguration>> HUGE_MAGNETIC_SHROOM = FEATURES.register("huge_magnetic_shroom", () -> new HugeMagneticShroomFeature(BigMagneticShroomConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<LargeShelfMushroomConfiguration>> LARGE_SHELF_MUSHROOM = FEATURES.register("large_shelf_mushroom", () -> new LargeShelfMushroom(LargeShelfMushroomConfiguration.CODEC));
    public static DeferredHolder<Feature<?>, Feature<StructureCoverConfiguration>> STRUCTURE_COVER = FEATURES.register("structure_cover", () -> new StructureCoverFeature(StructureCoverConfiguration.CODEC));
//    public static DeferredHolder<Feature<?>, Feature<InfectedPatchConfiguration>> INFECTED_PATCH = FEATURES.register("infected_patch", () -> new InfectedPatchFeature(InfectedPatchConfiguration.CODEC));
}