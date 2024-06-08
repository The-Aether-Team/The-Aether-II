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

public class SingularAmberootFoliagePlacer extends FoliagePlacer {
    public static final Codec<SingularAmberootFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> foliagePlacerParts(instance)
            .apply(instance, SingularAmberootFoliagePlacer::new));

    public SingularAmberootFoliagePlacer(IntProvider radius, IntProvider offset) {
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
    @Override  //TODO: Code Clean-Up
    protected void createFoliage(LevelSimulatedReader level, FoliageSetter foliageSetter, RandomSource random, TreeConfiguration config, int maxFreeTreeHeight, FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset) {
        BlockPos pos = attachment.pos();
        for (int i = offset; i >= offset - foliageHeight; --i) {
            this.placeLeavesRow(level, foliageSetter, random, config, attachment.pos(), 6, i, attachment.doubleTrunk());
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(pos.getX(), pos.getY() + 2, pos.getZ()), 3, i, attachment.doubleTrunk());
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(pos.getX(), pos.getY() + 3, pos.getZ()), 1, i, attachment.doubleTrunk());
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(pos.getX(), pos.getY() + 5, pos.getZ()), 0, i, attachment.doubleTrunk());
            if (random.nextInt(2) == 0) {
                this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(pos.getX() + random.nextIntBetweenInclusive(-1, 1), pos.getY() - random.nextIntBetweenInclusive(2, 4), pos.getZ() + random.nextIntBetweenInclusive(-1, 1)), 1, i, attachment.doubleTrunk());
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

    @Override
    protected FoliagePlacerType<?> type() {
        return AetherIIFoliagePlacerTypes.SINGULAR_AMBEROOT_FOLIAGE_PLACER.get();
    }
}