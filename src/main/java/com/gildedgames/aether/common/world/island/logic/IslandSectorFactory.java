package com.gildedgames.aether.common.world.island.logic;

import com.gildedgames.aether.common.registry.IslandBiomeSets;
import com.gildedgames.aether.common.registry.minecraft.BiomesAether;

import java.awt.*;
import java.util.Random;

public class IslandSectorFactory
{

	private IslandSectorFactory() {}

	public static IslandSector create(int sectorX, int sectorY, long seed)
	{
		Random rand = new Random(seed);

		int islandCount = 1;//0 + rand.nextInt(5);

		IslandData[] data = new IslandData[islandCount];

		for (int i = 0; i < islandCount; i++)
		{
			final int sectorArea = IslandSector.CHUNK_WIDTH_PER_SECTOR * 16;

			int width = 320;
			int height = 320;

			int x = (sectorArea * sectorX) + Math.max(0, rand.nextInt(sectorArea) - width);
			int y = (sectorArea * sectorY) + Math.max(0, rand.nextInt(sectorArea) - height);

			Rectangle bounds = new Rectangle(x, y, width, height);

			//int type = rand.nextInt(4);

			IslandBiomeSet set = IslandBiomeSets.HIGHLANDS;

			/*switch(type)
			{
				case 0:
				{
					set = IslandBiomeSets.HIGHLANDS;
					break;
				}
				case 1:
				{
					set = IslandBiomeSets.ENCHANTED_HIGHLANDS;
					break;
				}
				case 2:
				{
					set = IslandBiomeSets.FROSTPINE;
					break;
				}
				case 3:
				{
					set = IslandBiomeSets.BLIGHTED_HIGHLANDS;
					break;
				}
			}*/

			IslandData islandData = new IslandData(bounds, 10, 100 + rand.nextInt(20), set);

			data[i] = islandData;
		}

		IslandSectorFactory.separate(data, sectorX, sectorY, 40);

		IslandSector sector = new IslandSector(sectorX, sectorY, seed, data);

		return sector;
	}

	private static IslandData[] separate(IslandData[] data, int sectorX, int sectorY, int seperationIterations)
	{
		for (int iter = 0; iter < seperationIterations; iter++)//This part uses separation behavior to separate intersecting islands
		{
			for (IslandData island1 : data)
			{
				if (island1.isAsleep())
				{
					continue;
				}

				double vx = 0;
				double vz = 0;
				int overlaps = 0;
				double room1x = island1.getBounds().getCenterX();
				double room1z = island1.getBounds().getCenterY();
				double largestOverlap = 0;

				for (IslandData island2 : data)
				{
					if (island1 == island2)
					{
						continue;
					}

					Rectangle intersection = island1.getBounds().intersection(island2.getBounds());

					if (intersection.isEmpty())
					{
						continue;
					}

					vx += island2.getBounds().getCenterX() - room1x;
					vz += island2.getBounds().getCenterY() - room1z;

					double overlap = intersection.getWidth() > intersection.getHeight() ? intersection.getWidth() : intersection.getHeight();

					largestOverlap = largestOverlap > overlap ? largestOverlap : overlap;

					overlaps++;
					island2.setAsleep(false);
				}

				if (overlaps == 0)
				{
					island1.setAsleep(true);

					continue;
				}

				if (vx == 0 && vz == 0)
				{
					continue;
				}

				vx *= -1;
				vz *= -1;

				double length = Math.sqrt(vx * vx + vz * vz);//Normalizes the vector

				vx /= length;
				vz /= length;

				vx *= largestOverlap;
				vz *= largestOverlap;

				island1.getBounds().setLocation((int)(island1.getBounds().getX() + vx), (int)(island1.getBounds().getY() + vz));
			}
		}

		for (IslandData island1 : data)
		{
			for (IslandData island2 : data)
			{
				if (island1 == island2 || island2.toRemove())
				{
					continue;
				}

				if (island1.getBounds().intersects(island2.getBounds()))
				{
					island1.setToRemove(true);

					break;
				}
			}
		}

		final int sectorArea = IslandSector.CHUNK_WIDTH_PER_SECTOR * 16;

		int minX = (sectorArea * sectorX);
		int minY = (sectorArea * sectorY);

		int maxX = minX + sectorArea;
		int maxY = minY + sectorArea;

		for (IslandData island : data)
		{
			if (island.getBounds().getMaxX() > maxX || island.getBounds().getMaxY() > maxY || island.getBounds().getMinX() < minX || island.getBounds().getMinY() < minY)
			{
				island.setToRemove(true);
			}
		}

		int p = 0;

		while (p < data.length) // Removes islands which are still intersecting
		{
			if (data[p] != null && data[p].toRemove())
			{
				data[p] = null;
			}
			else
			{
				p++;
			}
		}

		return data;
	}

}
