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

public class SkyplaneFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<SkyplaneFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> foliagePlacerParts(instance)
            .apply(instance, SkyplaneFoliagePlacer::new));

    public SkyplaneFoliagePlacer(IntProvider radius, IntProvider offset) {
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
        int xOffset = random.nextBoolean() ? 1 : -1;
        int zOffset = random.nextBoolean() ? 1 : -1;

        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y - 8, z), 1, 0, doubleTrunk);
        for (int i = offset; i >= offset - foliageHeight; --i) {
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y - 7, z), 2, i, doubleTrunk);
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y - 5, z), 3, i, doubleTrunk);
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y - 3, z), 3, i, doubleTrunk);
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y - 1, z), 3, i, doubleTrunk);
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y + 1, z), 1, i, doubleTrunk);
        }
        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y + 2, z), 2, 0, doubleTrunk);


        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x + xOffset, y - 5, z + zOffset), 2, 1, doubleTrunk);
        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x + xOffset, y - 5, z + zOffset), 5, 0, doubleTrunk);
        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x + xOffset, y - 5, z + zOffset), 5, -1, doubleTrunk);

        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x + xOffset, y - 1, z - zOffset), 2, 1, doubleTrunk);
        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x + xOffset, y - 1, z - zOffset), 5, 0, doubleTrunk);
        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x + xOffset, y - 1, z - zOffset), 5, -1, doubleTrunk);


        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x - xOffset, y - 6, z - zOffset), 2, 1, doubleTrunk);
        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x - xOffset, y - 6, z - zOffset), 5, 0, doubleTrunk);
        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x - xOffset, y - 6, z - zOffset), 5, -1, doubleTrunk);

        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x - xOffset, y - 2, z + zOffset), 2, 1, doubleTrunk);
        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x - xOffset, y - 2, z + zOffset), 5, 0, doubleTrunk);
        this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x - xOffset, y - 2, z + zOffset), 5, -1, doubleTrunk);
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
        return Mth.square(localX) + Mth.square(localY) + Mth.square(localZ) > range + (localY != 0 && random.nextInt(4) == 0 ? -1 : 0);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return AetherIIFoliagePlacerTypes.SKYPLANE_FOLIAGE_PLACER.get();
    }
}