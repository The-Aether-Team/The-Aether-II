package com.aetherteam.aetherii.world.density;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.DensityFunction;

public record FindTopSurfaceFunction(DensityFunction density, DensityFunction upperBound, int lowerBound, int cellHeight) implements DensityFunction {
    private static final MapCodec<FindTopSurfaceFunction> DATA_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("density").forGetter(FindTopSurfaceFunction::density),
            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("upper_bound").forGetter(FindTopSurfaceFunction::upperBound),
            Codec.intRange(DimensionType.MIN_Y * 2, DimensionType.MAX_Y * 2).fieldOf("lower_bound").forGetter(FindTopSurfaceFunction::lowerBound),
            ExtraCodecs.POSITIVE_INT.fieldOf("cell_height").forGetter(FindTopSurfaceFunction::cellHeight)
    ).apply(instance, FindTopSurfaceFunction::new));
    public static final KeyDispatchDataCodec<FindTopSurfaceFunction> CODEC = KeyDispatchDataCodec.of(DATA_CODEC);

    @Override
    public double compute(DensityFunction.FunctionContext context) {
        int topY = Mth.floor(this.upperBound.compute(context) / this.cellHeight) * this.cellHeight;
        if (topY <= this.lowerBound) {
            return this.lowerBound;
        }
        for (int blockY = topY; blockY >= this.lowerBound; blockY -= this.cellHeight) {
            if (this.density.compute(new DensityFunction.SinglePointContext(context.blockX(), blockY, context.blockZ())) > 0.0) {
                return blockY;
            }
        }
        return this.lowerBound;
    }

    @Override
    public void fillArray(double[] output, DensityFunction.ContextProvider contextProvider) {
        contextProvider.fillAllDirectly(output, this);
    }

    @Override
    public DensityFunction mapAll(DensityFunction.Visitor visitor) {
        return visitor.apply(new FindTopSurfaceFunction(this.density.mapAll(visitor), this.upperBound.mapAll(visitor), this.lowerBound, this.cellHeight));
    }

    @Override
    public double minValue() {
        return this.lowerBound;
    }

    @Override
    public double maxValue() {
        return Math.max(this.lowerBound, this.upperBound.maxValue());
    }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC;
    }
}
