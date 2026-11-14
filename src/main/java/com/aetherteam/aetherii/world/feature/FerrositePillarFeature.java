package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.data.resources.registries.holyisles.HolyIslesConfiguredFeatures;
import com.aetherteam.aetherii.world.BlockPlacementUtil;
import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.aetherteam.aetherii.world.feature.configuration.FerrositePillarConfiguration;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.*;

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
        ChunkGenerator chunkGenerator = level.getLevel().getChunkSource().getGenerator();
        DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(level.getSeed());
        config.strataNoise().mapAll(visitor);

        ChunkPos originChunk = ChunkPos.containing(pos);
        pos = pos.offset(random.nextBoolean() ? 4 : -4, 0, random.nextBoolean() ? 4 : -4);

        float radius = random.nextInt(config.additionalRadius()) + config.baseRadius();
        int baseHeight = config.baseHeight();
        int additionalHeight = config.additionalHeight();

        this.placeCenterPillar(config, level, random, chunkGenerator, originChunk, pos, radius, baseHeight, additionalHeight);

        this.distributeSidePillars(config, level, random, chunkGenerator, originChunk, pos, radius, baseHeight, additionalHeight, 1);
        this.distributeSidePillars(config, level, random, chunkGenerator, originChunk, pos, radius, baseHeight, additionalHeight, -1);

        return true;
    }

    public void placeCenterPillar(FerrositePillarConfiguration config, WorldGenLevel level, RandomSource random, ChunkGenerator chunkGenerator, ChunkPos originChunk, BlockPos pos, float radius, int baseHeight, int additionalHeight) {
        Multimap<BlockPos, BlockPos> pillarDiscs = Multimaps.newMultimap(new HashMap<>(), HashSet::new);

        int height = random.nextInt(additionalHeight) + baseHeight;
        int offset = (int) (-radius * 20 + radius * 16);

        for (int i = offset; i < 0; ++i) {
            BlockPos center = new BlockPos(pos.getX(), pos.getY() + i + height + (int) radius, pos.getZ());
            pillarDiscs.putAll(center, this.prepareDisk(
                    level,
                    originChunk,
                    center,
                    radius + i * BlockPlacementUtil.shapeVariator(random, 0.05F)
            ));
            if (pillarDiscs.get(center).isEmpty()) {
                return;
            }
        }

        for (int i = (int) (-radius * 0.5); i < 0; ++i) {
            BlockPos center = new BlockPos(pos.getX(), pos.getY() + i + height + offset + (int) radius, pos.getZ());
            pillarDiscs.putAll(center, this.prepareDisk(
                    level,
                    originChunk,
                    center,
                    radius + i * 2F
            ));
            if (pillarDiscs.get(center).isEmpty()) {
                return;
            }
        }

        BlockPos center = new BlockPos(pos.getX() + random.nextInt(2) - 1, pos.getY() + height + (int) radius, pos.getZ() + random.nextInt(2) - 1);
        pillarDiscs.putAll(center, this.prepareDisk(
                level,
                originChunk,
                center,
                radius - 3
        ));
        if (pillarDiscs.get(center).isEmpty()) {
            return;
        }

        this.placePillar(config, level, random, pillarDiscs.values());

        ConfiguredFeature<?, ?> turf = Objects.requireNonNull(level.registryAccess().lookupOrThrow(Registries.CONFIGURED_FEATURE).get(HolyIslesConfiguredFeatures.FERROSITE_PILLAR_CENTER_TURF).orElse(null)).value();
        turf.place(level, chunkGenerator, random, new BlockPos(pos.getX(), pos.getY() + height + (int) radius, pos.getZ()));
    }

    public void distributeSidePillars(FerrositePillarConfiguration config, WorldGenLevel level, RandomSource random, ChunkGenerator chunkGenerator, ChunkPos originChunk, BlockPos pos, float radius, int baseHeight, int additionalHeight, int offsetMultiplier) {
        this.placeSidePillar(config, level, random, chunkGenerator, originChunk, new BlockPos(pos.getX() + random.nextInt((int) (radius * 1.25F)) * offsetMultiplier, pos.getY() + random.nextInt(additionalHeight + 2) + baseHeight, pos.getZ() + random.nextInt((int) (radius * 1.25F)) * offsetMultiplier));
        this.placeSidePillar(config, level, random, chunkGenerator, originChunk, new BlockPos(pos.getX() + random.nextInt((int) (radius * 1.25F)) * offsetMultiplier, pos.getY() + random.nextInt(additionalHeight + 2) + baseHeight, pos.getZ() + random.nextInt((int) (radius * 1.25F)) * offsetMultiplier));
        if (random.nextBoolean()) {
            this.placeSidePillar(config, level, random, chunkGenerator, originChunk, new BlockPos(pos.getX() + random.nextInt((int) (radius * 1.25F)) * offsetMultiplier, pos.getY() + random.nextInt(additionalHeight + 2) + baseHeight, pos.getZ() + random.nextInt((int) (radius * 1.25F)) * offsetMultiplier));
        }
        if (random.nextBoolean()) {
            this.placeSidePillar(config, level, random, chunkGenerator, originChunk, new BlockPos(pos.getX() + random.nextInt((int) (radius * 1.25F)) * offsetMultiplier, pos.getY() + random.nextInt(additionalHeight + 2) + baseHeight, pos.getZ() + random.nextInt((int) (radius * 1.25F)) * offsetMultiplier));
        }
    }

    public void placeSidePillar(FerrositePillarConfiguration config, WorldGenLevel level, RandomSource random, ChunkGenerator chunkGenerator, ChunkPos originChunk, BlockPos pos) {
        Multimap<BlockPos, BlockPos> pillarDiscs = Multimaps.newMultimap(new HashMap<>(), HashSet::new);

        float radius = random.nextInt(3) + 2.5F;
        int offset = (int) (-radius * 20 + radius * 16);

        for (int i = offset; i < 0; ++i) {
            BlockPos center = new BlockPos(pos.getX(), pos.getY() + i + (int) radius, pos.getZ());
            pillarDiscs.putAll(center, this.prepareDisk(
                    level,
                    originChunk,
                    center,
                    radius + i * BlockPlacementUtil.shapeVariator(random, 0.05F)
            ));
            if (pillarDiscs.get(center).isEmpty()) {
                return;
            }
        }

        for (int i = (int) (-radius * 0.5); i < 0; ++i) {
            BlockPos center = new BlockPos(pos.getX(), pos.getY() + i + offset + (int) radius, pos.getZ());
            pillarDiscs.putAll(center, this.prepareDisk(
                    level,
                    originChunk,
                    center,
                    radius + i * BlockPlacementUtil.shapeVariator(random, 2F)
            ));
            if (pillarDiscs.get(center).isEmpty()) {
                return;
            }
        }

        BlockPos center = new BlockPos(pos.getX() + random.nextInt(2) - 1, pos.getY() + (int) radius, pos.getZ() + random.nextInt(2) - 1);
        pillarDiscs.putAll(center, this.prepareDisk(
                level,
                originChunk,
                center,
                radius - 2
        ));
        if (pillarDiscs.get(center).isEmpty()) {
            return;
        }

        this.placePillar(config, level, random, pillarDiscs.values());

        ConfiguredFeature<?, ?> turf = Objects.requireNonNull(level.registryAccess().lookupOrThrow(Registries.CONFIGURED_FEATURE).get(HolyIslesConfiguredFeatures.FERROSITE_PILLAR_SIDE_TURF).orElse(null)).value();
        turf.place(level, chunkGenerator, random, new BlockPos(pos.getX(), pos.getY() + (int) radius, pos.getZ()));
    }

    protected Set<BlockPos> prepareDisk(WorldGenLevel level, ChunkPos originChunk, BlockPos center, float radius) {
        Set<BlockPos> positions = new HashSet<>();
        float radiusSq = radius * radius;
        boolean placed = this.prepareBlock(level, originChunk, center, true, positions);
        for (int z = 0; z <= radius; z++) {
            for (int x = 0; x <= radius; x++) {
                if (x * x + z * z <= radiusSq) {
                    placed = placed && this.prepareBlock(level, originChunk, center.offset(x, 0, z), true, positions);
                    placed = placed && this.prepareBlock(level, originChunk, center.offset(-x, 0, -z), true, positions);
                    placed = placed && this.prepareBlock(level, originChunk, center.offset(-z, 0, x), true, positions);
                    placed = placed && this.prepareBlock(level, originChunk, center.offset(z, 0, -x), true, positions);
                    if (!placed) {
                        return new HashSet<>();
                    }
                }
            }
        }
        return positions;
    }

    public boolean prepareBlock(WorldGenLevel level, ChunkPos originChunk, BlockPos pos, boolean replaceBlocks, Set<BlockPos> positions) {
        if (originChunk.getChessboardDistance(ChunkPos.containing(pos)) <= 1 && (replaceBlocks || level.getBlockState(pos).isAir())) {
            positions.add(pos);
            return true;
        }
        return false;
    }

    public void placePillar(FerrositePillarConfiguration config, WorldGenLevel level, RandomSource random, Collection<BlockPos> positions) {
        for (BlockPos pos : positions) {
            BlockStateProvider provider = config.baseBlock();
            double noiseValue = config.strataNoise().compute(new DensityFunction.SinglePointContext(pos.getX(), pos.getY(), pos.getZ()));
            if (noiseValue < -0.1F) {
                provider = config.strataBlock();
            }
            BlockState state = provider.getState(level, random, pos);
            if (!state.isAir()) {
                level.setBlock(pos, state, 2);
            }
        }
    }
}