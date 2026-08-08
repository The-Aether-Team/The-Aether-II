package com.aetherteam.aetherii.world.tree.foliage;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
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
        Direction randomDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        Direction oppositeDirection = randomDirection.getOpposite();
        Direction adjacentDirection = random.nextBoolean() ? randomDirection.getClockWise() : randomDirection.getCounterClockWise();

        FoliagePlacer.tryPlaceLeaf(level, foliageSetter, random, config, foliageAttachment.pos());

        this.placeLeavesRow(level, foliageSetter, random, config, foliageAttachment.pos(), 1, -1, false);

        this.placeLeavesRow(level, foliageSetter, random, config, foliageAttachment.pos().relative(randomDirection), 1, -1, false);
        if (random.nextBoolean()) {
            this.placeLeavesRow(level, foliageSetter, random, config, foliageAttachment.pos().relative(adjacentDirection), 1, -1, false);
            FoliagePlacer.tryPlaceLeaf(level, foliageSetter, random, config, foliageAttachment.pos().relative(randomDirection));
        } else if (random.nextBoolean()) {
            FoliagePlacer.tryPlaceLeaf(level, foliageSetter, random, config, foliageAttachment.pos().relative(randomDirection));
        }

        if (random.nextBoolean()) {
            FoliagePlacer.tryPlaceLeaf(level, foliageSetter, random, config, foliageAttachment.pos().below().relative(oppositeDirection).relative(adjacentDirection));
            if (random.nextBoolean()) {
                FoliagePlacer.tryPlaceLeaf(level, foliageSetter, random, config, foliageAttachment.pos().relative(adjacentDirection));
            }
        }

        if (random.nextBoolean()) {
            FoliagePlacer.tryPlaceLeaf(level, foliageSetter, random, config, foliageAttachment.pos().relative(adjacentDirection.getOpposite()));
        }
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
        return localX == range && localZ == range;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return AetherIIFoliagePlacerTypes.AETHER_BUSH_FOLIAGE_PLACER.get();
    }
}
