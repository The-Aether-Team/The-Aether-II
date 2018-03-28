package com.gildedgames.aether.common.world.aether.island.gen;

import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.aether.biomes.magnetic_hills.MagneticHillPillar;
import com.gildedgames.aether.common.world.aether.biomes.magnetic_hills.MagneticHillsData;
import com.gildedgames.orbis.api.util.ObjectFilter;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

public class IslandGeneratorMagneticHills implements IIslandGenerator
{
	// Resolution of the noise for a chunk. Should be a power of 2.
	private static final int NOISE_XZ_SCALE = 4;

	// Number of samples done per chunk.
	private static final int NOISE_SAMPLES = NOISE_XZ_SCALE + 1;

	private final Random magneticShaftsRand = new Random();

	private MagneticHillPillar currentPillar;

	@Override
	public void genIslandForChunk(final OpenSimplexNoise noise, final IBlockAccess access, final ChunkPrimer primer, final IIslandData island, final int chunkX,
			final int chunkZ)
	{
		final Biome biome = access.getBiome(new BlockPos(chunkX * 16, 0, chunkZ * 16));

		final IBlockState coastBlock = ((BiomeAetherBase) biome).getCoastalBlock();

		final double height = island.getBounds().getHeight();

		final double[] heightMap = this.generateNoise(island, chunkX, chunkZ, noise, 300.0D);

		final MagneticHillsData magneticHillsData = ObjectFilter.getFirstFrom(island.getComponents(), MagneticHillsData.class);

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				this.magneticShaftsRand.setSeed((long) x * 341873128712L + (long) z * 132897987541L);

				final double sample = this.interpolate(heightMap, x, z, NOISE_XZ_SCALE);
				final double heightSample = sample + 1.0D;

				double magneticSample = heightSample;

				if (magneticHillsData != null)
				{
					magneticSample = this.shapeMagneticShafts(magneticHillsData, magneticSample, x, z, chunkX, chunkZ);
				}

				boolean magnetic = false;

				if (magneticSample > 0.5)
				{
					magnetic = true;
				}

				double bottomHeight = 0.5 * height;
				double bottomMaxY = island.getBounds().getMinY() + bottomHeight;

				if (magnetic)
				{
					bottomMaxY += this.currentPillar.getPos().getY();
					bottomHeight = 0.35 * height;
				}

				final double sampleToUse = magnetic ? magneticSample : heightSample;

				final double topHeight = magnetic ? this.currentPillar.getTopHeight() : 0.2 * height;

				final double cutoffPoint = magnetic ? 0.5 : 0.8;

				double bottomHeightMod = Math.min(1.0, (sampleToUse - cutoffPoint) * 1.4);

				if (magnetic)
				{
					bottomHeightMod = Math.min(bottomHeightMod, bottomHeightMod * this.currentPillar.getElongationMod());
				}

				if (heightSample > cutoffPoint)
				{
					for (int y = (int) bottomMaxY; y > bottomMaxY - (bottomHeight * bottomHeightMod); y--)
					{
						if (!magnetic && coastBlock != null && sampleToUse < cutoffPoint + 0.05 && y == bottomMaxY - 1)
						{
							primer.setBlockState(x, y, z, coastBlock);
						}
						else
						{
							primer.setBlockState(x, y, z, magnetic ? BlocksAether.ferrosite.getDefaultState() : BlocksAether.holystone.getDefaultState());
						}
					}

					for (int y = (int) bottomMaxY; y < bottomMaxY + ((heightSample - cutoffPoint) * topHeight); y++)
					{
						if (!magnetic && coastBlock != null && (heightSample < cutoffPoint + 0.05 && y < bottomMaxY + 1))
						{
							primer.setBlockState(x, y, z, coastBlock);
						}
						else
						{
							primer.setBlockState(x, y, z, magnetic ? BlocksAether.ferrosite.getDefaultState() : BlocksAether.holystone.getDefaultState());
						}
					}
				}
			}
		}
	}

	private double shapeMagneticShafts(final MagneticHillsData data, final double magneticSample, final int x, final int z, final int chunkX, final int chunkZ)
	{
		final int worldX = (chunkX * 16) + x;
		final int worldZ = (chunkZ * 16) + z;

		double closestDistX = Double.MAX_VALUE;
		double closestDistZ = Double.MAX_VALUE;

		for (final MagneticHillPillar p : data.getMagneticPillars())
		{
			final double distX = Math.abs(p.getPos().getX() - worldX);
			final double distZ = Math.abs(p.getPos().getZ() - worldZ);

			if (distX + distZ < closestDistX + closestDistZ)
			{
				closestDistX = distX;
				closestDistZ = distZ;
				this.currentPillar = p;
			}
		}

		final double closestDist = Math.hypot(closestDistX, closestDistZ);

		return magneticSample - (closestDist / this.currentPillar.getRadius());
	}

	private double interpolate(final double[] data, final int x, final int z, final int noiseXZScale)
	{
		final double x0 = (double) x / noiseXZScale;
		final double z0 = (double) z / noiseXZScale;

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

	private double[] generateNoise(final IIslandData island, final int chunkX, final int chunkZ, final OpenSimplexNoise noise, final double scale)
	{
		final double posX = chunkX * 16;
		final double posZ = chunkZ * 16;

		final double minX = island.getBounds().getMinX();
		final double minZ = island.getBounds().getMinZ();

		final double centerX = island.getBounds().getCenterX();
		final double centerZ = island.getBounds().getCenterZ();

		final double[] data = new double[NOISE_SAMPLES * NOISE_SAMPLES];

		// Generate half-resolution noise
		for (int x = 0; x < NOISE_SAMPLES; x++)
		{
			// Creates world coordinate and normalized noise coordinate
			final double worldX = posX - (x == 0 ? NOISE_XZ_SCALE - 1 : 0) + (x * (16D / NOISE_SAMPLES));
			final double nx = (worldX + minX) / scale;

			for (int z = 0; z < NOISE_SAMPLES; z++)
			{
				// Creates world coordinate and normalized noise coordinate
				final double worldZ = posZ - (z == 0 ? NOISE_XZ_SCALE - 1 : 0) + (z * (16.0D / NOISE_SAMPLES));
				final double nz = (worldZ + minZ) / scale;

				final double distX = Math.abs(centerX - worldX);
				final double distZ = Math.abs(centerZ - worldZ);

				// Get distance from center of Island
				final double dist = (distX + distZ) / 450.0D;

				// Generate noise for X/Z coordinate
				final double noise1 = noise.eval(nx, nz);
				final double noise2 = 0.5D * noise.eval(nx * 8D, nz * 8D);
				final double noise3 = 0.25D * noise.eval(nx * 16D, nz * 16D);
				final double noise4 = 0.125D * noise.eval(nx * 32D, nz * 32D);

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
