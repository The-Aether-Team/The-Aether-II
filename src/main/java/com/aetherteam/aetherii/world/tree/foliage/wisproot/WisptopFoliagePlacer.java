package com.aetherteam.aetherii.world.tree.foliage.wisproot;

import com.aetherteam.aetherii.world.tree.foliage.AetherIIFoliagePlacerTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class WisptopFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<WisptopFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> foliagePlacerParts(instance)
            .apply(instance, WisptopFoliagePlacer::new));

    public WisptopFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected void createFoliage(LevelSimulatedReader level, FoliageSetter foliageSetter, RandomSource random, TreeConfiguration config, int maxFreeTreeHeight, FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset) {
        BlockPos pos = attachment.pos();
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        boolean doubleTrunk = attachment.doubleTrunk();

        for (int i = offset; i >= offset - foliageHeight; --i) {
            this.placeLeavesRow(level, foliageSetter, random, config, pos, 16, i, doubleTrunk);

            if (random.nextInt(2) == 0) {
                this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x + random.nextIntBetweenInclusive(-1, 1), y - random.nextIntBetweenInclusive(4, 5) + random.nextIntBetweenInclusive(-1, 1), z), 6, i, doubleTrunk);
            }
            if (random.nextInt(2) == 0) {
                this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x + random.nextIntBetweenInclusive(-1, 1), y - random.nextIntBetweenInclusive(5, 7) + random.nextIntBetweenInclusive(-1, 1), z), 6, i, doubleTrunk);
            }
            if (random.nextInt(2) == 0) {
                this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x + random.nextIntBetweenInclusive(-1, 1), y - random.nextIntBetweenInclusive(7, 9) + random.nextIntBetweenInclusive(-1, 1), z), 6, i, doubleTrunk);
            }
            if (random.nextInt(2) == 0) {
                this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x + random.nextIntBetweenInclusive(-1, 1), y - random.nextIntBetweenInclusive(9, 10) + random.nextIntBetweenInclusive(-1, 1), z), 6, i, doubleTrunk);
            }
        }
    }

    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return 7;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
        return Mth.square(localX) + Mth.square(localY - 2) + Mth.square(localZ) > range + random.nextInt(2);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return AetherIIFoliagePlacerTypes.WISPTOP_FOLIAGE_PLACER.get();
    }
}