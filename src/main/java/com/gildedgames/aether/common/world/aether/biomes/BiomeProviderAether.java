package com.gildedgames.aether.common.world.aether.biomes;

import com.gildedgames.aether.common.registry.content.BiomesAether;
import com.gildedgames.aether.common.world.aether.prep.PrepSectorDataAether;
import com.gildedgames.orbis_api.preparation.IPrepManager;
import com.gildedgames.orbis_api.preparation.IPrepSector;
import com.gildedgames.orbis_api.preparation.IPrepSectorAccessAsync;
import com.gildedgames.orbis_api.preparation.impl.util.PrepHelper;
import com.gildedgames.orbis_api.world.WorldObjectManager;
import com.google.common.collect.Lists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.IntCache;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class BiomeProviderAether extends BiomeProvider
{

	public static ArrayList<Biome> allowedBiomes = Lists.newArrayList(BiomesAether.VOID, BiomesAether.HIGHLANDS, BiomesAether.MAGNETIC_HILLS);

	private final ThreadLocal<BiomeCache> cache = ThreadLocal.withInitial(() -> new BiomeCache(this));

	private final World world;

	public BiomeProviderAether(final World world)
	{
		this.world = world;
	}

	@Override
	public List<Biome> getBiomesToSpawnIn()
	{
		return allowedBiomes;
	}

	@Override
	public void cleanupCache()
	{
		this.cache.get().cleanupCache();
	}

	@Override
	public Biome getBiome(final BlockPos pos)
	{
		return this.getBiome(pos, BiomesAether.VOID);
	}

	@Override
	public Biome getBiome(final BlockPos pos, final Biome defaultBiome)
	{
		return this.cache.get().getBiome(pos.getX(), pos.getZ(), BiomesAether.VOID);
	}

	private Biome[] generateBiomes(final Biome[] biomes, final int x, final int z, final int width, final int height)
	{
		Arrays.fill(biomes, BiomesAether.VOID);

		IPrepManager manager = PrepHelper.getManager(this.world);

		if (manager == null)
		{
			return biomes;
		}

		IPrepSectorAccessAsync access = manager.access();

		IPrepSector cachedSector = null;

		int prevChunkX = 0, prevChunkY = 0;

		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				final int index = j + (i * width);

				final int posX = i + x;
				final int posZ = j + z;

				final int chunkX = Math.floorDiv(posX, 16);
				final int chunkY = Math.floorDiv(posZ, 16);

				if (chunkX != prevChunkX || chunkY != prevChunkY || cachedSector == null)
				{
					try
					{
						cachedSector = this.world.isRemote ? access.provideSector(chunkX, chunkY).get() : access.getLoadedSector(chunkX, chunkY).orElse(null);
					}
					catch (InterruptedException | ExecutionException e)
					{
						throw new RuntimeException(e);
					}

					prevChunkX = chunkX;
					prevChunkY = chunkY;
				}

				if (cachedSector != null && cachedSector.getData() instanceof PrepSectorDataAether)
				{
					PrepSectorDataAether sectorDataAether = (PrepSectorDataAether) cachedSector.getData();

					biomes[index] = sectorDataAether.getIslandData().getBiome();
				}
			}
		}

		return biomes;
	}

	/**
	 * Returns an array of biomes for the location input.
	 */
	@Override
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
	@Override
	public Biome[] getBiomes(@Nullable final Biome[] listToReuse, final int x, final int z, final int width, final int length)
	{
		return this.getBiomes(listToReuse, x, z, width, length, true);
	}

	/**
	 * Gets a list of biomes for the specified blocks.
	 */
	@Override
	public Biome[] getBiomes(@Nullable Biome[] listToReuse, final int x, final int z, final int width, final int length, final boolean cacheFlag)
	{
		IntCache.resetIntCache();

		if (listToReuse == null || listToReuse.length < width * length)
		{
			listToReuse = new Biome[width * length];
		}

		if (!WorldObjectManager.hasWorldSeed(this.world.provider.getDimension()))
		{
			Arrays.fill(listToReuse, BiomesAether.VOID);

			return listToReuse;
		}

		if (cacheFlag && width == 16 && length == 16 && (x & 15) == 0 && (z & 15) == 0)
		{
			final Biome[] abiome = this.cache.get().getCachedBiomes(x, z);

			System.arraycopy(abiome, 0, listToReuse, 0, width * length);

			return listToReuse;
		}
		else
		{
			this.generateBiomes(listToReuse, x, z, width, length);

			return listToReuse;
		}
	}

	@Override
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
	@Override
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
