package com.aetherteam.aetherii.world.surfacerule;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;

import java.util.ArrayList;
import java.util.List;

public record NoisePalette3DPlacementRule(BlockState spot, BlockState backing, int spotRatio, int backingRatio, double noiseFreq) implements SurfaceRules.RuleSource {
	public static final KeyDispatchDataCodec<NoisePalette3DPlacementRule> KEY_CODEC = KeyDispatchDataCodec.of(RecordCodecBuilder.mapCodec(inst -> inst.group(
			BlockState.CODEC.fieldOf("spot_blockstate").forGetter(NoisePalette3DPlacementRule::spot),
			BlockState.CODEC.fieldOf("backing_blockstate").forGetter(NoisePalette3DPlacementRule::backing),
			Codec.INT.fieldOf("spot_ratio").forGetter(NoisePalette3DPlacementRule::spotRatio),
			Codec.INT.fieldOf("backing_ratio").forGetter(NoisePalette3DPlacementRule::backingRatio),
			Codec.DOUBLE.fieldOf("noise_frequency").forGetter(NoisePalette3DPlacementRule::noiseFreq)
	).apply(inst, NoisePalette3DPlacementRule::new)));

	@Override
	public SurfaceRules.SurfaceRule apply(SurfaceRules.Context context) {
		return (x, y, z) -> {
			List<BlockState> blockStates = new ArrayList<>();
            for (int i = 0; i < this.spotRatio; i++) {
				blockStates.add(this.spot);
			}
			for (int i = 0; i < this.backingRatio; i++) {
				blockStates.add(this.backing);
			}
			ImprovedNoise noise = new ImprovedNoise(new XoroshiroRandomSource(0)); //todo replace with future ResourceKey<NormalNoise.NoiseParameters> noise parameter usage and context.randomState.getOrCreateNoise(this.noise);
			double noiseValue = noise.noise(x * this.noiseFreq, y * this.noiseFreq, z * this.noiseFreq);
			float normalizedNoise = (float) noiseValue * 0.5F + 0.5F;
			double clamped = Mth.clampedLerp(0, blockStates.size(), normalizedNoise);
			double finalClamp = Mth.clamp(clamped, 0, blockStates.size() - 1);
			return blockStates.get((int) finalClamp);
		};
	}

	@Override
	public KeyDispatchDataCodec<? extends SurfaceRules.RuleSource> codec() {
		return KEY_CODEC;
	}
}
