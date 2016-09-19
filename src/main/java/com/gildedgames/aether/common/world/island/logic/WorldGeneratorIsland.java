package com.gildedgames.aether.common.world.island.logic;

import com.gildedgames.aether.common.world.GenUtil;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

import java.util.Random;

public class WorldGeneratorIsland
{

	private final NoiseGeneratorPerlin heightNoiseGen;

	private final Random rand;

	public WorldGeneratorIsland(Random rand)
	{
		this.rand = rand;
		this.heightNoiseGen = new NoiseGeneratorPerlin(rand, 1);
	}

	public double[] genHeightMapForChunk(IslandData data, IslandSector sector, int chunkX, int chunkZ, double[] heightMap)
	{
		if (heightMap != null && heightMap.length >= 256)
		{
			for (int i = 0; i < heightMap.length; ++i)
			{
				heightMap[i] = 0.0D;
			}
		}
		else
		{
			heightMap = new double[256];
		}

		int posX = chunkX * 16;
		int posZ = chunkZ * 16;

		double width = (double)data.getBounds().width;
		double height = (double)data.getBounds().height;

		for (double x = 0; x < 16; x++)
		{
			for (double y = 0; y < 16; y++)
			{
				double stepX = (double)posX - data.getBounds().getMinX() + x;
				double stepY = (double)posZ - data.getBounds().getMinY() + y;

				double freq = 1D; // changing this value will zoom noise in or out

				double nx = (stepX) / width - 0.5 + (double)sector.getSectorX(); // normalize coords
				double ny = (stepY) / height - 0.5 + (double)sector.getSectorY();

				double value = GenUtil.octavedNoise(heightNoiseGen, 8, 0.5D, 1.5D, freq * nx, freq * ny);

				double distNX = nx - (double)sector.getSectorX(); // Subtract sector coords from nx/ny so that the noise is within range of the island center
				double distNY = ny - (double)sector.getSectorY();

				double dist = 2 * Math.sqrt(distNX*distNX + distNY*distNY); // Get distance from center of Island

				value = (value + 0.05) - (1.40 * Math.pow(dist, 4.00)); // Apply formula to shape noise into island, noise decreases in value the further the coord is from the center

				value = Math.max(-1.0D, value); // Prevents noise from dropping below its minimum value

				heightMap[(int)(y  + (x * 16))] = value;
			}
		}

		return heightMap;
	}

}
