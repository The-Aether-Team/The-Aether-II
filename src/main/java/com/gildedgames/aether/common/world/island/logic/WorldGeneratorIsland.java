package com.gildedgames.aether.common.world.island.logic;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.util.OpenSimplexNoise;
import com.gildedgames.aether.common.world.GenUtil;
import com.google.common.base.Stopwatch;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import org.apache.commons.lang3.time.StopWatch;

import java.util.Random;

import static org.apache.http.HttpHeaders.DEPTH;

public class WorldGeneratorIsland
{

	private final OpenSimplexNoise simplex;

	public WorldGeneratorIsland(World world)
	{
		this.simplex = new OpenSimplexNoise(world.getSeed());
	}

	public void genIslandForChunk(ChunkPrimer primer, IslandData data, IslandSector sector, int chunkX, int chunkZ)
	{
		int posX = chunkX * 16;
		int posZ = chunkZ * 16;

		double width = (double)data.getBounds().width;
		double height = data.getHeight();
		double length = (double)data.getBounds().height;

		double minY = 10;

		for (double x = 0; x < 16; x++)
		{
			for (double z = 0; z < 16; z++)
			{
				double stepX = (double) posX - data.getBounds().getMinX() + x;
				double stepZ = (double) posZ - data.getBounds().getMinY() + z;

				double nx = (stepX) / width - 0.5 + (double) sector.getSectorX(); // normalize coords
				double nz = (stepZ) / length - 0.5 + (double) sector.getSectorY();

				double noise1 = this.simplex.eval(nx, nz);
				double noise2 = 0.5 * this.simplex.eval(nx * 8D, nz * 8D);
				double noise3 = 0.25 * this.simplex.eval(nx * 16D, nz * 16D);
				double noise4 = 0.1 * this.simplex.eval(nx * 32D, nz * 32D);

				double value = (noise1 + noise2 + noise3 + noise4) / 3.0D;

				double distNX = nx - (double) sector.getSectorX(); // Subtract sector coords from nx/ny so that the noise is within range of the island center
				double distNZ = nz - (double) sector.getSectorY();
				double dist = 2.0 * Math.sqrt((distNX * distNX) + (distNZ * distNZ)); // Get distance from center of Island

				value = (value + 0.0) - (0.7 * Math.pow(dist, 6)); // Apply formula to shape noise into island, noise decreases in value the further the coord is from the center

				double heightValue = value + 1.0;

				if (heightValue > 0.8)
				{
					double bottomHeight = 0.8 * height;
					double bottomMaxY = minY + bottomHeight;

					double topHeight = 0.2 * height;

					for (double y = bottomMaxY; y < bottomMaxY + ((heightValue - 0.8) * topHeight); y++)
					{
						primer.setBlockState((int) x, (int) y, (int) z, BlocksAether.holystone.getDefaultState());
					}

					for (double y = bottomMaxY; y > bottomMaxY - ((heightValue - 0.8) * bottomHeight); y--)
					{
						primer.setBlockState((int) x, (int) y, (int) z, BlocksAether.holystone.getDefaultState());
					}
				}

				/*for (double y = minY; y < minY + height; y++)
				{
					double stepY = y - minY;

					double ny = (stepY) / height - 0.5;

					double value = GenUtil.octavedNoise3D(this.simplex, 4, 0.7D, 2.5D, nx, ny / 1.8D, nz);

					double dist = 2.0 * Math.sqrt((distNX * distNX) + (ny * ny) + (distNZ * distNZ)); // Get distance from center of Island

					value = (value + 0.10) - (1.65 * Math.pow(dist, 1.50)); // Apply formula to shape noise into island, noise decreases in value the further the coord is from the center

					value = Math.min(1.0D, Math.max(-1.0D, value)); // Prevents noise from dropping below its minimum value

					if (flat < 0.0)
					{
						//value += flat * 4;
					}

					value = Math.min(1.0D, Math.max(-1.0D, value));

					if (value > -0.8)
					{
						primer.setBlockState((int)x, (int)y, (int)z, BlocksAether.holystone.getDefaultState());
					}
				}*/
			}
		}
	}

}
