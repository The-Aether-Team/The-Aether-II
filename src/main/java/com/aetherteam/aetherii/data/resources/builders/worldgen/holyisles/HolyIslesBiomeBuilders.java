package com.aetherteam.aetherii.data.resources.builders.worldgen.holyisles;

import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.data.resources.AetherIIMobCategory;
import com.aetherteam.aetherii.data.resources.registries.AetherIICarvers;
import com.aetherteam.aetherii.data.resources.registries.holyisles.HolyIslesPlacedFeatures;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.world.AetherIIEnvironmentAttributes;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.world.attribute.AmbientParticle;
import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;
import java.util.Optional;

// NOTE: Tweaks to biome colors should be done in AetherIIDataMapData
public class HolyIslesBiomeBuilders {
    private static final BiomeSpecialEffects HIGHFIELDS_EFFECTS = new BiomeSpecialEffects.Builder()
            .waterColor(0xa2d5f2)
            .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
            .build();
    private static final BiomeSpecialEffects MAGNETIC_EFFECTS = new BiomeSpecialEffects.Builder()
            .waterColor(0xabbdd9)
            .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
            .build();
    private static final BiomeSpecialEffects ARCTIC_EFFECTS = new BiomeSpecialEffects.Builder()
            .waterColor(0x637aa8)
            .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
            .build();
    private static final BiomeSpecialEffects IRRADIATED_EFFECTS = new BiomeSpecialEffects.Builder()
            .waterColor(0xaed4bf)
            .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
            .build();
    private static final BiomeSpecialEffects AERCLOUD_SEA_EFFECTS = new BiomeSpecialEffects.Builder()
            .waterColor(0xa2d5f2)
            .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
            .build();

    public static Biome flourishingFieldBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeHighfieldsBiome(Optional.of(HolyIslesPlacedFeatures.FLOURISHING_FIELD_TREES), new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                        .addCarver(AetherIICarvers.HOLY_ISLES_CAVE)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HolyIslesPlacedFeatures.COAST_QUICKSOIL)
                        .addFeature(GenerationStep.Decoration.LAKES, HolyIslesPlacedFeatures.WATER_POND)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HolyIslesPlacedFeatures.HOLYSTONE_ROCKS)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.MOA_NEST)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.GRASS_FIELD)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.HIGHFIELDS_FLOWER_FIELD)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.VALKYRIE_SPROUT_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.AETHER_BUSH_PATCH_FIELD),
                new MobSpawnSettings.Builder().creatureGenerationProbability(0.218F)
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.HIGHFIELDS_TAEGORE.get(), 1, 2))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI.get(), 1, 1))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), 1, 2))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.PHYG.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.FLYING_COW.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SHEEPUFF.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.AERBUNNY.get(), 1, 3)),
                temperature, downfall);
    }

    public static Biome verdantWoodsBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeHighfieldsBiome(Optional.of(HolyIslesPlacedFeatures.VERDANT_WOODS_TREES), new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                        .addCarver(AetherIICarvers.HOLY_ISLES_CAVE)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HolyIslesPlacedFeatures.COAST_QUICKSOIL_SPARSE)
                        .addFeature(GenerationStep.Decoration.LAKES, HolyIslesPlacedFeatures.WATER_POND)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HolyIslesPlacedFeatures.SKYROOT_TWIGS)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HolyIslesPlacedFeatures.HOLYSTONE_ROCKS)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.MOA_NEST)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.SMALL_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.MEDIUM_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.LARGE_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.HIGHFIELDS_FLOWER_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.VALKYRIE_SPROUT_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.AETHER_BUSH_PATCH_DEFAULT)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.ORANGE_TREE_PATCH),
                new MobSpawnSettings.Builder().creatureGenerationProbability(0.224F)
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.HIGHFIELDS_TAEGORE.get(), 1, 2))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI.get(), 1, 1))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), 1, 2))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.PHYG.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.FLYING_COW.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SHEEPUFF.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.AERBUNNY.get(), 1, 3)),
                temperature, downfall);
    }

    public static Biome shroudedForestBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeHighfieldsBiome(Optional.empty(), new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                        .addCarver(AetherIICarvers.HOLY_ISLES_CAVE)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HolyIslesPlacedFeatures.COAST_QUICKSOIL_SPARSE)
                        .addFeature(GenerationStep.Decoration.LAKES, HolyIslesPlacedFeatures.WATER_POND)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HolyIslesPlacedFeatures.SKYROOT_TWIGS)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HolyIslesPlacedFeatures.HOLYSTONE_ROCKS)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HolyIslesPlacedFeatures.MOSSY_HOLYSTONE_BOULDER)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.COARSE_AETHER_DIRT_SURFACE)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.SHROUDED_FOREST_TREES)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.TREE_MOSS_COVER)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.SMALL_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.MEDIUM_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.LARGE_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.HIGHFIELDS_FLOWER_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.AETHER_BUSH_PATCH_DEFAULT)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.BRYALINN_FLOWER_PATCH),
                new MobSpawnSettings.Builder().creatureGenerationProbability(0.224F)
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.HIGHFIELDS_TAEGORE.get(), 1, 2))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI.get(), 1, 1))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), 1, 2))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.PHYG.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.FLYING_COW.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SHEEPUFF.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.AERBUNNY.get(), 1, 3)),
                temperature, downfall);
    }

    public static Biome shimmeringBasinBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeHighfieldsBiome(Optional.of(HolyIslesPlacedFeatures.SHIMMERING_BASIN_TREES), new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HolyIslesPlacedFeatures.COAST_QUICKSOIL)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HolyIslesPlacedFeatures.HOLYSTONE_ROCKS)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HolyIslesPlacedFeatures.UNDERWATER_MOSSY_HOLYSTONE_BOULDER)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.SHIMMERING_BASIN_TREES_SUNKEN)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.GRASS_FIELD)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.HIGHFIELDS_FLOWER_FIELD)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.VALKYRIE_SPROUT_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.AETHER_BUSH_PATCH_FIELD)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.ORANGE_TREE_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.BRETTL_PATCH_LAKE)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.FALLEN_SKYROOT_LOG),
                new MobSpawnSettings.Builder().creatureGenerationProbability(0.218F)
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.HIGHFIELDS_TAEGORE.get(), 1, 2))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI.get(), 1, 1))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), 1, 2))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.PHYG.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.FLYING_COW.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SHEEPUFF.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.AERBUNNY.get(), 1, 3)),
                temperature, downfall);
    }

    public static Biome makeHighfieldsBiome(Optional<ResourceKey<PlacedFeature>> tree, BiomeGenerationSettings.Builder builder, MobSpawnSettings.Builder spawnSettingsBuilder, float temperature, float downfall) {
        builder = builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, HolyIslesPlacedFeatures.NOISE_LAKE)
                .addFeature(GenerationStep.Decoration.RAW_GENERATION, HolyIslesPlacedFeatures.CLOUDBED)
                .addFeature(GenerationStep.Decoration.LAKES, HolyIslesPlacedFeatures.WATER_POND_UNDERGROUND)
                .addFeature(GenerationStep.Decoration.LAKES, HolyIslesPlacedFeatures.ALKAHEST_POOL_RARE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_SCATTERGLASS)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_AGIOSITE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_ICESTONE_SMALL)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_AMBROSIUM)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_ZANITE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_ZANITE_MOUNTAIN)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_ARKENIUM)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_GRAVITITE_BURIED)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_GRAVITITE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_HOLYSTONE_QUARTZ)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_CORROBONITE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_GLINT)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.DISK_BRYALINN_MOSS)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.LAKE_DISK_BRYALINN_MOSS)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_HESTVEIL_OPEN)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_HESTVEIL_BURIED)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.UNSTABLE_HOLYSTONE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.UNSTABLE_UNDERSHALE)
                .addFeature(GenerationStep.Decoration.FLUID_SPRINGS, HolyIslesPlacedFeatures.WATER_SPRING)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.GRASS_AND_DIRT_FLOOR)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.COARSE_AETHER_DIRT_FLOOR)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.COARSE_AETHER_DIRT_CEILING)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.COARSE_AETHER_DIRT_OVERHANG)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.EXPOSED_BRYALINN_MOSS_COVER)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.HOLY_ISLES_FLOWER_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.BLUEBERRY_BUSH_PATCH_DEFAULT)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.SHORT_ARILUM)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.ARILUM)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.BLOOMING_ARILUM)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.MIXED_ARILUM)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.POND_ARILUM)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, HolyIslesPlacedFeatures.POINTED_HOLYSTONE)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.HIGH_STORM_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.HIGH_GREEN_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.HIGH_PURPLE_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.MIDDLE_COLD_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.MIDDLE_STORM_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_COLD_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_GOLDEN_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_BLUE_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_GREEN_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_PURPLE_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.LOWER_STORM_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.LOWER_BLUE_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.LOWER_GREEN_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.LOWER_PURPLE_AERCLOUD);
        if (tree.isPresent()) builder = builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, tree.get());
        return highfieldsDefinition(
                true,
                temperature,
                downfall,
                HIGHFIELDS_EFFECTS,
                spawnSettingsBuilder
                        .addMobCharge(AetherIIEntityTypes.PRISMALLARD.get(), 0.55, 0.12)
                        .addMobCharge(AetherIIEntityTypes.ZEPHYR.get(), 0.8, 0.14)
                        .addMobCharge(AetherIIEntityTypes.TEMPEST.get(), 0.65, 0.13)
                        .addMobCharge(AetherIIEntityTypes.COCKATRICE.get(), 0.6, 0.12)
                        .addMobCharge(AetherIIEntityTypes.AERWHALE.get(), 0.5, 0.11)
                        .addSpawn(AetherIIMobCategory.AETHER_AMBIENT, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.GLITTERWING.get(), 1, 2))
                        .addSpawn(AetherIIMobCategory.AETHER_AMBIENT, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SHROUDWING.get(), 1, 2))
                        .addSpawn(AetherIIMobCategory.AETHER_WATER_SURFACE_CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.PRISMALLARD.get(), 1, 3))
                        .addSpawn(AetherIIMobCategory.AETHER_PLANT_HAZARD, 5, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.AECHOR_PLANT.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_PLANT_HAZARD, 3, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.CARRION_SPROUT.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_SKY_HAZARD, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ZEPHYR.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_AERWHALE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.AERWHALE.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_HAZARD, 2, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.BLUE_SWET.get(), 1, 2))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_HAZARD, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.GOLDEN_SWET.get(), 1, 2))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_HAZARD, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SKEPHID.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_BLIGHT_MONSTER, 4, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.COCKATRICE.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_BLIGHT_MONSTER, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.TEMPEST.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_MONSTER, 3, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ARKENIUM_TALUTON.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_MONSTER, 4, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.GRAVITITE_TALUTON.get(), 1, 1))
                        .build(),
                builder.build(),
                Biome.TemperatureModifier.NONE
        );
    }

    public static Biome magneticScarBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeMagneticBiome(Optional.of(HolyIslesPlacedFeatures.MAGNETIC_SCAR_TREES), new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                        .addCarver(AetherIICarvers.HOLY_ISLES_CAVE)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HolyIslesPlacedFeatures.FERROSITE_SPIKE)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HolyIslesPlacedFeatures.FERROSITE_PILLAR)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HolyIslesPlacedFeatures.COAST_FERROSITE_PILLAR)
                        .addFeature(GenerationStep.Decoration.LAKES, HolyIslesPlacedFeatures.WATER_POND)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HolyIslesPlacedFeatures.HOLYSTONE_ROCKS)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.GRASS_FIELD),
                new MobSpawnSettings.Builder().creatureGenerationProbability(0.218F)
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.MAGNETIC_TAEGORE.get(), 1, 2))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.MAGNETIC_BURRUKAI.get(), 1, 1))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), 1, 2))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.PHYG.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.FLYING_COW.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SHEEPUFF.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.AERBUNNY.get(), 1, 3)),
                temperature, downfall);
    }

    public static Biome turquoiseForestBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeMagneticBiome(Optional.of(HolyIslesPlacedFeatures.TURQUOISE_FOREST_TREES), new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                        .addCarver(AetherIICarvers.HOLY_ISLES_CAVE)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HolyIslesPlacedFeatures.FERROSITE_SPIKE)
                        .addFeature(GenerationStep.Decoration.LAKES, HolyIslesPlacedFeatures.WATER_POND)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HolyIslesPlacedFeatures.SKYROOT_TWIGS)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HolyIslesPlacedFeatures.HOLYSTONE_ROCKS)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.MOA_NEST)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.SMALL_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.MEDIUM_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.LARGE_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.ORANGE_TREE_PATCH_RARE),
                new MobSpawnSettings.Builder().creatureGenerationProbability(0.224F)
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.MAGNETIC_TAEGORE.get(), 1, 2))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.MAGNETIC_BURRUKAI.get(), 1, 1))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), 1, 2))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.PHYG.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.FLYING_COW.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SHEEPUFF.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.AERBUNNY.get(), 1, 3)),
                temperature, downfall);
    }

    public static Biome glisteningSwampBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeMagneticBiome(Optional.of(HolyIslesPlacedFeatures.GLISTENING_SWAMP_TREES), new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                        .addFeature(GenerationStep.Decoration.FLUID_SPRINGS, HolyIslesPlacedFeatures.BONUS_WATER_SPRING)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HolyIslesPlacedFeatures.HOLYSTONE_ROCKS)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HolyIslesPlacedFeatures.UNDERWATER_MOSSY_HOLYSTONE_BOULDER)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.GLISTENING_SWAMP_TREES_SUNKEN)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.GLISTENING_SWAMP_MAGNETIC_SHROOMS)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.SMALL_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.MEDIUM_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.LARGE_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.BONUS_MAGNETIC_SHROOM_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.SWAMP_BRYALINN_MOSS_COVER)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.ORANGE_TREE_PATCH_RARE),
                new MobSpawnSettings.Builder().creatureGenerationProbability(0.218F)
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.MAGNETIC_TAEGORE.get(), 1, 2))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.MAGNETIC_BURRUKAI.get(), 1, 1))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), 1, 2))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.PHYG.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.FLYING_COW.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SHEEPUFF.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.AERBUNNY.get(), 1, 3)),
                temperature, downfall);
    }

    public static Biome violetHighwoodsBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeMagneticBiome(Optional.of(HolyIslesPlacedFeatures.VIOLET_HIGHWOODS_TREES), new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                        .addCarver(AetherIICarvers.HOLY_ISLES_CAVE)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HolyIslesPlacedFeatures.FERROSITE_SPIKE)
                        .addFeature(GenerationStep.Decoration.LAKES, HolyIslesPlacedFeatures.WATER_POND)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HolyIslesPlacedFeatures.SKYROOT_TWIGS)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HolyIslesPlacedFeatures.HOLYSTONE_ROCKS)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.MOA_NEST)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.SMALL_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.MEDIUM_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.LARGE_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.FALLEN_WISPROOT_LOG)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.ORANGE_TREE_PATCH_RARE),
                new MobSpawnSettings.Builder().creatureGenerationProbability(0.224F)
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.MAGNETIC_TAEGORE.get(), 1, 2))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.MAGNETIC_BURRUKAI.get(), 1, 1))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), 1, 2))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.PHYG.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.FLYING_COW.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SHEEPUFF.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.AERBUNNY.get(), 1, 3)),
                temperature, downfall);
    }

    public static Biome makeMagneticBiome(Optional<ResourceKey<PlacedFeature>> tree, BiomeGenerationSettings.Builder builder, MobSpawnSettings.Builder spawnSettingsBuilder, float temperature, float downfall) {
        builder = builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, HolyIslesPlacedFeatures.NOISE_LAKE_SWAMP)
                .addFeature(GenerationStep.Decoration.RAW_GENERATION, HolyIslesPlacedFeatures.COAST_FERROSITE_SAND)
                .addFeature(GenerationStep.Decoration.RAW_GENERATION, HolyIslesPlacedFeatures.CLOUDBED)
                .addFeature(GenerationStep.Decoration.LAKES, HolyIslesPlacedFeatures.WATER_POND_UNDERGROUND)
                .addFeature(GenerationStep.Decoration.LAKES, HolyIslesPlacedFeatures.ALKAHEST_POOL_RARE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_SCATTERGLASS)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_AGIOSITE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_ICESTONE_SMALL)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_AMBROSIUM)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_ZANITE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_ZANITE_MOUNTAIN)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_ARKENIUM)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_GRAVITITE_BURIED)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_GRAVITITE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_HOLYSTONE_QUARTZ)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_CORROBONITE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_GLINT)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_HESTVEIL_OPEN)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_HESTVEIL_BURIED)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.UNSTABLE_HOLYSTONE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.UNSTABLE_UNDERSHALE)
                .addFeature(GenerationStep.Decoration.FLUID_SPRINGS, HolyIslesPlacedFeatures.WATER_SPRING)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.GRASS_AND_DIRT_FLOOR)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.SMALL_MYCELIUM_FLOOR)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.BIG_MYCELIUM_FLOOR)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.COARSE_AETHER_DIRT_FLOOR)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.COARSE_AETHER_DIRT_CEILING)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.COARSE_AETHER_DIRT_OVERHANG)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.HOLY_ISLES_FLOWER_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.MAGNETIC_FLOWER_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.BLUEBERRY_BUSH_PATCH_DEFAULT)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.SHORT_ARILUM)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.ARILUM)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.BLOOMING_ARILUM)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.MAGNETIC_SHROOM_PATCH)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, HolyIslesPlacedFeatures.POINTED_HOLYSTONE)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.HIGH_STORM_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.HIGH_GREEN_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.HIGH_PURPLE_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.MIDDLE_COLD_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.MIDDLE_STORM_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_COLD_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_GOLDEN_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_BLUE_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_GREEN_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_PURPLE_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.LOWER_STORM_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.LOWER_BLUE_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.LOWER_GREEN_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.LOWER_PURPLE_AERCLOUD);
        if (tree.isPresent()) builder = builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, tree.get());
        return magneticDefinition(
                true,
                temperature,
                downfall,
                MAGNETIC_EFFECTS,
                spawnSettingsBuilder
                        .addMobCharge(AetherIIEntityTypes.ZEPHYR.get(), 0.8, 0.14)
                        .addMobCharge(AetherIIEntityTypes.TEMPEST.get(), 0.65, 0.13)
                        .addMobCharge(AetherIIEntityTypes.COCKATRICE.get(), 0.6, 0.12)
                        .addMobCharge(AetherIIEntityTypes.AERWHALE.get(), 0.5, 0.11)
                        .addSpawn(AetherIIMobCategory.AETHER_AMBIENT, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.GLITTERWING.get(), 1, 2))
                        .addSpawn(AetherIIMobCategory.AETHER_AMBIENT, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SHROUDWING.get(), 1, 2))
                        .addSpawn(AetherIIMobCategory.AETHER_PLANT_HAZARD, 5, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.AECHOR_PLANT.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_PLANT_HAZARD, 3, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.CARRION_SPROUT.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_SKY_HAZARD, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ZEPHYR.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_AERWHALE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.AERWHALE.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_HAZARD, 2, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.BLUE_SWET.get(), 1, 2))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_HAZARD, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.GOLDEN_SWET.get(), 1, 2))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_HAZARD, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SKEPHID.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_BLIGHT_MONSTER, 4, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.COCKATRICE.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_BLIGHT_MONSTER, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.TEMPEST.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_MONSTER, 3, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ARKENIUM_TALUTON.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_MONSTER, 4, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.GRAVITITE_TALUTON.get(), 1, 1))
                        .build(),
                builder.build(),
                Biome.TemperatureModifier.NONE
        );
    }

    public static Biome frigidSierraBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeArcticBiome(Optional.of(HolyIslesPlacedFeatures.FRIGID_SIERRA_TREES), new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                        .addCarver(AetherIICarvers.HOLY_ISLES_CAVE)
                        .addFeature(GenerationStep.Decoration.LAKES, HolyIslesPlacedFeatures.WATER_POND)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HolyIslesPlacedFeatures.ICESTONE_BOULDER)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.GRASS_FIELD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.FREEZE_TOP_LAYER_ARCTIC),
                new MobSpawnSettings.Builder().creatureGenerationProbability(0.208F)
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ARCTIC_TAEGORE.get(), 1, 2))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ARCTIC_BURRUKAI.get(), 1, 1))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ARCTIC_KIRRID.get(), 1, 2))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.PHYG.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.FLYING_COW.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SHEEPUFF.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.AERBUNNY.get(), 1, 3)),
                temperature, downfall, true);
    }

    public static Biome enduringWoodlandBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeArcticBiome(Optional.of(HolyIslesPlacedFeatures.ENDURING_WOODLAND_TREES), new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                        .addCarver(AetherIICarvers.HOLY_ISLES_CAVE)
                        .addFeature(GenerationStep.Decoration.LAKES, HolyIslesPlacedFeatures.WATER_POND)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.MOA_NEST)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.SMALL_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.MEDIUM_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.LARGE_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.BLUEBERRY_BUSH_PATCH_RARE)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.ORANGE_TREE_PATCH_RARE)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.FREEZE_TOP_LAYER_ARCTIC),
                new MobSpawnSettings.Builder().creatureGenerationProbability(0.212F)
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ARCTIC_TAEGORE.get(), 1, 2))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ARCTIC_BURRUKAI.get(), 1, 1))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ARCTIC_KIRRID.get(), 1, 2))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.PHYG.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.FLYING_COW.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SHEEPUFF.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.AERBUNNY.get(), 1, 3)),
                temperature, downfall, true);
    }

    public static Biome frozenLakesBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeArcticBiome(Optional.of(HolyIslesPlacedFeatures.FROZEN_LAKES_TREES), new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HolyIslesPlacedFeatures.HOLYSTONE_ROCKS)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HolyIslesPlacedFeatures.UNDERWATER_ARCTIC_HOLYSTONE_BOULDER)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.GRASS_FIELD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.FREEZE_TOP_LAYER_ARCTIC),
                new MobSpawnSettings.Builder().creatureGenerationProbability(0.212F)
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ARCTIC_TAEGORE.get(), 1, 2))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ARCTIC_BURRUKAI.get(), 1, 1))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ARCTIC_KIRRID.get(), 1, 2))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.PHYG.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.FLYING_COW.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SHEEPUFF.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.AERBUNNY.get(), 1, 3)),
                temperature, downfall, true);
    }

    public static Biome sheerTundraBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeArcticBiome(Optional.empty(), new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                        .addCarver(AetherIICarvers.HOLY_ISLES_CAVE)
                        .addFeature(GenerationStep.Decoration.LAKES, HolyIslesPlacedFeatures.WATER_POND_TUNDRA)
                        .addFeature(GenerationStep.Decoration.LAKES, HolyIslesPlacedFeatures.WATER_SPRING)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HolyIslesPlacedFeatures.HOLYSTONE_ROCKS_TUNDRA)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HolyIslesPlacedFeatures.MOSSY_HOLYSTONE_BOULDER_TUNDRA)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HolyIslesPlacedFeatures.ICESTONE_BOULDER)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.MOA_NEST)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.GRASS_FIELD)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.EXPOSED_SHAYELINN_MOSS_COVER)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.FREEZE_TOP_LAYER_TUNDRA),
                new MobSpawnSettings.Builder().creatureGenerationProbability(0.218F)
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ARCTIC_TAEGORE.get(), 1, 2))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ARCTIC_BURRUKAI.get(), 1, 1))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ARCTIC_KIRRID.get(), 1, 2))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.PHYG.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.FLYING_COW.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SHEEPUFF.get(), 2, 4))
                        .addSpawn(MobCategory.CREATURE, 14, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.AERBUNNY.get(), 1, 3)),
                temperature, downfall, true);
    }

    public static Biome makeArcticBiome(Optional<ResourceKey<PlacedFeature>> tree, BiomeGenerationSettings.Builder builder, MobSpawnSettings.Builder spawnSettingsBuilder,  float temperature, float downfall, boolean precipitation) {
        builder = builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, HolyIslesPlacedFeatures.COAST_ARCTIC_PACKED_ICE)
                .addFeature(GenerationStep.Decoration.RAW_GENERATION, HolyIslesPlacedFeatures.NOISE_LAKE_ARCTIC)
                .addFeature(GenerationStep.Decoration.RAW_GENERATION, HolyIslesPlacedFeatures.CLOUDBED)
                .addFeature(GenerationStep.Decoration.RAW_GENERATION, HolyIslesPlacedFeatures.COASTAL_ARCTIC_ICE_SPIKE)
                .addFeature(GenerationStep.Decoration.RAW_GENERATION, HolyIslesPlacedFeatures.ARCTIC_ICE_SPIKE_CLUSTER)
                .addFeature(GenerationStep.Decoration.LAKES, HolyIslesPlacedFeatures.WATER_POND_UNDERGROUND)
                .addFeature(GenerationStep.Decoration.LAKES, HolyIslesPlacedFeatures.ALKAHEST_POOL_RARE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_SCATTERGLASS)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_AGIOSITE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_ICESTONE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_AMBROSIUM)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_ZANITE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_ZANITE_MOUNTAIN)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_ARKENIUM)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_GRAVITITE_BURIED)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_GRAVITITE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_HOLYSTONE_QUARTZ)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_CORROBONITE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_GLINT)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_HESTVEIL_OPEN)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_HESTVEIL_BURIED)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.UNSTABLE_HOLYSTONE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.UNSTABLE_UNDERSHALE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, HolyIslesPlacedFeatures.POINTED_HOLYSTONE)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.GRASS_AND_DIRT_FLOOR)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.COARSE_AETHER_DIRT_FLOOR)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.COARSE_AETHER_DIRT_FROSTED_CEILING)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.ICE_OVERHANG)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.HOLY_ISLES_FLOWER_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.ARCTIC_FLOWER_PATCH)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.HIGH_STORM_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.HIGH_GREEN_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.HIGH_PURPLE_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.MIDDLE_COLD_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.MIDDLE_STORM_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_COLD_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_GOLDEN_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_BLUE_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_GREEN_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_PURPLE_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.LOWER_STORM_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.LOWER_BLUE_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.LOWER_GREEN_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.LOWER_PURPLE_AERCLOUD);
        if (tree.isPresent()) builder = builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, tree.get());
        return arcticDefinition(
                precipitation,
                temperature,
                downfall,
                ARCTIC_EFFECTS,
                spawnSettingsBuilder
                        .addMobCharge(AetherIIEntityTypes.ZEPHYR.get(), 0.8, 0.14)
                        .addMobCharge(AetherIIEntityTypes.TEMPEST.get(), 0.65, 0.13)
                        .addMobCharge(AetherIIEntityTypes.COCKATRICE.get(), 0.6, 0.12)
                        .addMobCharge(AetherIIEntityTypes.AERWHALE.get(), 0.5, 0.11)
                        .addSpawn(AetherIIMobCategory.AETHER_AMBIENT, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.GLITTERWING.get(), 1, 2))
                        .addSpawn(AetherIIMobCategory.AETHER_AMBIENT, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SHROUDWING.get(), 1, 2))
                        .addSpawn(AetherIIMobCategory.AETHER_PLANT_HAZARD, 5, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.AECHOR_PLANT.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_PLANT_HAZARD, 3, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.CARRION_SPROUT.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_SKY_HAZARD, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ZEPHYR.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_AERWHALE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.AERWHALE.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_HAZARD, 2, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.BLUE_SWET.get(), 1, 2))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_HAZARD, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.GOLDEN_SWET.get(), 1, 2))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_HAZARD, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SKEPHID.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_BLIGHT_MONSTER, 4, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.COCKATRICE.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_BLIGHT_MONSTER, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.TEMPEST.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_MONSTER, 3, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ARKENIUM_TALUTON.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_MONSTER, 4, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.GRAVITITE_TALUTON.get(), 1, 1))
                        .build(),
                builder.build(),
                Biome.TemperatureModifier.FROZEN
        );
    }

    public static Biome contaminatedJungleBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeIrradiatedBiome(Optional.of(HolyIslesPlacedFeatures.CONTAMINATED_JUNGLE_TREES), new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.ORANGE_TREE_PATCH_IRRADIATED),
                temperature, downfall);
    }

    public static Biome battlegroundWastesBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeIrradiatedBiome(Optional.of(HolyIslesPlacedFeatures.BATTLEGROUND_WASTES_TREES), new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HolyIslesPlacedFeatures.CRATER),
                temperature, downfall);
    }

    public static Biome makeIrradiatedBiome(Optional<ResourceKey<PlacedFeature>> tree, BiomeGenerationSettings.Builder builder, float temperature, float downfall) {
        MobSpawnSettings.Builder spawnSettingsBuilder = new MobSpawnSettings.Builder();
        builder = builder.addCarver(AetherIICarvers.HOLY_ISLES_CAVE).addFeature(GenerationStep.Decoration.RAW_GENERATION, HolyIslesPlacedFeatures.CLOUDBED)
                .addFeature(GenerationStep.Decoration.LAKES, HolyIslesPlacedFeatures.WATER_POND)
                .addFeature(GenerationStep.Decoration.LAKES, HolyIslesPlacedFeatures.WATER_POND_UNDERGROUND)
                .addFeature(GenerationStep.Decoration.LAKES, HolyIslesPlacedFeatures.ALKAHEST_POOL_RARE)
                .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HolyIslesPlacedFeatures.SKYROOT_TWIGS)
                .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, HolyIslesPlacedFeatures.HOLYSTONE_ROCKS)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_SCATTERGLASS)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_AGIOSITE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_ICESTONE_SMALL)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_AMBROSIUM)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_ZANITE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_ZANITE_MOUNTAIN)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_ARKENIUM)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_GRAVITITE_BURIED)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_GRAVITITE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_HOLYSTONE_QUARTZ)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_GLINT)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_HESTVEIL_OPEN)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_HESTVEIL_BURIED)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.UNSTABLE_HOLYSTONE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.UNSTABLE_UNDERSHALE)
                .addFeature(GenerationStep.Decoration.FLUID_SPRINGS, HolyIslesPlacedFeatures.WATER_SPRING)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.GRASS_FIELD)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.SMALL_GRASS_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.MEDIUM_GRASS_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.LARGE_GRASS_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.IRRADIATED_GRASS_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.ENCHANTED_GRASS_AND_DIRT_FLOOR)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.COARSE_AETHER_DIRT_FLOOR)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.BLUEBERRY_BUSH_PATCH_IRRADIATED)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HolyIslesPlacedFeatures.BRETTL_PATCH_IRRADIATED)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, HolyIslesPlacedFeatures.POINTED_HOLYSTONE)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.HIGH_STORM_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.HIGH_GREEN_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.HIGH_PURPLE_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.MIDDLE_COLD_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.MIDDLE_STORM_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_COLD_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_GOLDEN_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_BLUE_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_GREEN_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_PURPLE_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.LOWER_STORM_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.LOWER_BLUE_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.LOWER_GREEN_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.LOWER_PURPLE_AERCLOUD);
        if (tree.isPresent()) builder = builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, tree.get());
        return irradiatedDefinition(
                true,
                temperature,
                downfall,
                IRRADIATED_EFFECTS,
                spawnSettingsBuilder
                        .addMobCharge(AetherIIEntityTypes.ZEPHYR.get(), 0.8, 0.14)
                        .addMobCharge(AetherIIEntityTypes.TEMPEST.get(), 0.65, 0.13)
                        .addMobCharge(AetherIIEntityTypes.COCKATRICE.get(), 0.6, 0.12)
                        .addSpawn(AetherIIMobCategory.AETHER_SKY_HAZARD, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ZEPHYR.get(), 1, 2))
                        .addSpawn(AetherIIMobCategory.AETHER_BLIGHT_MONSTER, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.TEMPEST.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_MONSTER, 3, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ARKENIUM_TALUTON.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_MONSTER, 4, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.GRAVITITE_TALUTON.get(), 1, 1))
                        .build(),
                builder.build(),
                Biome.TemperatureModifier.NONE
        );
    }

    public static Biome makeAercloudSeaBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        MobSpawnSettings.Builder spawnSettingsBuilder = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generationSettingsBuilder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        return highfieldsDefinition(
                true,
                temperature,
                downfall,
                AERCLOUD_SEA_EFFECTS,
                spawnSettingsBuilder
                        .addMobCharge(AetherIIEntityTypes.ZEPHYR.get(), 0.8, 0.14)
                        .addMobCharge(AetherIIEntityTypes.TEMPEST.get(), 0.65, 0.13)
                        .addMobCharge(AetherIIEntityTypes.COCKATRICE.get(), 0.6, 0.12)
                        .addMobCharge(AetherIIEntityTypes.AERWHALE.get(), 0.5, 0.11)
                        .addSpawn(AetherIIMobCategory.AETHER_SKY_HAZARD, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ZEPHYR.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_AERWHALE, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.AERWHALE.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_HAZARD, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SKEPHID.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_BLIGHT_MONSTER, 4, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.COCKATRICE.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_BLIGHT_MONSTER, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.TEMPEST.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_MONSTER, 3, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ARKENIUM_TALUTON.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_MONSTER, 4, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.GRAVITITE_TALUTON.get(), 1, 1))
                        .build(),
                generationSettingsBuilder
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HolyIslesPlacedFeatures.CLOUDBED)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.HIGH_STORM_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.HIGH_GREEN_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.HIGH_PURPLE_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.MIDDLE_COLD_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.MIDDLE_STORM_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_COLD_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_GOLDEN_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_BLUE_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_GREEN_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_PURPLE_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.LOWER_STORM_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.LOWER_BLUE_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.LOWER_GREEN_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.LOWER_PURPLE_AERCLOUD)
                        .build(),
                Biome.TemperatureModifier.NONE
        );
    }

    public static Biome makeHestveilCavernsBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        MobSpawnSettings.Builder spawnSettingsBuilder = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generationSettingsBuilder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        return highfieldsDefinition(
                true,
                temperature,
                downfall,
                AERCLOUD_SEA_EFFECTS,
                spawnSettingsBuilder
                        .addMobCharge(AetherIIEntityTypes.ZEPHYR.get(), 0.8, 0.14)
                        .addMobCharge(AetherIIEntityTypes.TEMPEST.get(), 0.65, 0.13)
                        .addMobCharge(AetherIIEntityTypes.COCKATRICE.get(), 0.6, 0.12)
                        .addSpawn(AetherIIMobCategory.AETHER_SKY_HAZARD, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ZEPHYR.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_HAZARD, 2, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.BLUE_SWET.get(), 1, 2))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_HAZARD, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.GOLDEN_SWET.get(), 1, 2))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_HAZARD, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.SKEPHID.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_BLIGHT_MONSTER, 4, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.COCKATRICE.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_BLIGHT_MONSTER, 1, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.TEMPEST.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_MONSTER, 3, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.ARKENIUM_TALUTON.get(), 1, 1))
                        .addSpawn(AetherIIMobCategory.AETHER_DARKNESS_MONSTER, 4, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.GRAVITITE_TALUTON.get(), 1, 1))
                        .build(),
                generationSettingsBuilder
                        .addCarver(AetherIICarvers.HESTVEIL_CAVE)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, HolyIslesPlacedFeatures.CLOUDBED)
                        .addFeature(GenerationStep.Decoration.LAKES, HolyIslesPlacedFeatures.ALKAHEST_POOL)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_SCATTERGLASS)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_AMBROSIUM)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_ZANITE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_ZANITE_MOUNTAIN)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_ARKENIUM)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_GRAVITITE_BURIED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_GRAVITITE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_HOLYSTONE_QUARTZ)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_CORROBONITE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_GLINT)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_HESTVEIL_OPEN)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.ORE_HESTVEIL_BURIED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.UNSTABLE_HOLYSTONE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, HolyIslesPlacedFeatures.UNSTABLE_UNDERSHALE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, HolyIslesPlacedFeatures.POINTED_ICHORITE)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.HIGH_STORM_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.HIGH_GREEN_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.HIGH_PURPLE_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.MIDDLE_COLD_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.MIDDLE_STORM_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_COLD_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_GOLDEN_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_BLUE_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_GREEN_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.SURFACE_PURPLE_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.LOWER_STORM_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.LOWER_BLUE_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.LOWER_GREEN_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, HolyIslesPlacedFeatures.LOWER_PURPLE_AERCLOUD)
                        .build(),
                Biome.TemperatureModifier.NONE
        );
    }

    public static Biome highfieldsDefinition(boolean precipitation, float temperature, float downfall, BiomeSpecialEffects effects, MobSpawnSettings spawnSettings, BiomeGenerationSettings generationSettings, Biome.TemperatureModifier temperatureModifier) {
        return new Biome.BiomeBuilder()
                .setAttribute(AetherIIEnvironmentAttributes.AETHER_GRASS_COLOR.get(), 0xb5ffd0)
                .setAttribute(AetherIIEnvironmentAttributes.BASE_SKY_COLOR.get(), 0xC2C0E0)
                .setAttribute(AetherIIEnvironmentAttributes.TOP_SKY_GRADIENT_COLOR.get(), 0x8A81CB)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 0xecebfc)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 0xc9d1ff)
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 0x55708a)
                .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(new Music(AetherIISoundEvents.MUSIC_AETHER, 3600, 10800, false)))
                .setAttribute(EnvironmentAttributes.FOG_START_DISTANCE, -16.0F)
                .setAttribute(EnvironmentAttributes.FOG_END_DISTANCE, 1024.0F)
                .hasPrecipitation(precipitation)
                .temperature(temperature)
                .downfall(downfall)
                .specialEffects(effects)
                .mobSpawnSettings(spawnSettings)
                .generationSettings(generationSettings)
                .temperatureAdjustment(temperatureModifier)
                .build();
    }

    public static Biome magneticDefinition(boolean precipitation, float temperature, float downfall, BiomeSpecialEffects effects, MobSpawnSettings spawnSettings, BiomeGenerationSettings generationSettings, Biome.TemperatureModifier temperatureModifier) {
        return new Biome.BiomeBuilder()
                .setAttribute(AetherIIEnvironmentAttributes.AETHER_GRASS_COLOR.get(), 0xc9ffd1)
                .setAttribute(AetherIIEnvironmentAttributes.BASE_SKY_COLOR.get(), 0xC2C0E0)
                .setAttribute(AetherIIEnvironmentAttributes.TOP_SKY_GRADIENT_COLOR.get(), 0x8A81CB)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 0xedeef5)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 0xc5cbeb)
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 0x607496)
                .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(new Music(AetherIISoundEvents.MUSIC_AETHER, 3600, 10800, false)))
                .setAttribute(EnvironmentAttributes.FOG_START_DISTANCE, -16.0F)
                .setAttribute(EnvironmentAttributes.FOG_END_DISTANCE, 1024.0F)
                .hasPrecipitation(precipitation)
                .temperature(temperature)
                .downfall(downfall)
                .specialEffects(effects)
                .mobSpawnSettings(spawnSettings)
                .generationSettings(generationSettings)
                .temperatureAdjustment(temperatureModifier)
                .build();
    }

    public static Biome arcticDefinition(boolean precipitation, float temperature, float downfall, BiomeSpecialEffects effects, MobSpawnSettings spawnSettings, BiomeGenerationSettings generationSettings, Biome.TemperatureModifier temperatureModifier) {
        return new Biome.BiomeBuilder()
                .setAttribute(AetherIIEnvironmentAttributes.AETHER_GRASS_COLOR.get(), 0xbdf9ff)
                .setAttribute(AetherIIEnvironmentAttributes.BASE_SKY_COLOR.get(), 0xC2C0E0)
                .setAttribute(AetherIIEnvironmentAttributes.TOP_SKY_GRADIENT_COLOR.get(), 0x8A81CB)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 0xf3f0ff)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 0xe7e3fc)
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 0x3e5082)
                .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(new Music(AetherIISoundEvents.MUSIC_AETHER, 3600, 10800, false)))
                .setAttribute(EnvironmentAttributes.FOG_START_DISTANCE, -32.0F)
                .setAttribute(EnvironmentAttributes.FOG_END_DISTANCE, 512.0F)
                .hasPrecipitation(precipitation)
                .temperature(temperature)
                .downfall(downfall)
                .specialEffects(effects)
                .mobSpawnSettings(spawnSettings)
                .generationSettings(generationSettings)
                .temperatureAdjustment(temperatureModifier)
                .build();
    }

    public static Biome irradiatedDefinition(boolean precipitation, float temperature, float downfall, BiomeSpecialEffects effects, MobSpawnSettings spawnSettings, BiomeGenerationSettings generationSettings, Biome.TemperatureModifier temperatureModifier) {
        return new Biome.BiomeBuilder()
                .setAttribute(AetherIIEnvironmentAttributes.AETHER_GRASS_COLOR.get(), 0xffdd99)
                .setAttribute(AetherIIEnvironmentAttributes.BASE_SKY_COLOR.get(), 0xC2C0E0)
                .setAttribute(AetherIIEnvironmentAttributes.TOP_SKY_GRADIENT_COLOR.get(), 0x8A81CB)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 0xF0E8BE)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 0xfcebc5)
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 0xbccc81)
                .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(new Music(AetherIISoundEvents.MUSIC_AETHER, 3600, 10800, false)))
                .setAttribute(EnvironmentAttributes.AMBIENT_PARTICLES, List.of(new AmbientParticle(AetherIIParticleTypes.AMBROSIUM.get(), 0.00625F)))
                .setAttribute(EnvironmentAttributes.FOG_START_DISTANCE, -64.0F)
                .setAttribute(EnvironmentAttributes.FOG_END_DISTANCE, 256.0F)
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