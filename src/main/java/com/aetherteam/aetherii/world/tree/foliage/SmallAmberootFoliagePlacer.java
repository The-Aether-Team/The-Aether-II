package com.aetherteam.aetherii.world.tree.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class SmallAmberootFoliagePlacer extends FoliagePlacer {
    public static final Codec<SmallAmberootFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> foliagePlacerParts(instance)
            .apply(instance, SmallAmberootFoliagePlacer::new));

    public SmallAmberootFoliagePlacer(IntProvider pRadius, IntProvider pOffset) {
        super(pRadius, pOffset);
    }

    @Override
    protected void createFoliage(LevelSimulatedReader level, FoliageSetter setter, RandomSource rand, TreeConfiguration config, int maxHeight, FoliageAttachment attachment, int height, int radius, int offset) {
        boolean rotate = rand.nextBoolean();
        BlockPos origin = attachment.pos();
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        // Create main piece, radius should be 2 by default
        for (int x = -radius; x <= radius; x++) {
            for (int y = -3; y <= 0; y++) {
                for (int z = -radius; z <= radius; z++) {
                    int absX = Mth.abs(x);
                    int absZ = Mth.abs(z);
                    if (y == -3 || y == 0) {
                        // Bottom and top layers should be 3x3 squares, occasionally with pieces sticking out to potentially make a diamond shape (though it should be unlikely)
                        boolean square = absX <= 1 && absZ <= 1;
                        boolean diamond = absX + absZ <= 2;
                        if (square || (diamond && rand.nextFloat() < 0.3)) {
                            mutable.setWithOffset(origin, x, y, z);
                            tryPlaceLeaf(level, setter, rand, config, mutable);
                        }
                    } else if (y == -2) {
                        // Second layer should be a 5x5 square, with occasionally rounded corners
                        // As this loop does not go further than 2 blocks by default, this will create a rounded square
                        boolean roundedSquare = absX + absZ <= radius + 1;
                        if (roundedSquare || rand.nextFloat() < 0.3) {
                            mutable.setWithOffset(origin, x, y, z);
                            tryPlaceLeaf(level, setter, rand, config, mutable);
                        }
                    } else {
                        // Third layer should be a full square, this can be an else statement as there are no other possible y values in this for loop
                        mutable.setWithOffset(origin, x, y, z);
                        tryPlaceLeaf(level, setter, rand, config, mutable);
                    }
                }
            }
        }

    }

    @Override
    public int foliageHeight(RandomSource rand, int pHeight, TreeConfiguration config) {
        return 0;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource rand, int x, int y, int z, int radius, boolean large) {
        return false;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return AetherIIFoliagePlacerTypes.SMALL_AMBEROOT_FOLIAGE_PLACER.get();
    }
}
