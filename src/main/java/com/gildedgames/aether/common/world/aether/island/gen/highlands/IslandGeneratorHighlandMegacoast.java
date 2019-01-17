package com.gildedgames.aether.common.world.aether.island.gen.highlands;

import com.gildedgames.aether.api.util.NoiseUtil;
import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.IAetherChunkColumnInfo;
import com.gildedgames.aether.api.world.islands.IIslandChunkColumnInfo;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.common.util.ChunkNoiseGenerator;
import com.gildedgames.aether.common.world.aether.island.gen.AbstractIslandChunkColumnInfo;
import com.gildedgames.aether.common.world.aether.island.gen.IslandBlockType;
import com.gildedgames.aether.common.world.aether.island.gen.IslandChunkMaskTransformer;
import com.gildedgames.aether.common.world.util.data.ChunkDoubleSegment;
import com.gildedgames.orbis_api.preparation.IChunkMaskTransformer;
import com.gildedgames.orbis_api.preparation.impl.ChunkSegmentMask;
import net.minecraft.world.biome.Biome;

public class IslandGeneratorHighlandMegacoast implements IIslandGenerator
{
	@Override
	public void genMask(IAetherChunkColumnInfo info, ChunkSegmentMask mask, IIslandData island, int chunkX, int chunkY, int chunkZ)
	{
		int boundsMinY = chunkY * 16;
		int boundsMaxY = boundsMinY + 16;

		HighlandMegacostChunkColumnInfo column = info.getIslandData(0, HighlandMegacostChunkColumnInfo.class);

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				double heightSample = column.heightSample_xz.get(x, z);

				if (heightSample == 0.0D)
				{
					continue;
				}

				double bottomMaxY = column.bottomMaxY_xz.get(x, z);
				double maxY = column.maxY_xz.get(x, z);

				double cutoffPoint = column.cutoffPoint_xz.get(x, z);
				double topSample = column.topSample_xz.get(x, z);
				double bottomHeight = column.bottomHeight_xz.get(x, z);

				for (int y = Math.min((int) bottomMaxY, boundsMaxY - 1); y > Math.max(bottomMaxY - bottomHeight, boundsMinY - 1); y--)
				{
					if (y == 100 && heightSample < cutoffPoint + 0.10)
					{
						mask.setBlock(x, y - boundsMinY, z, IslandBlockType.COAST_BLOCK.ordinal());
					}
					else
					{
						mask.setBlock(x, y - boundsMinY, z, IslandBlockType.STONE_BLOCK.ordinal());
					}
				}

				for (int y = Math.max((int) bottomMaxY, boundsMinY); y < Math.min(maxY, boundsMaxY); y++)
				{
					if (y == 100 && topSample < cutoffPoint + 0.10)
					{
						mask.setBlock(x, y - boundsMinY, z, IslandBlockType.COAST_BLOCK.ordinal());
					}
					else
					{
						mask.setBlock(x, y - boundsMinY, z, IslandBlockType.STONE_BLOCK.ordinal());
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
	public IIslandChunkColumnInfo genInfo(Biome[] biomes, OpenSimplexNoise noise, IIslandData island, int chunkX, int chunkZ)
	{
		HighlandMegacostChunkColumnInfo info = new HighlandMegacostChunkColumnInfo(noise, chunkX, chunkZ);

		final ChunkNoiseGenerator heightMap = IslandGeneratorHighlands.generateNoise(noise, island, chunkX, chunkZ, 0, 300.0D);

		final int posX = chunkX * 16;
		final int posZ = chunkZ * 16;

		final double centerX = island.getBounds().getCenterX();
		final double centerZ = island.getBounds().getCenterZ();

		final double radiusX = island.getBounds().getWidth() / 2.0;
		final double radiusZ = island.getBounds().getLength() / 2.0;

		for (int x = 0; x < 16; x++)
		{
			final int worldX = posX + x;

			final double distX = Math.abs((centerX - worldX) * (1.0 / radiusX));

			for (int z = 0; z < 16; z++)
			{
				final int worldZ = posZ + z;

				final double distZ = Math.abs((centerZ - worldZ) * (1.0 / radiusZ));

				// Get distance from center of Island
				final double dist = Math.sqrt(distX * distX + distZ * distZ) / 1.0D;

				final double sample = heightMap.getNoiseValue(x, z) * 0.7;

				final double heightSample = sample + 1.0 - dist;
				final double cutoffPoint = 0.325;

				if (heightSample > cutoffPoint)
				{
					final double bottomMaxY = 100;

					final double topHeight = 80;

					final double normal = NoiseUtil.normalise(sample);

					final double bottomHeight = 100;

					final double terraceWidth = 0.15;

					final double k = Math.floor(heightSample / terraceWidth);

					final double f = (heightSample - k * terraceWidth) / 0.05;

					final double s = Math.min(2 * f, 1.0);

					final double terrace = (k + s) * terraceWidth;

					final double topSample = Math.pow(terrace, 2.5) + (heightSample / 2.0);

					double bottomSample = Math.min(1.0D, normal + 0.25);

					double islandEdgeBlendRange = 0.1;
					double islandBottomBlendRange = 0.25;

					double islandEdge = 0.75;
					double islandBottom = (bottomSample * 0.25) + 0.75;

					if (heightSample < cutoffPoint + islandEdgeBlendRange)
					{
						double thresh = (heightSample - cutoffPoint);

						double blend = thresh * (1.0 / islandEdgeBlendRange);

						bottomSample = NoiseUtil.lerp(0.0, islandEdge, blend);
					}
					else if (heightSample < cutoffPoint + islandBottomBlendRange + islandEdgeBlendRange)
					{
						double thresh = (heightSample - cutoffPoint - islandEdgeBlendRange);

						double blend = thresh * (1.0 / islandBottomBlendRange);

						bottomSample = NoiseUtil.lerp(islandEdge, islandBottom, blend);
					}

					double maxY = bottomMaxY + ((topSample - cutoffPoint) * topHeight);

					if (maxY > 254.0D)
					{
						maxY = 254.0D;
					}

					info.maxY_xz.set(x, z, maxY);

					info.bottomMaxY_xz.set(x, z, bottomMaxY);
					info.bottomHeight_xz.set(x, z, bottomHeight * bottomSample);

					info.heightSample_xz.set(x, z, heightSample);
					info.cutoffPoint_xz.set(x, z, cutoffPoint);
					info.topSample_xz.set(x, z, topSample);

					info.setHeight(x, z, (int) Math.max(maxY, bottomMaxY));
				}
			}
		}

		return info;
	}

	private class HighlandMegacostChunkColumnInfo extends AbstractIslandChunkColumnInfo
	{
		final ChunkDoubleSegment bottomMaxY_xz = new ChunkDoubleSegment();
		final ChunkDoubleSegment maxY_xz = new ChunkDoubleSegment();
		final ChunkDoubleSegment heightSample_xz = new ChunkDoubleSegment();
		final ChunkDoubleSegment cutoffPoint_xz = new ChunkDoubleSegment();
		final ChunkDoubleSegment topSample_xz = new ChunkDoubleSegment();
		final ChunkDoubleSegment bottomHeight_xz = new ChunkDoubleSegment();

		HighlandMegacostChunkColumnInfo(OpenSimplexNoise noise, int chunkX, int chunkZ)
		{
			super(noise, chunkX, chunkZ);
		}
	}
}
