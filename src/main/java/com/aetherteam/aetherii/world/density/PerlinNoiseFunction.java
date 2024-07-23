package com.aetherteam.aetherii.world.density;

import com.aetherteam.aetherii.mixin.mixins.common.accessor.PerlinNoiseAccessor;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class PerlinNoiseFunction implements DensityFunction {

    public static final KeyDispatchDataCodec<PerlinNoiseFunction> CODEC = KeyDispatchDataCodec.of(RecordCodecBuilder.mapCodec(
            p_208798_ -> p_208798_.group(
                            NormalNoise.NoiseParameters.DIRECT_CODEC.fieldOf("noise").forGetter((func) -> func.params),
                            Codec.DOUBLE.fieldOf("xz_scale").forGetter((func) -> func.xzScale),
                            Codec.DOUBLE.fieldOf("y_scale").forGetter((func) -> func.yScale),
                            Codec.LONG.fieldOf("seed").forGetter((func) -> func.seed)
                    )
                    .apply(p_208798_, PerlinNoiseFunction::new)));

    @Nullable
    public PerlinNoise noise = null;
    private static final Map<Long, PerlinNoiseVisitor> VISITORS = new HashMap<>();
    // This is used before the seed is initialized, for methods such as DensityFunction#maxValue
    private final PerlinNoise fakeNoise;
    public final NormalNoise.NoiseParameters params;
    private final long seed;
    private final double xzScale;
    private final double yScale;

    public PerlinNoiseFunction(NormalNoise.NoiseParameters params, double xzScale, double yScale, long seed) {
        this.seed = seed;
        this.params = params;
        this.xzScale = xzScale;
        this.yScale = yScale;
        this.fakeNoise = PerlinNoise.create(new XoroshiroRandomSource(seed), params.firstOctave(), params.amplitudes());
    }

    public double compute(FunctionContext context) {
        if (this.noise == null) {
            throw new NullPointerException("PerlinNoiseFunction has not been initialized yet! Please initialize by running mapAll on this function or a parent function with a PerlinNoiseVisitor!");
        } else {
            return this.noise
                    .getValue((double)context.blockX() * this.xzScale, (double)context.blockY() * this.yScale, (double)context.blockZ() * this.xzScale);
        }
    }

    @Override
    public void fillArray(double[] array, ContextProvider contextProvider) {
        contextProvider.fillAllDirectly(array, this);
    }

    @Override
    public DensityFunction mapAll(Visitor visitor) {
        return visitor.apply(this);
    }


    @Override
    public double minValue() {
        return -this.maxValue();
    }

    @Override
    public double maxValue() {
        if (this.noise != null) {
            return ((PerlinNoiseAccessor)this.noise).callMaxValue();
        } else {
            return ((PerlinNoiseAccessor)this.fakeNoise).callMaxValue();
        }
    }

    public PerlinNoiseFunction initialize(Function<Long, RandomSource> rand) {
        this.noise = PerlinNoise.create(rand.apply(this.seed), this.params.firstOctave(), this.params.amplitudes());
        return this;
    }

    public static PerlinNoiseVisitor createOrGetVisitor(long seed) {
        return VISITORS.computeIfAbsent(seed, l -> new PerlinNoiseVisitor(noise -> {
            if (noise.initialized()) {
                return noise;
            } else {
                return noise.initialize(offset -> new XoroshiroRandomSource(l + offset));
            }
        }));
    }

    public boolean initialized() {
        return this.noise != null;
    }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC;
    }

    public record PerlinNoiseVisitor(UnaryOperator<PerlinNoiseFunction> operator) implements DensityFunction.Visitor {
        @Override
        public DensityFunction apply(DensityFunction function) {
            if (function instanceof PerlinNoiseFunction pnf) {
                return operator.apply(pnf);
            }
            return function;
        }
    }
}
