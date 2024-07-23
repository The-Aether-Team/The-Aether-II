package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class BrettlPlantFeature extends Feature<NoneFeatureConfiguration> {
    public BrettlPlantFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    public static final IntegerProperty AGE = BlockStateProperties.AGE_25;
    public static final BooleanProperty GROWN = AetherIIBlockStateProperties.BRETTL_GROWN;

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();
        BlockState plant = AetherIIBlocks.BRETTL_PLANT.get().defaultBlockState();
        BlockState tip = AetherIIBlocks.BRETTL_PLANT_TIP.get().defaultBlockState();

        if (plant.canSurvive(level, pos) && level.isEmptyBlock(pos.above()) && level.isEmptyBlock(pos.above(2))) {
            if (random.nextInt(5) != 1) {
                level.setBlock(pos, plant.setValue(GROWN, true), 4);
                level.setBlock(pos.above(), plant.setValue(GROWN, true), 4);
                level.setBlock(pos.above(2), tip.setValue(GROWN, true).setValue(AGE, 2), 4);
            } else if (random.nextBoolean()) {
                level.setBlock(pos, plant, 4);
                level.setBlock(pos.above(), tip.setValue(AGE, 1), 4);
            } else {
                level.setBlock(pos, tip.setValue(AGE, 0), 4);
            }
            return true;
        } else {
            return false;
        }
    }
}