package com.aetherteam.aetherii.world.tree.foliage.skyroot;

import com.aetherteam.aetherii.world.tree.foliage.AetherIIFoliagePlacerTypes;
import com.mojang.serialization.MapCodec;
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

public class SkybirchFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<SkybirchFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> foliagePlacerParts(instance)
            .apply(instance, SkybirchFoliagePlacer::new));

    public SkybirchFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    /**
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

        int branchHeightX = random.nextInt(2) - 4;
        int branchHeightNX = random.nextInt(2) - 4;
        int branchHeightZ = random.nextInt(2) - 4;
        int branchHeightNZ = random.nextInt(2) - 4;

        for (int i = offset; i >= offset - foliageHeight; --i) {
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y - 4, z), 1, i, doubleTrunk);
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y - 2, z), 2, i, doubleTrunk);
            this.placeLeavesRow(level, foliageSetter, random, config, pos, 2, i, doubleTrunk);
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y + 2, z), 1, i, doubleTrunk);
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y + 4, z), 0, i, doubleTrunk);

            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x + 2, y + branchHeightX, z), 2, i, doubleTrunk);
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x + 3, y + 1 + branchHeightX, z), 1, i, doubleTrunk);
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x + 4, y + 3 + branchHeightX, z), 0, i, doubleTrunk);

            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x - 2, y + branchHeightNX, z), 2, i, doubleTrunk);
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x - 3, y + 1 + branchHeightNX, z), 1, i, doubleTrunk);
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x - 4, y + 3 + branchHeightNX, z), 0, i, doubleTrunk);

            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y + branchHeightZ, z + 2), 2, i, doubleTrunk);
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y + 1 + branchHeightZ, z + 3), 1, i, doubleTrunk);
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y + 3 + branchHeightZ, z + 4), 0, i, doubleTrunk);

            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y + branchHeightNZ, z - 2), 2, i, doubleTrunk);
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y + 1 + branchHeightNZ, z - 3), 1, i, doubleTrunk);
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y + 3 + branchHeightNZ, z - 4), 0, i, doubleTrunk);
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
        return Mth.square(localX) + Mth.square(localY + 2) + Mth.square(localZ) > range + random.nextInt(2);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return AetherIIFoliagePlacerTypes.SKYBIRCH_FOLIAGE_PLACER.get();
    }
}