package com.gildedgames.aether.common.world.aether;

import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.IChunkInfoAether;
import com.gildedgames.aether.api.world.islands.IIslandChunkInfo;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.api.world.noise.IChunkNoiseBuffer2D;
import com.gildedgames.aether.api.world.preparation.IChunkMask;
import com.gildedgames.aether.common.world.biomes.arctic_peaks.BiomeArcticPeaks;
import com.gildedgames.aether.common.world.decorations.WorldGenUndergroundVeins;
import com.gildedgames.aether.common.world.decorations.caves.WorldGenAetherCaves;
import com.gildedgames.aether.common.world.island.IslandBlockType;
import com.gildedgames.aether.common.world.preparation.mask.ChunkMask;
import com.gildedgames.orbis.lib.processing.BlockAccessExtendedWrapper;
import com.gildedgames.orbis.lib.processing.IBlockAccessExtended;
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

	private final WorldGenUndergroundVeins veinGenerator;

	public WorldPreparationAether(World world, OpenSimplexNoise noise)
	{
		this.access = new BlockAccessExtendedWrapper(world);
		this.noise = noise;
		this.caveGenerator = new WorldGenAetherCaves();
		this.veinGenerator = new WorldGenUndergroundVeins();
	}

	public void generateFull(IChunkInfoAether info, ChunkMask mask, IIslandData island, int chunkX, int chunkZ, long seed)
	{
		this.generateCloudLayer(info, mask);

		final IIslandGenerator generator = island.getGenerator();
		generator.generateChunkSegment(info, mask, island, chunkX, chunkZ);

		byte[] heightmap = this.createHeightmap(mask);

		this.caveGenerator.generate(island.getCaveSystemGenerator(), chunkX, chunkZ, mask);

		this.replaceBiomeBlocks(info, mask, heightmap);

		if (island.getBiome() instanceof BiomeArcticPeaks)
		{
			this.veinGenerator.generate(seed, chunkX, chunkZ, mask);
		}
	}

	private void generateCloudLayer(IChunkInfoAether info, final ChunkMask mask)
	{
		int maxDepth = 8;
		int levelY = 70;

		IIslandChunkInfo chunkInfo = info.getIslandData(0, IIslandChunkInfo.class);

		IChunkNoiseBuffer2D cloudBuffer = chunkInfo.getCloudDepthBuffer();

		double threshold = 0.2;

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				final double val = cloudBuffer.get(x, z);

				if (val > threshold)
				{
					final int depth = (int) ((val - threshold) * maxDepth);

					for (int y = levelY + depth; y >= levelY - depth; y--)
					{
						mask.setBlock(x, y, z, IslandBlockType.CLOUD_BED_BLOCK.ordinal());
					}
				}
			}
		}
	}

	// Calculate max penetration depth
	private void replaceBiomeBlocks(IChunkInfoAether info, final IChunkMask mask, final byte[] heightmap)
	{
		IIslandChunkInfo chunkInfo = info.getIslandData(0, IIslandChunkInfo.class);

		int i = 0;

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++, i++)
			{
				int height = Byte.toUnsignedInt(heightmap[i]);

				if (height == 0)
				{
					continue;
				}

				int penetration = 0;

				final int depth = (int) chunkInfo.getTerrainDepthBuffer().get(x, z);

				int m0a = Math.min(height, 254);
				int m0b = Math.max((height - depth), 0);

				for (int y = m0a; y >= m0b; y--)
				{
					final int state = mask.getBlock(x, y, z);

					if (state == IslandBlockType.STONE_BLOCK.ordinal() || state == IslandBlockType.STONE_MOSSY_BLOCK.ordinal() || state == IslandBlockType.FERROSITE_BLOCK.ordinal())
					{
						if (penetration == 0 && mask.getBlock(x, y + 1, z) == IslandBlockType.AIR_BLOCK.ordinal())
						{
							mask.setBlock(x, y, z, IslandBlockType.TOPSOIL_BLOCK.ordinal());
						}
						else
						{
							mask.setBlock(x, y, z, IslandBlockType.SOIL_BLOCK.ordinal());
						}

						penetration++;
					}
					else
					{
						penetration = 0;
					}
				}
			}
		}
	}

	private byte[] createHeightmap(final IChunkMask mask)
	{
		byte[] heightmap = new byte[256];

		int i = 0;

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				int height = mask.getHighestBlock(x, z);

				heightmap[i++] = (byte) height;
			}
		}

		return heightmap;
	}

	public void generateBaseTerrain(IChunkInfoAether info, IChunkMask mask, IIslandData island, int chunkX, int chunkZ, long seed)
	{
		island.getGenerator().generateChunkSegment(info, mask, island, chunkX, chunkZ);

		byte[] heightmap = this.createHeightmap(mask);

		this.replaceBiomeBlocks(info, mask, heightmap);
	}

	public IIslandChunkInfo generateChunkColumnInfo(IIslandData island, int chunkX, int chunkZ)
	{
		return island.getGenerator().generateColumnInfo(this.noise, island, chunkX, chunkZ);
	}
}
