package com.aetherteam.aetherii.world.tree.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class SmallAmberootFoliagePlacer extends FoliagePlacer {
    public static final Codec<SmallAmberootFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> foliagePlacerParts(instance)
            .apply(instance, SmallAmberootFoliagePlacer::new));

    public SmallAmberootFoliagePlacer(IntProvider pRadius, IntProvider pOffset) {
        super(pRadius, pOffset);
    }

    @Override
    protected void createFoliage(LevelSimulatedReader level, FoliageSetter setter, RandomSource rand, TreeConfiguration config, int maxHeight, FoliageAttachment attachment, int height, int radius, int offset) {
        Direction.Axis axis = Direction.Plane.HORIZONTAL.getRandomAxis(rand);
        BlockPos origin = attachment.pos();

        placeLeavesRow(level, setter, rand, config, origin, radius - 1, 0, false);
        placeLeavesRow(level, setter, rand, config, origin, radius, -1, false);
        placeLeavesRow(level, setter, rand, config, origin, radius, -2, false);
        placeLeavesRow(level, setter, rand, config, origin, radius, -3, false);
    }

    protected void placeLeavesRow(LevelSimulatedReader level, FoliagePlacer.FoliageSetter setter, RandomSource rand, TreeConfiguration config, BlockPos pos, int radius, int y, boolean large) {
        // Override vanilla behavior of using the 'large' boolean value to actually affect the size, this is unwanted behavior in this case
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        for(int j = -radius; j <= radius; ++j) {
            for(int k = -radius; k <= radius; ++k) {
                if (!this.shouldSkipLocationSigned(rand, j, y, k, radius, large)) {
                    blockpos$mutableblockpos.setWithOffset(pos, j, y, k);
                    tryPlaceLeaf(level, setter, rand, config, blockpos$mutableblockpos);
                }
            }
        }
    }

    @Override
    public int foliageHeight(RandomSource rand, int pHeight, TreeConfiguration config) {
        return 0;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource rand, int x, int y, int z, int radius, boolean large) {
        // when the radius is zero (placing a single block), the large value is used to determine if this should be always placed, or should be sometimes placed
        if (radius == 0) {
            return large && rand.nextFloat() >= 0.3;
        } else {
            if (y == 0) {
                // If the y offset is 0, only skip the location if it is on the corners, AND a boolean check succeeds
                return x + z >= radius * 2 && rand.nextBoolean();
            } else if (y == -1) {
                // If the y offset is -1, do not skip the location
                return false;
            } else if (y == -2) {
                // If the y offset is -2, skip the location if it is on the corners
                return x + z >= radius * 2;
            } else {
                boolean diamond = x + z <= radius;
                boolean square = x < radius && z < radius;
                return !diamond || (!square && rand.nextBoolean());
            }

        }
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return AetherIIFoliagePlacerTypes.SMALL_AMBEROOT_FOLIAGE_PLACER.get();
    }
}
