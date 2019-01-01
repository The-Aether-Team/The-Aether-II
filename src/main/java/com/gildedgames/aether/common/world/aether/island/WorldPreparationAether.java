package com.gildedgames.aether.common.world.aether.island;

import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.IAetherChunkColumnInfo;
import com.gildedgames.aether.api.world.islands.IIslandChunkColumnInfo;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.api.world.islands.INoiseProvider;
import com.gildedgames.aether.common.world.aether.island.gen.IslandBlockType;
import com.gildedgames.orbis_api.preparation.impl.ChunkMask;
import com.gildedgames.orbis_api.processing.BlockAccessExtendedWrapper;
import com.gildedgames.orbis_api.processing.IBlockAccessExtended;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

/**
 * This is like a ChunkGenerator, but instead using virtual chunks
 * Here we'll place the island for generation later
 */
public class WorldPreparationAether
{
	private final IBlockAccessExtended access;

	private final OpenSimplexNoise noise;

	public WorldPreparationAether(World world, OpenSimplexNoise noise)
	{
		this.access = new BlockAccessExtendedWrapper(world);
		this.noise = noise;
	}

	public void generateBaseTerrain(IAetherChunkColumnInfo info, Biome[] biomes, final ChunkPrimer primer, final IIslandData island, final int chunkX, final int chunkY, final int chunkZ)
	{
		ChunkMask mask = new ChunkMask(chunkX, chunkY, chunkZ);

		this.generateCloudLayer(info, mask, chunkY);

		final IIslandGenerator generator = island.getGenerator();
		generator.genMask(info, mask, island, chunkX, chunkY, chunkZ);

		this.replaceBiomeBlocks(info, mask, chunkY);
//
//		this.caveGenerator.generate(this.world, chunkX, chunkY, chunkZ, mask, biomes);
//
//		if (island.getBiome() instanceof BiomeArcticPeaks)
//		{
//			this.veinGenerator.generate(this.world, chunkX, chunkY, chunkZ, mask, biomes);
//		}

		generator.genChunk(biomes, this.noise, this.access, mask, primer, island, chunkX, chunkY, chunkZ);
	}

	private void generateCloudLayer(IAetherChunkColumnInfo info, final ChunkMask mask, final int chunkY)
	{
		int maxDepth = 10;
		int levelY = 70;

		int limitMinY = chunkY * 16;
		int limitMaxY = limitMinY + 16;

		IIslandChunkColumnInfo chunkInfo = info.getIslandData(0, IIslandChunkColumnInfo.class);

		INoiseProvider cloudBuffer = chunkInfo.getCloudDepthBuffer();

		double threshold = 0.2;

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				final double val = cloudBuffer.interpolate(x, z);

				if (val > threshold)
				{
					final int depth = (int) ((val - threshold) * maxDepth);

					for (int y = levelY + depth; y >= levelY - depth; y--)
					{
						if (y < limitMinY || y >= limitMaxY)
						{
							continue;
						}

						mask.setBlock(x, y - limitMinY, z, IslandBlockType.CLOUD_BED_BLOCK.ordinal());
					}
				}
			}
		}
	}

	// Calculate max penetration depth
	private void replaceBiomeBlocks(IAetherChunkColumnInfo info, final ChunkMask mask, final int chunkY)
	{
		IIslandChunkColumnInfo chunkInfo = info.getIslandData(0, IIslandChunkColumnInfo.class);

		int limitMinY = chunkY * 16;
		int limitMaxY = limitMinY + 16;

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				int height = chunkInfo.getHeight(x, z);

				if (height < 0)
				{
					continue;
				}

				final int depth = (int) chunkInfo.getTerrainDepthBuffer().interpolate(x, z);

				for (int y = Math.min(limitMaxY - 1, height); y >= Math.max((height - depth), limitMinY); y--)
				{
					int y2 = y - limitMinY;

					final int state = mask.getBlock(x, y2, z);

					if (state != IslandBlockType.STONE_BLOCK.ordinal() && state != IslandBlockType.FERROSITE_BLOCK.ordinal())
					{
						continue;
					}

					int pentration = height - y;

					if (pentration == 0)
					{
						mask.setBlock(x, y2, z, IslandBlockType.TOPSOIL_BLOCK.ordinal());
					}
					else if (pentration <= depth)
					{
						mask.setBlock(x, y2, z, IslandBlockType.SOIL_BLOCK.ordinal());
					}
					else
					{
						break;
					}
				}
			}
		}
	}

	public void generateBaseTerrainMask(IAetherChunkColumnInfo info, Biome[] biomes, ChunkMask mask, IIslandData island, int chunkX, int chunkY, int chunkZ)
	{
		island.getGenerator().genMask(info, mask, island, chunkX, chunkY, chunkZ);

		this.replaceBiomeBlocks(info, mask, chunkY);

//		this.caveGenerator.generate(this.world, chunkX, chunkZ, mask, biomes);
	}

	public IIslandChunkColumnInfo generateChunkColumnInfo(Biome[] biomes, IIslandData island, int chunkX, int chunkZ)
	{
		return island.getGenerator().genInfo(biomes, this.noise, island, chunkX, chunkZ);
	}
}
