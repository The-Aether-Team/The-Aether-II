package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.world.feature.configuration.CoastConfiguration;
import com.google.common.collect.Multimap;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;

public abstract class AbstractCoastFeature extends Feature<CoastConfiguration> {
    public AbstractCoastFeature(Codec<CoastConfiguration> codec) {
        super(codec);
    }

    public static BlockPos findOrigin(WorldGenLevel level, BlockPos pos, TagKey<Block> validTag, TagKey<Block> avoidTag) {
        for (BlockPos offset : BlockPos.spiralAround(pos, 7, Direction.SOUTH, Direction.EAST)) {
            offset = offset.immutable();
            if (level.getBlockState(offset).is(validTag)
                    && level.getBlockState(offset.above()).is(validTag)
                    && level.getBlockState(offset.below()).is(validTag)
                    && !level.getBlockState(offset.above()).is(avoidTag)
                    && !level.getBlockState(offset.below()).is(avoidTag)
                    && (!level.getBlockState(offset.north()).isSolid()
                    || !level.getBlockState(offset.east()).isSolid()
                    || !level.getBlockState(offset.south()).isSolid()
                    || !level.getBlockState(offset.west()).isSolid())) {
                return offset;
            }
        }
        return null;
    }

    public static void planPath(WorldGenLevel level, ChunkPos originChunk, BlockPos origin, Set<BlockPos> coastPositions, Consumer<BlockPos> addPosition, TagKey<Block> checkTag, int length) {
        BlockPos pointer = origin;
        boolean start = false;
        for (int i = 0; i < length; i++) {
            boolean end = true;
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos offset = pointer.relative(direction);
                if (!coastPositions.contains(offset)
                        && originChunk.getChessboardDistance(ChunkPos.containing(offset)) <= 1
                        && level.getBlockState(offset).is(checkTag)
                        && (!level.getBlockState(offset.north()).isSolid()
                        || !level.getBlockState(offset.north().east()).isSolid()
                        || !level.getBlockState(offset.east()).isSolid()
                        || !level.getBlockState(offset.south().east()).isSolid()
                        || !level.getBlockState(offset.south()).isSolid()
                        || !level.getBlockState(offset.south().west()).isSolid()
                        || !level.getBlockState(offset.west()).isSolid()
                        || !level.getBlockState(offset.north().west()).isSolid())) {
                    addPosition.accept(offset);
                    pointer = offset;
                    start = true;
                    end = false;
                    break;
                }
            }
            if (start && end) {
                break;
            }
        }
    }

    protected void prepareCoastDiscs(CoastConfiguration config, WorldGenLevel level, ChunkPos originChunk, LinkedHashSet<BlockPos> coastPositions, Multimap<BlockPos, BlockPos> coastDiscs, RandomSource random) {
        float power = config.widthPower() + (random.nextInt(3) * 0.05F);
        if (coastPositions.size() > 8) {
            int i = 0;
            int max = coastPositions.size() - 1;
            float half = max / 2.0F;
            for (BlockPos coastPos : coastPositions) {
                int mainDistToCenter = Mth.ceil(Math.abs(i - half));
                int scale = Mth.floor((coastPositions.size() / 2.0F) - mainDistToCenter);
                float radius = Mth.floor(Math.pow(scale + 1, power)) + 0.25F;
                if (radius > 1.25F && random.nextBoolean()) {
                    radius -= 1;
                }
                coastDiscs.putAll(coastPos, this.prepareCoast(level, originChunk, coastPos, radius)) ;
                coastDiscs.putAll(coastPos.below(), this.prepareCoast(level, originChunk, coastPos.below(), radius - 1.25F));
                i += 1;
            }
        }
    }

    protected Set<BlockPos> prepareCoast(WorldGenLevel level, ChunkPos originChunk, BlockPos center, float radius) {
        Set<BlockPos> positions = new HashSet<>();
        float radiusSq = radius * radius;
        boolean placed = this.prepareCoastBlock(level, originChunk, center, positions);
        for (int z = 0; z <= radius; z++) {
            for (int x = 0; x <= radius; x++) {
                if (x * x + z * z <= radiusSq) {
                    placed = placed && this.prepareCoastBlock(level, originChunk, center.offset(x, 0, z), positions);
                    placed = placed && this.prepareCoastBlock(level, originChunk, center.offset(-x, 0, -z), positions);
                    placed = placed && this.prepareCoastBlock(level, originChunk, center.offset(-z, 0, x), positions);
                    placed = placed && this.prepareCoastBlock(level, originChunk, center.offset(z, 0, -x), positions);
                    if (!placed) {
                        return new HashSet<>();
                    }
                }
            }
        }
        return positions;
    }

    protected boolean prepareCoastBlock(WorldGenLevel level, ChunkPos originChunk, BlockPos pos, Set<BlockPos> positions) {
        if (originChunk.getChessboardDistance(ChunkPos.containing(pos)) <= 1
                && !level.getBlockState(pos).is(AetherIITags.Blocks.PREVENTS_COASTS) && !level.getBlockState(pos.above()).is(AetherIITags.Blocks.PREVENTS_COASTS)
                && !level.getBlockState(pos.above(2)).is(AetherIITags.Blocks.COAST_SOILS) && !level.getBlockState(pos.below(2)).is(AetherIITags.Blocks.COAST_SOILS)) {
            if ((!level.getBlockState(pos).is(AetherIITags.Blocks.SHAPES_COASTS)
                    || !level.getBlockState(pos.below()).is(AetherIITags.Blocks.SHAPES_COASTS)
                    || !level.getBlockState(pos.above()).is(AetherIITags.Blocks.SHAPES_COASTS))
                    && !level.getBlockState(pos).liquid()) {
                positions.add(pos);
            }
            return true;
        }
        return false;
    }

    protected void generateBlocksAndVegetation(FeaturePlaceContext<CoastConfiguration> context, CoastConfiguration config, WorldGenLevel level, RandomSource random, Multimap<BlockPos, BlockPos> coastDiscs) {
        for (BlockPos coastPos : coastDiscs.values()) {
            BlockState state = config.block().getState(level, random, coastPos);
            if (!state.isAir() && level.setBlock(coastPos, state, 1 | 2)) {
                if (config.vegetationChance() > 0.0F && random.nextFloat() < config.vegetationChance()) {
                    config.vegetationFeature().ifPresent(placedFeatureHolder -> placedFeatureHolder.value().place(level, context.chunkGenerator(), random, coastPos));
                }
            }
        }
    }
}
