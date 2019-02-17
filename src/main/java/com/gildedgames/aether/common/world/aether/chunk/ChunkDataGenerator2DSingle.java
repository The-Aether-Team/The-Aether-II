package com.gildedgames.aether.common.world.aether.chunk;

import com.gildedgames.aether.api.world.noise.INoiseGenerator2D;

public class ChunkDataGenerator2DSingle extends ChunkDataGenerator2D<NoiseSampleData2D>
{
	private final INoiseGenerator2D generator;

	public ChunkDataGenerator2DSingle(INoiseGenerator2D generator, int noiseResolution)
	{
		super(noiseResolution);

		this.generator = generator;
	}

	@Override
	protected NoiseSampleData2D prepare(int chunkX, int chunkZ)
	{
		return new NoiseSampleData2D(this.noiseScaleFactor, this.noiseSampleCount);
	}

	@Override
	protected void generate(NoiseSampleData2D data, int x, int z, double worldX, double worldZ)
	{
		data.set(x, z, (float) this.generator.generate(worldX, worldZ));
	}
}
