package com.gildedgames.aether.common.world.dimensions.aether.island.logic;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.world.dimensions.aether.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.noise.OpenSimplexNoise;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class WorldGeneratorIsland
{

	private final World world;

	private final OpenSimplexNoise simplex;

	public WorldGeneratorIsland(World world)
	{
		this.world = world;
		this.simplex = new OpenSimplexNoise(world.getSeed());
	}

	public double getBias(double time, double bias)
	{
		return (time / ((((1.0/bias) - 2.0)*(1.0 - time))+1.0));
	}

	public void genIslandForChunk(ChunkPrimer primer, IslandData data, IslandSector sector, int chunkX, int chunkZ)
	{
		//Stopwatch watch = Stopwatch.createStarted();

		int posX = chunkX * 16;
		int posZ = chunkZ * 16;

		double width = (double) data.getBounds().width;
		double height = data.getHeight();
		double length = (double) data.getBounds().height;

		Biome biome = this.world.getBiome(new BlockPos(posX + 16, 0, posZ + 16));

		double minY = data.getMinY();

		for (double x = 0; x < 16; x++)
		{
			for (double z = 0; z < 16; z++)
			{
				double stepX = (double) posX + x;
				double stepZ = (double) posZ + z;

				double nx = (stepX + data.getBounds().getMinX()) / 300.0; // normalize coords
				double nz = (stepZ + data.getBounds().getMinY()) / 300.0;

				//double flat = GenUtil.octavedNoise(this.simplex, 4, 0.7D, 2.5D, nx, nz);

				double distNX = ((stepX - data.getBounds().getMinX()) / width) - 0.5; // Subtract sector coords from nx/ny so that the noise is within range of the island center
				double distNZ = ((stepZ - data.getBounds().getMinY()) / length) - 0.5;

				double noise1 = this.simplex.eval(nx, nz);
				double noise2 = 0.5 * this.simplex.eval(nx * 8D, nz * 8D);
				double noise3 = 0.25 * this.simplex.eval(nx * 16D, nz * 16D);
				double noise4 = 0.1 * this.simplex.eval(nx * 32D, nz * 32D);

				double value = (noise1 + noise2 + noise3 + noise4) / 4.0;

				double dist = 2.0 * Math.sqrt((distNX * distNX) + (distNZ * distNZ)); // Get distance from center of Island

				value = (value + 0.0) - (0.7 * Math.pow(dist, 4)); // Apply formula to shape noise into island, noise decreases in value the further the coord is from the center

				double heightValue = value + 1.0;//Math.pow(value, 0.7);

				double bottomHeight = 0.8 * height;
				double bottomMaxY = minY + bottomHeight;

				double topHeight = 0.2 * height;

				double cutoffPoint = 0.8;

				double bottomHeightMod = Math.min(1.0, (heightValue - cutoffPoint) * 1.4);

				if (heightValue > cutoffPoint)
				{
					for (double y = bottomMaxY; y > bottomMaxY - (bottomHeight * bottomHeightMod); y--)
					{
						if (heightValue < cutoffPoint + 0.05 && y == bottomMaxY - 1)
						{
							if (biome instanceof BiomeAetherBase)
							{
								BiomeAetherBase aetherBiome = (BiomeAetherBase)biome;

								primer.setBlockState((int) x, (int) y, (int) z, aetherBiome.getCoastalBlock());
							}
						}
						else
						{
							primer.setBlockState((int) x, (int) y, (int) z, BlocksAether.holystone.getDefaultState());
						}
					}

					for (double y = bottomMaxY; y < bottomMaxY + ((heightValue - cutoffPoint) * topHeight); y++)
					{
						if (heightValue < cutoffPoint + 0.05 && y < bottomMaxY + 1)
						{
							if (biome instanceof BiomeAetherBase)
							{
								BiomeAetherBase aetherBiome = (BiomeAetherBase)biome;

								primer.setBlockState((int) x, (int) y, (int) z, aetherBiome.getCoastalBlock());
							}
						}
						else
						{
							primer.setBlockState((int) x, (int) y, (int) z, BlocksAether.holystone.getDefaultState());
						}
					}
				}

				for (double y = minY + (height * 0.5); y < minY + height; y++)
				{
					double stepY = y - minY - (height * 0.55);

					double ny = (stepY) / (height - (height * 0.55)) - 0.5;

					double noise3d1 = this.simplex.eval(nx * 8.0, ny * 8.0 / 1.8, nz * 8.0);
					double noise3d2 = 0.5 * this.simplex.eval(nx * 16.0, ny * 16.0 / 1.8, nz * 16.0);

					double value3d = noise3d1 + noise3d2;

					double dist3d = 2.0 * Math.sqrt((distNX * distNX) + (ny * ny) + (distNZ * distNZ)); // Get distance from center of Island

					value3d = (value3d + 0.10) - (1.65 * Math.pow(dist3d, 1.50)); // Apply formula to shape noise into island, noise decreases in value the further the coord is from the center

					value3d -= dist;

					value3d = Math.min(1.0D, Math.max(-1.0D, value3d)); // Prevents noise from dropping below its minimum value

					if (value3d > -0.8)
					{
						//if (y < bottomMaxY + ((heightValue - 0.8) * topHeight))
						//{
						/*if (y < bottomMaxY + 2)
						{
							primer.setBlockState((int) x, (int) y, (int) z, BlocksAether.quicksoil.getDefaultState());
						}
						else*/
						{
							primer.setBlockState((int) x, (int) y, (int) z, BlocksAether.holystone.getDefaultState());
						}
						//}

						//primer.setBlockState((int) x, (int) y, (int) z, BlocksAether.holystone.getDefaultState());
					}
				}
			}
		}

		//System.out.println(watch.stop());
	}

}
