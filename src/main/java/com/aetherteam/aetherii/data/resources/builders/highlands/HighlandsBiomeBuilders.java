package com.aetherteam.aetherii.data.resources.builders.highlands;

import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBiomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.sounds.Music;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

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
            .grassColorOverride(0xfff7a8)
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

    public static BiomeSource buildHighlandsBiomeSource(HolderGetter<Biome> biomes) { //todo wip
        Climate.Parameter fullRange = Climate.Parameter.span(-1.0F, 1.0F);
        Climate.Parameter lowRange = Climate.Parameter.span(-1.0F, 0.0F);
        Climate.Parameter highRange = Climate.Parameter.span(0.0F, 1.0F);
        return MultiNoiseBiomeSource.createFromList(new Climate.ParameterList<>(List.of(
                // Highfields
                Pair.of(new Climate.ParameterPoint(highRange, lowRange, highRange, lowRange, highRange, lowRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.FLOURISHING_FIELD)),
                Pair.of(new Climate.ParameterPoint(highRange, highRange, highRange, lowRange, lowRange, lowRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.VERDANT_WOODS)),
                Pair.of(new Climate.ParameterPoint(highRange, lowRange, highRange, lowRange, highRange, lowRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.SHROUDED_FOREST)),
                Pair.of(new Climate.ParameterPoint(highRange, highRange, highRange, lowRange, lowRange, lowRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.SHIMMERING_BASIN)),
                // Magnetic
                Pair.of(new Climate.ParameterPoint(highRange, lowRange, highRange, highRange, highRange, lowRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.MAGNETIC_SCAR)),
                Pair.of(new Climate.ParameterPoint(highRange, highRange, highRange, highRange, lowRange, lowRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.TURQUOISE_FOREST)),
                Pair.of(new Climate.ParameterPoint(highRange, highRange, highRange, highRange, lowRange, lowRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.GLISTENING_SWAMP)),
                Pair.of(new Climate.ParameterPoint(highRange, lowRange, highRange, highRange, highRange, lowRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.VIOLET_HIGHWOODS)),
                // Arctic
                Pair.of(new Climate.ParameterPoint(lowRange, highRange, highRange, lowRange, lowRange, lowRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.FRIGID_SIERRA)),
                Pair.of(new Climate.ParameterPoint(lowRange, lowRange, highRange, lowRange, highRange, lowRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.ENDURING_WOODLAND)),
                Pair.of(new Climate.ParameterPoint(lowRange, highRange, highRange, lowRange, lowRange, lowRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.FROZEN_LAKES)),
                Pair.of(new Climate.ParameterPoint(lowRange, lowRange, highRange, lowRange, highRange, lowRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.SHEER_TUNDRA)),
                // Irradiated
                Pair.of(new Climate.ParameterPoint(highRange, highRange, highRange, lowRange, lowRange, highRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.CONTAMINATED_JUNGLE)),
                Pair.of(new Climate.ParameterPoint(highRange, lowRange, highRange, lowRange, highRange, highRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.BATTLEGROUND_WASTES)),
                // Aercloud Sea
                Pair.of(new Climate.ParameterPoint(highRange, fullRange, lowRange, fullRange, fullRange, fullRange, 0),
                        biomes.getOrThrow(AetherIIBiomes.EXPANSE))
        )));
    }
}
