package com.aetherteam.aetherii.world.tree.foliage;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;

public abstract class AbstractBranchedFoliagePlacer extends FoliagePlacer {

    public AbstractBranchedFoliagePlacer(IntProvider pRadius, IntProvider pOffset) {
        super(pRadius, pOffset);
    }

    protected static boolean tryPlaceLog(LevelSimulatedReader level, FoliageSetter foliageSetter, RandomSource random, TreeConfiguration treeConfiguration, BlockPos pos, Direction.Axis axis) {
        if (!TreeFeature.validTreePos(level, pos)) {
            return false;
        } else {
            BlockState state = treeConfiguration.trunkProvider.getState(random, pos);
            if (state.hasProperty(RotatedPillarBlock.AXIS)) {
                state = state.setValue(RotatedPillarBlock.AXIS, axis);
            }
            foliageSetter.set(pos, state);
            return true;
        }
    }
}