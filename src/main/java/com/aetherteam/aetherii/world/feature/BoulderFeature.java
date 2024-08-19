package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.feature.configuration.BoulderConfiguration;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;

public class BoulderFeature extends Feature<BoulderConfiguration> {
    public BoulderFeature(Codec<BoulderConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BoulderConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();
        BoulderConfiguration config = context.config();
        float radius = config.radius() + config.variation().sample(random);

        this.placeBoulder(level, pos, random, config, radius);

        if (random.nextInt(3) == 0) {
            pos.below().relative(Direction.getRandom(random), random.nextInt(1) + 1);
            this.placeBoulder(level, pos, random, config, radius);
        }
        if (random.nextInt(10) == 0) {
            pos.below().relative(Direction.getRandom(random), random.nextInt(1) + 1);
            this.placeBoulder(level, pos, random, config, radius);
        }

        for (int i = 0; i < Math.round(radius); i++) {
            if (config.vegetationChance() > 0.0F && random.nextFloat() < config.vegetationChance()) {
                int y = i;
                config.vegetationFeature().ifPresent(placedFeatureHolder -> placedFeatureHolder.value().place(level, context.chunkGenerator(), random, pos.above(y)));
            }
        }

        return true;
    }

    private void placeBoulder(WorldGenLevel level, BlockPos pos, RandomSource random, BoulderConfiguration config, float radius) {
        ObjectArrayList<BlockPos> positions = new ObjectArrayList<>();
        ObjectArrayList<BlockPos> placements = new ObjectArrayList<>();

        Vec3 origin = Vec3.atBottomCenterOf(pos);
        int limit = Math.round(radius);

        for (int x = -limit; x < limit; x++) {
            for (int y = -limit; y < limit; y++) {
                for (int z = -limit; z < limit; z++) {
                    Vec3 placeVec = origin.add(x, y, z);
                    BlockPos placePos = BlockPos.containing(Math.round(placeVec.x()), Math.round(placeVec.y()), Math.round(placeVec.z()));
                    positions.add(placePos);
                }
            }
        }

        positions.sort(Comparator.comparingInt(Vec3i::getY));
        for (BlockPos placementPos : positions) {
            if (positions.contains(placementPos.above()) || !random.nextBoolean()) {
                placements.add(placementPos);
            }
            BlockPos relativePos = placementPos.relative(Direction.Plane.HORIZONTAL.getRandomDirection(random));
            if (!positions.contains(relativePos) && level.getBlockState(relativePos).canBeReplaced() && level.getBlockState(relativePos.below()).isCollisionShapeFullBlock(level, relativePos.below()) && random.nextBoolean()) {
                placements.add(relativePos);
            }
            if (!level.getBlockState(placementPos.below()).isCollisionShapeFullBlock(level, placementPos.below())) {
                placements.add(placementPos.below());
            }
        }

        placements.sort(Comparator.comparingInt(Vec3i::getY).reversed());
        placements.forEach((placementPos) -> {
            if (!level.getBlockState(placementPos).isCollisionShapeFullBlock(level, placementPos) && level.getBlockState(placementPos.below()).is(config.validBlocks())) {
                level.setBlock(placementPos, config.block().getState(random, placementPos), 3);
            }
        });
    }
}
