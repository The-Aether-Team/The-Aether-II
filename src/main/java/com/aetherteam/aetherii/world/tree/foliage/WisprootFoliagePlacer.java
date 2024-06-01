package com.aetherteam.aetherii.world.tree.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class WisprootFoliagePlacer extends DarkOakFoliagePlacer {
    public static final Codec<WisprootFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> foliagePlacerParts(instance)
            .apply(instance, WisprootFoliagePlacer::new));

    public WisprootFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return AetherIIFoliagePlacerTypes.WISPROOT_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage(LevelSimulatedReader level, FoliageSetter foliageSetter, RandomSource random, TreeConfiguration config, int maxFreeTreeHeight, FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset) {
        for (int i = offset; i >= offset - foliageHeight; --i) {
            this.placeLeavesRow(level, foliageSetter, random, config, attachment.pos(), 14, i, attachment.doubleTrunk());
            this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(attachment.pos().getX(), attachment.pos().getY() + 2, attachment.pos().getZ()), 5, i, attachment.doubleTrunk());

            if (random.nextInt(2) == 0) {
                this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(attachment.pos().getX() + random.nextIntBetweenInclusive(-1, 1), attachment.pos().getY() - random.nextIntBetweenInclusive(4, 5) + random.nextIntBetweenInclusive(-1, 1), attachment.pos().getZ()), 2, i, attachment.doubleTrunk());
            }
            if (random.nextInt(2) == 0) {
                this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(attachment.pos().getX() + random.nextIntBetweenInclusive(-1, 1), attachment.pos().getY() - random.nextIntBetweenInclusive(5, 7) + random.nextIntBetweenInclusive(-1, 1), attachment.pos().getZ()), 2, i, attachment.doubleTrunk());
            }
            if (random.nextInt(2) == 0) {
                this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(attachment.pos().getX() + random.nextIntBetweenInclusive(-1, 1), attachment.pos().getY() - random.nextIntBetweenInclusive(7, 8) + random.nextIntBetweenInclusive(-1, 1), attachment.pos().getZ()), 2, i, attachment.doubleTrunk());
            }
        }
    }

    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return 7;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
        return Mth.square(localX) + Mth.square(localY - 1) + Mth.square(localZ) > range + random.nextInt(2);
    }
}