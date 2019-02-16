package com.gildedgames.aether.common.world.aether.island.gen.types;

import com.gildedgames.aether.api.util.NoiseUtil;
import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.IAetherChunkColumnInfo;
import com.gildedgames.aether.api.world.islands.IIslandBounds;
import com.gildedgames.aether.api.world.islands.IIslandChunkColumnInfo;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.api.world.noise.IChunkHeightmap;
import com.gildedgames.aether.api.world.noise.IChunkNoiseBuffer2D;
import com.gildedgames.aether.api.world.noise.INoiseGenerator2D;
import com.gildedgames.aether.common.world.aether.chunk.ChunkDataGenerator2D;
import com.gildedgames.aether.common.world.aether.chunk.NoiseSampleData2D;
import com.gildedgames.aether.common.world.aether.island.gen.AbstractIslandChunkColumnInfo;
import com.gildedgames.aether.common.world.aether.island.gen.IslandBlockType;
import com.gildedgames.aether.common.world.aether.island.gen.IslandChunkMaskTransformer;
import com.gildedgames.aether.common.world.aether.noise.NoiseGeneratorIslandTerrain;
import com.gildedgames.orbis_api.preparation.IChunkMaskTransformer;
import com.gildedgames.orbis_api.preparation.impl.ChunkMask;

public class IslandGeneratorHighlandMegacoast implements IIslandGenerator
{
	private static final double CUTOFF_POINT = 0.325;

	private static final int BOTTOM_MAX_Y = 100;

	private static final int TOP_HEIGHT = 80;

	private static final int BOTTOM_HEIGHT = 100;

	private static final double TERRACE_WIDTH = 0.15;

	private static final double ISLAND_EDGE_BLEND_RANGE = 0.1;

	private static final double ISLAND_BOTTOM_BLEND_RANGE = 0.25;

	private static final double ISLAND_EDGE = 0.75;

	@Override
	public void generateChunkSegment(IAetherChunkColumnInfo info, ChunkMask mask, IIslandData island, int chunkX, int chunkZ)
	{
		HighlandMegacostChunkColumnInfo column = info.getIslandData(0, HighlandMegacostChunkColumnInfo.class);

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				final int topHeight = (int) column.topHeight.get(x, z);
				final int bottomHeight = (int) column.bottomHeight.get(x, z);

				final int minY = Math.max(BOTTOM_MAX_Y - bottomHeight, 0);
				final int maxY = Math.min(BOTTOM_MAX_Y + topHeight, 255);

				final boolean coast = topHeight < 3;

				for (int y = minY; y <= maxY; y++)
				{
					if (coast && y > BOTTOM_MAX_Y - 2)
					{
						mask.setBlock(x, y, z, IslandBlockType.COAST_BLOCK.ordinal());
					}
					else
					{
						mask.setBlock(x, y, z, IslandBlockType.STONE_BLOCK.ordinal());
					}
				}
			}
		}
	}

	@Override
	public IChunkMaskTransformer createMaskTransformer(IIslandData island,
			int chunkX, int chunkZ)
	{
		return new IslandChunkMaskTransformer();
	}

	@Override
	public IIslandChunkColumnInfo generateColumnInfo(OpenSimplexNoise noise, IIslandData island, int chunkX, int chunkZ)
	{

		final ChunkDataGeneratorMegacoast.NoiseDataMegacoast data = new ChunkDataGeneratorMegacoast(noise, island)
				.generate(chunkX, chunkZ);

		final HighlandMegacostChunkColumnInfo info = new HighlandMegacostChunkColumnInfo(data, noise);

		IChunkHeightmap heightMap = info.getHeightmap();

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				heightMap.setHeight(x, z, BOTTOM_MAX_Y + (int) info.topHeight.get(x, z));
			}
		}

		return info;
	}

	private class ChunkDataGeneratorMegacoast extends ChunkDataGenerator2D<ChunkDataGeneratorMegacoast.NoiseDataMegacoast>
	{
		private static final int NOISE_RESOLUTION = 3;

		private final double centerX, centerZ;

		private final double radiusX, radiusZ;

		private final INoiseGenerator2D heightGen;

		public ChunkDataGeneratorMegacoast(OpenSimplexNoise noise, IIslandData island)
		{
			super(NOISE_RESOLUTION);

			IIslandBounds bounds = island.getBounds();

			this.centerX = bounds.getCenterX();
			this.centerZ = bounds.getCenterZ();

			this.radiusX = bounds.getWidth() / 2.0;
			this.radiusZ = bounds.getLength() / 2.0;

			this.heightGen = new NoiseGeneratorIslandTerrain(noise, bounds, 0);
		}

		@Override
		public NoiseDataMegacoast prepare(int chunkX, int chunkZ)
		{
			return new NoiseDataMegacoast(this.noiseScaleFactor, this.noiseSampleCount, chunkX, chunkZ);
		}

		@Override
		protected void generate(NoiseDataMegacoast data, int x, int z, double worldX, double worldZ)
		{
			double distX = Math.abs((this.centerX - worldX) * (1.0 / this.radiusX));
			double distZ = Math.abs((this.centerZ - worldZ) * (1.0 / this.radiusZ));

			// Get distance from center of Island
			double dist = Math.sqrt(distX * distX + distZ * distZ) / 1.0D;

			double sample = this.heightGen.generate(worldX, worldZ) * 0.7;

			final double heightSample = sample + 1.0 - dist;

			final double k = Math.floor(heightSample / TERRACE_WIDTH);

			final double f = (heightSample - k * TERRACE_WIDTH) / 0.05;

			final double s = Math.min(2.0 * f, 1.0);

			final double terrace = (k + s) * TERRACE_WIDTH;

			final double topSample = Math.pow(terrace, 2.5) + (heightSample / 2.0);

			final double normal = NoiseUtil.normalise(sample);
			double bottomSample = Math.min(1.0, normal + 0.25);

			double islandBottom = (bottomSample * 0.25) + 0.75;

			if (heightSample < CUTOFF_POINT + ISLAND_EDGE_BLEND_RANGE)
			{
				double thresh = (heightSample - CUTOFF_POINT);

				double blend = thresh * (1.0 / ISLAND_EDGE_BLEND_RANGE);

				bottomSample = NoiseUtil.lerp(0.0, ISLAND_EDGE, blend);
			}
			else if (heightSample < CUTOFF_POINT + ISLAND_BOTTOM_BLEND_RANGE + ISLAND_EDGE_BLEND_RANGE)
			{
				double thresh = (heightSample - CUTOFF_POINT - ISLAND_EDGE_BLEND_RANGE);

				double blend = thresh * (1.0 / ISLAND_BOTTOM_BLEND_RANGE);

				bottomSample = NoiseUtil.lerp(ISLAND_EDGE, islandBottom, blend);
			}

			final double maxY = (topSample - CUTOFF_POINT) * TOP_HEIGHT;

			data.topHeight.set(x, z, Math.max(0.0, maxY));
			data.bottomHeight.set(x, z, BOTTOM_HEIGHT * bottomSample);
		}

		// The generated noise data from the Highlands generator.
		private class NoiseDataMegacoast
		{
			final int chunkX, chunkZ;

			final NoiseSampleData2D bottomHeight, topHeight;

			NoiseDataMegacoast(double noiseScaleFactor, int sampleCount, int chunkX, int chunkZ)
			{
				this.chunkX = chunkX;
				this.chunkZ = chunkZ;

				this.bottomHeight = new NoiseSampleData2D(noiseScaleFactor, sampleCount);
				this.topHeight = new NoiseSampleData2D(noiseScaleFactor, sampleCount);
			}
		}
	}

	private class HighlandMegacostChunkColumnInfo extends AbstractIslandChunkColumnInfo
	{
		final IChunkNoiseBuffer2D bottomHeight, topHeight;

		HighlandMegacostChunkColumnInfo(ChunkDataGeneratorMegacoast.NoiseDataMegacoast data, OpenSimplexNoise noise)
		{
			super(noise, data.chunkX, data.chunkZ);

			this.bottomHeight = data.bottomHeight.createInterpolatedNoiseBuffer();
			this.topHeight = data.topHeight.createInterpolatedNoiseBuffer();
		}
	}

}
