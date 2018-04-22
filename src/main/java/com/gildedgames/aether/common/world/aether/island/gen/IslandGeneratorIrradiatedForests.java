package com.gildedgames.aether.common.world.aether.island.gen;

import com.gildedgames.aether.api.util.NoiseUtil;
import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockHolystone;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.aether.biomes.irradiated_forests.CrackChunk;
import com.gildedgames.aether.common.world.aether.biomes.irradiated_forests.CrackPos;
import com.gildedgames.aether.common.world.aether.biomes.irradiated_forests.IrradiatedForestsData;
import com.gildedgames.orbis_api.processing.IBlockAccessExtended;
import com.gildedgames.orbis_api.util.ObjectFilter;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.ChunkPrimer;

public class IslandGeneratorIrradiatedForests implements IIslandGenerator
{
	// Resolution of the evalNormalised for a chunk. Should be a power of 2.
	private static final int NOISE_XZ_SCALE = 4;

	// Number of samples done per chunk.
	private static final int NOISE_SAMPLES = NOISE_XZ_SCALE + 1;

	private IrradiatedForestsData data;

	public double interpolate(final double[] data, final int x, final int z)
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

	public double[] generateNoise(final OpenSimplexNoise noise, final IIslandData island, final int chunkX, final int chunkZ, final int offset,
			final double scale)
	{
		final double posX = chunkX * 16;
		final double posZ = chunkZ * 16;

		final double minX = island.getBounds().getMinX();
		final double minZ = island.getBounds().getMinZ();

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

				// Apply formula to shape evalNormalised into island, evalNormalised decreases in value the further the coord is from the center
				final double height = NoiseUtil.genNoise(noise, nx, nz);

				data[x + (z * NOISE_SAMPLES)] = height;
			}
		}

		return data;
	}

	@Override
	public void genIslandForChunk(final OpenSimplexNoise noise, final IBlockAccessExtended access, final ChunkPrimer primer, final IIslandData island,
			final int chunkX,
			final int chunkZ)
	{
		this.data = ObjectFilter.getFirstFrom(island.getComponents(), IrradiatedForestsData.class);

		final double[] heightMap = this.generateNoise(noise, island, chunkX, chunkZ, 0, 300.0D);
		final double[] terraceMap = this.generateNoise(noise, island, chunkX, chunkZ, 1000, 300.0D);

		final BiomeAetherBase biome = (BiomeAetherBase) access.getServerBiome(new BlockPos(chunkX * 16, 0, chunkZ * 16));

		IBlockState coastBlock = biome.getCoastalBlock();
		IBlockState stoneBlock = BlocksAether.holystone.getDefaultState();

		/*coastBlock = Blocks.SAND.getDefaultState();
		stoneBlock = Blocks.STONE.getDefaultState();*/

		final int posX = chunkX * 16;
		final int posZ = chunkZ * 16;

		final double centerX = island.getBounds().getCenterX();
		final double centerZ = island.getBounds().getCenterZ();

		final double radiusX = island.getBounds().getWidth() / 2.0;
		final double radiusZ = island.getBounds().getLength() / 2.0;

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				final int worldX = posX + x;
				final int worldZ = posZ + z;

				final double sample = this.interpolate(heightMap, x, z);

				final double distX = Math.abs((centerX - worldX) * (1.0 / radiusX));
				final double distZ = Math.abs((centerZ - worldZ) * (1.0 / radiusZ));

				// Get distance from center of Island
				final double dist = Math.sqrt(distX * distX + distZ * distZ) / 1.0D;

				final double heightSample = sample + 1.0 - dist;

				final double bottomMaxY = 100;

				final double topHeight = 80;

				final double cutoffPoint = 0.325;

				boolean cracked = false;

				final int radius = Math.max(2, (int) (16.0 * (1.0 - dist)));

				double closestDist = Double.POSITIVE_INFINITY;

				for (int x1 = -radius; x1 < radius; x1++)
				{
					for (int z1 = -radius; z1 < radius; z1++)
					{
						final int crackX = worldX + x1;
						final int crackZ = worldZ + z1;

						final CrackChunk crackChunk = this.data.getCracks(crackX >> 4, crackZ >> 4);

						if (crackChunk != null)
						{
							final CrackPos crack = crackChunk.get(Math.abs(crackX % 16), Math.abs(crackZ % 16));

							if (crack != null)
							{
								final double crackDistX = Math.abs(x1 * (1.0 / radius));
								final double crackDistZ = Math.abs(z1 * (1.0 / radius));

								final double crackDist = Math.hypot(crackDistX, crackDistZ);

								if (closestDist > crackDist)
								{
									closestDist = crackDist;
								}

								cracked = true;
							}
						}
					}
				}

				closestDist = Math.min(1.0, closestDist);

				final double normal = NoiseUtil.normalise(sample);
				final double cutoffPointDist = Math.abs(cutoffPoint - heightSample);
				final double diff = Math.max(0.0, cutoffPoint - cutoffPointDist) * 8.0;

				double bottomHeightMod = Math.pow(normal, 0.2);

				final double bottomHeight = 100;

				final double terraceSample = this.interpolate(terraceMap, x, z) + 1.0;

				final double topSample = NoiseUtil.lerp(heightSample, terraceSample - diff > 0.7 ? terraceSample - diff : heightSample, 0.7);

				if (heightSample > cutoffPoint)
				{
					if (heightSample < cutoffPoint + 0.1)
					{
						bottomHeightMod = cutoffPointDist * heightSample * 16.0;
					}

					if (!cracked)
					{
						for (int y = (int) bottomMaxY; y > bottomMaxY - (bottomHeight * bottomHeightMod); y--)
						{
							if (y < 0)
							{
								continue;
							}

							if (coastBlock != null && heightSample < cutoffPoint + 0.025 && y == 100)
							{
								primer.setBlockState(x, y, z, coastBlock);
							}
							else
							{
								primer.setBlockState(x, y, z, stoneBlock);
							}
						}

						final double maxY = bottomMaxY + ((topSample - cutoffPoint) * topHeight);

						for (int y = (int) bottomMaxY; y < maxY; y++)
						{
							if (coastBlock != null && (topSample < cutoffPoint + 0.025 && y == 100))
							{
								primer.setBlockState(x, y, z, coastBlock);
							}
							else
							{
								primer.setBlockState(x, y, z, stoneBlock);
							}
						}
					}
					else
					{
						final double bottomMinY = bottomMaxY - (bottomHeight * bottomHeightMod);
						final double expectedTopY = bottomMaxY + ((topSample - cutoffPoint) * topHeight);

						final double maxY = (expectedTopY * closestDist);

						for (int y = (int) bottomMinY; y < maxY; y++)
						{
							primer.setBlockState(x, y, z, closestDist < 0.95 ? BlocksAether.holystone.getDefaultState().withProperty(
									BlockHolystone.PROPERTY_VARIANT, BlockHolystone.MOSSY_HOLYSTONE) : stoneBlock);
						}
					}
				}
			}
		}
	}

}
