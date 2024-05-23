package com.aetherteam.aetherii.data.resources.builders.highlands;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.data.resources.ClimateGrid;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBiomes;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.ArrayList;
import java.util.List;

public class HighlandsBiomeBuilders {
    private static final BiomeSpecialEffects HIGHFIELDS_EFFECTS = new BiomeSpecialEffects.Builder()
            .fogColor(0x9393bc)
            .skyColor(0xc0c0ff)
            .waterColor(0x3f76e4)
            .waterFogColor(0x050533)
            .grassColorOverride(0xb5ffd0)
            .foliageColorOverride(0xb1ffcb)
            .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
            .backgroundMusic(new Music(AetherIISoundEvents.MUSIC_HIGHFIELDS, 12000, 24000, true))
            .build();
    private static final BiomeSpecialEffects MAGNETIC_EFFECTS = new BiomeSpecialEffects.Builder()
            .fogColor(0x9393bc)
            .skyColor(0xc0c0ff)
            .waterColor(0x3f76e4)
            .waterFogColor(0x050533)
            .grassColorOverride(0xc9ffd1)
            .foliageColorOverride(0xb1ffcb)
            .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
            .backgroundMusic(new Music(AetherIISoundEvents.MUSIC_MAGNETIC, 12000, 24000, true))
            .build();
    private static final BiomeSpecialEffects ARCTIC_EFFECTS = new BiomeSpecialEffects.Builder()
            .fogColor(0x9393bc)
            .skyColor(0xc0c0ff)
            .waterColor(0x3f76e4)
            .waterFogColor(0x050533)
            .grassColorOverride(0xbdf9ff)
            .foliageColorOverride(0xb1ffcb)
            .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
            .backgroundMusic(new Music(AetherIISoundEvents.MUSIC_ARCTIC, 12000, 24000, true))
            .build();
    private static final BiomeSpecialEffects IRRADIATED_EFFECTS = new BiomeSpecialEffects.Builder()
            .fogColor(0x9393bc)
            .skyColor(0xc0c0ff)
            .waterColor(0x3f76e4)
            .waterFogColor(0x050533)
            .grassColorOverride(0xfff7a8)
            .foliageColorOverride(0xb1ffcb)
            .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
            .backgroundMusic(new Music(AetherIISoundEvents.MUSIC_IRRADIATED, 12000, 24000, true))
            .build();
    private static final BiomeSpecialEffects AERCLOUD_SEA_EFFECTS = new BiomeSpecialEffects.Builder()
            .fogColor(0x9393bc)
            .skyColor(0xc0c0ff)
            .waterColor(0x3f76e4)
            .waterFogColor(0x050533)
            .grassColorOverride(0xb5ffd0)
            .foliageColorOverride(0xb1ffcb)
            .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
            .backgroundMusic(new Music(AetherIISoundEvents.MUSIC_AERCLOUD_SEA, 12000, 24000, true))
            .build();

    public static Biome makeHighfieldsBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        BiomeGenerationSettings.Builder generationSettingsBuilder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        MobSpawnSettings.Builder spawnSettingsBuilder = new MobSpawnSettings.Builder();
        return fullDefinition(
                false,
                0.8F,
                0.0F,
                HIGHFIELDS_EFFECTS,
                spawnSettingsBuilder.build(),
                generationSettingsBuilder.build(),
                Biome.TemperatureModifier.NONE
        );
    }

    public static Biome makeMagneticBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        BiomeGenerationSettings.Builder generationSettingsBuilder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        MobSpawnSettings.Builder spawnSettingsBuilder = new MobSpawnSettings.Builder();
        return fullDefinition(
                false,
                0.8F,
                0.0F,
                MAGNETIC_EFFECTS,
                spawnSettingsBuilder.build(),
                generationSettingsBuilder.build(),
                Biome.TemperatureModifier.NONE
        );
    }

    public static Biome makeArcticBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        BiomeGenerationSettings.Builder generationSettingsBuilder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        MobSpawnSettings.Builder spawnSettingsBuilder = new MobSpawnSettings.Builder();
        return fullDefinition(
                false,
                0.8F,
                0.0F,
                ARCTIC_EFFECTS,
                spawnSettingsBuilder.build(),
                generationSettingsBuilder.build(),
                Biome.TemperatureModifier.NONE
        );
    }

    public static Biome makeIrradiatedBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        BiomeGenerationSettings.Builder generationSettingsBuilder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        MobSpawnSettings.Builder spawnSettingsBuilder = new MobSpawnSettings.Builder();
        return fullDefinition(
                false,
                0.8F,
                0.0F,
                IRRADIATED_EFFECTS,
                spawnSettingsBuilder.build(),
                generationSettingsBuilder.build(),
                Biome.TemperatureModifier.NONE
        );
    }

    public static Biome makeAercloudSeaBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        BiomeGenerationSettings.Builder generationSettingsBuilder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        MobSpawnSettings.Builder spawnSettingsBuilder = new MobSpawnSettings.Builder();
        return fullDefinition(
                false,
                0.8F,
                0.0F,
                AERCLOUD_SEA_EFFECTS,
                spawnSettingsBuilder.build(),
                generationSettingsBuilder.build(),
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
//        List<Climate.Parameter> humidities = Lists.newArrayList(
//                Climate.Parameter.span(-1.0F, -0.35F),
//                Climate.Parameter.span(-0.35F, -0.1F),
//                Climate.Parameter.span(-0.1F, 0.1F),
//                Climate.Parameter.span(0.1F, 0.3F),
//                Climate.Parameter.span(0.3F, 0.7F),
//                Climate.Parameter.span(0.7F, 1.0F));
//        List<Climate.Parameter> temperatures = Lists.newArrayList(
//                Climate.Parameter.span(-1.0F, -0.45F),
//                Climate.Parameter.span(-0.45F, -0.15F),
//                Climate.Parameter.span(-0.15F, 0.2F),
//                Climate.Parameter.span(0.2F, 0.55F),
//                Climate.Parameter.span(0.55F, 1.0F));
//        List<Climate.Parameter> erosions = Lists.newArrayList(
//                Climate.Parameter.span(-1.0F, -0.78F),
//                Climate.Parameter.span(-0.78F, -0.375F),
//                Climate.Parameter.span(-0.375F, -0.2225F),
//                Climate.Parameter.span(-0.2225F, 0.05F),
//                Climate.Parameter.span(0.05F, 0.45F),
//                Climate.Parameter.span(0.45F, 0.55F),
//                Climate.Parameter.span(0.55F, 1.0F));
//        List<Climate.Parameter> continentalnesses = Lists.newArrayList(
//                Climate.Parameter.span(-1.2F, -1.05F),
//                Climate.Parameter.span(-1.05F, -0.455F),
//                Climate.Parameter.span(-0.455F, -0.19F),
//                Climate.Parameter.span(-0.19F, -0.11F),
//                Climate.Parameter.span(-0.11F, 0.55F),
//                Climate.Parameter.span(-0.11F, 0.03F),
//                Climate.Parameter.span(0.03F, 0.3F),
//                Climate.Parameter.span(0.3F, 1.0F));
//        List<Climate.Parameter> weirdnesses = Lists.newArrayList(
//                Climate.Parameter.span(-1.0F, -0.93333334F),
//                Climate.Parameter.span(-0.93333334F, -0.7666667F),
//                Climate.Parameter.span(-0.7666667F, -0.56666666F),
//                Climate.Parameter.span(-0.56666666F, -0.4F),
//                Climate.Parameter.span(-0.4F, -0.26666668F),
//                Climate.Parameter.span(-0.26666668F, -0.05F),
//                Climate.Parameter.span(-0.05F, 0.05F),
//                Climate.Parameter.span(0.05F, 0.26666668F),
//                Climate.Parameter.span(0.26666668F, 0.4F),
//                Climate.Parameter.span(0.4F, 0.56666666F),
//                Climate.Parameter.span(0.56666666F, 0.7666667F),
//                Climate.Parameter.span(0.7666667F, 0.93333334F),
//                Climate.Parameter.span(0.93333334F, 1.0F));
//        List<Climate.Parameter> depths = Lists.newArrayList(
//                Climate.Parameter.point(0.0F),
//                Climate.Parameter.span(0.2F, 0.9F),
//                Climate.Parameter.point(1.0F),
//                Climate.Parameter.point(1.1F));

        Climate.Parameter full = Climate.Parameter.span(-1.0F, 1.0F);

        List<Pair<Climate.ParameterPoint, Holder<Biome>>> biomeList = new ArrayList<>();

        biomeList.add(Pair.of(Climate.parameters(full, full, full, full, full, full, 0), biomes.getOrThrow(AetherIIBiomes.FLOURISHING_FIELD)));

//        ClimateGrid<ResourceKey<Biome>> aercloudSeaLayout = new ClimateGrid<>(humidities, temperatures);
//        aercloudSeaLayout.put(0, 0, AetherIIBiomes.EXPANSE);
//
//        ClimateGrid<ResourceKey<Biome>> highfieldsLayout = new ClimateGrid<>(humidities, temperatures);
//        highfieldsLayout.put(0, 0, AetherIIBiomes.FLOURISHING_FIELD);
//        highfieldsLayout.put(0, 1, AetherIIBiomes.FLOURISHING_FIELD);
//        highfieldsLayout.put(0, 2, AetherIIBiomes.FLOURISHING_FIELD);
//        highfieldsLayout.put(0, 3, AetherIIBiomes.SHROUDED_FOREST);
//        highfieldsLayout.put(0, 4, AetherIIBiomes.SHROUDED_FOREST);
//        highfieldsLayout.put(1, 0, AetherIIBiomes.VERDANT_WOODS);
//        highfieldsLayout.put(1, 1, AetherIIBiomes.FLOURISHING_FIELD);
//        highfieldsLayout.put(1, 2, AetherIIBiomes.FLOURISHING_FIELD);
//        highfieldsLayout.put(1, 3, AetherIIBiomes.SHROUDED_FOREST);
//        highfieldsLayout.put(1, 4, AetherIIBiomes.SHROUDED_FOREST);
//        highfieldsLayout.put(2, 0, AetherIIBiomes.VERDANT_WOODS);
//        highfieldsLayout.put(2, 1, AetherIIBiomes.VERDANT_WOODS);
//        highfieldsLayout.put(2, 2, AetherIIBiomes.FLOURISHING_FIELD);
//        highfieldsLayout.put(2, 3, AetherIIBiomes.VERDANT_WOODS);
//        highfieldsLayout.put(2, 4, AetherIIBiomes.SHROUDED_FOREST);
//        highfieldsLayout.put(3, 0, AetherIIBiomes.FLOURISHING_FIELD);
//        highfieldsLayout.put(3, 1, AetherIIBiomes.VERDANT_WOODS);
//        highfieldsLayout.put(3, 2, AetherIIBiomes.FLOURISHING_FIELD);
//        highfieldsLayout.put(3, 3, AetherIIBiomes.VERDANT_WOODS);
//        highfieldsLayout.put(3, 4, AetherIIBiomes.SHROUDED_FOREST);
//        highfieldsLayout.put(4, 0, AetherIIBiomes.FLOURISHING_FIELD);
//        highfieldsLayout.put(4, 1, AetherIIBiomes.SHIMMERING_BASIN);
//        highfieldsLayout.put(4, 2, AetherIIBiomes.SHIMMERING_BASIN);
//        highfieldsLayout.put(4, 3, AetherIIBiomes.VERDANT_WOODS);
//        highfieldsLayout.put(4, 4, AetherIIBiomes.SHROUDED_FOREST);
//        highfieldsLayout.put(5, 0, AetherIIBiomes.SHIMMERING_BASIN);
//        highfieldsLayout.put(5, 1, AetherIIBiomes.SHIMMERING_BASIN);
//        highfieldsLayout.put(5, 2, AetherIIBiomes.SHIMMERING_BASIN);
//        highfieldsLayout.put(5, 3, AetherIIBiomes.VERDANT_WOODS);
//        highfieldsLayout.put(5, 4, AetherIIBiomes.VERDANT_WOODS);
//
//        ClimateGrid<ResourceKey<Biome>> magneticLowLayout = new ClimateGrid<>(humidities, temperatures);
//        magneticLowLayout.put(0, 0, AetherIIBiomes.VIOLET_HIGHWOODS);
//        magneticLowLayout.put(0, 1, AetherIIBiomes.VIOLET_HIGHWOODS);
//        magneticLowLayout.put(0, 2, AetherIIBiomes.VIOLET_HIGHWOODS);
//        magneticLowLayout.put(0, 3, AetherIIBiomes.VIOLET_HIGHWOODS);
//        magneticLowLayout.put(0, 4, AetherIIBiomes.VIOLET_HIGHWOODS);
//        magneticLowLayout.put(1, 0, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticLowLayout.put(1, 1, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticLowLayout.put(1, 2, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticLowLayout.put(1, 3, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticLowLayout.put(1, 4, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticLowLayout.put(2, 0, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticLowLayout.put(2, 1, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticLowLayout.put(2, 2, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticLowLayout.put(2, 3, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticLowLayout.put(2, 4, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticLowLayout.put(3, 0, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticLowLayout.put(3, 1, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticLowLayout.put(3, 2, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticLowLayout.put(3, 3, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticLowLayout.put(3, 4, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticLowLayout.put(4, 0, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticLowLayout.put(4, 1, AetherIIBiomes.GLISTENING_SWAMP);
//        magneticLowLayout.put(4, 2, AetherIIBiomes.GLISTENING_SWAMP);
//        magneticLowLayout.put(4, 3, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticLowLayout.put(4, 4, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticLowLayout.put(5, 0, AetherIIBiomes.GLISTENING_SWAMP);
//        magneticLowLayout.put(5, 1, AetherIIBiomes.GLISTENING_SWAMP);
//        magneticLowLayout.put(5, 2, AetherIIBiomes.GLISTENING_SWAMP);
//        magneticLowLayout.put(5, 3, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticLowLayout.put(5, 4, AetherIIBiomes.TURQUOISE_FOREST);
//
//        ClimateGrid<ResourceKey<Biome>> magneticHighLayout = new ClimateGrid<>(humidities, temperatures);
//        magneticHighLayout.put(0, 0, AetherIIBiomes.MAGNETIC_SCAR);
//        magneticHighLayout.put(0, 1, AetherIIBiomes.MAGNETIC_SCAR);
//        magneticHighLayout.put(0, 2, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticHighLayout.put(0, 3, AetherIIBiomes.VIOLET_HIGHWOODS);
//        magneticHighLayout.put(0, 4, AetherIIBiomes.VIOLET_HIGHWOODS);
//        magneticHighLayout.put(1, 0, AetherIIBiomes.MAGNETIC_SCAR);
//        magneticHighLayout.put(1, 1, AetherIIBiomes.MAGNETIC_SCAR);
//        magneticHighLayout.put(1, 2, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticHighLayout.put(1, 3, AetherIIBiomes.VIOLET_HIGHWOODS);
//        magneticHighLayout.put(1, 4, AetherIIBiomes.VIOLET_HIGHWOODS);
//        magneticHighLayout.put(2, 0, AetherIIBiomes.MAGNETIC_SCAR);
//        magneticHighLayout.put(2, 1, AetherIIBiomes.MAGNETIC_SCAR);
//        magneticHighLayout.put(2, 2, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticHighLayout.put(2, 3, AetherIIBiomes.VIOLET_HIGHWOODS);
//        magneticHighLayout.put(2, 4, AetherIIBiomes.VIOLET_HIGHWOODS);
//        magneticHighLayout.put(3, 0, AetherIIBiomes.MAGNETIC_SCAR);
//        magneticHighLayout.put(3, 1, AetherIIBiomes.MAGNETIC_SCAR);
//        magneticHighLayout.put(3, 2, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticHighLayout.put(3, 3, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticHighLayout.put(3, 4, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticHighLayout.put(4, 0, AetherIIBiomes.MAGNETIC_SCAR);
//        magneticHighLayout.put(4, 1, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticHighLayout.put(4, 2, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticHighLayout.put(4, 3, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticHighLayout.put(4, 4, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticHighLayout.put(5, 0, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticHighLayout.put(5, 1, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticHighLayout.put(5, 2, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticHighLayout.put(5, 3, AetherIIBiomes.TURQUOISE_FOREST);
//        magneticHighLayout.put(5, 4, AetherIIBiomes.TURQUOISE_FOREST);
//
//        ClimateGrid<ResourceKey<Biome>> arcticLowLayout = new ClimateGrid<>(humidities, temperatures);
//        arcticLowLayout.put(0, 0, AetherIIBiomes.SHEER_TUNDRA);
//        arcticLowLayout.put(0, 1, AetherIIBiomes.SHEER_TUNDRA);
//        arcticLowLayout.put(0, 2, AetherIIBiomes.SHEER_TUNDRA);
//        arcticLowLayout.put(0, 3, AetherIIBiomes.ENDURING_WOODLAND);
//        arcticLowLayout.put(0, 4, AetherIIBiomes.ENDURING_WOODLAND);
//        arcticLowLayout.put(1, 0, AetherIIBiomes.SHEER_TUNDRA);
//        arcticLowLayout.put(1, 1, AetherIIBiomes.SHEER_TUNDRA);
//        arcticLowLayout.put(1, 2, AetherIIBiomes.SHEER_TUNDRA);
//        arcticLowLayout.put(1, 3, AetherIIBiomes.ENDURING_WOODLAND);
//        arcticLowLayout.put(1, 4, AetherIIBiomes.ENDURING_WOODLAND);
//        arcticLowLayout.put(2, 0, AetherIIBiomes.SHEER_TUNDRA);
//        arcticLowLayout.put(2, 1, AetherIIBiomes.ENDURING_WOODLAND);
//        arcticLowLayout.put(2, 2, AetherIIBiomes.SHEER_TUNDRA);
//        arcticLowLayout.put(2, 3, AetherIIBiomes.SHEER_TUNDRA);
//        arcticLowLayout.put(2, 4, AetherIIBiomes.ENDURING_WOODLAND);
//        arcticLowLayout.put(3, 0, AetherIIBiomes.SHEER_TUNDRA);
//        arcticLowLayout.put(3, 1, AetherIIBiomes.ENDURING_WOODLAND);
//        arcticLowLayout.put(3, 2, AetherIIBiomes.SHEER_TUNDRA);
//        arcticLowLayout.put(3, 3, AetherIIBiomes.SHEER_TUNDRA);
//        arcticLowLayout.put(3, 4, AetherIIBiomes.ENDURING_WOODLAND);
//        arcticLowLayout.put(4, 0, AetherIIBiomes.SHEER_TUNDRA);
//        arcticLowLayout.put(4, 1, AetherIIBiomes.FROZEN_LAKES);
//        arcticLowLayout.put(4, 2, AetherIIBiomes.FROZEN_LAKES);
//        arcticLowLayout.put(4, 3, AetherIIBiomes.SHEER_TUNDRA);
//        arcticLowLayout.put(4, 4, AetherIIBiomes.ENDURING_WOODLAND);
//        arcticLowLayout.put(5, 0, AetherIIBiomes.FROZEN_LAKES);
//        arcticLowLayout.put(5, 1, AetherIIBiomes.FROZEN_LAKES);
//        arcticLowLayout.put(5, 2, AetherIIBiomes.FROZEN_LAKES);
//        arcticLowLayout.put(5, 3, AetherIIBiomes.SHEER_TUNDRA);
//        arcticLowLayout.put(5, 4, AetherIIBiomes.ENDURING_WOODLAND);
//
//        ClimateGrid<ResourceKey<Biome>> arcticHighLayout = new ClimateGrid<>(humidities, temperatures);
//        arcticHighLayout.put(0, 0, AetherIIBiomes.FRIGID_SIERRA);
//        arcticHighLayout.put(0, 1, AetherIIBiomes.FRIGID_SIERRA);
//        arcticHighLayout.put(0, 2, AetherIIBiomes.SHEER_TUNDRA);
//        arcticHighLayout.put(0, 3, AetherIIBiomes.ENDURING_WOODLAND);
//        arcticHighLayout.put(0, 4, AetherIIBiomes.ENDURING_WOODLAND);
//        arcticHighLayout.put(1, 0, AetherIIBiomes.FRIGID_SIERRA);
//        arcticHighLayout.put(1, 1, AetherIIBiomes.FRIGID_SIERRA);
//        arcticHighLayout.put(1, 2, AetherIIBiomes.SHEER_TUNDRA);
//        arcticHighLayout.put(1, 3, AetherIIBiomes.ENDURING_WOODLAND);
//        arcticHighLayout.put(1, 4, AetherIIBiomes.ENDURING_WOODLAND);
//        arcticHighLayout.put(2, 0, AetherIIBiomes.FRIGID_SIERRA);
//        arcticHighLayout.put(2, 1, AetherIIBiomes.FRIGID_SIERRA);
//        arcticHighLayout.put(2, 2, AetherIIBiomes.SHEER_TUNDRA);
//        arcticHighLayout.put(2, 3, AetherIIBiomes.SHEER_TUNDRA);
//        arcticHighLayout.put(2, 4, AetherIIBiomes.ENDURING_WOODLAND);
//        arcticHighLayout.put(3, 0, AetherIIBiomes.FRIGID_SIERRA);
//        arcticHighLayout.put(3, 1, AetherIIBiomes.FRIGID_SIERRA);
//        arcticHighLayout.put(3, 2, AetherIIBiomes.SHEER_TUNDRA);
//        arcticHighLayout.put(3, 3, AetherIIBiomes.SHEER_TUNDRA);
//        arcticHighLayout.put(3, 4, AetherIIBiomes.ENDURING_WOODLAND);
//        arcticHighLayout.put(4, 0, AetherIIBiomes.FRIGID_SIERRA);
//        arcticHighLayout.put(4, 1, AetherIIBiomes.ENDURING_WOODLAND);
//        arcticHighLayout.put(4, 2, AetherIIBiomes.ENDURING_WOODLAND);
//        arcticHighLayout.put(4, 3, AetherIIBiomes.SHEER_TUNDRA);
//        arcticHighLayout.put(4, 4, AetherIIBiomes.ENDURING_WOODLAND);
//        arcticHighLayout.put(5, 0, AetherIIBiomes.ENDURING_WOODLAND);
//        arcticHighLayout.put(5, 1, AetherIIBiomes.ENDURING_WOODLAND);
//        arcticHighLayout.put(5, 2, AetherIIBiomes.ENDURING_WOODLAND);
//        arcticHighLayout.put(5, 3, AetherIIBiomes.SHEER_TUNDRA);
//        arcticHighLayout.put(5, 4, AetherIIBiomes.ENDURING_WOODLAND);
//
//        ClimateGrid<ResourceKey<Biome>> irradiatedLayout = new ClimateGrid<>(humidities, temperatures);
//        irradiatedLayout.put(0, 0, AetherIIBiomes.CONTAMINATED_JUNGLE);
//        irradiatedLayout.put(0, 1, AetherIIBiomes.CONTAMINATED_JUNGLE);
//        irradiatedLayout.put(0, 2, AetherIIBiomes.CONTAMINATED_JUNGLE);
//        irradiatedLayout.put(0, 3, AetherIIBiomes.CONTAMINATED_JUNGLE);
//        irradiatedLayout.put(0, 4, AetherIIBiomes.CONTAMINATED_JUNGLE);
//        irradiatedLayout.put(1, 0, AetherIIBiomes.CONTAMINATED_JUNGLE);
//        irradiatedLayout.put(1, 1, AetherIIBiomes.CONTAMINATED_JUNGLE);
//        irradiatedLayout.put(1, 2, AetherIIBiomes.CONTAMINATED_JUNGLE);
//        irradiatedLayout.put(1, 3, AetherIIBiomes.CONTAMINATED_JUNGLE);
//        irradiatedLayout.put(1, 4, AetherIIBiomes.CONTAMINATED_JUNGLE);
//        irradiatedLayout.put(2, 0, AetherIIBiomes.CONTAMINATED_JUNGLE);
//        irradiatedLayout.put(2, 1, AetherIIBiomes.CONTAMINATED_JUNGLE);
//        irradiatedLayout.put(2, 2, AetherIIBiomes.CONTAMINATED_JUNGLE);
//        irradiatedLayout.put(2, 3, AetherIIBiomes.CONTAMINATED_JUNGLE);
//        irradiatedLayout.put(2, 4, AetherIIBiomes.CONTAMINATED_JUNGLE);
//        irradiatedLayout.put(3, 0, AetherIIBiomes.BATTLEGROUND_WASTES);
//        irradiatedLayout.put(3, 1, AetherIIBiomes.BATTLEGROUND_WASTES);
//        irradiatedLayout.put(3, 2, AetherIIBiomes.BATTLEGROUND_WASTES);
//        irradiatedLayout.put(3, 3, AetherIIBiomes.BATTLEGROUND_WASTES);
//        irradiatedLayout.put(3, 4, AetherIIBiomes.BATTLEGROUND_WASTES);
//        irradiatedLayout.put(4, 0, AetherIIBiomes.BATTLEGROUND_WASTES);
//        irradiatedLayout.put(4, 1, AetherIIBiomes.BATTLEGROUND_WASTES);
//        irradiatedLayout.put(4, 2, AetherIIBiomes.BATTLEGROUND_WASTES);
//        irradiatedLayout.put(4, 3, AetherIIBiomes.BATTLEGROUND_WASTES);
//        irradiatedLayout.put(4, 4, AetherIIBiomes.BATTLEGROUND_WASTES);
//        irradiatedLayout.put(5, 0, AetherIIBiomes.BATTLEGROUND_WASTES);
//        irradiatedLayout.put(5, 1, AetherIIBiomes.BATTLEGROUND_WASTES);
//        irradiatedLayout.put(5, 2, AetherIIBiomes.BATTLEGROUND_WASTES);
//        irradiatedLayout.put(5, 3, AetherIIBiomes.BATTLEGROUND_WASTES);
//        irradiatedLayout.put(5, 4, AetherIIBiomes.BATTLEGROUND_WASTES);
//
//        ClimateGrid<ClimateGrid<ResourceKey<Biome>>> aercloudSeaSlice = new ClimateGrid<>(erosions, continentalnesses);
//        aercloudSeaSlice.put(0, 0, aercloudSeaLayout);
//
//        ClimateGrid<ClimateGrid<ResourceKey<Biome>>> lowSlice = new ClimateGrid<>(erosions, continentalnesses);
//        lowSlice.put(0, 0, highfieldsLayout);
//        lowSlice.put(0, 1, highfieldsLayout);
//        lowSlice.put(0, 2, highfieldsLayout);
//        lowSlice.put(0, 3, highfieldsLayout);
//        lowSlice.put(0, 4, aercloudSeaLayout);
//        lowSlice.put(0, 5, aercloudSeaLayout);
//        lowSlice.put(0, 6, arcticLowLayout);
//        lowSlice.put(0, 7, arcticLowLayout);
//        lowSlice.put(1, 0, highfieldsLayout);
//        lowSlice.put(1, 1, highfieldsLayout);
//        lowSlice.put(1, 2, highfieldsLayout);
//        lowSlice.put(1, 3, highfieldsLayout);
//        lowSlice.put(1, 4, aercloudSeaLayout);
//        lowSlice.put(1, 5, aercloudSeaLayout);
//        lowSlice.put(1, 6, arcticLowLayout);
//        lowSlice.put(1, 7, arcticLowLayout);
//        lowSlice.put(2, 0, highfieldsLayout);
//        lowSlice.put(2, 1, highfieldsLayout);
//        lowSlice.put(2, 2, highfieldsLayout);
//        lowSlice.put(2, 3, highfieldsLayout);
//        lowSlice.put(2, 4, aercloudSeaLayout);
//        lowSlice.put(2, 5, aercloudSeaLayout);
//        lowSlice.put(2, 6, arcticLowLayout);
//        lowSlice.put(2, 7, arcticLowLayout);
//        lowSlice.put(3, 0, highfieldsLayout);
//        lowSlice.put(3, 1, aercloudSeaLayout);
//        lowSlice.put(3, 2, aercloudSeaLayout);
//        lowSlice.put(3, 3, aercloudSeaLayout);
//        lowSlice.put(3, 4, aercloudSeaLayout);
//        lowSlice.put(3, 5, aercloudSeaLayout);
//        lowSlice.put(3, 6, aercloudSeaLayout);
//        lowSlice.put(3, 7, arcticLowLayout);
//        lowSlice.put(4, 0, aercloudSeaLayout);
//        lowSlice.put(4, 1, magneticLowLayout);
//        lowSlice.put(4, 2, aercloudSeaLayout);
//        lowSlice.put(4, 3, aercloudSeaLayout);
//        lowSlice.put(4, 4, aercloudSeaLayout);
//        lowSlice.put(4, 5, aercloudSeaLayout);
//        lowSlice.put(4, 6, aercloudSeaLayout);
//        lowSlice.put(4, 7, aercloudSeaLayout);
//        lowSlice.put(5, 0, magneticLowLayout);
//        lowSlice.put(5, 1, magneticLowLayout);
//        lowSlice.put(5, 2, magneticLowLayout);
//        lowSlice.put(5, 3, magneticLowLayout);
//        lowSlice.put(5, 4, aercloudSeaLayout);
//        lowSlice.put(5, 5, aercloudSeaLayout);
//        lowSlice.put(5, 6, irradiatedLayout);
//        lowSlice.put(5, 7, irradiatedLayout);
//        lowSlice.put(6, 0, magneticLowLayout);
//        lowSlice.put(6, 1, magneticLowLayout);
//        lowSlice.put(6, 2, magneticLowLayout);
//        lowSlice.put(6, 3, magneticLowLayout);
//        lowSlice.put(6, 4, aercloudSeaLayout);
//        lowSlice.put(6, 5, aercloudSeaLayout);
//        lowSlice.put(6, 6, irradiatedLayout);
//        lowSlice.put(6, 7, irradiatedLayout);
//
//        ClimateGrid<ClimateGrid<ResourceKey<Biome>>> highSlice = new ClimateGrid<>(erosions, continentalnesses);
//        highSlice.put(0, 0, highfieldsLayout);
//        highSlice.put(0, 1, highfieldsLayout);
//        highSlice.put(0, 2, highfieldsLayout);
//        highSlice.put(0, 3, highfieldsLayout);
//        highSlice.put(0, 4, aercloudSeaLayout);
//        highSlice.put(0, 5, aercloudSeaLayout);
//        highSlice.put(0, 6, arcticHighLayout);
//        highSlice.put(0, 7, arcticHighLayout);
//        highSlice.put(1, 0, highfieldsLayout);
//        highSlice.put(1, 1, highfieldsLayout);
//        highSlice.put(1, 2, highfieldsLayout);
//        highSlice.put(1, 3, highfieldsLayout);
//        highSlice.put(1, 4, aercloudSeaLayout);
//        highSlice.put(1, 5, aercloudSeaLayout);
//        highSlice.put(1, 6, arcticHighLayout);
//        highSlice.put(1, 7, arcticHighLayout);
//        highSlice.put(2, 0, highfieldsLayout);
//        highSlice.put(2, 1, highfieldsLayout);
//        highSlice.put(2, 2, highfieldsLayout);
//        highSlice.put(2, 3, highfieldsLayout);
//        highSlice.put(2, 4, aercloudSeaLayout);
//        highSlice.put(2, 5, aercloudSeaLayout);
//        highSlice.put(2, 6, arcticHighLayout);
//        highSlice.put(2, 7, arcticHighLayout);
//        highSlice.put(3, 0, highfieldsLayout);
//        highSlice.put(3, 1, aercloudSeaLayout);
//        highSlice.put(3, 2, aercloudSeaLayout);
//        highSlice.put(3, 3, aercloudSeaLayout);
//        highSlice.put(3, 4, aercloudSeaLayout);
//        highSlice.put(3, 5, aercloudSeaLayout);
//        highSlice.put(3, 6, aercloudSeaLayout);
//        highSlice.put(3, 7, arcticHighLayout);
//        highSlice.put(4, 0, aercloudSeaLayout);
//        highSlice.put(4, 1, magneticHighLayout);
//        highSlice.put(4, 2, aercloudSeaLayout);
//        highSlice.put(4, 3, aercloudSeaLayout);
//        highSlice.put(4, 4, aercloudSeaLayout);
//        highSlice.put(4, 5, aercloudSeaLayout);
//        highSlice.put(4, 6, aercloudSeaLayout);
//        highSlice.put(4, 7, aercloudSeaLayout);
//        highSlice.put(5, 0, magneticHighLayout);
//        highSlice.put(5, 1, magneticHighLayout);
//        highSlice.put(5, 2, magneticHighLayout);
//        highSlice.put(5, 3, magneticHighLayout);
//        highSlice.put(5, 4, aercloudSeaLayout);
//        highSlice.put(5, 5, aercloudSeaLayout);
//        highSlice.put(5, 6, irradiatedLayout);
//        highSlice.put(5, 7, irradiatedLayout);
//        highSlice.put(6, 0, magneticHighLayout);
//        highSlice.put(6, 1, magneticHighLayout);
//        highSlice.put(6, 2, magneticHighLayout);
//        highSlice.put(6, 3, magneticHighLayout);
//        highSlice.put(6, 4, aercloudSeaLayout);
//        highSlice.put(6, 5, aercloudSeaLayout);
//        highSlice.put(6, 6, irradiatedLayout);
//        highSlice.put(6, 7, irradiatedLayout);
//
//        ClimateGrid<ClimateGrid<ClimateGrid<ResourceKey<Biome>>>> dimension = new ClimateGrid<>(weirdnesses, depths);
//        dimension.put(0, 0, lowSlice);
//        dimension.put(1, 0, lowSlice);
//        dimension.put(2, 0, highSlice);
//        dimension.put(3, 0, highSlice);
//        dimension.put(4, 0, lowSlice);
//        dimension.put(5, 0, lowSlice);
//        dimension.put(6, 0, lowSlice);
//        dimension.put(7, 0, lowSlice);
//        dimension.put(8, 0, highSlice);
//        dimension.put(9, 0, highSlice);
//        dimension.put(10, 0, highSlice);
//        dimension.put(11, 0, lowSlice);
//        dimension.put(12, 0, lowSlice);
//
//        dimension.getTable().cellSet().forEach(
//                (a) -> {
//                    if (a.getValue() != null) {
//                        a.getValue().element().getTable().cellSet().forEach(
//                                (b) -> {
//                                    if (b.getValue() != null) {
//                                        b.getValue().element().getTable().cellSet().forEach(
//                                                (c) -> {
//                                                    if (c.getValue() != null) {
//                                                        biomeList.add(Pair.of(Climate.parameters(c.getValue().y(), c.getValue().x(), b.getValue().y(), b.getValue().x(), a.getValue().y(), a.getValue().x(), 0), biomes.getOrThrow(c.getValue().element())));
//                                                    }
//                                                });
//                                    }
//                                });
//                    }
//        });

        return MultiNoiseBiomeSource.createFromList(new Climate.ParameterList<>(biomeList));
    }
}
