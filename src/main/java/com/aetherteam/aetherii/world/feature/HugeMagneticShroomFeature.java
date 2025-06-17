package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.feature.configuration.HugeMagneticShroomConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class HugeMagneticShroomFeature extends Feature<HugeMagneticShroomConfiguration> {
    public HugeMagneticShroomFeature(Codec<HugeMagneticShroomConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<HugeMagneticShroomConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin().below();
        RandomSource random = context.random();
        HugeMagneticShroomConfiguration config = context.config();

        this.generateShroom(level, random, pos, config); //todo branching

        return true;
    }

    public void generateShroom(WorldGenLevel level, RandomSource random, BlockPos pos, HugeMagneticShroomConfiguration config) {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        this.generateStem(level, random, mutablePos, config);
        boolean small = true;
        if (small) {
            this.generateSmallCap(level, random, mutablePos, config);
        } else {
            this.generateLargeCap(level, random, mutablePos, config);
        }
    }

    public void generateStem(WorldGenLevel level, RandomSource random, BlockPos.MutableBlockPos pos, HugeMagneticShroomConfiguration config) {
        int max = 3; //todo random
        for (int i = 1; i <= max; i++) {
            pos.setWithOffset(pos, Direction.UP);
            this.setBlock(level, pos, config.stemProvider().getState(random, pos));
        }
    }

    public void generateSmallCap(WorldGenLevel level, RandomSource random, BlockPos.MutableBlockPos pos, HugeMagneticShroomConfiguration config) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos offsetPos = pos.relative(direction);
            this.setBlock(level, offsetPos, config.stemProvider().getState(random, offsetPos));
        }
        pos.setWithOffset(pos, Direction.UP);
        this.placeSquare(level, random, pos, config.capProvider(), 2, true);
        this.placeSquare(level, random, pos, config.bottomCapProvider(), 1, false);
        pos.setWithOffset(pos, Direction.UP);
        this.placeSquare(level, random, pos, config.capProvider(), 1, false);
        pos.setWithOffset(pos, Direction.UP);
        this.placeSquare(level, random, pos, config.capProvider(), 1, true);
    }

    public void generateLargeCap(WorldGenLevel level, RandomSource random, BlockPos.MutableBlockPos pos, HugeMagneticShroomConfiguration config) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            for (int j = 1; j <= 2; j++) {
                BlockPos offsetPos = pos.relative(direction, j);
                this.setBlock(level, offsetPos, config.stemProvider().getState(random, offsetPos));
            }
        }
        pos.setWithOffset(pos, Direction.UP);
        this.placeSquare(level, random, pos, config.capProvider(), 3, true);
        this.placeSquare(level, random, pos, config.bottomCapProvider(), 2, true);
        pos.setWithOffset(pos, Direction.UP);
        this.placeSquare(level, random, pos, config.capProvider(), 2, true);
        pos.setWithOffset(pos, Direction.UP);
        this.placeSquare(level, random, pos, config.capProvider(), 1, false);
        pos.setWithOffset(pos, Direction.UP);
        this.placeSquare(level, random, pos, config.capProvider(), 1, true);
    }

    public void placeSquare(WorldGenLevel level, RandomSource random, BlockPos pos, BlockStateProvider state, int radius, boolean rounded) {
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                BlockPos offsetPos = pos.offset(x, 0, z);
                if (!rounded || (Math.abs(x) != radius || Math.abs(z) != radius)) {
                    this.setBlock(level, offsetPos, state.getState(random, offsetPos));
                }
            }
        }
    }
}
