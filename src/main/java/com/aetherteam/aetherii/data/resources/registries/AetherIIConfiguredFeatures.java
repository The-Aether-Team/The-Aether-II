package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.block.natural.AetherLeafLitterBlock;
import com.aetherteam.aetherii.data.resources.registries.holyisles.HolyIslesConfiguredFeatures;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class AetherIIConfiguredFeatures {
    /**
     Separation of Configured Features Datagen into Sub-Classes, this helps with code cleansity,
     especially later on once more Features are added.
     Based on {@link net.minecraft.data.worldgen.features.FeatureUtils}
     */
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolyIslesConfiguredFeatures.bootstrap(context);
    }

    public static WeightedList.Builder<BlockState> segmentedBlockPatchBuilder(Block block, int minState, int maxState, IntegerProperty amountProperty, EnumProperty<Direction> directionProperty) {
        WeightedList.Builder<BlockState> segmentedBlockBuild = WeightedList.builder();
        for (int amount = minState; amount <= maxState; amount++) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                segmentedBlockBuild.add(block.defaultBlockState().setValue(amountProperty, amount).setValue(directionProperty, direction).setValue(AetherLeafLitterBlock.PERSISTENT, true), 1);
            }
        }
        return segmentedBlockBuild;
    }
}