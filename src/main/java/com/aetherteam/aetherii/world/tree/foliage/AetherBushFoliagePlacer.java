package com.aetherteam.aetherii.world.tree.foliage;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class AetherBushFoliagePlacer extends BlobFoliagePlacer {
    public static final MapCodec<AetherBushFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((i) -> blobParts(i).apply(i, AetherBushFoliagePlacer::new));

    public AetherBushFoliagePlacer(IntProvider radius, IntProvider offset, int height) {
        super(radius, offset, height);
    }

    @Override
    protected void createFoliage(WorldGenLevel level, FoliagePlacer.FoliageSetter foliageSetter, RandomSource random, TreeConfiguration config, int treeHeight, FoliagePlacer.FoliageAttachment foliageAttachment, int foliageHeight, int leafRadius, int offset) {
        this.placeLeavesRow(level, foliageSetter, random, config, foliageAttachment.pos(), 1, offset, foliageAttachment.doubleTrunk());
        this.placeLeavesRow(level, foliageSetter, random, config, foliageAttachment.pos(), 2, offset - 1, foliageAttachment.doubleTrunk());
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int dx, int y, int dz, int currentRadius, boolean doubleTrunk) {
        return Math.sqrt(dx*dx + dz*dz) > currentRadius - random.nextInt(2);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return AetherIIFoliagePlacerTypes.AETHER_BUSH_FOLIAGE_PLACER.get();
    }
}
