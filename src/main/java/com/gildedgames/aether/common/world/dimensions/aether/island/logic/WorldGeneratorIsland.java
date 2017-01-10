package com.gildedgames.aether.common.world.dimensions.aether.island.logic;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.world.dimensions.aether.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.noise.OpenSimplexNoise;
import net.minecraft.block.state.IBlockState;
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
		return (time / ((((1.0 / bias) - 2.0) * (1.0 - time)) + 1.0));
	}

	public void genIslandForChunk(ChunkPrimer primer, IslandData data, IslandSector sector, int chunkX, int chunkZ)
	{
		int posX = chunkX * 16;
		int posZ = chunkZ * 16;

		double width = (double) data.getBounds().width;
		double height = data.getHeight();
		double length = (double) data.getBounds().height;

		Biome biome = this.world.getBiome(new BlockPos(posX + 16, 0, posZ + 16));

		double minY = data.getMinY();

		IBlockState coastBlock = ((BiomeAetherBase) biome).getCoastalBlock();

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				double stepX = (double) posX + x;
				double stepZ = (double) posZ + z;

				// normalize coords
				double nx = (stepX + data.getBounds().getMinX()) / 300.0D;
				double nz = (stepZ + data.getBounds().getMinY()) / 300.0D;

				// Subtract sector coords from nx/ny so that the noise is within range of the island center
				double distNX = ((stepX - data.getBounds().getMinX()) / width) - 0.5D;
				double distNZ = ((stepZ - data.getBounds().getMinY()) / length) - 0.5D;

				// Generate noise for X/Z coordinate
				double noise1 = this.simplex.eval(nx, nz);
				double noise2 = 0.5 * this.simplex.eval(nx * 8D, nz * 8D);
				double noise3 = 0.25 * this.simplex.eval(nx * 16D, nz * 16D);
				double noise4 = 0.1 * this.simplex.eval(nx * 32D, nz * 32D);

				double value = (noise1 + noise2 + noise3 + noise4) / 4.0;

				// Get distance from center of Island
				double dist = 2.0 * Math.sqrt((distNX * distNX) + (distNZ * distNZ));

				// Apply formula to shape noise into island, noise decreases in value the further the coord is from the center
				value = value - (0.7 * Math.pow(dist, 4));

				double heightValue = value + 1.0;

				double bottomHeight = 0.8 * height;
				double bottomMaxY = minY + bottomHeight;

				double topHeight = 0.2 * height;

				double cutoffPoint = 0.8;

				double bottomHeightMod = Math.min(1.0, (heightValue - cutoffPoint) * 1.4);

				if (heightValue > cutoffPoint)
				{
					for (int y = (int) Math.floor(bottomMaxY); y > bottomMaxY - (bottomHeight * bottomHeightMod); y--)
					{
						if (heightValue < cutoffPoint + 0.05 && y == bottomMaxY - 1)
						{
							if (coastBlock != null)
							{
								primer.setBlockState(x, y, z, coastBlock);
							}
						}
						else
						{
							primer.setBlockState(x, y, z, BlocksAether.holystone.getDefaultState());
						}
					}

					for (int y = (int) Math.floor(bottomMaxY); y < bottomMaxY + ((heightValue - cutoffPoint) * topHeight); y++)
					{
						if (heightValue < cutoffPoint + 0.05 && y < bottomMaxY + 1)
						{
							if (coastBlock != null)
							{
								primer.setBlockState(x, y, z, coastBlock);
							}
						}
						else
						{
							primer.setBlockState(x, y, z, BlocksAether.holystone.getDefaultState());
						}
					}
				}

				for (int y = (int) Math.floor(minY + (height * 0.5)); y < minY + height; y++)
				{
					double stepY = y - minY - (height * 0.55);

					double ny = (stepY) / (height - (height * 0.55)) - 0.5;

					double noise3d1 = this.simplex.eval(nx * 8.0, ny * 8.0 / 1.8, nz * 8.0);
					double noise3d2 = 0.5 * this.simplex.eval(nx * 16.0, ny * 16.0 / 1.8, nz * 16.0);

					double value3d = noise3d1 + noise3d2;

					double dist3d = 2.0 * Math.sqrt((distNX * distNX) + (ny * ny) + (distNZ * distNZ)); // Get distance from center of Island

					// Apply formula to shape noise into island, noise decreases in value the further the coord is from the center
					value3d = (value3d + 0.10) - (1.65 * Math.pow(dist3d, 1.50));

					value3d -= dist;

					// Prevents noise from dropping below its minimum value
					value3d = Math.min(1.0D, Math.max(-1.0D, value3d));

					if (value3d > -0.8)
					{
						primer.setBlockState(x, y, z, BlocksAether.holystone.getDefaultState());
					}
				}
			}
		}
	}

}
