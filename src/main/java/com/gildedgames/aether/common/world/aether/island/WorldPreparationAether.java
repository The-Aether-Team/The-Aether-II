package com.gildedgames.aether.common.world.aether.island;

import com.gildedgames.aether.api.util.NoiseUtil;
import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandDataPartial;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.common.world.aether.features.WorldGenAetherCaves;
import com.gildedgames.aether.common.world.aether.island.gen.IslandBlockType;
import com.gildedgames.orbis_api.preparation.impl.ChunkMask;
import com.gildedgames.orbis_api.processing.BlockAccessExtendedWrapper;
import com.gildedgames.orbis_api.processing.IBlockAccessExtended;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

import java.util.Random;

/**
 * This is like a ChunkGenerator, but instead using virtual chunks
 * Here we'll place the island for generation later
 */
public class WorldPreparationAether
{

	private final World world;

	private final IBlockAccessExtended access;

	private final Random rand;

	private final WorldGenAetherCaves caveGenerator;

	private final NoiseGeneratorPerlin surfaceNoise;

	private double[] depthBuffer;

	private OpenSimplexNoise noise;

	public WorldPreparationAether(final World world, final Random rand, OpenSimplexNoise noise)
	{
		this.world = world;
		this.rand = rand;

		this.noise = noise;

		this.access = new BlockAccessExtendedWrapper(this.world);

		this.surfaceNoise = new NoiseGeneratorPerlin(this.rand, 4);

		this.caveGenerator = new WorldGenAetherCaves();
	}

	public void generateBaseTerrain(Biome[] biomes, final ChunkPrimer primer, final IIslandData island, final int chunkX, final int chunkZ)
	{
		final int worldX = chunkX * 16;
		final int worldZ = chunkZ * 16;

		ChunkMask mask = new ChunkMask();

		//TODO: Interpolate this! Can increase performance a lot
		for (int x = 0; x < 16; x++)
		{
			final double nx = (worldX + x) / 70D;

			for (int z = 0; z < 16; z++)
			{
				final double nz = (worldZ + z) / 70D;

				final double val = NoiseUtil.normalise(NoiseUtil.something(this.noise, nx, nz));

				if (val > 0.2)
				{
					for (int y = 70; y < 70 + ((val - 0.2) * 10); y++)
					{
						mask.setBlock(x, y, z, IslandBlockType.CLOUD_BED_BLOCK.ordinal());
					}

					for (int y = 70; y > 70 - ((val - 0.2) * 10); y--)
					{
						mask.setBlock(x, y, z, IslandBlockType.CLOUD_BED_BLOCK.ordinal());
					}
				}
			}
		}

		final IIslandGenerator generator = island.getGenerator();
		generator.genMask(biomes, this.noise, this.access, mask, island, chunkX, chunkZ);

		this.replaceBiomeBlocks(island, mask, chunkX, chunkZ);

		this.caveGenerator.generate(this.world, chunkX, chunkZ, mask);

		generator.genChunk(biomes, this.noise, this.access, mask, primer, island, chunkX, chunkZ);
	}

	// Calculate max penetration depth
	public void replaceBiomeBlocks(final IIslandDataPartial island, final ChunkMask mask, final int chunkX, final int chunkZ)
	{
		// Penetration depth evalNormalised generation
		this.depthBuffer = this.surfaceNoise.getRegion(this.depthBuffer,
				(double) (chunkX * 16), (double) (chunkZ * 16), 16, 16, 0.0625D, 0.0625D, 1.0D);

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				final double val = this.depthBuffer[x + (z * 16)];

				// Calculate max penetration depth
				final int depth = (int) (val / 3.0D + 3.0D + this.rand.nextDouble() * 0.25D);

				int pentration = 0;
				int top;

				boolean searchingSolid = true;

				// Find top-most block
				for (int y = island.getBounds().getMaxY(); y > island.getBounds().getMinY(); y--)
				{
					if (!searchingSolid)
					{
						if (mask.getBlock(x, y, z) == IslandBlockType.AIR_BLOCK.ordinal())
						{
							searchingSolid = true;
						}

						continue;
					}

					if (mask.getBlock(x, y, z) != IslandBlockType.AIR_BLOCK.ordinal())
					{
						top = y;

						// Penetrate ground and set biome blocks
						for (int y1 = top; pentration <= depth & y1 > 0; y1--)
						{
							final int state = mask.getBlock(x, y1, z);

							if (state == IslandBlockType.STONE_BLOCK.ordinal() || state == IslandBlockType.FERROSITE_BLOCK.ordinal())
							{
								mask.setBlock(x, y1, z, pentration < 1 ? IslandBlockType.TOPSOIL_BLOCK.ordinal() : IslandBlockType.SOIL_BLOCK.ordinal());
							}

							pentration++;
						}

						searchingSolid = false;
						pentration = 0;
					}
				}
			}
		}
	}

	public void generateBaseTerrainMask(Biome[] biomes, ChunkMask mask, IIslandData island, int chunkX, int chunkZ)
	{
		island.getGenerator().genMask(biomes, this.noise, this.access, mask, island, chunkX, chunkZ);
		
		this.replaceBiomeBlocks(island, mask, chunkX, chunkZ);

		this.caveGenerator.generate(this.world, chunkX, chunkZ, mask);
	}
}
