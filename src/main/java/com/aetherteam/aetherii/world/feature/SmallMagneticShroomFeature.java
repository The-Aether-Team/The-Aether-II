package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.feature.configuration.BigMagneticShroomConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class SmallMagneticShroomFeature extends Feature<BigMagneticShroomConfiguration> { //todo abstract big mushroom feature class
    public SmallMagneticShroomFeature(Codec<BigMagneticShroomConfiguration > codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BigMagneticShroomConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();
        BigMagneticShroomConfiguration config = context.config();

        if (this.canPlace(level, random, pos, config)) {
            this.generateSmallShroom(level, random, pos, config);
            return true;
        }
        return false;
    }

    public boolean canPlace(WorldGenLevel level, RandomSource random, BlockPos pos, BigMagneticShroomConfiguration config) {
        int height = 4; //todo ?

        for (int y = 0; y <= height; ++y) {
            int i = config.minimumSize().getSizeAtHeight(height, y);

            for (int x = -i; x <= i; ++x) {
                for (int z = -i; z <= i; ++z) {
                    BlockPos checkPos = pos.offset(x, y, z);
                    if (!level.isStateAtPosition(checkPos, BlockBehaviour.BlockStateBase::isAir)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void generateSmallShroom(WorldGenLevel level, RandomSource random, BlockPos pos, BigMagneticShroomConfiguration config) {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        this.generateStem(level, random, mutablePos, config, Direction.UP, UniformInt.of(1, 2));
        this.generateSmallCap(level, random, mutablePos, config);
    }

    public void generateStem(WorldGenLevel level, RandomSource random, BlockPos.MutableBlockPos pos, BigMagneticShroomConfiguration config, Direction direction, IntProvider length) {
        int max = length.sample(random);
        for (int i = 1; i <= max; i++) {
            this.setBlock(level, pos, config.stemProvider().getState(random, pos));
            pos.setWithOffset(pos, Direction.UP);
        }
    }

    public void generateSmallCap(WorldGenLevel level, RandomSource random, BlockPos.MutableBlockPos pos, BigMagneticShroomConfiguration config) {
        this.placeSquare(level, random, pos, config.bottomCapProvider(), 1, false);
        pos.setWithOffset(pos, Direction.UP);
        this.setBlock(level, pos, config.capProvider().getState(random, pos));
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
