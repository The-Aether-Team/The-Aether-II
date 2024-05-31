package com.aetherteam.aetherii.data.resources.builders.highlands;

import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBiomes;
import com.aetherteam.aetherii.data.resources.registries.placement.AetherIIMiscPlacements;
import com.aetherteam.aetherii.data.resources.registries.placement.AetherIIOrePlacements;
import com.aetherteam.aetherii.data.resources.registries.placement.AetherIIVegetationPlacements;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.sounds.Music;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public class HighlandsBiomeBuilders {
    private static final BiomeSpecialEffects HIGHFIELDS_EFFECTS = new BiomeSpecialEffects.Builder()
            .fogColor(0xecebfc)
            .skyColor(0xc9d1ff)
            .waterColor(0xa2d5f2)
            .waterFogColor(0x55708a)
            .grassColorOverride(0xb5ffd0)
            .foliageColorOverride(0xb1ffcb)
            .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
            .backgroundMusic(new Music(AetherIISoundEvents.MUSIC_HIGHFIELDS, 12000, 24000, true))
            .build();
    private static final BiomeSpecialEffects MAGNETIC_EFFECTS = new BiomeSpecialEffects.Builder()
            .fogColor(0xedeef5)
            .skyColor(0xc5cbeb)
            .waterColor(0xabbdd9)
            .waterFogColor(0x607496)
            .grassColorOverride(0xc9ffd1)
            .foliageColorOverride(0xb1ffcb)
            .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
            .backgroundMusic(new Music(AetherIISoundEvents.MUSIC_MAGNETIC, 12000, 24000, true))
            .build();
    private static final BiomeSpecialEffects ARCTIC_EFFECTS = new BiomeSpecialEffects.Builder()
            .fogColor(0xf3f0ff)
            .skyColor(0xe7e3fc)
            .waterColor(0x637aa8)
            .waterFogColor(0x3e5082)
            .grassColorOverride(0xbdf9ff)
            .foliageColorOverride(0xb1ffcb)
            .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
            .backgroundMusic(new Music(AetherIISoundEvents.MUSIC_ARCTIC, 12000, 24000, true))
            .build();
    private static final BiomeSpecialEffects IRRADIATED_EFFECTS = new BiomeSpecialEffects.Builder()
            .fogColor(0xf0e8dd)
            .skyColor(0xfcebc5)
            .waterColor(0xaed4bf)
            .waterFogColor(0xbccc81)
            .grassColorOverride(0xffe694)
            .foliageColorOverride(0xb1ffcb)
            .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
            .backgroundMusic(new Music(AetherIISoundEvents.MUSIC_IRRADIATED, 12000, 24000, true))
            .ambientParticle(new AmbientParticleSettings(AetherIIParticleTypes.IRRADIATION.get(), 0.00625F))
            .build();
    private static final BiomeSpecialEffects AERCLOUD_SEA_EFFECTS = new BiomeSpecialEffects.Builder()
            .fogColor(0x000000)
            .skyColor(0xd4e7ff)
            .waterColor(0xaad0e6)
            .waterFogColor(0x507799)
            .grassColorOverride(0xb5ffd0)
            .foliageColorOverride(0xb1ffcb)
            .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
            .backgroundMusic(new Music(AetherIISoundEvents.MUSIC_AERCLOUD_SEA, 12000, 24000, true))
            .build();

    public static Biome flourishingFieldBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeHighfieldsBiome(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers).addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherIIMiscPlacements.MOA_NEST).addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherIIVegetationPlacements.FLOURISHING_FIELD_TREES), temperature, downfall);
    }

    public static Biome verdantWoodsBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeHighfieldsBiome(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers).addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherIIMiscPlacements.MOA_NEST).addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherIIVegetationPlacements.VERDANT_WOODS_TREES), temperature, downfall);
    }

    public static Biome shroudedForestBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeHighfieldsBiome(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers), temperature, downfall);
    }

    public static Biome shimmeringBasinBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeHighfieldsBiome(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers), temperature, downfall);
    }

    public static Biome makeHighfieldsBiome(BiomeGenerationSettings.Builder builder, float temperature, float downfall) {
        MobSpawnSettings.Builder spawnSettingsBuilder = new MobSpawnSettings.Builder();
        return fullDefinition(
                true,
                temperature,
                downfall,
                HIGHFIELDS_EFFECTS,
                spawnSettingsBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.AERBUNNY.get(), 10, 1, 2)).addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.FLYING_COW.get(), 6, 1, 3)).addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.PHYG.get(), 6, 1, 3)).build(),
                builder
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherIIVegetationPlacements.SHORT_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherIIVegetationPlacements.MEDIUM_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherIIVegetationPlacements.LONG_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherIIVegetationPlacements.HIGHLANDS_BUSH_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherIIVegetationPlacements.BLUEBERRY_BUSH_PATCH)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, AetherIIMiscPlacements.COAST_QUICKSOIL)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherIIOrePlacements.ORE_AGIOSITE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherIIOrePlacements.ORE_AMBROSIUM)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherIIOrePlacements.ORE_ZANITE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherIIOrePlacements.ORE_ARKENIUM)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherIIOrePlacements.ORE_GRAVITITE_BURIED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherIIOrePlacements.ORE_GRAVITITE)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.COLD_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.BLUE_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.GOLDEN_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.GREEN_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.PURPLE_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.STORM_AERCLOUD)
                        .build(),
                Biome.TemperatureModifier.NONE
        );
    }

    public static Biome magneticScarBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeMagneticBiome(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers).addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherIIMiscPlacements.MOA_NEST), temperature, downfall);
    }

    public static Biome turquoiseForestBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeMagneticBiome(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers).addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherIIMiscPlacements.MOA_NEST), temperature, downfall);
    }

    public static Biome glisteningSwampBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeMagneticBiome(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers), temperature, downfall);
    }

    public static Biome violetHighwoodsBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        return makeMagneticBiome(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers).addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherIIMiscPlacements.MOA_NEST).addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherIIVegetationPlacements.VIOLET_HIGHWOODS_TREES), temperature, downfall);
    }

    public static Biome makeMagneticBiome(BiomeGenerationSettings.Builder builder, float temperature, float downfall) {
        MobSpawnSettings.Builder spawnSettingsBuilder = new MobSpawnSettings.Builder();
        return fullDefinition(
                true,
                temperature,
                downfall,
                MAGNETIC_EFFECTS,
                spawnSettingsBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.AERBUNNY.get(), 10, 1, 2)).addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.FLYING_COW.get(), 6, 1, 3)).addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.PHYG.get(), 6, 1, 3)).build(),
                builder
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherIIVegetationPlacements.SHORT_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherIIVegetationPlacements.MEDIUM_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherIIVegetationPlacements.LONG_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, AetherIIMiscPlacements.COAST_FERROSITE_SAND)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherIIOrePlacements.ORE_AGIOSITE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherIIOrePlacements.ORE_AMBROSIUM)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherIIOrePlacements.ORE_ZANITE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherIIOrePlacements.ORE_ARKENIUM)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherIIOrePlacements.ORE_GRAVITITE_BURIED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherIIOrePlacements.ORE_GRAVITITE)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.COLD_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.BLUE_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.GOLDEN_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.GREEN_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.PURPLE_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.STORM_AERCLOUD)
                        .build(),
                Biome.TemperatureModifier.NONE
        );
    }

    public static Biome makeArcticBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        MobSpawnSettings.Builder spawnSettingsBuilder = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generationSettingsBuilder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        return fullDefinition(
                true,
                temperature,
                downfall,
                ARCTIC_EFFECTS,
                spawnSettingsBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherIIEntityTypes.AERBUNNY.get(), 10, 1, 2)).build(),
                generationSettingsBuilder
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherIIVegetationPlacements.SHORT_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherIIVegetationPlacements.MEDIUM_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherIIVegetationPlacements.LONG_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherIIOrePlacements.ORE_AGIOSITE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherIIOrePlacements.ORE_AMBROSIUM)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherIIOrePlacements.ORE_ZANITE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherIIOrePlacements.ORE_ARKENIUM)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherIIOrePlacements.ORE_GRAVITITE_BURIED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherIIOrePlacements.ORE_GRAVITITE)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.COLD_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.BLUE_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.GOLDEN_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.GREEN_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.PURPLE_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.STORM_AERCLOUD)
                        .build(),
                Biome.TemperatureModifier.NONE
        );
    }

    public static Biome makeIrradiatedBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers, float temperature, float downfall) {
        MobSpawnSettings.Builder spawnSettingsBuilder = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generationSettingsBuilder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        return fullDefinition(
                true,
                temperature,
                downfall,
                IRRADIATED_EFFECTS,
                spawnSettingsBuilder.build(),
                generationSettingsBuilder
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherIIVegetationPlacements.SHORT_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherIIVegetationPlacements.MEDIUM_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherIIVegetationPlacements.LONG_GRASS_PATCH)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherIIOrePlacements.ORE_AGIOSITE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherIIOrePlacements.ORE_AMBROSIUM)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherIIOrePlacements.ORE_ZANITE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherIIOrePlacements.ORE_ARKENIUM)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherIIOrePlacements.ORE_GRAVITITE_BURIED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherIIOrePlacements.ORE_GRAVITITE)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.COLD_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.BLUE_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.GOLDEN_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.GREEN_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.PURPLE_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.STORM_AERCLOUD)
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
                spawnSettingsBuilder.build(),
                generationSettingsBuilder
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.COLD_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.BLUE_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.GOLDEN_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.GREEN_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.PURPLE_AERCLOUD)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherIIMiscPlacements.STORM_AERCLOUD)
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

    public static BiomeSource buildHighlandsBiomeSource(HolderGetter<Biome> biomes) {
        Climate.Parameter fullRange = Climate.Parameter.span(-1.5F, 1.5F);

        Climate.Parameter tempArctic = Climate.Parameter.span(-1.5F, -0.4F);
        Climate.Parameter tempHighfields1 = Climate.Parameter.span(-0.4F, -0.1F);
        Climate.Parameter tempHighfields2 = Climate.Parameter.span(-0.1F, 0.3F);
        Climate.Parameter tempHighfields3 = Climate.Parameter.span(0.3F, 0.65F);
        Climate.Parameter tempMagnetic1 = Climate.Parameter.span(-1.5F, -0.25F);
        Climate.Parameter tempMagnetic2 = Climate.Parameter.span(-0.25F, 0.25F);
        Climate.Parameter tempMagnetic3 = Climate.Parameter.span(0.25F, 0.65F);
        Climate.Parameter tempIrradiated = Climate.Parameter.span(0.65F, 1.5F);

        Climate.Parameter erosionDefault = Climate.Parameter.span(0F, 0.5F);
        Climate.Parameter erosionMagnetic = Climate.Parameter.span(0.5F, 1.5F);

        return MultiNoiseBiomeSource.createFromList(new Climate.ParameterList<>(List.of(

                // Arctic
                Pair.of(new Climate.ParameterPoint(tempArctic, Climate.Parameter.span(-1.0F, -0.2F), fullRange, erosionDefault, fullRange, fullRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.SHEER_TUNDRA)),
                Pair.of(new Climate.ParameterPoint(tempArctic, Climate.Parameter.span(-0.2F, 1.0F), fullRange, erosionDefault, fullRange, fullRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.ENDURING_WOODLAND)),

                // Highfields
                Pair.of(new Climate.ParameterPoint(tempHighfields1, Climate.Parameter.span(-1.0F, -0.25F), fullRange, erosionDefault, fullRange, fullRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.SHROUDED_FOREST)),
                Pair.of(new Climate.ParameterPoint(tempHighfields1, Climate.Parameter.span(-0.25F, 0.15F), fullRange, erosionDefault, fullRange, fullRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.VERDANT_WOODS)),
                Pair.of(new Climate.ParameterPoint(tempHighfields1, Climate.Parameter.span(0.15F, 1.0F), fullRange, erosionDefault, fullRange, fullRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.FLOURISHING_FIELD)),

                Pair.of(new Climate.ParameterPoint(tempHighfields2, Climate.Parameter.span(-1.0F, -0.1F), fullRange, erosionDefault, fullRange, fullRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.VERDANT_WOODS)),
                Pair.of(new Climate.ParameterPoint(tempHighfields2, Climate.Parameter.span(-0.1F, 0.3F), fullRange, erosionDefault, fullRange, fullRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.FLOURISHING_FIELD)),
                Pair.of(new Climate.ParameterPoint(tempHighfields2, Climate.Parameter.span(0.3F, 1.0F), fullRange, erosionDefault, fullRange, fullRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.SHROUDED_FOREST)),

                Pair.of(new Climate.ParameterPoint(tempHighfields3, Climate.Parameter.span(-1.0F, -0.25F), fullRange, erosionDefault, fullRange, fullRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.FLOURISHING_FIELD)),
                Pair.of(new Climate.ParameterPoint(tempHighfields3, Climate.Parameter.span(-0.25F, 0.2F), fullRange, erosionDefault, fullRange, fullRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.VERDANT_WOODS)),
                Pair.of(new Climate.ParameterPoint(tempHighfields3, Climate.Parameter.span(0.2F, 1.0F), fullRange, erosionDefault, fullRange, fullRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.SHROUDED_FOREST)),

                // Magnetic
                Pair.of(new Climate.ParameterPoint(tempMagnetic1, Climate.Parameter.span(-1.0F, -0.2F), fullRange, erosionMagnetic, fullRange, fullRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.VIOLET_HIGHWOODS)),
                Pair.of(new Climate.ParameterPoint(tempMagnetic1, Climate.Parameter.span(-0.2F, 0.15F), fullRange, erosionMagnetic, fullRange, fullRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.MAGNETIC_SCAR)),
                Pair.of(new Climate.ParameterPoint(tempMagnetic1, Climate.Parameter.span(0.15F, 1.0F), fullRange, erosionMagnetic, fullRange, fullRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.GLISTENING_SWAMP)),

                Pair.of(new Climate.ParameterPoint(tempMagnetic2, Climate.Parameter.span(-1.0F, -0.15F), fullRange, erosionMagnetic, fullRange, fullRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.MAGNETIC_SCAR)),
                Pair.of(new Climate.ParameterPoint(tempMagnetic2, Climate.Parameter.span(-0.15F, 0.2F), fullRange, erosionMagnetic, fullRange, fullRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.TURQUOISE_FOREST)),
                Pair.of(new Climate.ParameterPoint(tempMagnetic2, Climate.Parameter.span(0.2F, 1.0F), fullRange, erosionMagnetic, fullRange, fullRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.VIOLET_HIGHWOODS)),

                Pair.of(new Climate.ParameterPoint(tempMagnetic3, Climate.Parameter.span(-1.0F, -0.2F), fullRange, erosionMagnetic, fullRange, fullRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.GLISTENING_SWAMP)),
                Pair.of(new Climate.ParameterPoint(tempMagnetic3, Climate.Parameter.span(-0.2F, 0.1F), fullRange, erosionMagnetic, fullRange, fullRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.MAGNETIC_SCAR)),
                Pair.of(new Climate.ParameterPoint(tempMagnetic3, Climate.Parameter.span(0.1F, 0.3F), fullRange, erosionMagnetic, fullRange, fullRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.TURQUOISE_FOREST)),
                Pair.of(new Climate.ParameterPoint(tempMagnetic3, Climate.Parameter.span(0.3F, 1.0F), fullRange, erosionMagnetic, fullRange, fullRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.GLISTENING_SWAMP)),

                Pair.of(new Climate.ParameterPoint(tempIrradiated, fullRange, fullRange, erosionMagnetic, fullRange, fullRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.MAGNETIC_SCAR)),

                // Irradiated
                Pair.of(new Climate.ParameterPoint(tempIrradiated, Climate.Parameter.span(-1.0F, -0.25F), erosionDefault, fullRange, fullRange, fullRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.BATTLEGROUND_WASTES)),
                Pair.of(new Climate.ParameterPoint(tempIrradiated, Climate.Parameter.span(-0.25F, 0.25F), erosionDefault, fullRange, fullRange, fullRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.CONTAMINATED_JUNGLE)),
                Pair.of(new Climate.ParameterPoint(tempIrradiated, Climate.Parameter.span(0.25F, 1.0F), erosionDefault, fullRange, fullRange, fullRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.BATTLEGROUND_WASTES))
        )));
    }
}