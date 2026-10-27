package com.aetherteam.aetherii.world.feature.modifier;

import com.aetherteam.aetherii.AetherII;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.placement.RepeatingPlacement;

public class ElevationBasedCountPlacement extends RepeatingPlacement {
    public static final MapCodec<ElevationBasedCountPlacement> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.INT.fieldOf("start_height").forGetter(c -> c.startHeight),
            Codec.INT.fieldOf("end_height").forGetter(c -> c.endHeight),
            Codec.INT.fieldOf("start_count").forGetter(c -> c.startCount),
            Codec.INT.fieldOf("end_count").forGetter(c -> c.endCount)
    ).apply(i, ElevationBasedCountPlacement::new));
    private final int startHeight;
    private final int endHeight;
    private final int startCount;
    private final int endCount;

    public ElevationBasedCountPlacement(int startHeight, int endHeight, int startCount, int endCount) {
        this.startHeight = startHeight;
        this.endHeight = endHeight;
        this.startCount = startCount;
        this.endCount = endCount;
    }

    @Override
    protected int count(RandomSource random, BlockPos origin) {
        int y = origin.getY();
        if (y >= this.startHeight && y <= this.endHeight) {
            int heightDifference = this.endHeight - this.startHeight;
            int relativeHeightDifference = y - this.startHeight;
            float ratio = (float) relativeHeightDifference / heightDifference;
            float scaledCount;
            if (this.endCount > this.startCount) {
                scaledCount = this.endCount * ratio;
            } else {
                scaledCount = this.startCount - (this.startCount * ratio);
            }
            return Math.round(scaledCount);
        } else if (y < this.startHeight) {
            return this.startCount;
        } else {
            return this.endCount;
        }
    }

    @Override
    public PlacementModifierType<?> type() {
        return AetherIIPlacementModifierTypes.ELEVATION_BASED_COUNT_PLACEMENT.get();
    }
}
