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

    // Used for Greatroot Trees
    protected void placeCornerLogs(LevelSimulatedReader level, FoliageSetter foliageSetter, RandomSource random, TreeConfiguration config, BlockPos pos, int verticalOffset, int horizontalOffset) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();



        tryPlaceLog(level, foliageSetter, random, config, new BlockPos(x + random.nextInt(2), y - 1 + verticalOffset, z - 1 - horizontalOffset), Direction.Axis.Y);
        tryPlaceLog(level, foliageSetter, random, config, new BlockPos(x + 2 + horizontalOffset, y - 1 + verticalOffset, z + random.nextInt(2)), Direction.Axis.Y);

        tryPlaceLog(level, foliageSetter, random, config, new BlockPos(x + 1 - random.nextInt(2), y - 1 + verticalOffset, z + 2 + horizontalOffset), Direction.Axis.Y);
        tryPlaceLog(level, foliageSetter, random, config, new BlockPos(x - 1 - horizontalOffset, y - 1 + verticalOffset, z + 1 - random.nextInt(2)), Direction.Axis.Y);
    }
}