package com.aetherteam.aetherii.data.resources.builders.highlands;

import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.data.resources.AetherIIMobCategory;
import com.aetherteam.aetherii.data.resources.registries.AetherIICarvers;
import com.aetherteam.aetherii.data.resources.registries.highlands.HighlandsPlacedFeatures;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import net.minecraft.core.HolderGetter;
import net.minecraft.sounds.Music;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class HighlandsBiomeBuilders {
    private static final BiomeSpecialEffects HIGHFIELDS_EFFECTS = new BiomeSpecialEffects.Builder()
            .fogColor(0xecebfc)
            .skyColor(0xc9d1ff)
            .waterColor(0xa2d5f2)
            .waterFogColor(0x55708a)
            .grassColorOverride(0xb5ffd0)
            .foliageColorOverride(0xb1ffcb)
            .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
            .backgroundMusic(new Music(AetherIISoundEvents.MUSIC_AETHER, 3600, 10800, false))
            .build();
    private static final BiomeSpecialEffects MAGNETIC_EFFECTS = new BiomeSpecialEffects.Builder()
            .fogColor(0xedeef5)
            .skyColor(0xc5cbeb)
            .waterColor(0xabbdd9)
            .waterFogColor(0x607496)
            .grassColorOverride(0xc9ffd1)
            .foliageColorOverride(0xb1ffcb)
            .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
            .backgroundMusic(new Music(AetherIISoundEvents.MUSIC_AETHER, 3600, 10800, false))
            .build();
    private static final BiomeSpecialEffects ARCTIC_EFFECTS = new BiomeSpecialEffects.Builder()
            .fogColor(0xf3f0ff)
            .skyColor(0xe7e3fc)
            .waterColor(0x637aa8)
            .waterFogColor(0x3e5082)
            .grassColorOverride(0xbdf9ff)
            .foliageColorOverride(0xb1ffcb)
            .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
            .backgroundMusic(new Music(AetherIISoundEvents.MUSIC_AETHER, 3600, 10800, false))
            .build();
    private static final BiomeSpecialEffects IRRADIATED_EFFECTS = new BiomeSpecialEffects.Builder()
            .fogColor(0xF0E8BE)
            .skyColor(0xfcebc5)
            .waterColor(0xaed4bf)
            .waterFogColor(0xbccc81)
            .grassColorOverride(0xffdd99)
            .foliageColorOverride(0xffdd99)
            .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
            .backgroundMusic(new Music(AetherIISoundEvents.MUSIC_AETHER, 3600, 10800, false))
            .ambientParticle(new AmbientParticleSettings(AetherIIParticleTypes.AMBROSIUM.get(), 0.00625F))
            .build();
    private static final BiomeSpecialEffects AERCLOUD_SEA_EFFECTS = new BiomeSpecialEffects.Builder()
            .fogColor(0xecebfc)
            .skyColor(0xc9d1ff)
            .waterColor(0xa2d5f2)
            .waterFogColor(0x55708a)
            .grassColorOverride(0xb5ffd0)
            .foliageColorOverride(0xb1ffcb)
            .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
            .backgroundMusic(new Music(AetherIISoundEvents.MUSIC_AETHER, 3600, 10800, false))
            .build();

    public static Biome flourishingFieldBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeHighfieldsBiome(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                        .addCarver(GenerationStep.Carving.AIR, AetherIICarvers.HIGHLANDS_CAVE)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HighlandsPlacedFeatures.COAST_QUICKSOIL)
                        .addFeature(GenerationStep.Decoration.LAKES, HighlandsPlacedFeatures.WATER_POND)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HighlandsPlacedFeatures.HOLYSTONE_ROCKS)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.MOA_NEST)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.FLOURISHING_FIELD_TREES)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.GRASS_FIELD)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.VALKYRIE_SPROUT_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.HIGHLANDS_BUSH_PATCH_FIELD),
                temperature, downfall);
    }

    public static Biome verdantWoodsBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeHighfieldsBiome(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                        .addCarver(GenerationStep.Carving.AIR, AetherIICarvers.HIGHLANDS_CAVE)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HighlandsPlacedFeatures.COAST_QUICKSOIL_SPARSE)
                        .addFeature(GenerationStep.Decoration.LAKES, HighlandsPlacedFeatures.WATER_POND)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HighlandsPlacedFeatures.SKYROOT_TWIGS)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HighlandsPlacedFeatures.HOLYSTONE_ROCKS)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.MOA_NEST)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.VERDANT_WOODS_TREES)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.SMALL_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.MEDIUM_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.LARGE_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.VALKYRIE_SPROUT_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.HIGHLANDS_BUSH_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.ORANGE_TREE_PATCH),
                temperature, downfall);
    }

    public static Biome shroudedForestBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeHighfieldsBiome(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                        .addCarver(GenerationStep.Carving.AIR, AetherIICarvers.HIGHLANDS_CAVE)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HighlandsPlacedFeatures.COAST_QUICKSOIL_SPARSE)
                        .addFeature(GenerationStep.Decoration.LAKES, HighlandsPlacedFeatures.WATER_POND)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HighlandsPlacedFeatures.SKYROOT_TWIGS)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HighlandsPlacedFeatures.HOLYSTONE_ROCKS)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HighlandsPlacedFeatures.MOSSY_HOLYSTONE_BOULDER)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.SHROUDED_FOREST_TREES)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.SMALL_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.MEDIUM_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.LARGE_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.HIGHLANDS_BUSH_PATCH),
                temperature, downfall);
    }

    public static Biome shimmeringBasinBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeHighfieldsBiome(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HighlandsPlacedFeatures.COAST_QUICKSOIL)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HighlandsPlacedFeatures.HOLYSTONE_ROCKS)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.SHIMMERING_BASIN_TREES)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.SHIMMERING_BASIN_TREES_SUNKEN)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.GRASS_FIELD)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.VALKYRIE_SPROUT_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.HIGHLANDS_BUSH_PATCH_FIELD)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.ORANGE_TREE_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.FALLEN_SKYROOT_LOG),
                temperature, downfall);
    }

    public static Biome makeHighfieldsBiome(BiomeGenerationSettings.Builder builder, float temperature, float downfall) {
        MobSpawnSettings.Builder spawnSettingsBuilder = new MobSpawnSettings.Builder();
        return fullDefinition(
                true,
                temperature,
                downfall,
                HIGHFIELDS_EFFECTS,
                spawnSettingsBuilder
                        .addMobCharge(AetherIIEntityTypes.ZEPHYR.get(), 0.6, 0.16)
                        .addMobCharge(AetherIIEntityTypes.TEMPEST.get(), 0.6, 0.16)
                        .addMobCharge(AetherIIEntityTypes.AECHOR_PLANT.get(), 0.4, 0.11)
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), 10, 1, 3))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI.get(), 8, 1, 3))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.HIGHFIELDS_TAEGORE.get(), 8, 1, 3))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.AERBUNNY.get(), 10, 1, 2))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SHEEPUFF.get(), 6, 1, 3))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.FLYING_COW.get(), 6, 1, 3))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.PHYG.get(), 6, 1, 3))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.MOA.get(), 1, 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_SKY_MONSTER, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ZEPHYR.get(), 3, 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_SKY_MONSTER, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.TEMPEST.get(), 3, 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.AECHOR_PLANT.get(), 7, 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.COCKATRICE.get(), 10, 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SWET.get(), 10, 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_MONSTER, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SKEPHID.get(), 5, 1, 3))
                        .build(),
                builder
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HighlandsPlacedFeatures.NOISE_LAKE)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HighlandsPlacedFeatures.CLOUDBED)
                        .addFeature(GenerationStep.Decoration.LAKES, HighlandsPlacedFeatures.WATER_POND_UNDERGROUND)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_SCATTERGLASS)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_AGIOSITE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_ICESTONE_SMALL)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_AMBROSIUM)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_ZANITE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_ARKENIUM)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_GRAVITITE_BURIED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_GRAVITITE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_HOLYSTONE_QUARTZ)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_CORROBONITE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_GLINT)
                        .addFeature(GenerationStep.Decoration.FLUID_SPRINGS, HighlandsPlacedFeatures.WATER_SPRING)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.GRASS_AND_DIRT_FLOOR)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.COARSE_AETHER_DIRT_FLOOR)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.COARSE_AETHER_DIRT_CEILING)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.COARSE_AETHER_DIRT_OVERHANG)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.EXPOSED_BRYALINN_MOSS_COVER)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.HIGHLANDS_FLOWER_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.HIGHFIELDS_FLOWER_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.BLUEBERRY_BUSH_PATCH)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.COLD_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.GOLDEN_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.BLUE_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.PURPLE_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.PURPLE_AERCLOUD_SMALL)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.GREEN_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.STORM_AERCLOUD)
                        .build(),
                Biome.TemperatureModifier.NONE
        );
    }

    public static Biome magneticScarBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeMagneticBiome(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                        .addCarver(GenerationStep.Carving.AIR, AetherIICarvers.HIGHLANDS_CAVE)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HighlandsPlacedFeatures.FERROSITE_SPIKE)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HighlandsPlacedFeatures.FERROSITE_PILLAR)
                        .addFeature(GenerationStep.Decoration.LAKES, HighlandsPlacedFeatures.WATER_POND)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HighlandsPlacedFeatures.HOLYSTONE_ROCKS)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.MAGNETIC_SCAR_TREES)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.GRASS_FIELD),
                temperature, downfall);
    }

    public static Biome turquoiseForestBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeMagneticBiome(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                        .addCarver(GenerationStep.Carving.AIR, AetherIICarvers.HIGHLANDS_CAVE)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HighlandsPlacedFeatures.FERROSITE_SPIKE)
                        .addFeature(GenerationStep.Decoration.LAKES, HighlandsPlacedFeatures.WATER_POND)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HighlandsPlacedFeatures.SKYROOT_TWIGS)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HighlandsPlacedFeatures.HOLYSTONE_ROCKS)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.MOA_NEST)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.TURQUOISE_FOREST_TREES)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.SMALL_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.MEDIUM_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.LARGE_GRASS_PATCH),
                temperature, downfall);
    }

    public static Biome glisteningSwampBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeMagneticBiome(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                        .addFeature(GenerationStep.Decoration.FLUID_SPRINGS, HighlandsPlacedFeatures.BONUS_WATER_SPRING)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.GLISTENING_SWAMP_TREES)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.GLISTENING_SWAMP_TREES_SUNKEN)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.SMALL_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.MEDIUM_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.LARGE_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.BONUS_MAGNETIC_SHROOM_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.SWAMP_BRYALINN_MOSS_COVER)
                , temperature, downfall);
    }

    public static Biome violetHighwoodsBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeMagneticBiome(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                        .addCarver(GenerationStep.Carving.AIR, AetherIICarvers.HIGHLANDS_CAVE)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HighlandsPlacedFeatures.FERROSITE_SPIKE)
                        .addFeature(GenerationStep.Decoration.LAKES, HighlandsPlacedFeatures.WATER_POND)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HighlandsPlacedFeatures.SKYROOT_TWIGS)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HighlandsPlacedFeatures.HOLYSTONE_ROCKS)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.MOA_NEST)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.VIOLET_HIGHWOODS_TREES)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.SMALL_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.MEDIUM_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.LARGE_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.FALLEN_WISPROOT_LOG),
                temperature, downfall);
    }

    public static Biome makeMagneticBiome(BiomeGenerationSettings.Builder builder, float temperature, float downfall) {
        MobSpawnSettings.Builder spawnSettingsBuilder = new MobSpawnSettings.Builder();
        return fullDefinition(
                true,
                temperature,
                downfall,
                MAGNETIC_EFFECTS,
                spawnSettingsBuilder
                        .addMobCharge(AetherIIEntityTypes.ZEPHYR.get(), 0.6, 0.16)
                        .addMobCharge(AetherIIEntityTypes.TEMPEST.get(), 0.6, 0.16)
                        .addMobCharge(AetherIIEntityTypes.AECHOR_PLANT.get(), 0.4, 0.11)
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), 10, 1, 3))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.MAGNETIC_BURRUKAI.get(), 8, 1, 3))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.MAGNETIC_TAEGORE.get(), 8, 1, 3))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.AERBUNNY.get(), 10, 1, 2))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SHEEPUFF.get(), 6, 1, 3))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.FLYING_COW.get(), 6, 1, 3))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.PHYG.get(), 6, 1, 3))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.MOA.get(), 1, 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_SKY_MONSTER, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ZEPHYR.get(), 3, 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_SKY_MONSTER, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.TEMPEST.get(), 3, 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.AECHOR_PLANT.get(), 7, 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.COCKATRICE.get(), 10, 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SWET.get(), 10, 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_MONSTER, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SKEPHID.get(), 5, 1, 3))
                        .build(),
                builder
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HighlandsPlacedFeatures.NOISE_LAKE_SWAMP)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HighlandsPlacedFeatures.COAST_FERROSITE_SAND)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HighlandsPlacedFeatures.CLOUDBED)
                        .addFeature(GenerationStep.Decoration.LAKES, HighlandsPlacedFeatures.WATER_POND_UNDERGROUND)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_SCATTERGLASS)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_AGIOSITE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_ICESTONE_SMALL)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_AMBROSIUM)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_ZANITE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_ARKENIUM)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_GRAVITITE_BURIED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_GRAVITITE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_HOLYSTONE_QUARTZ)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_CORROBONITE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_GLINT)
                        .addFeature(GenerationStep.Decoration.FLUID_SPRINGS, HighlandsPlacedFeatures.WATER_SPRING)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.GRASS_AND_DIRT_FLOOR)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.COARSE_AETHER_DIRT_FLOOR)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.COARSE_AETHER_DIRT_CEILING)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.COARSE_AETHER_DIRT_OVERHANG)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.HIGHLANDS_FLOWER_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.MAGNETIC_FLOWER_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.BLUEBERRY_BUSH_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.MAGNETIC_SHROOM_PATCH)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.COLD_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.GOLDEN_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.BLUE_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.PURPLE_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.PURPLE_AERCLOUD_SMALL)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.GREEN_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.STORM_AERCLOUD)
                        .build(),
                Biome.TemperatureModifier.NONE
        );
    }

    public static Biome frigidSierraBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeArcticBiome(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                        .addCarver(GenerationStep.Carving.AIR, AetherIICarvers.HIGHLANDS_CAVE)
                        .addFeature(GenerationStep.Decoration.LAKES, HighlandsPlacedFeatures.WATER_POND)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HighlandsPlacedFeatures.ICESTONE_BOULDER)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.FRIGID_SIERRA_TREES)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.GRASS_FIELD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.FREEZE_TOP_LAYER_ARCTIC),
                temperature, downfall);
    }

    public static Biome enduringWoodlandBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeArcticBiome(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                        .addCarver(GenerationStep.Carving.AIR, AetherIICarvers.HIGHLANDS_CAVE)
                        .addFeature(GenerationStep.Decoration.LAKES, HighlandsPlacedFeatures.WATER_POND)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.MOA_NEST)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.ENDURING_WOODLAND_TREES)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.SMALL_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.MEDIUM_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.LARGE_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.FREEZE_TOP_LAYER_ARCTIC),
                temperature, downfall);
    }

    public static Biome frozenLakesBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeArcticBiome(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.FROZEN_LAKES_TREES)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.GRASS_FIELD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.FREEZE_TOP_LAYER_ARCTIC),
                temperature, downfall);
    }

    public static Biome sheerTundraBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeArcticBiome(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                        .addCarver(GenerationStep.Carving.AIR, AetherIICarvers.HIGHLANDS_CAVE)
                        .addFeature(GenerationStep.Decoration.LAKES, HighlandsPlacedFeatures.WATER_POND_TUNDRA)
                        .addFeature(GenerationStep.Decoration.LAKES, HighlandsPlacedFeatures.WATER_SPRING)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HighlandsPlacedFeatures.HOLYSTONE_ROCKS_TUNDRA)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HighlandsPlacedFeatures.MOSSY_HOLYSTONE_BOULDER_TUNDRA)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HighlandsPlacedFeatures.ICESTONE_BOULDER)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.MOA_NEST)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.GRASS_FIELD)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.EXPOSED_SHAYELINN_MOSS_COVER)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.FREEZE_TOP_LAYER_TUNDRA),
                temperature, downfall);
    }

    public static Biome makeArcticBiome(BiomeGenerationSettings.Builder builder,  float temperature, float downfall) {
        MobSpawnSettings.Builder spawnSettingsBuilder = new MobSpawnSettings.Builder();
        return fullDefinition(
                true,
                temperature,
                downfall,
                ARCTIC_EFFECTS,
                spawnSettingsBuilder
                        .addMobCharge(AetherIIEntityTypes.ZEPHYR.get(), 0.6, 0.16)
                        .addMobCharge(AetherIIEntityTypes.TEMPEST.get(), 0.6, 0.16)
                        .addMobCharge(AetherIIEntityTypes.AECHOR_PLANT.get(), 0.4, 0.11)
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ARCTIC_KIRRID.get(), 10, 1, 3))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ARCTIC_BURRUKAI.get(), 8, 1, 3))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ARCTIC_TAEGORE.get(), 8, 1, 3))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.AERBUNNY.get(), 10, 1, 2))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SHEEPUFF.get(), 6, 1, 3))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.FLYING_COW.get(), 6, 1, 3))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.PHYG.get(), 6, 1, 3))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.MOA.get(), 1, 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_SKY_MONSTER, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ZEPHYR.get(), 3, 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_SKY_MONSTER, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.TEMPEST.get(), 3, 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.AECHOR_PLANT.get(), 7, 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.COCKATRICE.get(), 10, 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SWET.get(), 10, 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_MONSTER, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SKEPHID.get(), 5, 1, 3))

                        .build(),
                builder
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HighlandsPlacedFeatures.COAST_ARCTIC_PACKED_ICE)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HighlandsPlacedFeatures.NOISE_LAKE_ARCTIC)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HighlandsPlacedFeatures.CLOUDBED)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HighlandsPlacedFeatures.COASTAL_ARCTIC_ICE_SPIKE)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HighlandsPlacedFeatures.ARCTIC_ICE_SPIKE_CLUSTER)
                        .addFeature(GenerationStep.Decoration.LAKES, HighlandsPlacedFeatures.WATER_POND_UNDERGROUND)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_SCATTERGLASS)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_AGIOSITE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_ICESTONE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_AMBROSIUM)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_ZANITE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_ARKENIUM)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_GRAVITITE_BURIED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_GRAVITITE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_HOLYSTONE_QUARTZ)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_CORROBONITE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_GLINT)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.GRASS_AND_DIRT_FLOOR)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.COARSE_AETHER_DIRT_FLOOR)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.COARSE_AETHER_DIRT_FROSTED_CEILING)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.ICE_OVERHANG)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.HIGHLANDS_FLOWER_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.ARCTIC_FLOWER_PATCH)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.COLD_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.GOLDEN_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.BLUE_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.PURPLE_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.PURPLE_AERCLOUD_SMALL)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.GREEN_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.STORM_AERCLOUD)
                        .build(),
                Biome.TemperatureModifier.NONE
        );
    }

    public static Biome contaminatedJungleBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeIrradiatedBiome(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers).addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.CONTAMINATED_JUNGLE_TREES), temperature, downfall);
    }

    public static Biome battlegroundWastesBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeIrradiatedBiome(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers).addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.BATTLEGROUND_WASTES_TREES), temperature, downfall);
    }

    public static Biome makeIrradiatedBiome(BiomeGenerationSettings.Builder builder, float temperature, float downfall) {
        MobSpawnSettings.Builder spawnSettingsBuilder = new MobSpawnSettings.Builder();
        return fullDefinition(
                true,
                temperature,
                downfall,
                IRRADIATED_EFFECTS,
                spawnSettingsBuilder
                        .addMobCharge(AetherIIEntityTypes.ZEPHYR.get(), 0.6, 0.16)
                        .addMobCharge(AetherIIEntityTypes.TEMPEST.get(), 0.6, 0.16)
                        .addSpawn(AetherIIMobCategory.AETHER_SKY_MONSTER, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ZEPHYR.get(), 3, 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_SKY_MONSTER, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.TEMPEST.get(), 3, 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.COCKATRICE.get(), 10, 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_MONSTER, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SKEPHID.get(), 5, 1, 3))
                        .build(),
                builder
                        .addCarver(GenerationStep.Carving.AIR, AetherIICarvers.HIGHLANDS_CAVE)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HighlandsPlacedFeatures.CLOUDBED)
                        .addFeature(GenerationStep.Decoration.LAKES, HighlandsPlacedFeatures.WATER_POND)
                        .addFeature(GenerationStep.Decoration.LAKES, HighlandsPlacedFeatures.WATER_POND_UNDERGROUND)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HighlandsPlacedFeatures.SKYROOT_TWIGS)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HighlandsPlacedFeatures.HOLYSTONE_ROCKS)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_SCATTERGLASS)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_AGIOSITE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_ICESTONE_SMALL)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_AMBROSIUM)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_ZANITE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_ARKENIUM)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_GRAVITITE_BURIED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_GRAVITITE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_HOLYSTONE_QUARTZ)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HighlandsPlacedFeatures.ORE_GLINT)
                        .addFeature(GenerationStep.Decoration.FLUID_SPRINGS, HighlandsPlacedFeatures.WATER_SPRING)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.GRASS_FIELD)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.SMALL_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.MEDIUM_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.LARGE_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.IRRADIATED_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.ENCHANTED_GRASS_AND_DIRT_FLOOR)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HighlandsPlacedFeatures.COARSE_AETHER_DIRT_FLOOR)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.COLD_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.GOLDEN_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.BLUE_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.PURPLE_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.PURPLE_AERCLOUD_SMALL)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.GREEN_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.STORM_AERCLOUD)
                        .build(),
                Biome.TemperatureModifier.NONE
        );
    }

    public static Biome makeAercloudSeaBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        MobSpawnSettings.Builder spawnSettingsBuilder = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generationSettingsBuilder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        return fullDefinition(
                false,
                temperature,
                downfall,
                AERCLOUD_SEA_EFFECTS,
                spawnSettingsBuilder
                        .addMobCharge(AetherIIEntityTypes.ZEPHYR.get(), 0.6, 0.16)
                        .addMobCharge(AetherIIEntityTypes.TEMPEST.get(), 0.6, 0.16)
                        .addSpawn(AetherIIMobCategory.AETHER_SKY_MONSTER, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ZEPHYR.get(), 3, 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_SKY_MONSTER, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.TEMPEST.get(), 3, 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.COCKATRICE.get(), 10, 1, 1))
                        .build(),
                generationSettingsBuilder
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HighlandsPlacedFeatures.CLOUDBED)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.COLD_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.GOLDEN_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.BLUE_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.PURPLE_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.PURPLE_AERCLOUD_SMALL)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.GREEN_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HighlandsPlacedFeatures.STORM_AERCLOUD)
                        .build(),
                Biome.TemperatureModifier.NONE
        );
    }

    public static Biome fullDefinition(boolean precipitation, float temperature, float downfall, BiomeSpecialEffects effects, MobSpawnSettings spawnSettings, BiomeGenerationSettings generationSettings, Biome.TemperatureModifier temperatureModifier) {
        return new Biome.BiomeBuilder()
                .hasPrecipitation(precipitation)
                .temperature(temperature)
                .downfall(downfall)
                .specialEffects(effects)
                .mobSpawnSettings(spawnSettings)
                .generationSettings(generationSettings)
                .temperatureAdjustment(temperatureModifier)
                .build();
    }
}