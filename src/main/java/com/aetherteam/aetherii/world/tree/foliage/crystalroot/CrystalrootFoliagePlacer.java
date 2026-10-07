package com.aetherteam.aetherii.world.tree.foliage.crystalroot;

import com.aetherteam.aetherii.world.tree.foliage.AbstractBranchedFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.AetherIIFoliagePlacerTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class CrystalrootFoliagePlacer extends AbstractBranchedFoliagePlacer {
    public static final MapCodec<CrystalrootFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> foliagePlacerParts(instance).apply(instance, CrystalrootFoliagePlacer::new));

    public CrystalrootFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    /**
     * Places a sphere of leaves.
     *
     * @param level             The {@link WorldGenLevel}.
     * @param foliageSetter     The {@link BiConsumer} of a {@link BlockPos} and {@link BlockState} used for block placement.
     * @param random            The {@link RandomSource}.
     * @param config            The {@link TreeConfiguration}.
     * @param maxFreeTreeHeight The {@link Integer} for the maximum tree height.
     * @param attachment        A {@link FoliageAttachment} to add foliage to.
     * @param foliageHeight     The {@link Integer} for the foliage height.
     * @param foliageRadius     The {@link Integer} for the foliage radius.
     * @param offset            The {@link Integer} for the foliage offset.
     */
    @Override
    protected void createFoliage(WorldGenLevel level, FoliageSetter foliageSetter, RandomSource random, TreeConfiguration config, int maxFreeTreeHeight, FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset) {
        BlockPos pos = attachment.pos();
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        boolean doubleTrunk = attachment.doubleTrunk();

        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y, z), 0, 1, doubleTrunk);
        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y, z), 0, 0, doubleTrunk);
        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y, z), 1, -1, doubleTrunk);
        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y, z), 1, -2, doubleTrunk);
        int randomOffset = random.nextInt(2);
        this.placeLeavesSquare(level, foliageSetter, random, config, new BlockPos(x, y, z), 1, -2 - randomOffset, doubleTrunk);
        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y, z), 1, -3 - randomOffset, doubleTrunk);
        this.placeLeavesSquare(level, foliageSetter, random, config, new BlockPos(x, y, z), 1, -4 - randomOffset, doubleTrunk);
        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y, z), 1, -5 - randomOffset, doubleTrunk);
        this.placeLeavesSquare(level, foliageSetter, random, config, new BlockPos(x, y, z), 1, -6 - randomOffset, doubleTrunk);
        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y, z), 2, -7 - randomOffset, doubleTrunk);
        this.placeLeavesSquare(level, foliageSetter, random, config, new BlockPos(x, y, z), 1, -8 - randomOffset, doubleTrunk);
        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y, z), 3, -9 - randomOffset, doubleTrunk);
        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y, z), 4, -10 - randomOffset, doubleTrunk);
        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y, z), 2, -11 - randomOffset, doubleTrunk);

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y, z).relative(direction, 4), 1, -10 - randomOffset, doubleTrunk);
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y, z).relative(direction, 3), 0, -11 - randomOffset, doubleTrunk);
            tryPlaceLog(level, foliageSetter, random, config, new BlockPos(x, y, z).relative(direction).offset(0, -11 - randomOffset, 0), Direction.Axis.Y);
        }

        Map<Direction, Integer> spikes = new HashMap<>();

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (random.nextBoolean()) {
                int spikeOffset = (random.nextInt(2) * 2);

                BlockPos basePos = new BlockPos(x, y, z).relative(direction, 2);
                this.placeLeavesSquare(level, foliageSetter, random, config, basePos, 1, -9 - randomOffset + spikeOffset, doubleTrunk);
                this.placeLeavesRow(level, foliageSetter, random, config, basePos.relative(direction, 1), 1, -8 - randomOffset + spikeOffset, doubleTrunk);
                this.placeLeavesRow(level, foliageSetter, random, config, basePos, 0, -7 - randomOffset + spikeOffset, doubleTrunk);
                this.placeLeavesRow(level, foliageSetter, random, config, basePos.relative(direction, 1), 0, -7 - randomOffset + spikeOffset, doubleTrunk);
                this.placeLeavesRow(level, foliageSetter, random, config, basePos.relative(direction, 1), 0, -6 - randomOffset + spikeOffset, doubleTrunk);
                this.placeLeavesRow(level, foliageSetter, random, config, basePos.relative(direction, 1), 0, -5 - randomOffset + spikeOffset, doubleTrunk);

                spikes.put(direction, spikeOffset);
            }
        }
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (!spikes.containsKey(direction) || spikes.get(direction) > 0) {
                this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y, z).relative(direction, 3), 1, -8 - randomOffset, doubleTrunk);
                this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y, z).relative(direction, 2), 1, -8 - randomOffset, doubleTrunk);
                if (!spikes.containsKey(direction)) {
                    this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y, z).relative(direction, 2), 1, -6 - randomOffset, doubleTrunk);
                    this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y, z).relative(direction, 1), 1, -6 - randomOffset, doubleTrunk);
                    this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y, z).relative(direction, 1), 1, -4 - randomOffset, doubleTrunk);
                }
            }
        }
    }

    protected void placeLeavesSquare(WorldGenLevel level, FoliageSetter foliageSetter, RandomSource random, TreeConfiguration config, BlockPos origin, int currentRadius, int y, boolean doubleTrunk) {
        int offset = doubleTrunk ? 1 : 0;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int dx = -currentRadius; dx <= currentRadius + offset; ++dx) {
            for (int dz = -currentRadius; dz <= currentRadius + offset; ++dz) {
                pos.setWithOffset(origin, dx, y, dz);
                tryPlaceLeaf(level, foliageSetter, random, config, pos);
            }
        }
    }

    /**
     * Determines the foliage height at a constant value of 10.
     *
     * @param random The {@link RandomSource}.
     * @param height The {@link Integer} for the foliage height.
     * @param config The {@link TreeConfiguration}.
     * @return The {@link Integer} for the foliage height.
     */
    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return 10;
    }

    /**
     * Skips placing a foliage block at a spherical edge location and with some randomness.
     *
     * @param random The {@link RandomSource}.
     * @param localX The local {@link Integer} x-position.
     * @param localY The local {@link Integer} y-position.
     * @param localZ The local {@link Integer} z-position.
     * @param range  The {@link Integer} for the placement range.
     * @param large  The {@link Boolean} for whether the tree is large.
     * @return Whether the location should be skipped, as a {@link Boolean}.
     */

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
        return Math.abs(localX) + Mth.abs(localZ) > range;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return AetherIIFoliagePlacerTypes.CRYSTALROOT_FOLIAGE_PLACER.get();
    }
}