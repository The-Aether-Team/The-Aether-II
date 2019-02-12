package com.gildedgames.aether.common.world.aether.chunk;

import com.gildedgames.aether.api.world.noise.INoiseGenerator;

public class ChunkDataGeneratorSingle extends ChunkDataGenerator<NoiseSampleData>
{
	private final INoiseGenerator generator;

	public ChunkDataGeneratorSingle(INoiseGenerator generator, int noiseResolution)
	{
		super(noiseResolution);

		this.generator = generator;
	}

	@Override
	protected NoiseSampleData prepare(int chunkX, int chunkZ)
	{
		return new NoiseSampleData(this.noiseScaleFactor, this.noiseSampleCount);
	}

	@Override
	protected void generate(NoiseSampleData data, int x, int z, double worldX, double worldZ)
	{
		data.set(x, z, this.generator.generate(worldX, worldZ));
	}
}
