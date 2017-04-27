package com.gildedgames.aether.common.world.dimensions.aether.island.logic;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.world.dimensions.aether.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.util.OpenSimplexNoise;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class WorldGeneratorIsland
{
	// Resolution = x^2
	private static final int NOISE_RESOLUTION = 9;

	private static final double NOISE_SCALE = 16.0D / NOISE_RESOLUTION,
			NOISE_INVERSE = (16.0D / (NOISE_RESOLUTION - 1));

	private final World world;

	private final OpenSimplexNoise simplex;

	public WorldGeneratorIsland(World world)
	{
		this.world = world;
		this.simplex = new OpenSimplexNoise(world.getSeed());
	}

	public void genIslandForChunk(ChunkPrimer primer, IslandData island, IslandSector sector, int chunkX, int chunkZ)
	{
		Biome biome = this.world.getBiome(new BlockPos(chunkX * 16, 0, chunkZ * 16));

		IBlockState coastBlock = ((BiomeAetherBase) biome).getCoastalBlock();

		double height = island.getHeight();

		double[] heightMap = this.generateNoise(island, chunkX, chunkZ);

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				double sample = this.interpolate(heightMap, x, z);
				double heightSample = sample + 1.0D;

				double bottomHeight = 0.8 * height;
				double bottomMaxY = island.getMinY() + bottomHeight;

				double topHeight = 0.2 * height;

				double cutoffPoint = 0.8;

				double bottomHeightMod = Math.min(1.0, (heightSample - cutoffPoint) * 1.4);

				if (heightSample > cutoffPoint)
				{
					for (int y = (int) bottomMaxY; y > bottomMaxY - (bottomHeight * bottomHeightMod); y--)
					{
						if (coastBlock != null && heightSample < cutoffPoint + 0.05 && y == bottomMaxY - 1)
						{
							primer.setBlockState(x, y, z, coastBlock);
						}
						else
						{
							primer.setBlockState(x, y, z, BlocksAether.holystone.getDefaultState());
						}
					}

					for (int y = (int) bottomMaxY; y < bottomMaxY + ((heightSample - cutoffPoint) * topHeight); y++)
					{
						if (coastBlock != null && (heightSample < cutoffPoint + 0.05 && y < bottomMaxY + 1))
						{
							primer.setBlockState(x, y, z, coastBlock);
						}
						else
						{
							primer.setBlockState(x, y, z, BlocksAether.holystone.getDefaultState());
						}
					}
				}
			}
		}
	}

	private double interpolate(double[] heightMap, int x, int z)
	{
		final double x0 = (x / NOISE_INVERSE);
		final double z0 = (z / NOISE_INVERSE);

		final double x1 = Math.floor(x0);
		final double x2 = Math.ceil(x0) + 0.5D;

		final double z1 = Math.floor(z0);
		final double z2 = Math.ceil(z0) + 0.5D;

		final double q11 = this.valueAt(heightMap, (int) x1, (int) z1);
		final double q12 = this.valueAt(heightMap, (int) x1, (int) z2);
		final double q21 = this.valueAt(heightMap, (int) x2, (int) z1);
		final double q22 = this.valueAt(heightMap, (int) x2, (int) z2);

		final double a1 = (x2 - x0) / (x2 - x1);
		final double a2 = (x0 - x1) / (x2 - x1);

		final double r1 = a1 * q11 + a2 * q21;
		final double r2 = a1 * q12 + a2 * q22;

		return ((z2 - z0) / (z2 - z1)) * r1 + ((z0 - z1) / (z2 - z1)) * r2;
	}

	private double valueAt(double[] heightMap, int x, int z)
	{
		return heightMap[x + (z * NOISE_RESOLUTION)];
	}

	private double[] generateNoise(IslandData island, int chunkX, int chunkZ)
	{
		final double posX = chunkX * 16;
		final double posZ = chunkZ * 16;

		final double width = (double) island.getBounds().width;
		final double length = (double) island.getBounds().height;

		final double minX = island.getBounds().getMinX();
		final double minZ = island.getBounds().getMinY();

		final double centerX = island.getBounds().getMinX() + (width / 2);
		final double centerZ = island.getBounds().getMinY() + (length / 2);

		final double[] data = new double[NOISE_RESOLUTION * NOISE_RESOLUTION];

		// Generate half-resolution noise
		for (int x = 0; x < NOISE_RESOLUTION; x++)
		{
			// Creates world coordinate and normalized noise coordinate
			final double worldX = posX + (x * NOISE_SCALE);
			final double nx = (worldX + minX) / 300.0D;

			for (int z = 0; z < NOISE_RESOLUTION; z++)
			{
				// Creates world coordinate and normalized noise coordinate
				final double worldZ = posZ + (z * NOISE_SCALE);
				final double nz = (worldZ + minZ) / 300.0D;

				double distX = Math.abs(centerX - worldX);
				double distZ = Math.abs(centerZ - worldZ);

				// Get distance from center of Island
				double dist = (distX + distZ) / 450.0D;

				// Generate noise for X/Z coordinate
				double noise1 = this.simplex.eval(nx, nz);
				double noise2 = 0.5D * this.simplex.eval(nx * 8D, nz * 8D);
				double noise3 = 0.25D * this.simplex.eval(nx * 16D, nz * 16D);
				double noise4 = 0.1D * this.simplex.eval(nx * 32D, nz * 32D);

				// Averages noise samples linearly
				double sample = (noise1 + noise2 + noise3 + noise4) / 4.0D;

				// Apply formula to shape noise into island, noise decreases in value the further the coord is from the center
				double height = sample - (0.7D * Math.pow(dist, 2));

				data[x + (z * NOISE_RESOLUTION)] = height;
			}
		}

		return data;
	}

}
