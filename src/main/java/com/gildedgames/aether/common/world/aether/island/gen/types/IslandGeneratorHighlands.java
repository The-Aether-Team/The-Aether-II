package com.gildedgames.aether.common.world.aether.island.gen.types;

import com.gildedgames.aether.api.util.NoiseUtil;
import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.IAetherChunkColumnInfo;
import com.gildedgames.aether.api.world.islands.IIslandBounds;
import com.gildedgames.aether.api.world.islands.IIslandChunkColumnInfo;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.api.world.noise.IChunkHeightmap;
import com.gildedgames.aether.api.world.noise.IChunkNoiseBuffer;
import com.gildedgames.aether.api.world.noise.INoiseGenerator;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.aether.chunk.ChunkDataGenerator;
import com.gildedgames.aether.common.world.aether.chunk.NoiseSampleData;
import com.gildedgames.aether.common.world.aether.island.gen.AbstractIslandChunkColumnInfo;
import com.gildedgames.aether.common.world.aether.island.gen.IslandBlockType;
import com.gildedgames.aether.common.world.aether.island.gen.IslandChunkMaskTransformer;
import com.gildedgames.aether.common.world.aether.island.gen.IslandVariables;
import com.gildedgames.aether.common.world.aether.noise.NoiseGeneratorIslandTerrain;
import com.gildedgames.orbis_api.preparation.IChunkMaskTransformer;
import com.gildedgames.orbis_api.preparation.impl.ChunkMask;
import net.minecraft.util.math.MathHelper;

public class IslandGeneratorHighlands implements IIslandGenerator
{
	private static final double ISLAND_EDGE_BLEND_RANGE = 0.1;
	private static final double ISLAND_BOTTOM_BLEND_RANGE = 0.25;

	private static final double ISLAND_EDGE = 0.75;
	private static final double ISLAND_CUTOFF_POINT = 0.325;

	private static final int ISLAND_BOTTOM_HEIGHT = 100;

	private static final double SAMPLE_SCALE_FACTOR = 0.7;

	private final IslandVariables vars;

	public IslandGeneratorHighlands(IslandVariables variables)
	{
		this.vars = variables;
	}

	@Override
	public IIslandChunkColumnInfo generateColumnInfo(OpenSimplexNoise noise, IIslandData island, int chunkX, int chunkZ)
	{
		// Generate low-resolution terrain data
		final ChunkDataGeneratorHighlands.NoiseDataHighlands data = new ChunkDataGeneratorHighlands(noise, island, this.vars)
				.generate(chunkX, chunkZ);

		// Generate chunk column information from low-resolution terrain data
		final HighlandsChunkColumnInfo info = new HighlandsChunkColumnInfo(data, noise);

		IChunkHeightmap heightmap = info.getHeightmap();

		// Populate height-map
		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				double maxY = info.maxY.get(x, z);

				heightmap.setHeight(x, z, (int) maxY);
			}
		}

		return info;
	}

	@Override
	public void generateChunkSegment(IAetherChunkColumnInfo columnInfo, ChunkMask mask, IIslandData island, int chunkX, int chunkZ)
	{
		HighlandsChunkColumnInfo info = columnInfo.getIslandData(0, HighlandsChunkColumnInfo.class);

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				int maxY = (int) info.maxY.get(x, z);
				int minY = (int) info.minY.get(x, z);

				boolean snowy = this.vars.hasSnowCaps() && maxY > (ISLAND_BOTTOM_HEIGHT + (this.vars.getMaxTerrainHeight() * 0.7));

				int m1a = Math.max(minY, 0);
				int m1b = Math.min(maxY, 255);

				int snowThreshold = maxY - 8;

				for (int y = m1a; y <= m1b; y++)
				{
					if (snowy && y > snowThreshold)
					{
						mask.setBlock(x, y, z, IslandBlockType.SNOW_BLOCK.ordinal());
					}
					else
					{
						mask.setBlock(x, y, z, IslandBlockType.STONE_BLOCK.ordinal());
					}
				}

				if (this.vars.getCoastHeight() > 0)
				{
					int coastMaxY = ISLAND_BOTTOM_HEIGHT + this.vars.getCoastHeight();

					if (maxY <= coastMaxY)
					{
						int m3a = Math.min(coastMaxY, 255);
						int m3b = Math.max(ISLAND_BOTTOM_HEIGHT, 0);

						for (int y = m3a; y >= m3b; y--)
						{
							final int state = mask.getBlock(x, y, z);

							if (state == IslandBlockType.STONE_BLOCK.ordinal() || state == IslandBlockType.FERROSITE_BLOCK.ordinal())
							{
								mask.setBlock(x, y, z, IslandBlockType.COAST_BLOCK.ordinal());
							}
						}
					}
				}
			}
		}
	}

	@Override
	public IChunkMaskTransformer createMaskTransformer(IIslandData island, int chunkX, int chunkZ)
	{
		BiomeAetherBase biome = (BiomeAetherBase) island.getBiome();

		IslandChunkMaskTransformer transformer = new IslandChunkMaskTransformer();
		transformer.setMaskValue(IslandBlockType.TOPSOIL_BLOCK, biome.topBlock);
		transformer.setMaskValue(IslandBlockType.SOIL_BLOCK, biome.fillerBlock);
		transformer.setMaskValue(IslandBlockType.COAST_BLOCK, biome.getCoastalBlock());

		return transformer;
	}

	private class ChunkDataGeneratorHighlands extends ChunkDataGenerator<ChunkDataGeneratorHighlands.NoiseDataHighlands>
	{
		private static final int NOISE_RESOLUTION = 3;

		private final IslandVariables vars;

		private final double centerX, centerZ;

		private final double radiusX, radiusZ;

		private final INoiseGenerator heightGen;

		private final INoiseGenerator terraceGen;

		public ChunkDataGeneratorHighlands(OpenSimplexNoise noise, IIslandData island, IslandVariables vars)
		{
			super(NOISE_RESOLUTION);

			this.vars = vars;

			IIslandBounds bounds = island.getBounds();

			this.centerX = bounds.getCenterX();
			this.centerZ = bounds.getCenterZ();

			this.radiusX = bounds.getWidth() / 2.0;
			this.radiusZ = bounds.getLength() / 2.0;

			this.heightGen = new NoiseGeneratorIslandTerrain(noise, bounds, 0);
			this.terraceGen = new NoiseGeneratorIslandTerrain(noise, bounds, 1000);
		}

		@Override
		public NoiseDataHighlands prepare(int chunkX, int chunkZ)
		{
			return new NoiseDataHighlands(this.noiseScaleFactor, this.noiseSampleCount, chunkX, chunkZ);
		}

		@Override
		protected void generate(NoiseDataHighlands data, int x, int z, double worldX, double worldZ)
		{
			final double distX = Math.abs((this.centerX - worldX) * (1.0 / this.radiusX));
			final double distZ = Math.abs((this.centerZ - worldZ) * (1.0 / this.radiusZ));

			// Get distance from center of Island
			final double dist = Math.sqrt(distX * distX + distZ * distZ) / 1.0D;

			final double sample = this.heightGen.generate(worldX, worldZ) * SAMPLE_SCALE_FACTOR;

			final double heightSample = sample + 1.0 - dist;

			double topSample = this.vars.getHeightSampleFilter().transform(heightSample);

			if (this.vars.hasTerraces())
			{
				final double cutoffPointDist = Math.abs(ISLAND_CUTOFF_POINT - heightSample);
				final double diff = Math.max(0.0, ISLAND_CUTOFF_POINT - cutoffPointDist) * 8.0;

				final double terraceSample = this.terraceGen.generate(worldX, worldZ) + 1.0;

				topSample = NoiseUtil.lerp(heightSample, terraceSample - diff > 0.7 ? terraceSample - diff : heightSample, 0.7);
			}

			topSample = Math.pow(topSample, 1.0 + (this.vars.getCoastSpread() * 0.25));

			final double normal = NoiseUtil.normalise(sample);

			double bottomSample = Math.max(1.0D, normal + 0.25);

			if (heightSample < ISLAND_CUTOFF_POINT + ISLAND_EDGE_BLEND_RANGE)
			{
				final double thresh = (heightSample - ISLAND_CUTOFF_POINT);
				final double blend = thresh * (1.0 / ISLAND_EDGE_BLEND_RANGE);

				bottomSample = NoiseUtil.lerp(0.0, ISLAND_EDGE, blend);
			}
			else if (heightSample < ISLAND_CUTOFF_POINT + ISLAND_BOTTOM_BLEND_RANGE + ISLAND_EDGE_BLEND_RANGE)
			{
				final double thresh = (heightSample - ISLAND_CUTOFF_POINT - ISLAND_EDGE_BLEND_RANGE);
				final double blend = thresh * (1.0 / ISLAND_BOTTOM_BLEND_RANGE);

				final double islandBottom = (bottomSample * 0.25) + 0.75;

				bottomSample = NoiseUtil.lerp(ISLAND_EDGE, islandBottom, blend);
			}

			final double maxY = this.vars.getMaxYFilter().transform(ISLAND_BOTTOM_HEIGHT, topSample, ISLAND_CUTOFF_POINT);
			final double minY = ISLAND_BOTTOM_HEIGHT - (ISLAND_BOTTOM_HEIGHT * bottomSample);

			data.maxY.set(x, z, MathHelper.clamp(maxY, ISLAND_BOTTOM_HEIGHT, 254.0D));
			data.minY.set(x, z, minY);
		}

		// The generated noise data from the Highlands generator.
		private class NoiseDataHighlands
		{
			final int chunkX, chunkZ;

			final NoiseSampleData minY, maxY;

			NoiseDataHighlands(double noiseScaleFactor, int sampleCount, int chunkX, int chunkZ)
			{
				this.chunkX = chunkX;
				this.chunkZ = chunkZ;

				this.minY = new NoiseSampleData(noiseScaleFactor, sampleCount);
				this.maxY = new NoiseSampleData(noiseScaleFactor, sampleCount);
			}
		}
	}

	// The final resulting data for the chunk column for Highlands terrain.
	private class HighlandsChunkColumnInfo extends AbstractIslandChunkColumnInfo
	{
		final IChunkNoiseBuffer minY, maxY;

		HighlandsChunkColumnInfo(ChunkDataGeneratorHighlands.NoiseDataHighlands data, OpenSimplexNoise noise)
		{
			super(noise, data.chunkX, data.chunkZ);

			this.minY = data.minY.createChunkBuffer();
			this.maxY = data.maxY.createChunkBuffer();
		}
	}

}
