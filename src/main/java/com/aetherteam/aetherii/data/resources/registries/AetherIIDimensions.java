package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.data.resources.builders.worldgen.holyisles.HolyIslesBiomeSourceBuilders;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.OptionalLong;

public class AetherIIDimensions {
    private final static ResourceLocation AETHER_HOLY_ISLES_LEVEL_ID = new ResourceLocation(AetherII.MODID, "aether_holy_isles");

    // DimensionType - Specifies the logic and settings for a dimension.
    public static final ResourceKey<DimensionType> AETHER_HOLY_ISLES_DIMENSION_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, AETHER_HOLY_ISLES_LEVEL_ID);
    // Level - The dimension during runtime.
    public static final ResourceKey<Level> AETHER_HOLY_ISLES_LEVEL = ResourceKey.create(Registries.DIMENSION, AETHER_HOLY_ISLES_LEVEL_ID);
    // LevelStem - The dimension during lifecycle start and datagen.
    public static final ResourceKey<LevelStem> AETHER_HOLY_ISLES_LEVEL_STEM = ResourceKey.create(Registries.LEVEL_STEM, AETHER_HOLY_ISLES_LEVEL_ID);

    public static void bootstrapDimensionType(BootstapContext<DimensionType> context) {
        context.register(AETHER_HOLY_ISLES_DIMENSION_TYPE, new DimensionType(
                OptionalLong.empty(),
                true,
                false,
                false,
                true,
                1.0D,
                true,
                false,
                -32,
                416,
                416,
                BlockTags.INFINIBURN_OVERWORLD,
                AETHER_HOLY_ISLES_LEVEL_ID,
                0.0F,
                new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 7), 0)));
    }

    public static void bootstrapLevelStem(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<NoiseGeneratorSettings> noiseSettings = context.lookup(Registries.NOISE_SETTINGS);
        HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
        BiomeSource source = HolyIslesBiomeSourceBuilders.buildHolyIslesBiomeSource(biomes);
        NoiseBasedChunkGenerator aetherChunkGen = new NoiseBasedChunkGenerator(source, noiseSettings.getOrThrow(AetherIINoiseSettings.HOLY_ISLES));
        context.register(AETHER_HOLY_ISLES_LEVEL_STEM, new LevelStem(dimensionTypes.getOrThrow(AetherIIDimensions.AETHER_HOLY_ISLES_DIMENSION_TYPE), aetherChunkGen));
    }
}
