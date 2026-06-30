package com.aetherteam.aetherii.world.tree.foliage.amberoot;

import com.aetherteam.aetherii.world.tree.foliage.AbstractBranchedFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.AetherIIFoliagePlacerTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

import java.util.function.BiConsumer;

public class LargeAmberootFoliagePlacer extends AbstractBranchedFoliagePlacer {
    public static final MapCodec<LargeAmberootFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> foliagePlacerParts(instance)
            .apply(instance, LargeAmberootFoliagePlacer::new));

    public LargeAmberootFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    /**
     * Places a sphere of leaves.
     *
     * @param level             The {@link LevelSimulatedReader}.
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
    protected void createFoliage(LevelSimulatedReader level, FoliageSetter foliageSetter, RandomSource random, TreeConfiguration config, int maxFreeTreeHeight, FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset) {
        BlockPos pos = attachment.pos();
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        boolean doubleTrunk = attachment.doubleTrunk();

        if (random.nextBoolean()) {
            for (int i = offset; i >= offset - foliageHeight; --i) {
                placeTreeHalf(level, foliageSetter, random, config, attachment, 1, 0, i, Direction.Axis.X);
                placeTreeHalf(level, foliageSetter, random, config, attachment, -1, 0, i, Direction.Axis.X);
                if (random.nextBoolean()) {
                    this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x + random.nextIntBetweenInclusive(-1, 1), y - random.nextIntBetweenInclusive(2, 5), z + random.nextIntBetweenInclusive(-1, 1)), 1, i, doubleTrunk);
                }
            }
        } else {
            for (int i = offset; i >= offset - foliageHeight; --i) {
                placeTreeHalf(level, foliageSetter, random, config, attachment, 0, 1, i, Direction.Axis.Z);
                placeTreeHalf(level, foliageSetter, random, config, attachment, 0, -1, i, Direction.Axis.Z);
                if (random.nextBoolean()) {
                    this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x + random.nextIntBetweenInclusive(-1, 1), y - random.nextIntBetweenInclusive(2, 5), z + random.nextIntBetweenInclusive(-1, 1)), 1, i, doubleTrunk);
                }
            }
        }
    }

    //Places half of the tree top and changes the offset and rotation using the factor parameters
    public void placeTreeHalf(LevelSimulatedReader level, FoliageSetter foliageSetter, RandomSource random, TreeConfiguration config, FoliageAttachment attachment, int factorX, int factorZ, int i, Direction.Axis axis) {
        BlockPos pos = attachment.pos();
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        boolean doubleTrunk = attachment.doubleTrunk();

        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x + 2 * factorX, y + 1, z + 2 * factorZ), 6, i, doubleTrunk);
        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x + 4 * factorX, y + 2, z + 4 * factorZ), 2, i, doubleTrunk);
        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x + 5 * factorX, y + 3, z + 5 * factorZ), 0, i, doubleTrunk);
        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x + 2 * factorX, y + 4, z + 2 * factorZ), 1, i, doubleTrunk);
        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x + 2 * factorX, y + 6, z + 2 * factorZ), 0, i, doubleTrunk);
        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x + 2 * factorZ, y + 2, z + 2 * factorX), 2, i, doubleTrunk); //Factors are swapped intentionally here

        tryPlaceLog(level, foliageSetter, random, config, new BlockPos(x + factorX, y - 1, z + factorZ), axis);
        tryPlaceLog(level, foliageSetter, random, config, new BlockPos(x + 2 * factorX, y - 1, z + 2 * factorZ), axis);
        tryPlaceLog(level, foliageSetter, random, config, new BlockPos(x + 3 * factorX, y, z + 3 * factorZ), Direction.Axis.Y);
        tryPlaceLog(level, foliageSetter, random, config, new BlockPos(x + 3 * factorX, y + 1, z + 3 * factorZ), Direction.Axis.Y);
    }

    /**
     * Determines the foliage height at a constant value of 7.
     *
     * @param random The {@link RandomSource}.
     * @param height The {@link Integer} for the foliage height.
     * @param config The {@link TreeConfiguration}.
     * @return The {@link Integer} for the foliage height.
     */
    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return 7;
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
        return Mth.square(localX) + Mth.square(localY + 2) + Mth.square(localZ) > range + random.nextInt(3);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return AetherIIFoliagePlacerTypes.LARGE_AMBEROOT_FOLIAGE_PLACER.get();
    }
}