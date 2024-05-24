package com.aetherteam.aetherii.data.resources.builders.highlands;

import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBiomes;
import com.aetherteam.aetherii.data.resources.registries.placement.AetherIIMiscPlacements;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.sounds.Music;
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

    public static Biome makeHighfieldsBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        BiomeGenerationSettings.Builder generationSettingsBuilder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        MobSpawnSettings.Builder spawnSettingsBuilder = new MobSpawnSettings.Builder();
        return fullDefinition(
                false,
                0.8F,
                0.0F,
                HIGHFIELDS_EFFECTS,
                spawnSettingsBuilder.build(),
                generationSettingsBuilder
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, AetherIIMiscPlacements.COAST_QUICKSOIL)
                        .build(),
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