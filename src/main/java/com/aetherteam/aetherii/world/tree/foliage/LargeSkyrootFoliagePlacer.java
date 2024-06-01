package com.aetherteam.aetherii.world.tree.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

import java.util.function.BiConsumer;

public class LargeSkyrootFoliagePlacer extends FoliagePlacer {
    public static final Codec<LargeSkyrootFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> foliagePlacerParts(instance)
            .apply(instance, LargeSkyrootFoliagePlacer::new));

    public LargeSkyrootFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return AetherIIFoliagePlacerTypes.LARGE_SKYROOT_FOLIAGE_PLACER.get();
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
        if (random.nextInt(2) == 0) {
            if (random.nextInt(2) == 0) {
                for (int i = offset; i >= offset - foliageHeight; --i) {
                    this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(attachment.pos().getX() - 1, attachment.pos().getY() - 1, attachment.pos().getZ() - 1), 8, i, attachment.doubleTrunk());
                    this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(attachment.pos().getX() + 1, attachment.pos().getY() + 1, attachment.pos().getZ() + 1), 8, i, attachment.doubleTrunk());
                }
            } else {
                for (int i = offset; i >= offset - foliageHeight; --i) {
                    this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(attachment.pos().getX() - 1, attachment.pos().getY() + 1, attachment.pos().getZ() - 1), 8, i, attachment.doubleTrunk());
                    this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(attachment.pos().getX() + 1, attachment.pos().getY() - 1, attachment.pos().getZ() + 1), 8, i, attachment.doubleTrunk());
                }
            }
        } else {
            if (random.nextInt(2) == 0) {
                for (int i = offset; i >= offset - foliageHeight; --i) {
                    this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(attachment.pos().getX() + 1, attachment.pos().getY() - 1, attachment.pos().getZ() - 1), 8, i, attachment.doubleTrunk());
                    this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(attachment.pos().getX() - 1, attachment.pos().getY() + 1, attachment.pos().getZ() + 1), 8, i, attachment.doubleTrunk());
                }
            } else {
                for (int i = offset; i >= offset - foliageHeight; --i) {
                    this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(attachment.pos().getX() - 1, attachment.pos().getY() + 1, attachment.pos().getZ() + 1), 8, i, attachment.doubleTrunk());
                    this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(attachment.pos().getX() + 1, attachment.pos().getY() - 1, attachment.pos().getZ() - 1), 8, i, attachment.doubleTrunk());
                }
            }
        }
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
}