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

public class WisprootFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<WisprootFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> foliagePlacerParts(instance)
            .apply(instance, WisprootFoliagePlacer::new));

    public WisprootFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected void createFoliage(LevelSimulatedReader level, FoliageSetter foliageSetter, RandomSource random, TreeConfiguration config, int maxFreeTreeHeight, FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset) {
        BlockPos pos = attachment.pos();
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        boolean doubleTrunk = attachment.doubleTrunk();
        boolean flatTop = random.nextBoolean();

        for (int i = offset; i >= offset - foliageHeight; --i) {
            this.placeLeavesRow(level, foliageSetter, random, config, pos, 14, i, doubleTrunk);
            if (flatTop) {
                this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x + 1 - random.nextInt(3), y + 1, z + 1 - random.nextInt(3)), 5, i, doubleTrunk);
            } else {
                this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x, y + 2, z), 5, i, doubleTrunk);
            }

            if (random.nextInt(2) == 0) {
                this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x + random.nextIntBetweenInclusive(-1, 1), y - random.nextIntBetweenInclusive(4, 6) + random.nextIntBetweenInclusive(-1, 1), z), 2, i, doubleTrunk);
            }
            if (random.nextInt(2) == 0) {
                this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x + random.nextIntBetweenInclusive(-1, 1), y - random.nextIntBetweenInclusive(5, 7) + random.nextIntBetweenInclusive(-1, 1), z), 2, i, doubleTrunk);
            }
            if (random.nextInt(2) == 0) {
                this.placeLeavesRow(level, foliageSetter, random, config, new BlockPos(x + random.nextIntBetweenInclusive(-1, 1), y - random.nextIntBetweenInclusive(6, 8) + random.nextIntBetweenInclusive(-1, 1), z), 2, i, doubleTrunk);
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

    @Override
    protected FoliagePlacerType<?> type() {
        return AetherIIFoliagePlacerTypes.WISPROOT_FOLIAGE_PLACER.get();
    }
}