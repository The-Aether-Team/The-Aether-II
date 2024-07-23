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
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos pos = context.origin();
        FerrositePillarConfiguration config = context.config();
        ChunkGenerator chunk = level.getLevel().getChunkSource().getGenerator();

        float radius = random.nextInt(config.additionalRadius()) + config.baseRadius();
        int baseHeight = config.baseHeight();
        int additionalHeight = config.additionalHeight();
        int height = random.nextInt(additionalHeight) + baseHeight;
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
                    radius + i * 2F,
                    random,
                    true);
        }

        BlockPlacementUtil.placeDisk(
                level,
                config.block(),
                new BlockPos(pos.getX() + random.nextInt(2) - 1, pos.getY() + height + (int) radius, pos.getZ() + random.nextInt(2) - 1),
                radius - 3,
                random,
                true);

        ConfiguredFeature<?, ?> turf = Objects.requireNonNull(level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(AetherIIVegetationFeatures.FERROSITE_PILLAR_TURF).orElse(null)).value();
        turf.place(level, chunk, random, new BlockPos(pos.getX(), pos.getY() + height + (int) radius, pos.getZ()));

        placeSidePillar(context, new BlockPos(pos.getX() + random.nextInt((int) (radius * 1.25F)), pos.getY() + random.nextInt(additionalHeight + 2) + baseHeight - 4, pos.getZ() + random.nextInt((int) (radius * 1.25F))));
        placeSidePillar(context, new BlockPos(pos.getX() + random.nextInt((int) (radius * 1.25F)), pos.getY() + random.nextInt(additionalHeight + 2) + baseHeight - 4, pos.getZ() + random.nextInt((int) (radius * 1.25F))));
        if (random.nextBoolean()) {
            placeSidePillar(context, new BlockPos(pos.getX() + random.nextInt((int) (radius * 1.25F)), pos.getY() + random.nextInt(additionalHeight + 2) + baseHeight - 4, pos.getZ() + random.nextInt((int) (radius * 1.25F))));
        }
        if (random.nextBoolean()) {
            placeSidePillar(context, new BlockPos(pos.getX() + random.nextInt((int) (radius * 1.25F)), pos.getY() + random.nextInt(additionalHeight + 2) + baseHeight - 4, pos.getZ() + random.nextInt((int) (radius * 1.25F))));
        }

        placeSidePillar(context, new BlockPos(pos.getX() - random.nextInt((int) (radius * 1.25F)), pos.getY() + random.nextInt(additionalHeight + 2) + baseHeight - 4, pos.getZ() - random.nextInt((int) (radius * 1.25F))));
        placeSidePillar(context, new BlockPos(pos.getX() - random.nextInt((int) (radius * 1.25F)), pos.getY() + random.nextInt(additionalHeight + 2) + baseHeight - 4, pos.getZ() - random.nextInt((int) (radius * 1.25F))));
        if (random.nextBoolean()) {
            placeSidePillar(context, new BlockPos(pos.getX() - random.nextInt((int) (radius * 1.25F)), pos.getY() + random.nextInt(additionalHeight + 2) + baseHeight - 4, pos.getZ() - random.nextInt((int) (radius * 1.25F))));
        }
        if (random.nextBoolean()) {
            placeSidePillar(context, new BlockPos(pos.getX() - random.nextInt((int) (radius * 1.25F)), pos.getY() + random.nextInt(additionalHeight + 2) + baseHeight - 4, pos.getZ() - random.nextInt((int) (radius * 1.25F))));
        }

        return true;
    }

    public void placeSidePillar(FeaturePlaceContext<FerrositePillarConfiguration> context, BlockPos pos) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        FerrositePillarConfiguration config = context.config();
        ChunkGenerator chunk = level.getLevel().getChunkSource().getGenerator();

        float radius = random.nextInt(3) + 2.5F;
        int offset = (int) (-radius * 20 + radius * 16);

        for (int i = offset; i < 0; ++i) {
            BlockPlacementUtil.placeDisk(
                    level,
                    config.block(),
                    new BlockPos(pos.getX(), pos.getY() + i + (int) radius, pos.getZ()),
                    radius + i * shapeVariator(random, 0.05F),
                    random,
                    true);
        }

        for (int i = (int) (-radius * 0.5); i < 0; ++i) {
            BlockPlacementUtil.placeDisk(
                    level,
                    config.block(),
                    new BlockPos(pos.getX(), pos.getY() + i + offset + (int) radius, pos.getZ()),
                    radius + i * shapeVariator(random, 2F),
                    random,
                    true);
        }

        BlockPlacementUtil.placeDisk(
                level,
                config.block(),
                new BlockPos(pos.getX() + random.nextInt(2) - 1, pos.getY() + (int) radius, pos.getZ() + random.nextInt(2) - 1),
                radius - 2,
                random,
                true);

        ConfiguredFeature<?, ?> turf = Objects.requireNonNull(level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(AetherIIVegetationFeatures.FERROSITE_PILLAR_TURF).orElse(null)).value();
        turf.place(level, chunk, random, new BlockPos(pos.getX(), pos.getY() + (int) radius, pos.getZ()));
    }

    private float shapeVariator(RandomSource random, float value) {
        if (random.nextInt(16) == 5) {
            return value * 1.5F;
        }
        else if (random.nextInt(12) == 5) {
            return value * 1.25F;
        } else {
            return value;
        }
    }
}