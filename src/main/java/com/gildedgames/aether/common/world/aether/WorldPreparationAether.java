package com.gildedgames.aether.common.world.aether;

import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.IAetherChunkColumnInfo;
import com.gildedgames.aether.api.world.islands.IIslandChunkColumnInfo;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.api.world.noise.IChunkHeightmap;
import com.gildedgames.aether.api.world.noise.IChunkNoiseBuffer;
import com.gildedgames.aether.common.world.aether.features.WorldGenAetherCaves;
import com.gildedgames.aether.common.world.aether.island.gen.IslandBlockType;
import com.gildedgames.orbis_api.preparation.impl.ChunkSegmentMask;
import com.gildedgames.orbis_api.processing.BlockAccessExtendedWrapper;
import com.gildedgames.orbis_api.processing.IBlockAccessExtended;
import net.minecraft.world.World;

/**
 * This is like a ChunkGenerator, but instead using virtual chunks
 * Here we'll place the island for generation later
 */
public class WorldPreparationAether
{
	private final IBlockAccessExtended access;

	private final OpenSimplexNoise noise;

	private final WorldGenAetherCaves caveGenerator;


	public WorldPreparationAether(World world, OpenSimplexNoise noise)
	{
		this.access = new BlockAccessExtendedWrapper(world);
		this.noise = noise;
		this.caveGenerator = new WorldGenAetherCaves();
	}

	public void generateFull(IAetherChunkColumnInfo info, ChunkSegmentMask mask, IIslandData island, int chunkX, int chunkY, int chunkZ,
			long seed)
	{
		this.generateCloudLayer(info, mask, chunkY);

		final IIslandGenerator generator = island.getGenerator();
		generator.generateChunkSegment(info, mask, island, chunkX, chunkY, chunkZ);

		this.replaceBiomeBlocks(info, mask, chunkY);

//		this.caveGenerator.generate(seed, chunkX, chunkY, chunkZ, mask, biomes, info);
//
//		if (island.getBiome() instanceof BiomeArcticPeaks)
//		{
//			this.veinGenerator.generate(this.world, chunkX, chunkY, chunkZ, mask, biomes);
//		}
	}

	private void generateCloudLayer(IAetherChunkColumnInfo info, final ChunkSegmentMask mask, final int chunkY)
	{
		int maxDepth = 8;
		int levelY = 70;

		int offsetY = chunkY * 16;

		IIslandChunkColumnInfo chunkInfo = info.getIslandData(0, IIslandChunkColumnInfo.class);

		IChunkNoiseBuffer cloudBuffer = chunkInfo.getCloudDepthBuffer();

		double threshold = 0.2;

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				final double val = cloudBuffer.get(x, z);

				if (val > threshold)
				{
					final int depth = (int) ((val - threshold) * maxDepth);

					final int m0a = Math.min(levelY + depth - offsetY, 15);
					final int m0b = Math.max(levelY - depth - offsetY, 0);

					for (int y = m0a; y >= m0b; y--)
					{
						mask.setBlock(x, y, z, IslandBlockType.CLOUD_BED_BLOCK.ordinal());
					}
				}
			}
		}
	}

	// Calculate max penetration depth
	private void replaceBiomeBlocks(IAetherChunkColumnInfo info, final ChunkSegmentMask mask, final int chunkY)
	{
		if (!mask.wasTouched())
		{
			return;
		}

		IIslandChunkColumnInfo chunkInfo = info.getIslandData(0, IIslandChunkColumnInfo.class);

		IChunkHeightmap heightmap = chunkInfo.getHeightmap();

		if (heightmap.isEmpty())
		{
			return;
		}

		int offsetY = chunkY * 16;

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				if (!chunkInfo.hasSoil(x, z))
				{
					continue;
				}

				int height = heightmap.getHeight(x, z);

				if (height == 0)
				{
					continue;
				}

				final int depth = (int) chunkInfo.getTerrainDepthBuffer().get(x, z);

				int m0a = Math.min(height - offsetY, 15);
				int m0b = Math.max((height - depth) - offsetY, 0);

				for (int y = m0a; y >= m0b; y--)
				{
					final int state = mask.getBlock(x, y, z);

					if (state == IslandBlockType.STONE_BLOCK.ordinal() || state == IslandBlockType.STONE_MOSSY_BLOCK.ordinal() || state == IslandBlockType.FERROSITE_BLOCK.ordinal())
					{
						int pentration = height - (offsetY + y);

						if (pentration == 0)
						{
							mask.setBlock(x, y, z, IslandBlockType.TOPSOIL_BLOCK.ordinal());
						}
						else
						{
							mask.setBlock(x, y, z, IslandBlockType.SOIL_BLOCK.ordinal());
						}
					}
				}
			}
		}
	}

	public void generateBaseTerrain(IAetherChunkColumnInfo info, ChunkSegmentMask mask, IIslandData island, int chunkX, int chunkY, int chunkZ, long seed)
	{
		island.getGenerator().generateChunkSegment(info, mask, island, chunkX, chunkY, chunkZ);

		this.replaceBiomeBlocks(info, mask, chunkY);
	}

	public IIslandChunkColumnInfo generateChunkColumnInfo(IIslandData island, int chunkX, int chunkZ)
	{
		return island.getGenerator().generateColumnInfo(this.noise, island, chunkX, chunkZ);
	}
}
