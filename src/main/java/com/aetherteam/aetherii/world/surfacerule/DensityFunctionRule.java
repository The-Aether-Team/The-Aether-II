package com.aetherteam.aetherii.world.surfacerule;

import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.SurfaceRules;

import java.util.List;

public record DensityFunctionRule(List<DensityTest> options, DensityFunction function) implements SurfaceRules.RuleSource {
	public static final KeyDispatchDataCodec<DensityFunctionRule> KEY_CODEC = KeyDispatchDataCodec.of(RecordCodecBuilder.mapCodec(inst -> inst.group(
			DensityTest.CODEC.listOf().fieldOf("options").forGetter(DensityFunctionRule::options),
			DensityFunction.HOLDER_HELPER_CODEC.fieldOf("function").forGetter(DensityFunctionRule::function)
	).apply(inst, DensityFunctionRule::new)));

	@Override
	public SurfaceRules.SurfaceRule apply(SurfaceRules.Context context) {
		DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(1234L);
		this.function().mapAll(visitor);
		return this::tryApply;
	}

	public BlockState tryApply(int x, int y, int z) {
		double noiseValue = this.function().compute(new DensityFunction.SinglePointContext(x, y, z));
		for (DensityTest option : this.options()) {
			BlockState block = option.test(noiseValue);
			if (block != null) {
				return block;
			}
		}
		return null;
	}

	@Override
	public KeyDispatchDataCodec<? extends SurfaceRules.RuleSource> codec() {
		return KEY_CODEC;
	}

	public record DensityTest(BlockState block, double min, double max) {
		public static final Codec<DensityTest> CODEC = RecordCodecBuilder.create(inst -> inst.group(
				BlockState.CODEC.fieldOf("block").forGetter(DensityTest::block),
				Codec.DOUBLE.fieldOf("min").forGetter(DensityTest::min),
				Codec.DOUBLE.fieldOf("max").forGetter(DensityTest::max)
		).apply(inst, DensityTest::new));

		public BlockState test(double value) {
			if (value > this.min() && value < this.max()) {
				return this.block();
			}
			return null;
		}
	}
}
