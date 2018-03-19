package com.gildedgames.aether.common.world.aether.island.gen;

import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.util.OpenSimplexNoise;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class IslandGenerator
{
	// Resolution of the noise for a chunk. Should be a power of 2.
	private static final int NOISE_XZ_SCALE = 4;

	// Number of samples done per chunk.
	private static final int NOISE_SAMPLES = NOISE_XZ_SCALE + 1;

	private final World world;

	private final OpenSimplexNoise simplex;

	public IslandGenerator(final World world)
	{
		this.world = world;
		this.simplex = new OpenSimplexNoise(world.getSeed());
	}

	public void genIslandForChunk(final ChunkPrimer primer, final IIslandData island, final int chunkX, final int chunkZ)
	{
		final Biome biome = this.world.getBiome(new BlockPos(chunkX * 16, 0, chunkZ * 16));

		final IBlockState coastBlock = ((BiomeAetherBase) biome).getCoastalBlock();

		final double height = island.getBounds().getHeight();

		final double[] heightMap = this.generateNoise(island, chunkX, chunkZ);

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				final double sample = this.interpolate(heightMap, x, z);
				final double heightSample = sample + 1.0D;

				final double bottomHeight = 0.8 * height;
				final double bottomMaxY = island.getBounds().getMinY() + bottomHeight;

				final double topHeight = 0.2 * height;

				final double cutoffPoint = 0.8;

				final double bottomHeightMod = Math.min(1.0, (heightSample - cutoffPoint) * 1.4);

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

	private double interpolate(final double[] data, final int x, final int z)
	{
		final double x0 = (double) x / NOISE_XZ_SCALE;
		final double z0 = (double) z / NOISE_XZ_SCALE;

		final int integerX = (int) Math.floor(x0);
		final double fractionX = x0 - integerX;

		final int integerZ = (int) Math.floor(z0);
		final double fractionZ = z0 - integerZ;

		final double a = data[integerX + (integerZ * NOISE_SAMPLES)];
		final double b = data[integerX + ((integerZ + 1) * NOISE_SAMPLES)];
		final double c = data[integerX + 1 + (integerZ * NOISE_SAMPLES)];
		final double d = data[integerX + 1 + ((integerZ + 1) * NOISE_SAMPLES)];

		return (1.0 - fractionX) * ((1.0 - fractionZ) * a + fractionZ * b) +
				fractionX * ((1.0 - fractionZ) * c + fractionZ * d);
	}

	private double[] generateNoise(final IIslandData island, final int chunkX, final int chunkZ)
	{
		final double posX = chunkX * 16;
		final double posZ = chunkZ * 16;

		final double width = (double) island.getBounds().getWidth();
		final double length = (double) island.getBounds().getHeight();

		final double minX = island.getBounds().getMinX();
		final double minZ = island.getBounds().getMinY();

		final double centerX = island.getBounds().getMinX() + (width / 2);
		final double centerZ = island.getBounds().getMinY() + (length / 2);

		final double[] data = new double[NOISE_SAMPLES * NOISE_SAMPLES];

		// Generate half-resolution noise
		for (int x = 0; x < NOISE_SAMPLES; x++)
		{
			// Creates world coordinate and normalized noise coordinate
			final double worldX = posX + (x * (16.0D / NOISE_SAMPLES));
			final double nx = (worldX + minX) / 300.0D;

			for (int z = 0; z < NOISE_SAMPLES; z++)
			{
				// Creates world coordinate and normalized noise coordinate
				final double worldZ = posZ + (z * (16.0D / NOISE_SAMPLES));
				final double nz = (worldZ + minZ) / 300.0D;

				final double distX = Math.abs(centerX - worldX);
				final double distZ = Math.abs(centerZ - worldZ);

				// Get distance from center of Island
				final double dist = (distX + distZ) / 450.0D;

				// Generate noise for X/Z coordinate
				final double noise1 = this.simplex.eval(nx, nz);
				final double noise2 = 0.5D * this.simplex.eval(nx * 8D, nz * 8D);
				final double noise3 = 0.25D * this.simplex.eval(nx * 16D, nz * 16D);
				final double noise4 = 0.1D * this.simplex.eval(nx * 32D, nz * 32D);

				// Averages noise samples linearly
				final double sample = (noise1 + noise2 + noise3 + noise4) / 4.0D;

				// Apply formula to shape noise into island, noise decreases in value the further the coord is from the center
				final double height = sample - (0.7D * Math.pow(dist, 2));

				data[x + (z * NOISE_SAMPLES)] = height;
			}
		}

		return data;
	}

}
