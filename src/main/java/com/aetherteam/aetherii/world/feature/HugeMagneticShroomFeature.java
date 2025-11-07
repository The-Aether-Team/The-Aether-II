package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.feature.configuration.BigMagneticShroomConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class HugeMagneticShroomFeature extends AbstractMagneticShroomFeature {
    public HugeMagneticShroomFeature(Codec<BigMagneticShroomConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BigMagneticShroomConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();
        BigMagneticShroomConfiguration config = context.config();

        if (this.canPlace(level, random, pos, config)) {
            if (!config.tall()) {
                this.generateSmallShroom(level, random, pos, config);
            } else {
                BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
//            if (random.nextBoolean()) {
//                this.generateStem(level, random, mutableBlockPos, config, Direction.UP, UniformInt.of(1, 2));
//                List<Direction> directions = new ArrayList<>(Direction.Plane.HORIZONTAL.stream().toList());
//                Collections.shuffle(directions);
//                for (Direction direction : Direction.Plane.HORIZONTAL) {
//                    if (random.nextBoolean()) {
//                        this.generateStem(level, random, mutableBlockPos, config, Direction.UP, UniformInt.of(1, 3));
//                        this.generateBranch(level, random, new BlockPos(mutableBlockPos).mutable(), direction, config);
//                    }
//                }
//                this.generateStem(level, random, mutableBlockPos, config, Direction.UP, UniformInt.of(1, 2));
//            }
                this.generateStem(level, random, mutableBlockPos, config, Direction.UP, UniformInt.of(1, 2));
                this.generateLargeShroom(level, random, mutableBlockPos, config);
            }
            return true;
        }
        return false;
    }

    public boolean canPlace(WorldGenLevel level, RandomSource random, BlockPos pos, BigMagneticShroomConfiguration config) {
        int height = 10; //todo ?

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
}
