package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.data.resources.registries.features.AetherIIVegetationFeatures;
import com.aetherteam.aetherii.world.BlockPlacementUtil;
import com.aetherteam.aetherii.world.feature.configuration.FerrositePillarConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.Objects;

public class FerrositePillarFeature extends Feature<FerrositePillarConfiguration> {

    public FerrositePillarFeature(Codec<FerrositePillarConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<FerrositePillarConfiguration> context) {
        if (enablePillarGeneration()) {
            WorldGenLevel level = context.level();
            RandomSource random = context.random();
            BlockPos pos = context.origin();
            FerrositePillarConfiguration config = context.config();
            ChunkGenerator chunk = level.getLevel().getChunkSource().getGenerator();

            float radius = random.nextInt(6) + 4.5F;
            int height = random.nextInt(16) + 24;
            int offset = (int) (-radius * 20 + radius * 16);

            for (int i = offset; i < 0; ++i) {
                BlockPlacementUtil.placeDisk(
                        level,
                        config.block(),
                        new BlockPos(pos.getX(), pos.getY() + i + height + (int) radius, pos.getZ()),
                        radius + i * shapeVariator(random, 0.05F),
                        random,
                        true);
            }

            for (int i = (int) (-radius * 0.5); i < 0; ++i) {
                BlockPlacementUtil.placeDisk(
                        level,
                        config.block(),
                        new BlockPos(pos.getX(), pos.getY() + i + height + offset + (int) radius, pos.getZ()),
                        radius + i * shapeVariator(random, 2F),
                        random,
                        true);
            }

            ConfiguredFeature<?, ?> turf = Objects.requireNonNull(level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(AetherIIVegetationFeatures.FERROSITE_PILLAR_TURF).orElse(null)).value();
            turf.place(level, chunk, random, new BlockPos(pos.getX(), pos.getY() + height + (int) radius, pos.getZ()));
            return true;
        }
        return false;
    }

    private float shapeVariator(RandomSource random, float value) {
        if (random.nextInt(15) == 5) {
            return value * 1.5F;
        }
        else if (random.nextInt(10) == 5) {
            return value * 1.25F;
        } else {
            return value;
        }
    }

    //TODO: Exists for Debug purposes, deleted once Ferrosite Pillars are fully implemented
    private boolean enablePillarGeneration() {
       return false;
    }
}