package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.BlockPlacementUtil;
import com.aetherteam.aetherii.world.feature.configuration.FerrositePillarConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class FerrositePillarFeature extends Feature<FerrositePillarConfiguration> {

    public FerrositePillarFeature(Codec<FerrositePillarConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<FerrositePillarConfiguration> context) {
        /*
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos pos = context.origin();
        FerrositePillarConfiguration config = context.config();

        int height = random.nextInt(28) + 16;
        float radius = random.nextInt(5) + 4.5F;
        int floatingHeight = random.nextInt(4) + 8;

        // Places a straight Pillar after a specific Height
        for (int i = 0; i < height; ++i) {
            BlockPlacementUtil.placeDisk(level, config.block(), new BlockPos(pos.getX(), pos.getY() + i + floatingHeight + (int) radius, pos.getZ()), radius, random, true);
        }

        // Forms a gradient at the bottom of the Pillar until the height of radius * 2
        // Removes the Underside Peak afterward to give them a more blobby look
        for (int i = (int) ((int) -radius * 2 + radius / 1.5F); i < 0; ++i) {
            BlockPlacementUtil.placeDisk(level, config.block(), new BlockPos(pos.getX(), pos.getY() + i + floatingHeight + (int) radius, pos.getZ()), radius + i * 0.5F, random, true);
        }

        // Places a randomized disk at the top of the Pillar
        BlockPlacementUtil.placeDisk(level, config.block(), new BlockPos(pos.getX() + random.nextInt(2) -1, pos.getY() + floatingHeight + height + (int) radius, pos.getZ() + random.nextInt(2) -1), radius * 0.75F, random, true);

        // Places Side Pillars
        placeSidePillar(level, random, pos, config);
        placeSidePillar(level, random, pos, config);
        placeSidePillar(level, random, pos, config);
        placeSidePillar(level, random, pos, config);

        // Places Bonus Side Pillars
        if (random.nextInt(1) == 0) {
            placeSidePillar(level, random, pos, config);
        }
        if (random.nextInt(1) == 0) {
            placeSidePillar(level, random, pos, config);
        }
        if (random.nextInt(1) == 0) {
            placeSidePillar(level, random, pos, config);
        }
        if (random.nextInt(1) == 0) {
            placeSidePillar(level, random, pos, config);
        }

         */

        return true;
    }

    public void placeSidePillar(WorldGenLevel level, RandomSource random, BlockPos pos, FerrositePillarConfiguration config) {
        int height = random.nextInt(8) + 4;
        float radius = random.nextInt(3) + 2.5F;

        // Determines a randomized offset pos
        int sidePillarPosXZ = random.nextInt(6) - 3;
        int sidePillarPosY = random.nextInt(12) + 8;

        BlockPos posPillar = new BlockPos(pos.getX() + sidePillarPosXZ, pos.getY() + sidePillarPosY, pos.getZ() + sidePillarPosXZ);

        for (int i = 0; i < height; ++i) {
            BlockPlacementUtil.placeDisk(level, config.block(), new BlockPos(posPillar.getX(), posPillar.getY() + i + (int)radius, posPillar.getZ()), radius, random, true);
        }
        for (int i = (int) ((int) -radius * 2 + radius / 1.5F); i < 0; ++i) {
            BlockPlacementUtil.placeDisk(level, config.block(), new BlockPos(posPillar.getX(), posPillar.getY() + i + (int)radius, posPillar.getZ()), radius + i * 0.5F, random, true);
        }
        BlockPlacementUtil.placeDisk(level, config.block(), new BlockPos(posPillar.getX() + random.nextInt(2) -1, posPillar.getY() + height + (int) radius, posPillar.getZ() + random.nextInt(2) -1), radius * 0.75F, random, true);
    }
}