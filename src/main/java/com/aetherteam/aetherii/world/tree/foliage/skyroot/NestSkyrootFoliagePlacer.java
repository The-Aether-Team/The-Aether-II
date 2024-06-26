package com.aetherteam.aetherii.world.tree.foliage.skyroot;

import com.aetherteam.aetherii.api.moaegg.MoaType;
import com.aetherteam.aetherii.data.resources.registries.AetherIIMoaTypes;
import com.aetherteam.aetherii.data.resources.registries.features.AetherIIMiscFeatures;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.ai.brain.MoaAi;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.world.tree.foliage.AbstractBranchedFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.AetherIIFoliagePlacerTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

import java.util.Objects;
import java.util.function.BiConsumer;

public class NestSkyrootFoliagePlacer extends AbstractBranchedFoliagePlacer {
    public static final MapCodec<NestSkyrootFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> foliagePlacerParts(instance)
            .apply(instance, NestSkyrootFoliagePlacer::new));

    public NestSkyrootFoliagePlacer(IntProvider radius, IntProvider offset) {
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
        int offsetX = random.nextInt(1) == 0 ? -1 : 1;
        int offsetZ = random.nextInt(1) == 0 ? -1 : 1;

        if (level instanceof WorldGenLevel worldGenLevel) {
            ChunkGenerator chunk = worldGenLevel.getLevel().getChunkSource().getGenerator();
            ConfiguredFeature<?, ?> nest = Objects.requireNonNull(worldGenLevel.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(AetherIIMiscFeatures.MOA_NEST_TREE).orElse(null)).value();

            for (int i = offset; i >= offset - foliageHeight; --i) {
                this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(pos.getX() + 2 * offsetX, pos.getY() - 7, pos.getZ() + 2 * offsetZ), 13, i, attachment.doubleTrunk());
                this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(pos.getX() + 2 * offsetX, pos.getY() - 6, pos.getZ() + 2 * offsetZ), 16, i, attachment.doubleTrunk());

                this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(pos.getX() + offsetX, pos.getY() - 1, pos.getZ() + offsetZ), 11, i, attachment.doubleTrunk());
                this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(pos.getX() + offsetX, pos.getY(), pos.getZ() + offsetZ), 15, i, attachment.doubleTrunk());

                tryPlaceLog(level, foliageSetter, random, config, new BlockPos(pos.getX() + offsetX, pos.getY() - 1, pos.getZ() + offsetZ), Direction.Axis.Y);

                if (!(random.nextInt(2) == 0)) {
                    this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(pos.getX() + random.nextIntBetweenInclusive(-1, 1), pos.getY() - random.nextIntBetweenInclusive(9, 10) + random.nextIntBetweenInclusive(-1, 1), pos.getZ()), 4, i, attachment.doubleTrunk());
                }
            }
            nest.place(worldGenLevel, chunk, random, new BlockPos(pos.getX() + 2 * offsetX, pos.getY() - 6, pos.getZ() + 2 * offsetZ));

            MoaType moaType = AetherIIMoaTypes.getWeightedChance(worldGenLevel.registryAccess(), random);
            for (int i = 0; i < 2; i++) {
                Moa moa = AetherIIEntityTypes.MOA.get().create(worldGenLevel.getLevel());
                moa.setPos(new BlockPos(new BlockPos(pos.getX() + 2 * offsetX, pos.getY() - 6, pos.getZ() + 2 * offsetZ)).getCenter().add(i, 0, i));
                moa.setMoaTypeByKey(Objects.requireNonNull(AetherIIMoaTypes.getResourceKey(worldGenLevel.registryAccess(), moaType)));
                MoaAi.initMoaHomeMemories(moa, worldGenLevel.getRandom());moa.setBaby(false);
                worldGenLevel.getLevel().addFreshEntity(moa);
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
        return 3;
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
        return AetherIIFoliagePlacerTypes.NEST_SKYROOT_FOLIAGE_PLACER.get();
    }
}