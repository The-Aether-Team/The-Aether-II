package com.aetherteam.aetherii.util;

import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;

public final class PlacementUtil {
    private PlacementUtil() {
    }

    public static RandomOffsetPlacement ofTriangle(int xzSpread, int ySpread) {
        return RandomOffsetPlacement.of(triangularOffset(xzSpread), triangularOffset(ySpread));
    }

    private static IntProvider triangularOffset(int spread) {
        if (spread <= 0) {
            return ConstantInt.of(0);
        }
        return ClampedNormalInt.of(0.0F, Math.max(1.0F, spread / 2.0F), -spread, spread);
    }
}
