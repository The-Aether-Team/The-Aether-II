package com.gildedgames.aether.common.world.aether.island.data;

import com.gildedgames.aether.api.world.islands.IIslandBounds;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import com.google.common.collect.Lists;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class IslandSectorFactory
{
	public static List<BiomeAetherBase> POSSIBLE_BIOMES = Lists.newArrayList();

	public static void registerPossibleBiome(final BiomeAetherBase... biomes)
	{
		POSSIBLE_BIOMES.addAll(Arrays.asList(biomes));
	}

	public static float getTotalBiomeWeight()
	{
		float total = 0.0F;

		for (final BiomeAetherBase b : POSSIBLE_BIOMES)
		{
			total += b.getRarityWeight();
		}

		return total;
	}

	public static BiomeAetherBase fetchRandomBiome(final Random rand)
	{
		final float randomValue = rand.nextFloat() * getTotalBiomeWeight();
		float chanceSum = 0.0F;

		for (final BiomeAetherBase b : POSSIBLE_BIOMES)
		{
			if (b.getRarityWeight() + chanceSum >= randomValue)
			{
				return b;
			}

			chanceSum += b.getRarityWeight();
		}

		return null;
	}

	public static IslandSector create(final World world, final int sectorX, final int sectorY, final long seed)
	{
		final int islandCount = 1;

		final ArrayList<IIslandData> islands = new ArrayList<>(islandCount);

		for (int i = 0; i < islandCount; i++)
		{
			final int sectorArea = IslandSector.CHUNK_SIZE * 16;

			final int width = 640;
			final int height = 240;
			final int length = 640;

			final int x = (sectorArea * sectorX);
			final int y = 10 /*+ (i * 60)*/;
			final int z = (sectorArea * sectorY);

			final IIslandBounds bounds = new IslandBounds(x, y, z, x + width, y + height, z + length);

			final long islandSeed = seed ^ ((long) x * 341873128712L + (long) z * 132897987541L) & ((long) i * 341873128712L);

			final Random rand = new Random(islandSeed);

			final BiomeAetherBase chosen = fetchRandomBiome(rand);

			final IslandData island = new IslandData(world, bounds, chosen,
					islandSeed);

			island.addComponents(chosen.createIslandComponents(island));

			islands.add(island);
		}

		return new IslandSector(world, sectorX, sectorY, seed, islands);
	}

}
