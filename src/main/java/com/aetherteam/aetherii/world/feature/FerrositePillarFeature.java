package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.BlockPlacementUtil;
import com.aetherteam.aetherii.world.feature.configuration.FerrositePillarConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class FerrositePillarFeature extends Feature<FerrositePillarConfiguration> {

    public FerrositePillarFeature(Codec<FerrositePillarConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<FerrositePillarConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos pos = context.origin();
        FerrositePillarConfiguration config = context.config();

        int height = random.nextInt(24) + 24;
        float radius = random.nextInt(6) + 3.5F;
        int floatingHeight = random.nextInt(2) + 4;

        for (int i = -128; i < floatingHeight; ++i) {
            BlockPlacementUtil.placeDisk(level, BlockStateProvider.simple(Blocks.AIR), new BlockPos(pos.getX(), pos.getY() + i + floatingHeight - (int) radius, pos.getZ()), radius, random, true);
        }
        for (int i = 0; i < height; ++i) {
            BlockPlacementUtil.placeDisk(level, config.block(), new BlockPos(pos.getX(), pos.getY() + i + floatingHeight + (int)radius, pos.getZ()), radius, random, true);
        }
        for (int i = (int) -radius; i < 0; ++i) {
            BlockPlacementUtil.placeDisk(level, config.block(), new BlockPos(pos.getX(), pos.getY() + i + floatingHeight + (int)radius, pos.getZ()), radius + i, random, true);
        }
        return true;
    }
}