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
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

import java.util.function.BiConsumer;

public class CrystalrootFoliagePlacer extends AbstractBranchedFoliagePlacer {
    public static final MapCodec<CrystalrootFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> foliagePlacerParts(instance)
            .apply(instance, CrystalrootFoliagePlacer::new));

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

        for (int i = offset; i >= offset - foliageHeight; --i) {


            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y - 5, z), 3, i, doubleTrunk);
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y - 4, z), 4, i, doubleTrunk);
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y - 3, z), 2, i, doubleTrunk);
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y - 1, z), 1, i, doubleTrunk);
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y + 1, z), 1, i, doubleTrunk);
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y + 2, z), 0, i, doubleTrunk);
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y + 3, z), 0, i, doubleTrunk);
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y + 4, z), 0, i, doubleTrunk);

            tryPlaceLog(level, foliageSetter, random, config, new BlockPos(x + 1, y - 3, z), Direction.Axis.Y);
            tryPlaceLog(level, foliageSetter, random, config, new BlockPos(x - 1, y - 3, z), Direction.Axis.Y);
            tryPlaceLog(level, foliageSetter, random, config, new BlockPos(x, y - 3, z + 1), Direction.Axis.Y);
            tryPlaceLog(level, foliageSetter, random, config, new BlockPos(x, y - 3, z - 1), Direction.Axis.Y);

            int yOffset = y - random.nextInt(4) - 2;

            createLeafSpikes(level, foliageSetter, random, config, attachment, foliageHeight, offset, x + (y - 1 > yOffset ? random.nextIntBetweenInclusive(-2, 2) : random.nextIntBetweenInclusive(-1, 1)), yOffset, z + (y - 1 > yOffset ? random.nextIntBetweenInclusive(-2, 2) : random.nextIntBetweenInclusive(-1, 1)));
        }
    }

    protected void createLeafSpikes(WorldGenLevel level, FoliageSetter foliageSetter, RandomSource random, TreeConfiguration config, FoliageAttachment attachment, int foliageHeight, int offset, int x, int y, int z) {
        boolean doubleTrunk = attachment.doubleTrunk();

        for (int i = offset; i >= offset - foliageHeight; --i) {
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y, z), 1, i, doubleTrunk);
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x + additionalOffset(attachment.pos().getX(), x), y, z + additionalOffset(attachment.pos().getZ(), z)), 0, i, doubleTrunk);
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x + additionalOffset(attachment.pos().getX(), x), y + 1, z + additionalOffset(attachment.pos().getZ(), z)), 0, i, doubleTrunk);
            if (random.nextBoolean()) {
                this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x + additionalOffset(attachment.pos().getX(), x), y + 2, z + additionalOffset(attachment.pos().getZ(), z)), 0, i, doubleTrunk);
            }
        }
    }

    public int additionalOffset(int i, int j) {
        return Integer.compare(j - i , 0);
    }

    /**
     * Determines the foliage height at a constant value of 6.
     *
     * @param random The {@link RandomSource}.
     * @param height The {@link Integer} for the foliage height.
     * @param config The {@link TreeConfiguration}.
     * @return The {@link Integer} for the foliage height.
     */
    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return 6;
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
        return Mth.square(localX) + Mth.square(localY) + Mth.square(localZ) > range + random.nextInt(2);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return AetherIIFoliagePlacerTypes.CRYSTALROOT_FOLIAGE_PLACER.get();
    }
}