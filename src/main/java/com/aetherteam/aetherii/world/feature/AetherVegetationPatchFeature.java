package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.feature.configuration.AetherVegetationPatchConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class AetherVegetationPatchFeature extends Feature<AetherVegetationPatchConfiguration> {
    public AetherVegetationPatchFeature(Codec<AetherVegetationPatchConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<AetherVegetationPatchConfiguration> context) {
        WorldGenLevel level = context.level();
        AetherVegetationPatchConfiguration config = context.config();
        RandomSource random = context.random();
        BlockPos origin = context.origin();
        ChunkPos originChunk = ChunkPos.containing(origin);
        Predicate<BlockState> replaceable = (s) -> s.is(config.replaceable());
        int xRadius = config.xzRadius().sample(random) + 1;
        int zRadius = config.xzRadius().sample(random) + 1;
        Set<BlockPos> surface = this.placeGroundPatch(level, config, random, originChunk, origin, replaceable, xRadius, zRadius);
        this.distributeVegetation(context, level, config, random, surface);
        return !surface.isEmpty();
    }

    protected Set<BlockPos> placeGroundPatch(WorldGenLevel level, AetherVegetationPatchConfiguration config, RandomSource random, ChunkPos originChunk, BlockPos origin, Predicate<BlockState> replaceable, int xRadius, int zRadius) {
        BlockPos.MutableBlockPos pos = origin.mutable();
        BlockPos.MutableBlockPos belowPos = pos.mutable();
        Direction inwards = config.surface().getDirection();
        Direction outwards = inwards.getOpposite();
        Set<BlockPos> surface = new HashSet<>();

        for (int dx = -xRadius; dx <= xRadius; ++dx) {
            boolean isXEdge = dx == -xRadius || dx == xRadius;

            for (int dz = -zRadius; dz <= zRadius; ++dz) {
                boolean isZEdge = dz == -zRadius || dz == zRadius;
                boolean isEdge = isXEdge || isZEdge;
                boolean isCorner = isXEdge && isZEdge;
                boolean isEdgeButNotCorner = isEdge && !isCorner;

                if (!isCorner && (!isEdgeButNotCorner || config.extraEdgeColumnChance() != 0.0F && !(random.nextFloat() > config.extraEdgeColumnChance()))) {
                    pos.setWithOffset(origin, dx, 0, dz);

                    if (originChunk.getChessboardDistance(ChunkPos.containing(pos)) <= 1) {
                        for (int offset = 0; level.isStateAtPosition(pos, BlockBehaviour.BlockStateBase::isAir) && offset < config.verticalRange(); ++offset) {
                            pos.move(inwards);
                        }
                        for (int offset = 0; level.isStateAtPosition(pos, (s) -> !s.isAir()) && offset < config.verticalRange(); ++offset) {
                            pos.move(outwards);
                        }

                        belowPos.setWithOffset(pos, config.surface().getDirection());
                        BlockState belowState = level.getBlockState(belowPos);
                        if (level.isEmptyBlock(pos) && belowState.isFaceSturdy(level, belowPos, config.surface().getDirection().getOpposite())) {
                            int depth = config.depth().sample(random) + (config.extraBottomBlockChance() > 0.0F && random.nextFloat() < config.extraBottomBlockChance() ? 1 : 0);
                            BlockPos groundPos = belowPos.immutable();
                            boolean groundPlaced = this.placeGround(level, config, replaceable, random, belowPos, depth);
                            if (groundPlaced) {
                                surface.add(groundPos);
                            }
                        }
                    }
                }
            }
        }
        return surface;
    }

    protected boolean placeGround(WorldGenLevel level, AetherVegetationPatchConfiguration config, Predicate<BlockState> replaceable, RandomSource random, BlockPos.MutableBlockPos belowPos, int depth) {
        for (int i = 0; i < depth; ++i) {
            BlockState stateToPlace = config.groundState().getState(level, random, belowPos);
            BlockState belowState = level.getBlockState(belowPos);
            if (!stateToPlace.is(belowState.getBlock())) {
                if (!replaceable.test(belowState)) {
                    return i != 0;
                }
                level.setBlock(belowPos, stateToPlace, 2);
                belowPos.move(config.surface().getDirection());
            }
        }
        return true;
    }

    protected void distributeVegetation(FeaturePlaceContext<AetherVegetationPatchConfiguration> context, WorldGenLevel level, AetherVegetationPatchConfiguration config, RandomSource random, Set<BlockPos> surface) {
        for (BlockPos surfacePos : surface) {
            if (config.vegetationChance() > 0.0F && random.nextFloat() < config.vegetationChance()) {
                this.placeVegetation(level, config, context.chunkGenerator(), random, surfacePos);
            }
        }
    }

    protected void placeVegetation(WorldGenLevel level, AetherVegetationPatchConfiguration config, ChunkGenerator generator, RandomSource random, BlockPos vegetationPos) {
        config.vegetationFeature().value().place(level, generator, random, vegetationPos.relative(config.surface().getDirection().getOpposite()));
    }
}
