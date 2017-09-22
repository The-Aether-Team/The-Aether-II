package com.gildedgames.aether.common.world.aether.island.data;

import com.gildedgames.aether.api.world.islands.IIslandBounds;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.common.registry.content.BiomesAether;
import net.minecraft.world.World;

import java.util.ArrayList;

public class IslandSectorFactory
{

	public static IslandSector create(final World world, final int sectorX, final int sectorY, final long seed)
	{
		final int islandCount = 1;

		final ArrayList<IIslandData> islands = new ArrayList<>(islandCount);

		for (int i = 0; i < islandCount; i++)
		{
			final int sectorArea = IslandSector.CHUNK_SIZE * 16;

			final int width = 640;
			final int height = 120;
			final int length = 640;

			final int x = (sectorArea * sectorX);
			final int y = 10 /*+ (i * 60)*/;
			final int z = (sectorArea * sectorY);

			final IIslandBounds bounds = new IslandBounds(x, y, z, x + width, y + height, z + length);

			final long islandSeed = seed ^ ((long) x * 341873128712L + (long) z * 132897987541L) & ((long) i * 341873128712L);

			final IslandData island = new IslandData(world, bounds, BiomesAether.HIGHLANDS, islandSeed);

			islands.add(island);
		}

		return new IslandSector(world, sectorX, sectorY, seed, islands);
	}

}
