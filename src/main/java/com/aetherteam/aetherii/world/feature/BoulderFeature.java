package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.world.feature.configuration.BoulderConfiguration;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.function.Predicate;

public class BoulderFeature extends Feature<BoulderConfiguration> {
    public BoulderFeature(Codec<BoulderConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BoulderConfiguration> context) {
        WorldGenLevel level = context.level();
        ChunkGenerator generator = context.chunkGenerator();
        BlockPos pos = context.origin();
        RandomSource random = context.random();
        BoulderConfiguration config = context.config();
        float radius = config.radius() + config.variation().sample(random);

        this.placeBoulder(level, generator, pos.relative(Direction.Plane.HORIZONTAL.getRandomDirection(random)), random, config, radius);

        return true;
    }

    private void placeBoulder(WorldGenLevel level, ChunkGenerator generator, BlockPos pos, RandomSource random, BoulderConfiguration config, float radius) {
        ObjectArrayList<BlockPos> positions = new ObjectArrayList<>();
        ObjectArrayList<BlockPos> placements = new ObjectArrayList<>();

        Predicate<BlockPos> empty = (blockPos) -> !level.getBlockState(blockPos).isCollisionShapeFullBlock(level, blockPos) || !level.getBlockState(blockPos).isSolid();
        Vec3 origin = Vec3.atBottomCenterOf(pos);
        int limit = Math.round(radius);

        boolean canGenerate = true;

        for (int x = -limit; x < limit; x++) {
            for (int y = -limit; y < limit; y++) {
                for (int z = -limit; z < limit; z++) {
                    Vec3 placeVec = origin.add(x, y, z);
                    BlockPos placePos = BlockPos.containing(Math.round(placeVec.x()), Math.round(placeVec.y()), Math.round(placeVec.z()));
                    if ((level.getBlockState(placePos.below(1)).is(AetherIITags.Blocks.SUPPORTS_BOULDER) || level.getBlockState(placePos.below(2)).is(AetherIITags.Blocks.SUPPORTS_BOULDER)) && (level.getBlockState(placePos).is(AetherIITags.Blocks.SUPPORTS_BOULDER) || empty.test(placePos))) {
                        positions.add(placePos);
                    } else {
                        canGenerate = false;
                        break;
                    }
                }
            }
        }

        if (canGenerate) {
            positions.sort(Comparator.comparingInt(Vec3i::getY));
            for (BlockPos placementPos : positions) {
                if (positions.contains(placementPos)) {
                    placements.add(placementPos);
                }
                if (random.nextInt(4) > 0) {
                    if (!positions.contains(placementPos.above()) && (level.getBlockState(placementPos.above()).is(AetherIITags.Blocks.SUPPORTS_BOULDER) || empty.test(placementPos.above()))) {
                        placements.add(placementPos.above());

                        BlockPos relativePos = placementPos.relative(Direction.Plane.HORIZONTAL.getRandomDirection(random));
                        if (!positions.contains(relativePos) && level.getBlockState(relativePos.below()).is(AetherIITags.Blocks.SUPPORTS_BOULDER) && (level.getBlockState(relativePos).is(AetherIITags.Blocks.SUPPORTS_BOULDER) || empty.test(relativePos)) && random.nextBoolean()) {
                            placements.add(relativePos);
                        }
                    }
                }
            }

            placements.forEach((placementPos) -> {
                if (level.setBlock(placementPos, config.block().getState(level, random, placementPos), 3)) {
                    if (config.vegetationChance() > 0.0F && random.nextFloat() < config.vegetationChance()) {
                        config.vegetationFeature().ifPresent(placedFeatureHolder -> placedFeatureHolder.value().place(level, generator, random, placementPos.above()));
                    }
                }
            });
        }


    }
}
