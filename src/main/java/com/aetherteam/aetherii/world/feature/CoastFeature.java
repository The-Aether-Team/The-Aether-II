package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.world.feature.configuration.CoastConfiguration;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.*;

public class CoastFeature extends Feature<CoastConfiguration> {
    public CoastFeature(Codec<CoastConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<CoastConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos pos = context.origin();
        CoastConfiguration config = context.config();

        ChunkPos chunkPos = ChunkPos.containing(pos);
        pos = chunkPos.getBlockAt(0, pos.getY(), 0);

        //todo
        //  fix chunk cascade issue
        //  this all doesnt apply to lakes

        BlockPos origin = null;

        for (int x = pos.getX(); x < pos.getX() + 16; ++x) {
            for (int z = pos.getZ(); z < pos.getZ() + 16; ++z) {
                BlockPos offset = new BlockPos(x, pos.getY(), z);
                if (level.getBlockState(offset).is(AetherIITags.Blocks.SHAPES_COASTS)
                        && level.getBlockState(offset.above()).is(AetherIITags.Blocks.SHAPES_COASTS)
                        && level.getBlockState(offset.below()).is(AetherIITags.Blocks.SHAPES_COASTS)
                        && !level.getBlockState(offset.above()).is(AetherIITags.Blocks.COAST_SOILS)
                        && !level.getBlockState(offset.below()).is(AetherIITags.Blocks.COAST_SOILS)
                        && (!level.getBlockState(offset.north()).isSolid()
                        || !level.getBlockState(offset.east()).isSolid()
                        || !level.getBlockState(offset.south()).isSolid()
                        || !level.getBlockState(offset.west()).isSolid())) {
                    origin = offset;
                    break;
                }
            }
        }

        if (origin != null) {
            Set<BlockPos> coastPositions = new LinkedHashSet<>();

            BlockPos pointer = origin;
            boolean start = false;
            for (int i = 0; i < 16; i++) {
                boolean end = true;
                for (Direction direction : Direction.Plane.HORIZONTAL) {
                    BlockPos offset = pointer.relative(direction);
                    if (!coastPositions.contains(offset)
                            && level.getBlockState(offset).is(AetherIITags.Blocks.SHAPES_COASTS)
                            && (!level.getBlockState(offset.north()).isSolid()
                            || !level.getBlockState(offset.north().east()).isSolid()
                            || !level.getBlockState(offset.east()).isSolid()
                            || !level.getBlockState(offset.south().east()).isSolid()
                            || !level.getBlockState(offset.south()).isSolid()
                            || !level.getBlockState(offset.south().west()).isSolid()
                            || !level.getBlockState(offset.west()).isSolid()
                            || !level.getBlockState(offset.north().west()).isSolid())) {
                        coastPositions.add(offset);
                        pointer = offset;
                        start = true;
                        end = false;
                    }
                    if (start && end) {
                        break;
                    }
                }
            }

            Multimap<BlockPos, BlockPos> coastDiscs = Multimaps.newMultimap(new HashMap<>(), ArrayList::new);

            if (coastPositions.size() > 8) {
                int i = 0;
                int max = coastPositions.size() - 1;
                float half = max / 2.0F;
                for (BlockPos coastPos : coastPositions) {
                    int mainDistToCenter = Mth.ceil(Math.abs(i - half));
                    int scale = Mth.floor((coastPositions.size() / 2.0F) - mainDistToCenter);
                    float radius = Mth.floor(Math.pow(scale + 1, 0.65F)) + 0.25F;
                    if (radius > 1 && random.nextBoolean()) {
                        radius -= 1;
                    }
                    coastDiscs.putAll(coastPos, prepareCoast(level, coastPos, radius)) ;
                    coastDiscs.putAll(coastPos.below(), prepareCoast(level, coastPos.below(), radius - 1.25F));
                    i += 1;
                }
            }
            for (BlockPos coastPos : coastDiscs.values()) {
                BlockState state = config.block().getState(level, random, coastPos);
                if (level.setBlock(coastPos, state, 2)) {
                    if (config.vegetationChance() > 0.0F && random.nextFloat() < config.vegetationChance()) {
                        config.vegetationFeature().ifPresent(placedFeatureHolder -> placedFeatureHolder.value().place(level, context.chunkGenerator(), random, coastPos));
                    }
                }
            }
        }
        return true;
    }

    public static List<BlockPos> prepareCoast(WorldGenLevel level, BlockPos center, float radius) {
        List<BlockPos> positions = new ArrayList<>();
        float radiusSq = radius * radius;
        boolean placed = prepareCoastBlock(level, center, positions);
        for (int z = 0; z <= radius; z++) {
            for (int x = 0; x <= radius; x++) {
                if (x * x + z * z <= radiusSq) {
                    placed = placed && prepareCoastBlock(level, center.offset(x, 0, z), positions);
                    placed = placed && prepareCoastBlock(level, center.offset(-x, 0, -z), positions);
                    placed = placed && prepareCoastBlock(level, center.offset(-z, 0, x), positions);
                    placed = placed && prepareCoastBlock(level, center.offset(z, 0, -x), positions);
                    if (!placed) {
                        return List.of();
                    }
                }
            }
        }
        return positions;
    }

    public static boolean prepareCoastBlock(WorldGenLevel level, BlockPos pos, List<BlockPos> positions) {
        if (!level.getBlockState(pos).is(AetherIITags.Blocks.PREVENTS_COASTS) && !level.getBlockState(pos.above()).is(AetherIITags.Blocks.PREVENTS_COASTS)) {
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
}