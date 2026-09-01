package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.builders.worldgen.holyisles.HolyIslesSurfaceBuilders;
import com.aetherteam.aetherii.data.resources.registries.holyisles.HolyIslesConfiguredFeatures;
import com.aetherteam.aetherii.world.BlockPlacementUtil;
import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.aetherteam.aetherii.world.feature.configuration.FerrositePillarConfiguration;
import com.aetherteam.aetherii.world.surfacerule.DensityFunctionRule;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

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
        HolderGetter<DensityFunction> function = level.registryAccess().lookupOrThrow(Registries.DENSITY_FUNCTION);
        DensityFunctionRule ruleSource = HolyIslesSurfaceBuilders.FERROSITE.apply(function);
        DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(1234L);
        ruleSource.function().mapAll(visitor);

        float radius = random.nextInt(config.additionalRadius()) + config.baseRadius();
        int baseHeight = config.baseHeight();
        int additionalHeight = config.additionalHeight();
        int height = random.nextInt(additionalHeight) + baseHeight;
        int offset = (int) (-radius * 20 + radius * 16);

        for (int i = offset; i < 0; ++i) {
            this.placeDisk(
                    ruleSource,
                    level,
                    new BlockPos(pos.getX(), pos.getY() + i + height + (int) radius, pos.getZ()),
                    radius + i * BlockPlacementUtil.shapeVariator(random, 0.05F),
                    random,
                    true);
        }

        for (int i = (int) (-radius * 0.5); i < 0; ++i) {
            this.placeDisk(
                    ruleSource,
                    level,
                    new BlockPos(pos.getX(), pos.getY() + i + height + offset + (int) radius, pos.getZ()),
                    radius + i * 2F,
                    random,
                    true);
        }

        this.placeDisk(
                ruleSource,
                level,
                new BlockPos(pos.getX() + random.nextInt(2) - 1, pos.getY() + height + (int) radius, pos.getZ() + random.nextInt(2) - 1),
                radius - 3,
                random,
                true);

        ConfiguredFeature<?, ?> turf = Objects.requireNonNull(level.registryAccess().lookupOrThrow(Registries.CONFIGURED_FEATURE).get(HolyIslesConfiguredFeatures.FERROSITE_PILLAR_TURF).orElse(null)).value();
        turf.place(level, chunk, random, new BlockPos(pos.getX(), pos.getY() + height + (int) radius, pos.getZ()));

        this.distributeSidePillars(context, ruleSource, pos, random, radius, baseHeight, additionalHeight, 1);
        this.distributeSidePillars(context, ruleSource, pos, random, radius, baseHeight, additionalHeight, -1);

        return true;
    }

    public void placeSidePillar(FeaturePlaceContext<FerrositePillarConfiguration> context, DensityFunctionRule ruleSource, BlockPos pos) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        ChunkGenerator chunk = level.getLevel().getChunkSource().getGenerator();

        float radius = random.nextInt(3) + 2.5F;
        int offset = (int) (-radius * 20 + radius * 16);

        for (int i = offset; i < 0; ++i) {
            this.placeDisk(
                    ruleSource,
                    level,
                    new BlockPos(pos.getX(), pos.getY() + i + (int) radius, pos.getZ()),
                    radius + i * BlockPlacementUtil.shapeVariator(random, 0.05F),
                    random,
                    true);
        }

        for (int i = (int) (-radius * 0.5); i < 0; ++i) {
            this.placeDisk(
                    ruleSource,
                    level,
                    new BlockPos(pos.getX(), pos.getY() + i + offset + (int) radius, pos.getZ()),
                    radius + i * BlockPlacementUtil.shapeVariator(random, 2F),
                    random,
                    true);
        }

        this.placeDisk(
                ruleSource,
                level,
                new BlockPos(pos.getX() + random.nextInt(2) - 1, pos.getY() + (int) radius, pos.getZ() + random.nextInt(2) - 1),
                radius - 2,
                random,
                true);

        ConfiguredFeature<?, ?> turf = Objects.requireNonNull(level.registryAccess().lookupOrThrow(Registries.CONFIGURED_FEATURE).get(HolyIslesConfiguredFeatures.FERROSITE_PILLAR_TURF).orElse(null)).value();
        turf.place(level, chunk, random, new BlockPos(pos.getX(), pos.getY() + (int) radius, pos.getZ()));
    }

    public void distributeSidePillars(FeaturePlaceContext<FerrositePillarConfiguration> context, DensityFunctionRule ruleSource, BlockPos pos, RandomSource random, float radius, int baseHeight, int additionalHeight, int offsetMultiplier) {
        this.placeSidePillar(context, ruleSource, new BlockPos(pos.getX() + random.nextInt((int) (radius * 1.25F)) * offsetMultiplier, pos.getY() + random.nextInt(additionalHeight + 2) + baseHeight, pos.getZ() + random.nextInt((int) (radius * 1.25F)) * offsetMultiplier));
        this.placeSidePillar(context, ruleSource, new BlockPos(pos.getX() + random.nextInt((int) (radius * 1.25F)) * offsetMultiplier, pos.getY() + random.nextInt(additionalHeight + 2) + baseHeight, pos.getZ() + random.nextInt((int) (radius * 1.25F)) * offsetMultiplier));
        if (random.nextBoolean()) {
            this.placeSidePillar(context, ruleSource, new BlockPos(pos.getX() + random.nextInt((int) (radius * 1.25F)) * offsetMultiplier, pos.getY() + random.nextInt(additionalHeight + 2) + baseHeight, pos.getZ() + random.nextInt((int) (radius * 1.25F)) * offsetMultiplier));
        }
        if (random.nextBoolean()) {
            this.placeSidePillar(context, ruleSource, new BlockPos(pos.getX() + random.nextInt((int) (radius * 1.25F)) * offsetMultiplier, pos.getY() + random.nextInt(additionalHeight + 2) + baseHeight, pos.getZ() + random.nextInt((int) (radius * 1.25F)) * offsetMultiplier));
        }
    }

    public void placeDisk(DensityFunctionRule ruleSource, WorldGenLevel level, BlockPos center, float radius, RandomSource random, boolean replaceBlocks) {
        BlockState state = ruleSource.tryApply(center.getX(), center.getY(), center.getZ());
        if (state == null) {
            state = AetherIIBlocks.HOLYSTONE.get().defaultBlockState();
        }
        BlockPlacementUtil.placeDisk(level, BlockStateProvider.simple(state), center, radius, random, replaceBlocks);
    }
}