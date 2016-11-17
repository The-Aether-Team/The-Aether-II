package com.gildedgames.aether.common.world.dimensions.aether.biomes;

import com.gildedgames.aether.common.registry.minecraft.BiomesAether;
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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BiomeProviderAether extends BiomeProvider
{

	public static ArrayList<Biome> allowedBiomes = Lists.newArrayList(BiomesAether.VOID, BiomesAether.HIGHLANDS);

	private final BiomeCache cache;

	private final World world;

	public BiomeProviderAether(World world)
	{
		this.world = world;
		this.cache = new BiomeCache(this);

		Random rand = new Random(world.getSeed());
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
		return this.getBiome(pos, BiomesAether.VOID);
	}

	@Override
	public Biome getBiome(BlockPos pos, Biome defaultBiome)
	{
		return this.cache.getBiome(pos.getX(), pos.getZ(), BiomesAether.VOID);
	}

	private Biome[] generateBiomes(Biome[] biomes, int x, int z, int width, int height)
	{
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				int index = j + (i * width);

				int posX = i + x;
				int posZ = j + z;

				int sectorX = IslandSectorAccess.inst().getSectorCoord(posX / 16);
				int sectorY = IslandSectorAccess.inst().getSectorCoord(posZ / 16);

				biomes[index] = BiomesAether.VOID;

				if (this.world.isRemote)
				{
					continue;
				}

				IslandSector sector = IslandSectorAccess.inst().attemptToLoadSector(this.world, sectorX, sectorY);

				if (sector != null)
				{
					List<IslandData> islands = sector.getIslandDataAtBlockPos(posX, posZ);

					if (islands.size() > 0)
					{
						IslandData island = islands.get(0);

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
	public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int height)
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
			this.generateBiomes(listToReuse, x, z, width, length);

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
