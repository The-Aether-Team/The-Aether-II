package com.gildedgames.aether.common.world.aether.island;

import com.gildedgames.aether.api.util.NoiseUtil;
import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.common.util.ChunkNoiseGenerator;
import com.gildedgames.aether.common.world.aether.biomes.arctic_peaks.BiomeArcticPeaks;
import com.gildedgames.aether.common.world.aether.features.WorldGenAetherCaves;
import com.gildedgames.aether.common.world.aether.features.WorldGenUndergroundVeins;
import com.gildedgames.aether.common.world.aether.island.gen.IslandBlockType;
import com.gildedgames.orbis_api.preparation.impl.ChunkMask;
import com.gildedgames.orbis_api.processing.BlockAccessExtendedWrapper;
import com.gildedgames.orbis_api.processing.IBlockAccessExtended;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

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

	private final WorldGenUndergroundVeins veinGenerator;

	private final OpenSimplexNoise surfaceNoise;

	private final OpenSimplexNoise noise;

	public WorldPreparationAether(final World world, final Random rand, OpenSimplexNoise noise)
	{
		this.world = world;
		this.rand = rand;

		this.noise = noise;

		this.access = new BlockAccessExtendedWrapper(this.world);

		this.surfaceNoise = new OpenSimplexNoise(world.getSeed() ^ 745684654L);

		this.caveGenerator = new WorldGenAetherCaves();

		this.veinGenerator = new WorldGenUndergroundVeins();
	}

	public void generateBaseTerrain(Biome[] biomes, final ChunkPrimer primer, final IIslandData island, final int chunkX, final int chunkZ)
	{
		ChunkMask mask = new ChunkMask();

		this.generateCloudLayer(island, mask, chunkX, chunkZ);

		final IIslandGenerator generator = island.getGenerator();
		generator.genMask(biomes, this.noise, this.access, mask, island, chunkX, chunkZ);

		this.replaceBiomeBlocks(island, mask, chunkX, chunkZ);

		this.caveGenerator.generate(this.world, chunkX, chunkZ, mask);

		if (island.getBiome() instanceof BiomeArcticPeaks)
		{
			this.veinGenerator.generate(this.world, chunkX, chunkZ, mask);
		}

		generator.genChunk(biomes, this.noise, this.access, mask, primer, island, chunkX, chunkZ);
	}

	private void generateCloudLayer(final IIslandData island, final ChunkMask mask, final int chunkX, final int chunkZ)
	{
		ChunkNoiseGenerator cloudBuffer = this.createCloudBuffer(island, chunkX, chunkZ, 70.0D);

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				final double val = cloudBuffer.interpolate(x, z);

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
	}

	// Calculate max penetration depth
	public void replaceBiomeBlocks(final IIslandData island, final ChunkMask mask, final int chunkX, final int chunkZ)
	{
		ChunkNoiseGenerator depthBuffer = this.createDepthBuffer(island, chunkX, chunkZ, 0.0625D);

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				final double val = depthBuffer.interpolate(x, z);

				// Calculate max penetration depth
				final int depth = (int) (val / 3.0D + 3.0D + this.rand.nextDouble() * 0.25D);

				int pentration = 0;

				int top = mask.getTopBlock(x, z);

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
			}
		}
	}

	private ChunkNoiseGenerator createDepthBuffer(IIslandData island, int chunkX, int chunkZ, double scale)
	{
		return new ChunkNoiseGenerator(this.surfaceNoise, chunkX * 16, chunkZ * 16, 4, 5, island.getBounds().getMinX(), island.getBounds().getMinZ(), scale)
		{
			@Override
			protected double sample(double nx, double nz)
			{
				return this.generator.eval(nx, nz);
			}
		};
	}

	private ChunkNoiseGenerator createCloudBuffer(IIslandData island, int chunkX, int chunkZ, double scale)
	{
		return new ChunkNoiseGenerator(this.noise, chunkX * 16, chunkZ * 16, 4, 5, 0, 0, scale)
		{
			@Override
			protected double sample(double nx, double nz)
			{
				return NoiseUtil.normalise(NoiseUtil.something(this.generator, nx, nz));
			}
		};
	}

	public void generateBaseTerrainMask(Biome[] biomes, ChunkMask mask, IIslandData island, int chunkX, int chunkZ)
	{
		island.getGenerator().genMask(biomes, this.noise, this.access, mask, island, chunkX, chunkZ);

		this.replaceBiomeBlocks(island, mask, chunkX, chunkZ);

		this.caveGenerator.generate(this.world, chunkX, chunkZ, mask);

		if (island.getBiome() instanceof BiomeArcticPeaks)
		{
			this.veinGenerator.generate(this.world, chunkX, chunkZ, mask);
		}
	}
}
