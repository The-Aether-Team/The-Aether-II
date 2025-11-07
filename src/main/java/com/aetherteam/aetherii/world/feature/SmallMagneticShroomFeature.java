package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.feature.configuration.BigMagneticShroomConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class SmallMagneticShroomFeature extends AbstractMagneticShroomFeature {
    public SmallMagneticShroomFeature(Codec<BigMagneticShroomConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BigMagneticShroomConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();
        BigMagneticShroomConfiguration config = context.config();

        config.groundProvider().ifPresent(provider -> this.placeGround(level, random, pos.below(), provider));
        if (this.canPlace(level, random, pos, config)) {
            this.generateSmallShroom(level, random, pos, config);
            return true;
        }
        return false;
    }

    public boolean canPlace(WorldGenLevel level, RandomSource random, BlockPos pos, BigMagneticShroomConfiguration config) {
        int height = 4;

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
