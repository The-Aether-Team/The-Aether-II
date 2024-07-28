package com.aetherteam.aetherii.world.tree.foliage.amberoot;

import com.aetherteam.aetherii.world.tree.foliage.AetherIIFoliagePlacerTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class AmberootFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<AmberootFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> foliagePlacerParts(instance)
            .apply(instance, AmberootFoliagePlacer::new));

    public AmberootFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected void createFoliage(LevelSimulatedReader level, FoliageSetter foliageSetter, RandomSource random, TreeConfiguration config, int maxHeight, FoliageAttachment attachment, int height, int radius, int offset) {
        Direction.Axis axis = Direction.Plane.HORIZONTAL.getRandomAxis(random);
        Direction.Axis oppositeAxis = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
        BlockPos origin = attachment.pos();

        // Place main piece
        placeLeavesRow(level, foliageSetter, random, config, origin, radius - 1, 0, false);
        placeLeavesRow(level, foliageSetter, random, config, origin, radius, -1, false);
        placeLeavesRow(level, foliageSetter, random, config, origin, radius, -2, false);
        placeLeavesRow(level, foliageSetter, random, config, origin, radius, -3, false);

        // Place corner spikes
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for (int x = 0; x <= 1; x++) {
            for (int z = 0; z <= 1; z++) {
                int realX = x == 0 ? -radius : radius;
                int realZ = z == 0 ? -radius : radius;
                mutablePos.setWithOffset(origin, realX, 0, realZ);
                placeLeavesRow(level, foliageSetter, random, config, mutablePos, 0, 0, random.nextBoolean());
            }
        }

        // Direction-dependent stuff
        boolean addConnector = false;
        BlockPos above = origin.above();
        for (Direction.AxisDirection ad : Direction.AxisDirection.values()) {
            // Construct both of these at the same time, why do two for loops when you can do it in one?
            Direction currentSpikeDir = Direction.fromAxisAndDirection(axis, ad);
            Direction currentOppositeDir = Direction.fromAxisAndDirection(oppositeAxis, ad);
   
            // Place top spike
            mutablePos.setWithOffset(above, currentSpikeDir);
            placeLeavesRow(level, foliageSetter, random, config, mutablePos, 0, 0, false);
            BlockPos spike1Loc = mutablePos.immutable();
            mutablePos.setWithOffset(spike1Loc, 0, 1, 0);
            placeLeavesRow(level, foliageSetter, random, config, mutablePos, 0, 0, false);
            mutablePos.setWithOffset(spike1Loc, 0, 2, 0);
            boolean skip = random.nextBoolean();
            placeLeavesRow(level, foliageSetter, random, config, mutablePos, 0, 0, skip);
            if (!skip) {
                addConnector = true;
            }

            // Place the side connector piece
            // Create an 'opposite offset' value and a 'y offset' value. This part of the method will create a plus shape on the side of the tree.
            Direction oppAxisStep = Direction.fromAxisAndDirection(oppositeAxis, Direction.AxisDirection.POSITIVE);
            BlockPos sideLoc = origin.relative(currentSpikeDir, radius + 1);
            for (int oppOffs = -1; oppOffs <= 1; oppOffs++) {
                for (int yOffs = -1; yOffs <= 1; yOffs++) {
                    int oppAbs = Mth.abs(oppOffs);
                    int yAbs = Mth.abs(yOffs);
                    if (oppAbs + yAbs < 2) {
                        Vec3i offs =  new Vec3i(oppAxisStep.getStepX() * oppOffs, yOffs - 1, oppAxisStep.getStepZ() * oppOffs);
                        mutablePos.setWithOffset(sideLoc, offs);
                        placeLeavesRow(level, foliageSetter, random, config, mutablePos, 0, 0, false);
                    }
                }
            }
            // Place top spike connector
            placeLeavesRow(level, foliageSetter, random, config, above, 0, 0, addConnector);

            // Place the side spike
            BlockPos sideSpikeLoc = sideLoc.relative(currentSpikeDir, 1);
            for (int y = 0; y <= 1; y++) {
                mutablePos.setWithOffset(sideSpikeLoc, 0, y, 0);
                placeLeavesRow(level, foliageSetter, random, config, mutablePos, 0, 0, y == 1 && random.nextBoolean());
            }

            // Place the front/back spikes
            BlockPos frontLoc = origin.relative(currentOppositeDir, radius + 1);
            for (int y = -1; y <= 0; y++) {
                mutablePos.setWithOffset(frontLoc, 0, y, 0);
                placeLeavesRow(level, foliageSetter, random, config, mutablePos, 0, 0, false);
            }
        }
    }

    // Override vanilla behavior of using the 'large' boolean value to actually affect the size, this is unwanted behavior in this case
    protected void placeLeavesRow(LevelSimulatedReader level, FoliageSetter setter, RandomSource random, TreeConfiguration config, BlockPos pos, int radius, int y, boolean large) {
        // Also avoid creating a new mutable block pos if the radius is 0 anyway
        if (radius <= 0) {
            if (!this.shouldSkipLocationSigned(random, 0, y, 0, radius, large)) {
                tryPlaceLeaf(level, setter, random, config, pos.above(y));
                return;
            }
        }
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for(int j = -radius; j <= radius; ++j) {
            for(int k = -radius; k <= radius; ++k) {
                if (!this.shouldSkipLocationSigned(random, j, y, k, radius, large)) {
                    mutablePos.setWithOffset(pos, j, y, k);
                    tryPlaceLeaf(level, setter, random, config, mutablePos);
                }
            }
        }
    }

    @Override
    public int foliageHeight(RandomSource rand, int pHeight, TreeConfiguration config) {
        return 0;
    }


    @Override
    protected boolean shouldSkipLocation(RandomSource rand, int x, int y, int z, int radius, boolean remove) {
        // when the radius is zero (placing a single block), the remove parameter, usually called large, is used to determine whether to remove the block
        if (radius == 0) {
            return remove;
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
        return AetherIIFoliagePlacerTypes.AMBEROOT_FOLIAGE_PLACER.get();
    }
}