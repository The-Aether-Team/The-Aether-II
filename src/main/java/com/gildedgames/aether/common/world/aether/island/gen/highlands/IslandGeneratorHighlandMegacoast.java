package com.gildedgames.aether.common.world.aether.island.gen.highlands;

import com.gildedgames.aether.api.util.NoiseUtil;
import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.IAetherChunkColumnInfo;
import com.gildedgames.aether.api.world.islands.IIslandChunkColumnInfo;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.common.world.aether.island.gen.AbstractIslandChunkColumnInfo;
import com.gildedgames.aether.common.world.aether.island.gen.IslandBlockType;
import com.gildedgames.aether.common.world.aether.island.gen.IslandChunkMaskTransformer;
import com.gildedgames.orbis_api.preparation.impl.ChunkMask;
import com.gildedgames.orbis_api.processing.IBlockAccessExtended;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class IslandGeneratorHighlandMegacoast implements IIslandGenerator
{
	// Resolution of the evalNormalised for a chunk. Should be a power of 2.
	private static final int NOISE_XZ_SCALE = 4;

	// Number of samples done per chunk.
	private static final int NOISE_SAMPLES = NOISE_XZ_SCALE + 1;

	public static double interpolate(final double[] data, final int x, final int z)
	{
		final double x0 = (double) x / NOISE_XZ_SCALE;
		final double z0 = (double) z / NOISE_XZ_SCALE;

		final int integerX = (int) Math.floor(x0);
		final double fractionX = x0 - integerX;

		final int integerZ = (int) Math.floor(z0);
		final double fractionZ = z0 - integerZ;

		final double a = data[integerX + (integerZ * NOISE_SAMPLES)];
		final double b = data[integerX + ((integerZ + 1) * NOISE_SAMPLES)];
		final double c = data[integerX + 1 + (integerZ * NOISE_SAMPLES)];
		final double d = data[integerX + 1 + ((integerZ + 1) * NOISE_SAMPLES)];

		return (1.0 - fractionX) * ((1.0 - fractionZ) * a + fractionZ * b) +
				fractionX * ((1.0 - fractionZ) * c + fractionZ * d);
	}

	private static double[] generateNoise(final OpenSimplexNoise noise, final IIslandData island, final int chunkX, final int chunkZ)
	{
		final double posX = chunkX * 16;
		final double posZ = chunkZ * 16;

		final double minX = island.getBounds().getMinX();
		final double minZ = island.getBounds().getMinZ();

		final double[] data = new double[NOISE_SAMPLES * NOISE_SAMPLES];

		// Generate half-resolution evalNormalised
		for (int x = 0; x < NOISE_SAMPLES; x++)
		{
			// Creates world coordinate and normalized evalNormalised coordinate
			final double worldX = posX - (x == 0 ? NOISE_XZ_SCALE - 1 : 0) + (x * (16D / NOISE_SAMPLES));
			final double nx = (worldX + minX) / 300.0D;

			for (int z = 0; z < NOISE_SAMPLES; z++)
			{
				// Creates world coordinate and normalized evalNormalised coordinate
				final double worldZ = posZ - (z == 0 ? NOISE_XZ_SCALE - 1 : 0) + (z * (16.0D / NOISE_SAMPLES));
				final double nz = (worldZ + minZ) / 300.0D;

				data[x + (z * NOISE_SAMPLES)] = NoiseUtil.genNoise(noise, nx, nz);
			}
		}

		return data;
	}


	@Override
	public void genMask(IAetherChunkColumnInfo info, ChunkMask mask, IIslandData island, int chunkX, int chunkY, int chunkZ)
	{
		int boundsMinY = chunkY * 16;
		int boundsMaxY = boundsMinY + 16;

		HighlandMegacostChunkColumnInfo column = info.getIslandData(0, HighlandMegacostChunkColumnInfo.class);

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				double heightSample = column.heightSample_xz[x][z];

				if (heightSample == 0.0D)
				{
					continue;
				}

				double bottomMaxY = column.bottomMaxY_xz[x][z];
				double maxY = column.maxY_xz[x][z];

				double cutoffPoint = column.cutoffPoint_xz[x][z];
				double topSample = column.topSample_xz[x][z];
				double bottomHeight = column.bottomHeight_xz[x][z];

				for (int y = (int) bottomMaxY; y > bottomMaxY - bottomHeight; y--)
				{
					if (y < boundsMinY || y >= boundsMaxY)
					{
						continue;
					}

					if (heightSample < cutoffPoint + 0.10 && y == 100)
					{
						mask.setBlock(x, y - boundsMinY, z, IslandBlockType.COAST_BLOCK.ordinal());
					}
					else
					{
						mask.setBlock(x, y - boundsMinY, z, IslandBlockType.STONE_BLOCK.ordinal());
					}
				}

				for (int y = (int) bottomMaxY; y < maxY; y++)
				{
					if (y < boundsMinY || y >= boundsMaxY)
					{
						continue;
					}

					if ((topSample < cutoffPoint + 0.10 && y == 100))
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
	public void genChunk(Biome[] biomes, OpenSimplexNoise noise, IBlockAccessExtended access, ChunkMask mask, ChunkPrimer primer, IIslandData island,
			int chunkX, int chunkY, int chunkZ)
	{
		mask.createChunk(primer, new IslandChunkMaskTransformer());
	}

	@Override
	public IIslandChunkColumnInfo genInfo(Biome[] biomes, OpenSimplexNoise noise, IIslandData island, int chunkX, int chunkZ)
	{
		HighlandMegacostChunkColumnInfo info = new HighlandMegacostChunkColumnInfo(noise, chunkX, chunkZ);

		final double[] heightMap = generateNoise(noise, island, chunkX, chunkZ);

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

				final double sample = interpolate(heightMap, x, z);

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

					info.maxY_xz[x][z] = maxY;

					info.bottomMaxY_xz[x][z] = bottomMaxY;
					info.bottomHeight_xz[x][z] = (bottomHeight * bottomSample);

					info.heightSample_xz[x][z] = heightSample;
					info.cutoffPoint_xz[x][z] = cutoffPoint;
					info.topSample_xz[x][z] = topSample;

					info.setHeight(x, z, (int) Math.max(maxY, bottomMaxY));
				}
				else
				{
					info.setHeight(x, z, -1);
				}
			}
		}

		return info;
	}

	private class HighlandMegacostChunkColumnInfo extends AbstractIslandChunkColumnInfo
	{
		final double[][] bottomMaxY_xz = new double[16][16];
		final double[][] maxY_xz = new double[16][16];
		final double[][] heightSample_xz = new double[16][16];
		final double[][] cutoffPoint_xz = new double[16][16];
		final double[][] topSample_xz = new double[16][16];
		final double[][] bottomHeight_xz = new double[16][16];

		HighlandMegacostChunkColumnInfo(OpenSimplexNoise noise, int chunkX, int chunkZ)
		{
			super(noise, chunkX, chunkZ);
		}
	}
}
