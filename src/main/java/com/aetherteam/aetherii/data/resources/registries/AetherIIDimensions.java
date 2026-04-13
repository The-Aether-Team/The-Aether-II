package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIDimensionRenderers;
import com.aetherteam.aetherii.data.resources.builders.worldgen.holyisles.HolyIslesBiomeSourceBuilders;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TimelineTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.attribute.BedRule;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.neoforged.neoforge.common.world.NeoForgeEnvironmentAttributes;

public class AetherIIDimensions {
    private final static Identifier AETHER_HOLY_ISLES_LEVEL_ID = Identifier.fromNamespaceAndPath(AetherII.MODID, "aether_holy_isles");

    // DimensionType - Specifies the logic and settings for a dimension.
    public static final ResourceKey<DimensionType> AETHER_HOLY_ISLES_DIMENSION_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, AETHER_HOLY_ISLES_LEVEL_ID);
    // Level - The dimension during runtime.
    public static final ResourceKey<Level> AETHER_HOLY_ISLES_LEVEL = ResourceKey.create(Registries.DIMENSION, AETHER_HOLY_ISLES_LEVEL_ID);
    // LevelStem - The dimension during lifecycle start and datagen.
    public static final ResourceKey<LevelStem> AETHER_HOLY_ISLES_LEVEL_STEM = ResourceKey.create(Registries.LEVEL_STEM, AETHER_HOLY_ISLES_LEVEL_ID);

    public static void bootstrapDimensionType(BootstrapContext<DimensionType> context) {
        context.register(AETHER_HOLY_ISLES_DIMENSION_TYPE, new DimensionType(
                false,
                true,
                false,
                1.0D,
                -32,
                416,
                416,
                BlockTags.INFINIBURN_OVERWORLD,
                0.0F,
                new DimensionType.MonsterSettings(UniformInt.of(0, 7), 0),
                DimensionType.Skybox.OVERWORLD,
                DimensionType.CardinalLightType.DEFAULT,
                EnvironmentAttributeMap.builder()
                        .set(EnvironmentAttributes.CLOUD_COLOR, -1)
                        .set(EnvironmentAttributes.CLOUD_HEIGHT, 320.33F)
                        .set(EnvironmentAttributes.BED_RULE, BedRule.CAN_SLEEP_WHEN_DARK)
                        .set(EnvironmentAttributes.RESPAWN_ANCHOR_WORKS, false)
                        .set(EnvironmentAttributes.NETHER_PORTAL_SPAWNS_PIGLINS, true)
                        .set(NeoForgeEnvironmentAttributes.CUSTOM_SKYBOX, AetherIIDimensionRenderers.AETHER_SKY_ID)
                        .set(NeoForgeEnvironmentAttributes.CUSTOM_WEATHER_EFFECTS, AetherIIDimensionRenderers.AETHER_WEATHER_ID)
                        .set(NeoForgeEnvironmentAttributes.CUSTOM_CLOUDS, AetherIIDimensionRenderers.AETHER_CLOUDS_ID)
                        .build(),
                context.lookup(Registries.TIMELINE).getOrThrow(TimelineTags.IN_OVERWORLD)));
    }

    public static void bootstrapLevelStem(BootstrapContext<LevelStem> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<NoiseGeneratorSettings> noiseSettings = context.lookup(Registries.NOISE_SETTINGS);
        HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
        BiomeSource source = HolyIslesBiomeSourceBuilders.buildHolyIslesBiomeSource(biomes);
        NoiseBasedChunkGenerator aetherChunkGen = new NoiseBasedChunkGenerator(source, noiseSettings.getOrThrow(AetherIINoiseSettings.HOLY_ISLES));
        context.register(AETHER_HOLY_ISLES_LEVEL_STEM, new LevelStem(dimensionTypes.getOrThrow(AetherIIDimensions.AETHER_HOLY_ISLES_DIMENSION_TYPE), aetherChunkGen));
    }
}