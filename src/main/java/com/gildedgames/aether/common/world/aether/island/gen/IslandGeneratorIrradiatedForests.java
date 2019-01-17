package com.gildedgames.aether.common.world.aether.island.gen;

import com.gildedgames.aether.api.util.NoiseUtil;
import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.IAetherChunkColumnInfo;
import com.gildedgames.aether.api.world.islands.IIslandChunkColumnInfo;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.api.world.islands.INoiseProvider;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.aether.biomes.irradiated_forests.CrackChunk;
import com.gildedgames.aether.common.world.aether.biomes.irradiated_forests.CrackPos;
import com.gildedgames.aether.common.world.aether.biomes.irradiated_forests.IrradiatedForestsData;
import com.gildedgames.aether.common.world.aether.island.gen.highlands.IslandGeneratorHighlands;
import com.gildedgames.aether.common.world.util.data.ChunkBooleanSegment;
import com.gildedgames.aether.common.world.util.data.ChunkDoubleSegment;
import com.gildedgames.orbis_api.preparation.IChunkMaskTransformer;
import com.gildedgames.orbis_api.preparation.impl.ChunkSegmentMask;
import com.gildedgames.orbis_api.util.ObjectFilter;
import net.minecraft.world.biome.Biome;

public class IslandGeneratorIrradiatedForests implements IIslandGenerator
{
	@Override
	public void genMask(IAetherChunkColumnInfo info, ChunkSegmentMask mask, IIslandData island, int chunkX, int chunkY, int chunkZ)
	{
		IrradiatedForestsChunkColumnData column = info.getIslandData(0, IrradiatedForestsChunkColumnData.class);

		int boundsMinY = chunkY * 16;
		int boundsMaxY = boundsMinY + 16;

		boolean hasCoast = ((BiomeAetherBase) island.getBiome()).getCoastalBlock() != null;

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				double topSample = column.topSample_xz.get(x, z);

				if (topSample == 0.0D)
				{
					continue;
				}

				double maxY = column.maxY_xz.get(x, z);
				double heightSample = column.heightSample_xz.get(x, z);

				double bottomMinY = column.bottomMinY_xz.get(x, z);
				double bottomMaxY = column.bottomMaxY_xz.get(x, z);
				double bottomHeight = column.bottomHeight_xz.get(x, z);

				double cutoffPoint = column.cutoffPoint_xz.get(x, z);

				boolean cracked = column.cracked_xz.get(x, z);
				boolean mossy = column.mossy_xz.get(x, z);

				if (!cracked)
				{
					for (int y = Math.min((int) bottomMaxY, boundsMaxY - 1); y > Math.max(bottomMaxY - bottomHeight, boundsMinY - 1); y--)
					{
						if (hasCoast && y == 100 && heightSample < cutoffPoint + 0.025)
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
						if (hasCoast && (y == 100 && topSample < cutoffPoint + 0.025))
						{
							mask.setBlock(x, y - boundsMinY, z, IslandBlockType.COAST_BLOCK.ordinal());
						}
						else
						{
							mask.setBlock(x, y - boundsMinY, z, IslandBlockType.STONE_BLOCK.ordinal());
						}
					}
				}
				else
				{
					IslandBlockType type = mossy ? IslandBlockType.STONE_MOSSY_BLOCK : IslandBlockType.STONE_BLOCK;

					for (int y = Math.max((int) bottomMinY, boundsMinY); y < Math.min(maxY, boundsMaxY); y++)
					{
						mask.setBlock(x, y - boundsMinY, z, type.ordinal());
					}
				}
			}
		}
	}

	@Override
	public IChunkMaskTransformer createMaskTransformer(IIslandData island,
			int chunkX, int chunkZ)
	{
		IslandChunkMaskTransformer transformer = new IslandChunkMaskTransformer();
		transformer.setMaskValue(IslandBlockType.TOPSOIL_BLOCK,
				BlocksAether.aether_grass.getDefaultState().withProperty(BlockAetherGrass.PROPERTY_VARIANT, BlockAetherGrass.IRRADIATED));

		return transformer;
	}

	@Override
	public IIslandChunkColumnInfo genInfo(Biome[] biomes, OpenSimplexNoise noise, IIslandData island, int chunkX, int chunkZ)
	{
		IrradiatedForestsChunkColumnData info = new IrradiatedForestsChunkColumnData(noise, chunkX, chunkZ);

		IrradiatedForestsData data = ObjectFilter.getFirstFrom(island.getComponents(), IrradiatedForestsData.class);

		if (data == null)
		{
			throw new RuntimeException("IrradiatedForestsData could not be found");
		}

		data.checkInit();

		final INoiseProvider heightMap = IslandGeneratorHighlands.generateNoise(noise, island, chunkX, chunkZ, 0, 300.0D);

		INoiseProvider terraceMap = null;

		final int posX = chunkX * 16;
		final int posZ = chunkZ * 16;

		final double centerX = island.getBounds().getCenterX();
		final double centerZ = island.getBounds().getCenterZ();

		final double radiusX = island.getBounds().getWidth() / 2.0;
		final double radiusZ = island.getBounds().getLength() / 2.0;

		CrackChunk[][] crackChunks = new CrackChunk[3][3];

		for (int x = -1; x <= 1; x++)
		{
			for (int z = -1; z <= 1; z++)
			{
				crackChunks[x + 1][z + 1] = data.getCracks(chunkX + x, chunkZ + z);
			}
		}

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				final int worldX = posX + x;
				final int worldZ = posZ + z;

				final double sample = heightMap.getNoiseValue(x, z) * 0.7;

				final double distX = Math.abs((centerX - worldX) * (1.0 / radiusX));
				final double distZ = Math.abs((centerZ - worldZ) * (1.0 / radiusZ));

				// Get distance from center of Island
				final double dist = Math.sqrt(distX * distX + distZ * distZ) / 1.0D;

				final double heightSample = sample + 1.0 - dist;

				final double cutoffPoint = 0.325;

				if (heightSample > cutoffPoint)
				{
					final double bottomMaxY = 100;

					final double topHeight = 80;

					boolean cracked = false;

					final int radius = Math.max(2, (int) (16.0 * (1.0 - dist)));

					double closestDist = Double.POSITIVE_INFINITY;

					for (int x1 = -radius; x1 < radius; x1++)
					{
						for (int z1 = -radius; z1 < radius; z1++)
						{
							final int crackX = worldX + x1;
							final int crackZ = worldZ + z1;

							final CrackChunk crackChunk = crackChunks[(crackX >> 4) - chunkX + 1][(crackZ >> 4) - chunkZ + 1];

							if (crackChunk != null)
							{
								final CrackPos crack = crackChunk.get(crackX & 15, crackZ & 15);

								if (crack != null)
								{
									final double crackDistX = Math.abs(x1 * (1.0 / radius));
									final double crackDistZ = Math.abs(z1 * (1.0 / radius));

									final double crackDist = Math.sqrt((crackDistX * crackDistX) + (crackDistZ * crackDistZ));

									if (closestDist > crackDist)
									{
										closestDist = crackDist;
									}

									cracked = true;
								}
							}
						}
					}

					closestDist = Math.min(1.0, closestDist);

					final double normal = NoiseUtil.normalise(sample);
					final double cutoffPointDist = Math.abs(cutoffPoint - heightSample);
					final double diff = Math.max(0.0, cutoffPoint - cutoffPointDist) * 8.0;

					double bottomHeightMod = Math.pow(normal, 0.2);

					final double bottomHeight = 100;

					if (terraceMap == null)
					{
						terraceMap = IslandGeneratorHighlands.generateNoise(noise, island, chunkX, chunkZ, 1000, 300.0D);
					}

					final double terraceSample = terraceMap.getNoiseValue(x, z) + 1.0;

					final double topSample = NoiseUtil.lerp(heightSample, terraceSample - diff > 0.7 ? terraceSample - diff : heightSample, 0.7);

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

					if (!cracked)
					{
						double maxY = bottomMaxY + ((topSample - cutoffPoint) * topHeight);

						if (maxY > 254.0D)
						{
							maxY = 254.0D;
						}

						info.maxY_xz.set(x, z, maxY);
						info.bottomHeight_xz.set(x, z, bottomHeight * bottomSample);

						info.setHeight(x, z, (int) Math.max(maxY, bottomHeight));
					}
					else
					{
						final double bottomMinY = bottomMaxY - (bottomHeight * bottomHeightMod);
						final double expectedTopY = bottomMaxY + ((topSample - cutoffPoint) * topHeight);

						final double maxY = (expectedTopY * closestDist);

						info.maxY_xz.set(x, z, maxY);
						info.bottomMinY_xz.set(x, z, bottomMinY);

						info.setHeight(x, z, (int) Math.max(maxY, bottomHeight));
					}

					info.cracked_xz.set(x, z, cracked);
					info.mossy_xz.set(x, z, closestDist < 0.95D);

					info.bottomMaxY_xz.set(x, z, bottomMaxY);

					info.heightSample_xz.set(x, z, heightSample);
					info.cutoffPoint_xz.set(x, z, cutoffPoint);
					info.topSample_xz.set(x, z, topSample);
				}
			}
		}

		return info;
	}

	private class IrradiatedForestsChunkColumnData extends AbstractIslandChunkColumnInfo
	{
		final ChunkBooleanSegment cracked_xz = new ChunkBooleanSegment();
		final ChunkBooleanSegment mossy_xz = new ChunkBooleanSegment();

		final ChunkDoubleSegment maxY_xz = new ChunkDoubleSegment();

		final ChunkDoubleSegment bottomHeight_xz = new ChunkDoubleSegment();
		final ChunkDoubleSegment bottomMaxY_xz = new ChunkDoubleSegment();
		final ChunkDoubleSegment bottomMinY_xz = new ChunkDoubleSegment();

		final ChunkDoubleSegment heightSample_xz = new ChunkDoubleSegment();
		final ChunkDoubleSegment cutoffPoint_xz = new ChunkDoubleSegment();
		final ChunkDoubleSegment topSample_xz = new ChunkDoubleSegment();

		IrradiatedForestsChunkColumnData(OpenSimplexNoise noise, int chunkX, int chunkZ)
		{
			super(noise, chunkX, chunkZ);
		}
	}
}
