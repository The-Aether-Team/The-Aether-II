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

public record NoisePalette3DPlacementRule(BlockState spot, int spotRatio, int emptyRatio, double noiseFreq) implements SurfaceRules.RuleSource {
	public static final KeyDispatchDataCodec<NoisePalette3DPlacementRule> KEY_CODEC = KeyDispatchDataCodec.of(RecordCodecBuilder.mapCodec(inst -> inst.group(
			BlockState.CODEC.fieldOf("spot_blockstate").forGetter(NoisePalette3DPlacementRule::spot),
			Codec.INT.fieldOf("spot_ratio").forGetter(NoisePalette3DPlacementRule::spotRatio),
			Codec.INT.fieldOf("empty_ratio").forGetter(NoisePalette3DPlacementRule::emptyRatio),
			Codec.DOUBLE.fieldOf("noise_frequency").forGetter(NoisePalette3DPlacementRule::noiseFreq)
	).apply(inst, NoisePalette3DPlacementRule::new)));

	@Override
	public SurfaceRules.SurfaceRule apply(SurfaceRules.Context context) {
		return (x, y, z) -> {
//			NormalNoise noise = context.randomState.getOrCreateNoise(this.noise);
			ImprovedNoise noise = new ImprovedNoise(new XoroshiroRandomSource(0)); //todo replace with future ResourceKey<NormalNoise.NoiseParameters> noise parameter usage and context.randomState.getOrCreateNoise(this.noise);

			List<BlockState> blockStates = new ArrayList<>();
			for (int i = 0; i < this.spotRatio; i++) {
				blockStates.add(this.spot);
			}
			for (int i = 0; i < this.emptyRatio; i++) {
				blockStates.add(null);
			}

			double noiseValue = noise.noise(x * this.noiseFreq, y * this.noiseFreq, z * this.noiseFreq);
			double normalizedNoise = noiseValue * 0.5 + 0.5;
			double clampedNoise = Mth.clamp(normalizedNoise, 0, 1);
			int lerpedNoise = Mth.lerpInt((float) clampedNoise, 0, blockStates.size() - 1);

			return blockStates.get(lerpedNoise);
		};
	}

	@Override
	public KeyDispatchDataCodec<? extends SurfaceRules.RuleSource> codec() {
		return KEY_CODEC;
	}
}
