package com.gildedgames.aether.common.world.dimensions.aether.biomes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.gildedgames.aether.common.registry.content.BiomesAether;
import com.gildedgames.aether.common.world.dimensions.aether.island.logic.IslandData;
import com.gildedgames.aether.common.world.dimensions.aether.island.logic.IslandSector;
import com.gildedgames.aether.common.world.dimensions.aether.island.logic.IslandSectorAccess;
import com.google.common.collect.Lists;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.IntCache;

public class BiomeProviderAether extends BiomeProvider
{

	public static ArrayList<Biome> allowedBiomes = Lists.newArrayList(BiomesAether.VOID, BiomesAether.HIGHLANDS);

	private final BiomeCache cache;

	private final World world;

	private final Random rand;

	public BiomeProviderAether(final World world)
	{
		this.world = world;
		this.cache = new BiomeCache(this);
		this.rand = new Random();

		final Random rand = new Random(world.getSeed());
	}

	public List<Biome> getBiomesToSpawnIn()
	{
		return allowedBiomes;
	}

	@Override
	public void cleanupCache()
	{
		this.cache.cleanupCache();
	}

	@Override
	public Biome getBiome(final BlockPos pos)
	{
		return this.getBiome(pos, BiomesAether.VOID);
	}

	@Override
	public Biome getBiome(final BlockPos pos, final Biome defaultBiome)
	{
		return this.cache.getBiome(pos.getX(), pos.getZ(), BiomesAether.VOID);
	}

	private Biome[] generateBiomes(final Biome[] biomes, final int x, final int z, final int width, final int height)
	{
		IslandSector sector = null;

		int prevChunkX = 0, prevChunkY = 0;

		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				final int index = j + (i * width);

				final int posX = i + x;
				final int posZ = j + z;

				final int chunkX = posX / 16;
				final int chunkY = posZ / 16;

				biomes[index] = BiomesAether.VOID;

				if (this.world.isRemote)
				{
					continue;
				}

				if (chunkX != prevChunkX || chunkY != prevChunkY || sector == null)
				{
					long sectorSeed = 0;

					if (!IslandSectorAccess.inst().wasSectorEverCreatedInChunk(this.world, chunkX, chunkY))
					{
						this.rand.setSeed((long) chunkX * 341873128712L + (long) chunkY * 132897987541L);

						sectorSeed = this.rand.nextLong();
					}

					sector = IslandSectorAccess.inst().attemptToLoadSectorInChunk(this.world, chunkX, chunkY, sectorSeed);

					prevChunkX = chunkX;
					prevChunkY = chunkY;
				}

				if (sector != null)
				{
					final List<IslandData> islands = sector.getIslandDataAtBlockPos(posX, posZ);

					if (islands.size() > 0)
					{
						final IslandData island = islands.get(0);

						if (island != null)
						{
							biomes[index] = island.getBiome();
						}
					}
				}
			}
		}

		return biomes;
	}

	/**
	 * Returns an array of biomes for the location input.
	 */
	public Biome[] getBiomesForGeneration(Biome[] biomes, final int x, final int z, final int width, final int height)
	{
		IntCache.resetIntCache();

		if (biomes == null || biomes.length < width * height)
		{
			biomes = new Biome[width * height];
		}

		this.generateBiomes(biomes, x, z, width, height);

		return biomes;
	}

	/**
	 * Gets biomes to use for the blocks and loads the other data like tempNoise and humidity onto the
	 * WorldChunkManager.
	 */
	public Biome[] getBiomes(@Nullable final Biome[] listToReuse, final int x, final int z, final int width, final int length)
	{
		return this.getBiomes(listToReuse, x, z, width, length, true);
	}

	/**
	 * Gets a list of biomes for the specified blocks.
	 */
	public Biome[] getBiomes(@Nullable Biome[] listToReuse, final int x, final int z, final int width, final int length, final boolean cacheFlag)
	{
		IntCache.resetIntCache();

		if (listToReuse == null || listToReuse.length < width * length)
		{
			listToReuse = new Biome[width * length];
		}

		if (cacheFlag && width == 16 && length == 16 && (x & 15) == 0 && (z & 15) == 0)
		{
			final Biome[] abiome = this.cache.getCachedBiomes(x, z);
			System.arraycopy(abiome, 0, listToReuse, 0, width * length);

			return listToReuse;
		}
		else
		{
			this.generateBiomes(listToReuse, x, z, width, length);

			return listToReuse;
		}
	}

	@Nullable
	public BlockPos findBiomePosition(final int x, final int z, final int range, final List<Biome> biomes, final Random random)
	{
		for (final Biome biome : biomes)
		{
			if (!allowedBiomes.contains(biome))
			{
				return null;
			}
		}

		return new BlockPos(x - range + random.nextInt(range * 2 + 1), 0, z - range + random.nextInt(range * 2 + 1));
	}

	/**
	 * checks given Chunk's Biomes against List of allowed ones
	 */
	public boolean areBiomesViable(final int x, final int z, final int radius, final List<Biome> allowed)
	{
		for (final Biome biome : allowed)
		{
			if (!allowedBiomes.contains(biome))
			{
				return false;
			}
		}

		return true;
	}

}
