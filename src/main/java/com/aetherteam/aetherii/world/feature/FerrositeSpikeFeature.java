package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.BlockPlacementUtil;
import com.aetherteam.aetherii.world.feature.configuration.FerrositeSpikeConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.HashSet;
import java.util.Set;

public class FerrositeSpikeFeature extends Feature<FerrositeSpikeConfiguration> {

    public FerrositeSpikeFeature(Codec<FerrositeSpikeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<FerrositeSpikeConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos pos = context.origin().below(2);
        FerrositeSpikeConfiguration config = context.config();

        Set<BlockPos> positions = new HashSet<>();

        float radius = random.nextInt(config.additionalRadius()) + config.baseRadius();
        float heightFactor = 6.5F + random.nextInt(5);

        for (int i = 0; i < radius * heightFactor; ++i) {
            if (i < radius * heightFactor - heightFactor * 0.1F) {
                this.placeDisk(level, new BlockPos(pos.getX(), pos.getY() + i, pos.getZ()), radius - (float) i / 20 - BlockPlacementUtil.shapeVariator(random, 0.5F), true, positions);
            }
            if (i == radius * heightFactor - heightFactor * 0.2F) {
                this.placeDisk(level, new BlockPos(pos.getX() + random.nextIntBetweenInclusive(-1, 1), pos.getY() + i, pos.getZ() + random.nextIntBetweenInclusive(-1, 1)), radius - (float) i / 10, false, positions);
            }
        }

        if (random.nextBoolean()) {
            this.placeSideSpike(context, new BlockPos(pos.getX() + random.nextInt(3) + 2, pos.getY() - random.nextInt(2) - 1, pos.getZ() + random.nextInt(3) + 2), positions);
        }
        if (random.nextBoolean()) {
            this.placeSideSpike(context, new BlockPos(pos.getX() - random.nextInt(3) + 2, pos.getY() - random.nextInt(2) - 1, pos.getZ() - random.nextInt(3) + 2), positions);
        }
        if (random.nextInt(2) == 0) {
            this.placeSideSpike(context, new BlockPos(pos.getX() + random.nextInt(3) + 2, pos.getY() - random.nextInt(2) - 1, pos.getZ() + random.nextInt(3) + 2), positions);
        }
        if (random.nextInt(2) == 0) {
            this.placeSideSpike(context, new BlockPos(pos.getX() - random.nextInt(3) + 2, pos.getY() - random.nextInt(2) - 1, pos.getZ() - random.nextInt(3) + 2), positions);
        }

        for (BlockPos position : positions) {
            if (position.getY() == pos.getY()) {
                if (!level.getBlockState(position.below()).isSolid()) {
                    return false;
                }
            }
        }

        for (BlockPos position : positions) {
            level.setBlock(position, config.block().getState(level, random, position), 2);
        }

        return true;
    }

    public void placeSideSpike(FeaturePlaceContext<FerrositeSpikeConfiguration> context, BlockPos pos, Set<BlockPos> positions) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        FerrositeSpikeConfiguration config = context.config();

        float radius = random.nextInt(config.additionalRadius()) + config.baseRadius();
        float heightFactor = 3.5F + random.nextInt(2);

        for (int i = 0; i < radius * heightFactor; ++i) {
            if (i < radius * heightFactor - heightFactor * 0.5F) {
                this.placeDisk(level, new BlockPos(pos.getX(), pos.getY() + i, pos.getZ()), radius - (float) i / 10 - BlockPlacementUtil.shapeVariator(random, 0.35F), true, positions);
            }
            if (i == radius * heightFactor - heightFactor * 0.5F) {
                this.placeDisk(level, new BlockPos(pos.getX() + random.nextIntBetweenInclusive(-1, 1), pos.getY() + i, pos.getZ() + random.nextIntBetweenInclusive(-1, 1)), radius - (float) i / 4, true, positions);
            }
        }
    }

    public void placeDisk(WorldGenLevel level, BlockPos center, float radius, boolean replaceBlocks, Set<BlockPos> positions) {
        float radiusSq = radius * radius;
        this.placeProvidedBlock(level, center, replaceBlocks, positions);
        for (int z = 0; z < radius; z++) {
            for (int x = 0; x < radius; x++) {
                if (x * x + z * z > radiusSq) continue;
                this.placeProvidedBlock(level, center.offset(x, 0, z), replaceBlocks, positions);
                this.placeProvidedBlock(level, center.offset(-x, 0, -z), replaceBlocks, positions);
                this.placeProvidedBlock(level, center.offset(-z, 0, x), replaceBlocks, positions);
                this.placeProvidedBlock(level, center.offset(z, 0, -x), replaceBlocks, positions);
            }
        }
    }

    public void placeProvidedBlock(WorldGenLevel level, BlockPos pos, boolean replaceBlocks, Set<BlockPos> positions) {
        if (replaceBlocks) {
            positions.add(pos);
        } else if (level.getBlockState(pos).isAir()) {
            positions.add(pos);
        }
    }
}