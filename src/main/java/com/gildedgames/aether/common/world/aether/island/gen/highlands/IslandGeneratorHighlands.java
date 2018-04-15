package com.gildedgames.aether.common.world.aether.island.gen.highlands;

import com.gildedgames.aether.api.util.NoiseUtil;
import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import com.gildedgames.orbis.api.processing.IBlockAccessExtended;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class IslandGeneratorHighlands implements IIslandGenerator
{
	// Resolution of the evalNormalised for a chunk. Should be a power of 2.
	private static final int NOISE_XZ_SCALE = 4;

	// Number of samples done per chunk.
	private static final int NOISE_SAMPLES = NOISE_XZ_SCALE + 1;

	private boolean terraces = true;

	private int maxTerrainHeight = 80;

	public IslandGeneratorHighlands()
	{

	}

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

	public static double[] generateNoise(final OpenSimplexNoise noise, final IIslandData island, final int chunkX, final int chunkZ, final int offset,
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

	public IslandGeneratorHighlands terraces(boolean flag)
	{
		this.terraces = flag;

		return this;
	}

	public IslandGeneratorHighlands maxTerrainHeight(int maxTerrainHeight)
	{
		this.maxTerrainHeight = maxTerrainHeight;

		return this;
	}

	@Override
	public void genIslandForChunk(final OpenSimplexNoise noise, final IBlockAccessExtended access, final ChunkPrimer primer, final IIslandData island,
			final int chunkX,
			final int chunkZ)
	{
		final double[] heightMap = generateNoise(noise, island, chunkX, chunkZ, 0, 300.0D);
		final double[] terraceMap = this.terraces ? generateNoise(noise, island, chunkX, chunkZ, 1000, 300.0D) : null;
		//final double[] quicksoilMap = NoiseUtil.something(noise, island, chunkX, chunkZ, 2000, 200D);

		final Biome biome = access.getServerBiome(new BlockPos(chunkX * 16, 0, chunkZ * 16));

		IBlockState coastBlock = ((BiomeAetherBase) biome).getCoastalBlock();
		IBlockState stoneBlock = BlocksAether.holystone.getDefaultState();

		//coastBlock = Blocks.SAND.getDefaultState();
		//stoneBlock = Blocks.STONE.getDefaultState();

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

				final double sample = interpolate(heightMap, x, z);

				final double distX = Math.abs((centerX - worldX) * (1.0 / radiusX));
				final double distZ = Math.abs((centerZ - worldZ) * (1.0 / radiusZ));

				// Get distance from center of Island
				final double dist = Math.sqrt(distX * distX + distZ * distZ) / 1.0D;

				final double heightSample = sample + 1.0 - dist;

				final double bottomMaxY = 100;

				final double topHeight = this.maxTerrainHeight;

				final double cutoffPoint = 0.325;

				final double normal = NoiseUtil.normalise(sample);
				final double cutoffPointDist = Math.abs(cutoffPoint - heightSample);
				final double diff = Math.max(0.0, cutoffPoint - cutoffPointDist) * 8.0;

				double bottomHeightMod = Math.pow(normal, 0.2);

				final double bottomHeight = 100;

				double topSample = heightSample;

				if (this.terraces)
				{
					final double terraceSample = interpolate(terraceMap, x, z) + 1.0;

					topSample = NoiseUtil.lerp(heightSample, terraceSample - diff > 0.7 ? terraceSample - diff : heightSample, 0.7);
				}

				final double nx = (worldX) / 65D;
				final double nz = (worldZ) / 65D;

				double sampleQuicksoil = NoiseUtil.something(noise, nx, nz);

				double dif = MathHelper.clamp(Math.abs(cutoffPoint - heightSample) * 10.0, 0.0, 1.0);

				double quicksoil = sampleQuicksoil * dif;
				double waterBottomValue = cutoffPoint;

				boolean water = false;

				if (quicksoil > 0.2)
				{
					if (quicksoil < 0.7)
					{
						double blend = (quicksoil - 0.2) * 2.0;

						topSample = NoiseUtil.lerp(topSample, waterBottomValue, blend);
					}
					else
					{
						water = true;
					}
				}

				if (heightSample > cutoffPoint)
				{
					if (heightSample < cutoffPoint + 0.1)
					{
						bottomHeightMod = cutoffPointDist * heightSample * 16.0;
					}

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

					double maxY = bottomMaxY + ((topSample - cutoffPoint) * topHeight);
					double waterHeight = bottomMaxY + ((waterBottomValue - cutoffPoint) * topHeight);

					if (water)
					{
						maxY = bottomMaxY + ((waterBottomValue - cutoffPoint) * topHeight);
					}

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

						if (quicksoil >= 0.2)
						{
							if (y >= waterHeight && y < waterHeight + 2)
							{
								primer.setBlockState(x, y, z, coastBlock);
							}
						}
					}

					if (quicksoil >= 0.2 && water)
					{
						double minY = maxY - ((Math.max(0.0, quicksoil - 0.7)) * 25);

						for (int y = (int) maxY; y > minY - 1; y--)
						{
							if (y <= minY)
							{
								primer.setBlockState(x, y, z, coastBlock);
							}
							else
							{
								primer.setBlockState(x, y, z, Blocks.WATER.getDefaultState());
							}
						}
					}
				}
			}
		}
	}

}
