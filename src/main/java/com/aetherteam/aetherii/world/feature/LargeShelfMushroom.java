package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.BlockPlacementUtil;
import com.aetherteam.aetherii.world.feature.configuration.LargeShelfMushroomConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class LargeShelfMushroom extends Feature<LargeShelfMushroomConfiguration> {

    public LargeShelfMushroom(Codec<LargeShelfMushroomConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<LargeShelfMushroomConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();
        LargeShelfMushroomConfiguration config = context.config();

        if (pos.getY() > config.minY()) {
            BlockPlacementUtil.placeDisk(level, config.block(), pos, config.baseRadius() + config.additionalRadius() + 0.5F, random, false);
            return true;
        }
        return false;
    }
}