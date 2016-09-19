package com.gildedgames.aether.common.world.biome;

import com.gildedgames.aether.common.registry.minecraft.BiomesAether;
import com.gildedgames.aether.common.world.island.logic.IslandData;
import com.gildedgames.aether.common.world.island.logic.IslandSector;
import com.gildedgames.aether.common.world.island.logic.IslandSectorAccess;
import com.google.common.collect.Lists;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.IntCache;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BiomeProviderAether extends BiomeProvider
{

	public static List<Biome> allowedBiomes = Lists.newArrayList(BiomesAether.BIOME_AETHER, BiomesAether.BIOME_AETHER_ENCHANTED);

	private final BiomeCache cache;

	public BiomeProviderAether()
	{
		this.cache = new BiomeCache(this);
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
	public Biome getBiome(BlockPos pos)
	{
		return this.getBiome(pos, (Biome)null);
	}

	@Override
	public Biome getBiome(BlockPos pos, Biome defaultBiome)
	{
		return this.cache.getBiome(pos.getX(), pos.getZ(), defaultBiome);
	}

	/**
	 * Returns an array of biomes for the location input.
	 */
	public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int height)
	{
		IntCache.resetIntCache();

		if (biomes == null || biomes.length < width * height)
		{
			biomes = new Biome[width * height];
		}

		for (int i = 0; i < width * height; ++i)
		{
			//int posX = x +

			int sectorX = IslandSectorAccess.inst().getSectorCoord(x / 16);
			int sectorY = IslandSectorAccess.inst().getSectorCoord(z / 16);

			IslandSector sector = IslandSectorAccess.inst().attemptToLoadSector(sectorX, sectorY);

			if (sector != null)
			{
				IslandData island = sector.getIslandDataAtBlockPos(x, z);

				if (island != null)
				{
					biomes[i] = island.getBiome();
				}
				else
				{
					biomes[i] = BiomesAether.BIOME_AETHER_VOID;
				}
			}
			else
			{
				biomes[i] = BiomesAether.BIOME_AETHER_VOID;
			}
		}

		return biomes;
	}

	/**
	 * Gets biomes to use for the blocks and loads the other data like temperature and humidity onto the
	 * WorldChunkManager.
	 */
	public Biome[] getBiomes(@Nullable Biome[] listToReuse, int x, int z, int width, int length)
	{
		return this.getBiomes(listToReuse, x, z, width, length, true);
	}

	/**
	 * Gets a list of biomes for the specified blocks.
	 */
	public Biome[] getBiomes(@Nullable Biome[] listToReuse, int x, int z, int width, int length, boolean cacheFlag)
	{
		IntCache.resetIntCache();

		if (listToReuse == null || listToReuse.length < width * length)
		{
			listToReuse = new Biome[width * length];
		}

		if (cacheFlag && width == 16 && length == 16 && (x & 15) == 0 && (z & 15) == 0)
		{
			Biome[] abiome = this.cache.getCachedBiomes(x, z);
			System.arraycopy(abiome, 0, listToReuse, 0, width * length);

			return listToReuse;
		}
		else
		{
			for (int i = 0; i < width * length; ++i)
			{
				int sectorX = IslandSectorAccess.inst().getSectorCoord(x / 16);
				int sectorY = IslandSectorAccess.inst().getSectorCoord(z / 16);

				IslandSector sector = IslandSectorAccess.inst().attemptToLoadSector(sectorX, sectorY);

				if (sector != null)
				{
					IslandData island = sector.getIslandDataAtBlockPos(x, z);

					if (island != null)
					{
						listToReuse[i] = island.getBiome();
					}
					else
					{
						listToReuse[i] = BiomesAether.BIOME_AETHER_VOID;
					}
				}
				else
				{
					listToReuse[i] = BiomesAether.BIOME_AETHER_VOID;
				}
			}

			return listToReuse;
		}
	}

	@Nullable
	public BlockPos findBiomePosition(int x, int z, int range, List<Biome> biomes, Random random)
	{
		for (Biome biome : biomes)
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
	public boolean areBiomesViable(int x, int z, int radius, List<Biome> allowed)
	{
		for (Biome biome : allowed)
		{
			if (!allowedBiomes.contains(biome))
			{
				return false;
			}
		}

		return true;
	}

}
