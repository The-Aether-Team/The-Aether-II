package com.gildedgames.aether.common.world.aether.island.gen;

import com.gildedgames.aether.api.util.NoiseUtil;
import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandDataPartial;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import com.gildedgames.orbis_api.processing.IBlockAccessExtended;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class IslandGeneratorForgottenHighlands implements IIslandGenerator
{
	// Resolution of the evalNormalised for a chunk. Should be a power of 2.
	private static final int NOISE_XZ_SCALE = 4;

	private static final int NOISE_Y_SCALE = 32;

	// Number of samples done per chunk.
	private static final int NOISE_SAMPLES = NOISE_XZ_SCALE + 1;

	private static final int SAMPLE_HEIGHT = (256 / NOISE_Y_SCALE) + 1;

	public static double interpolate(final double[] data, final int x, final int z)
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

	public static float lerp(final float x, final float x1, final float x2, final float q00, final float q01)
	{
		return ((x2 - x) / (x2 - x1)) * q00 + ((x - x1) / (x2 - x1)) * q01;
	}

	public static double interpolate(final double[] data, final int x, final int y, final int z)
	{
		final double x0 = (double) x / NOISE_XZ_SCALE;
		final double y0 = (double) y / NOISE_Y_SCALE;
		final double z0 = (double) z / NOISE_XZ_SCALE;

		final int integerX = (int) Math.floor(x0);
		final double fractionX = x0 - integerX;

		final int integerY = (int) Math.floor(y0);
		final double fractionY = y0 - integerY;

		final int integerZ = (int) Math.floor(z0);
		final double fractionZ = z0 - integerZ;

		//z + y * NOISE_SAMPLES + x * SAMPLE_HEIGHT * NOISE_SAMPLES

		final double a = data[integerZ + integerY * NOISE_SAMPLES + integerX * SAMPLE_HEIGHT * NOISE_SAMPLES];
		final double b = data[(integerZ + 1) + integerY * NOISE_SAMPLES + integerX * SAMPLE_HEIGHT * NOISE_SAMPLES];
		final double c = data[integerZ + integerY * NOISE_SAMPLES + (integerX + 1) * SAMPLE_HEIGHT * NOISE_SAMPLES];
		final double d = data[(integerZ + 1) + integerY * NOISE_SAMPLES + (integerX + 1) * SAMPLE_HEIGHT * NOISE_SAMPLES];

		final double e = data[integerZ + (integerY + 1) * NOISE_SAMPLES + integerX * SAMPLE_HEIGHT * NOISE_SAMPLES];
		final double f = data[(integerZ + 1) + (integerY + 1) * NOISE_SAMPLES + integerX * SAMPLE_HEIGHT * NOISE_SAMPLES];
		final double g = data[integerZ + (integerY + 1) * NOISE_SAMPLES + (integerX + 1) * SAMPLE_HEIGHT * NOISE_SAMPLES];
		final double h = data[(integerZ + 1) + (integerY + 1) * NOISE_SAMPLES + (integerX + 1) * SAMPLE_HEIGHT * NOISE_SAMPLES];

		final double c1 = (1.0 - fractionX) * ((1.0 - fractionZ) * a + fractionZ * b) +
				fractionX * ((1.0 - fractionZ) * c + fractionZ * d);
		final double c2 = (1.0 - fractionX) * ((1.0 - fractionZ) * e + fractionZ * f) +
				fractionX * ((1.0 - fractionZ) * g + fractionZ * h);

		return NoiseUtil.lerp(c1, c2, fractionY);
	}

	public static double[] generate2DNoise(final OpenSimplexNoise noise, final IIslandDataPartial island, final int chunkX, final int chunkZ, final int offset,
			final double scale, final boolean centerGradient)
	{
		final double posX = chunkX * 16;
		final double posZ = chunkZ * 16;

		final double minX = island.getBounds().getMinX();
		final double minZ = island.getBounds().getMinZ();

		final double centerX = island.getBounds().getCenterX();
		final double centerZ = island.getBounds().getCenterZ();

		final double[] data = new double[NOISE_SAMPLES * NOISE_SAMPLES];

		// Generate half-resolution evalNormalised
		for (int x = 0; x < NOISE_SAMPLES; x++)
		{
			// Creates world coordinate and normalized evalNormalised coordinate
			final double worldX = posX - (x == 0 ? NOISE_XZ_SCALE - 1 : 0) + (x * (16D / NOISE_SAMPLES));
			final double nx = (worldX + minX + offset) / scale;

			for (int z = 0; z < NOISE_SAMPLES; z++)
			{
				// Creates world coordinate and normalized evalNormalised coordinate
				final double worldZ = posZ - (z == 0 ? NOISE_XZ_SCALE - 1 : 0) + (z * (16.0D / NOISE_SAMPLES));
				final double nz = (worldZ + minZ + offset) / scale;

				final double radiusX = island.getBounds().getWidth() / 2.0;
				final double radiusZ = island.getBounds().getLength() / 2.0;

				final double distX = Math.abs((centerX - worldX) * (1.0 / radiusX));
				final double distZ = Math.abs((centerZ - worldZ) * (1.0 / radiusZ));

				// Get distance from center of Island
				final double dist = Math.sqrt(distX * distX + distZ * distZ);

				final double sample = NoiseUtil.genNoise(noise, nx, nz);

				// Apply formula to shape evalNormalised into island, evalNormalised decreases in value the further the coord is from the center
				final double height = sample - (centerGradient ? dist : 0);

				data[x + (z * NOISE_SAMPLES)] = height;
			}
		}

		return data;
	}

	public static double[] generate3DNoise(final OpenSimplexNoise noise, final IIslandDataPartial island, final int chunkX, final int chunkZ, final int offset,
			final double scale, final double yScale, final boolean centerGradient)
	{
		final double posX = chunkX * 16;
		final double posZ = chunkZ * 16;

		final double minX = island.getBounds().getMinX();
		final double minZ = island.getBounds().getMinZ();

		final double centerX = island.getBounds().getCenterX();
		final double centerY = island.getBounds().getCenterY();
		final double centerZ = island.getBounds().getCenterZ();

		final double[] data = new double[NOISE_SAMPLES * SAMPLE_HEIGHT * NOISE_SAMPLES];

		// Generate half-resolution evalNormalised
		for (int x = 0; x < NOISE_SAMPLES; x++)
		{
			// Creates world coordinate and normalized evalNormalised coordinate
			final double worldX = posX - (x == 0 ? NOISE_XZ_SCALE - 1 : 0) + (x * (16D / NOISE_SAMPLES));
			final double nx = (worldX + minX + offset) / scale;

			for (int z = 0; z < NOISE_SAMPLES; z++)
			{
				// Creates world coordinate and normalized evalNormalised coordinate
				final double worldZ = posZ - (z == 0 ? NOISE_XZ_SCALE - 1 : 0) + (z * (16.0D / NOISE_SAMPLES));
				final double nz = (worldZ + minZ + offset) / scale;

				for (int y = 0; y < SAMPLE_HEIGHT; y++)
				{
					// Creates world coordinate and normalized evalNormalised coordinate
					final double worldY = (y * (16.0D / NOISE_Y_SCALE));
					final double ny = (worldY + offset) / yScale;

					final double radiusX = island.getBounds().getWidth() / 2.0;
					final double radiusY = island.getBounds().getHeight() / 2.0;
					final double radiusZ = island.getBounds().getLength() / 2.0;

					final double distX = Math.abs((centerX - worldX) * (1.0 / radiusX));
					final double distY = Math.abs((centerY - worldY) * (1.0 / radiusY));
					final double distZ = Math.abs((centerZ - worldZ) * (1.0 / radiusZ));

					// Get distance from center of Island
					final double dist = Math.sqrt(distX * distX + distY * distY + distZ * distZ);

					final double sample = NoiseUtil.genNoise(noise, nx, ny, nz);

					// Apply formula to shape evalNormalised into island, evalNormalised decreases in value the further the coord is from the center
					final double height = sample - (centerGradient ? dist : 0);

					data[z + y * NOISE_SAMPLES + x * SAMPLE_HEIGHT * NOISE_SAMPLES] = height;
				}
			}
		}

		return data;
	}

	@Override
	public void genIslandForChunk(Biome[] biomes, final OpenSimplexNoise noise, final IBlockAccessExtended access, final ChunkPrimer primer,
			final IIslandData island,
			final int chunkX,
			final int chunkZ)
	{
		final Biome biome = biomes[0];

		final IBlockState coastBlock = ((BiomeAetherBase) biome).getCoastalBlock();
		final IBlockState stoneBlock = BlocksAether.holystone.getDefaultState();

		final double[] height3D = generate3DNoise(noise, island, chunkX, chunkZ, 0, 300.0D, 0.5D, false);

		final double centerX = island.getBounds().getCenterX();
		final double centerY = 100;
		final double centerZ = island.getBounds().getCenterZ();

		final int posX = chunkX * 16;
		final int posY = 0;
		final int posZ = chunkZ * 16;

		final double radiusX = island.getBounds().getWidth() / 2.0;
		final double radiusY = 180 / 2.0;
		final double radiusZ = island.getBounds().getLength() / 2.0;

		for (int x = 0; x < 16; x++)
		{
			final int worldX = posX + x;

			for (int z = 0; z < 16; z++)
			{
				final int worldZ = posZ + z;

				for (int y = 0; y < 250; y++)
				{
					final int worldY = posY + y;

					final double sample = interpolate(height3D, x, y, z);

					final double distX = Math.abs((centerX - worldX) * (1.0 / radiusX));
					final double distY = Math.abs((centerY - worldY) * (1.0 / radiusY));
					final double distZ = Math.abs((centerZ - worldZ) * (1.0 / radiusZ));

					// Get distance from center of Island
					final double dist = Math.sqrt(distX * distX + distY * distY + distZ * distZ);

					final double heightSample = sample + 1.0 - dist;

					if (heightSample > 0.2)
					{
						primer.setBlockState(x, y, z, stoneBlock);
					}
				}
			}
		}
	}

}
