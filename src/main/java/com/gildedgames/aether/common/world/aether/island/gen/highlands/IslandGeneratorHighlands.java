package com.gildedgames.aether.common.world.aether.island.gen.highlands;

import com.gildedgames.aether.api.util.NoiseUtil;
import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.aether.biomes.magnetic_hills.MagneticHillPillar;
import com.gildedgames.aether.common.world.aether.biomes.magnetic_hills.MagneticHillsData;
import com.gildedgames.aether.common.world.aether.island.gen.IslandVariables;
import com.gildedgames.orbis_api.processing.IBlockAccessExtended;
import com.gildedgames.orbis_api.util.ObjectFilter;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class IslandGeneratorHighlands implements IIslandGenerator
{
	// Resolution of the evalNormalised for a chunk. Should be a power of 2.
	private static final int NOISE_XZ_SCALE = 4;

	// Number of samples done per chunk.
	private static final int NOISE_SAMPLES = NOISE_XZ_SCALE + 1;

	private IslandVariables v;

	public IslandGeneratorHighlands(IslandVariables variables)
	{
		this.v = variables;
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

	@Override
	public void genIslandForChunk(Biome[] biomes, final OpenSimplexNoise noise, final IBlockAccessExtended access, final ChunkPrimer primer,
			final IIslandData island,
			final int chunkX,
			final int chunkZ)
	{
		final double[] heightMap = generateNoise(noise, island, chunkX, chunkZ, 0, 300.0D);
		final double[] terraceMap = this.v.hasTerraces() ? generateNoise(noise, island, chunkX, chunkZ, 1000, 300.0D) : null;
		//final double[] quicksoilMap = generateNoise(noise, island, chunkX, chunkZ, 2000, 200D);

		final Biome biome = biomes[0];

		final MagneticHillsData magneticHillsData = ObjectFilter.getFirstFrom(island.getComponents(), MagneticHillsData.class);

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

				boolean magnetic = false;

				double bottomMaxY = 100;

				final double cutoffPoint = 0.325;

				final double normal = NoiseUtil.normalise(sample);
				final double cutoffPointDist = Math.abs(cutoffPoint - heightSample);
				final double diff = Math.max(0.0, cutoffPoint - cutoffPointDist) * 8.0;

				double bottomHeight = 100;

				double filteredSample = this.v.getHeightSampleFilter().apply(heightSample);

				if (this.v.hasTerraces())
				{
					final double terraceSample = interpolate(terraceMap, x, z) + 1.0;

					filteredSample = NoiseUtil.lerp(heightSample, terraceSample - diff > 0.7 ? terraceSample - diff : heightSample, 0.7);
				}

				filteredSample = Math.pow(filteredSample, 1.0 + (this.v.getCoastSpread() * 0.25));

				final double lakeX = (worldX) / this.v.getLakeScale();
				final double lakeZ = (worldZ) / this.v.getLakeScale();

				double lakeSample = NoiseUtil.something(noise, lakeX, lakeZ);

				double dif = MathHelper.clamp(Math.abs(cutoffPoint - heightSample) * 10.0, 0.0, 1.0);

				double lakeNoise = (lakeSample + this.v.getLakeConcentrationModifier()) * dif;
				double lakeBottomValue = this.v.getLakeBottomValueFilter().apply(cutoffPoint);

				boolean water = false;

				if (lakeNoise > this.v.getLakeThreshold())
				{
					if (lakeNoise < this.v.getLakeThreshold() + this.v.getLakeBlendRange())
					{
						double blend = (lakeNoise - this.v.getLakeThreshold()) * (1.0 / this.v.getLakeBlendRange());

						filteredSample = NoiseUtil.lerp(filteredSample, lakeBottomValue, blend);
					}
					else
					{
						water = true;
					}
				}

				double magneticSample = filteredSample;
				MagneticHillPillar currentPillar = null;

				if (this.v.hasMagneticPillars())
				{
					if (magneticHillsData != null)
					{
						Object[] magneticData = this.shapeMagneticShafts(magneticHillsData, magneticSample, x, z, chunkX, chunkZ);

						magneticSample = (double) magneticData[1];
						currentPillar = (MagneticHillPillar) magneticData[0];
					}

					if (magneticSample > 0.5)
					{
						magnetic = true;
					}

					if (magnetic)
					{
						bottomMaxY += currentPillar.getPos().getY();
						bottomHeight = 55;
					}
				}

				final double topHeight = magnetic ? currentPillar.getTopHeight() : this.v.getMaxTerrainHeight();

				double bottomSample = magnetic ? magneticSample : Math.min(1.0D, normal + 0.25);

				if (heightSample > cutoffPoint)
				{
					double islandEdgeBlendRange = 0.1;
					double islandBottomBlendRange = 0.25;

					double islandEdge = 0.75;
					double islandBottom = (bottomSample * 0.25) + 0.75;

					if (heightSample < cutoffPoint + islandEdgeBlendRange)
					{
						double thresh = (heightSample - cutoffPoint);

						double blend = thresh * (1.0 / islandEdgeBlendRange);

						bottomSample = NoiseUtil.lerp(0.0, islandEdge, blend);
					}
					else if (heightSample < cutoffPoint + islandBottomBlendRange + islandEdgeBlendRange)
					{
						double thresh = (heightSample - cutoffPoint - islandEdgeBlendRange);

						double blend = thresh * (1.0 / islandBottomBlendRange);

						bottomSample = NoiseUtil.lerp(islandEdge, islandBottom, blend);
					}

					if (magnetic)
					{
						bottomSample = Math.min(1.0, (bottomSample - cutoffPoint) * 1.4);
						bottomSample = Math.min(bottomSample, bottomSample * currentPillar.getElongationMod());
					}

					for (int y = (int) bottomMaxY; y > bottomMaxY - (bottomHeight * bottomSample); y--)
					{
						if (y < 0)
						{
							continue;
						}

						primer.setBlockState(x, y, z, magnetic ? BlocksAether.ferrosite.getDefaultState() : stoneBlock);
					}

					double maxY = this.v.getMaxYFilter().maxY(bottomMaxY, filteredSample, cutoffPoint, topHeight);

					if (water && !magnetic)
					{
						maxY = bottomMaxY;
					}

					for (int y = (int) bottomMaxY; y < maxY; y++)
					{
						if (this.v.hasSnowCaps() && filteredSample > 0.7 && y > maxY - 8)
						{
							primer.setBlockState(x, y, z, Blocks.SNOW.getDefaultState());
						}
						else
						{
							primer.setBlockState(x, y, z, magnetic ? BlocksAether.ferrosite.getDefaultState() : stoneBlock);
						}

						if (lakeNoise >= this.v.getLakeThreshold())
						{
							if (y >= 100 && y <= 100 + this.v.getCoastHeight() - 1)
							{
								primer.setBlockState(x, y, z, coastBlock);
							}
						}
					}

					if (water && !magnetic)
					{
						double minY = maxY - ((Math.max(0.0, lakeNoise - (this.v.getLakeThreshold() + this.v.getLakeBlendRange()))) * this.v.getLakeDepth());

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

					if (this.v.getCoastHeight() > 0)
					{
						for (int y = 100 + this.v.getCoastHeight() - 1; y >= 100; y--)
						{
							IBlockState found = primer.getBlockState(x, y, z);

							if (found == stoneBlock)
							{
								IBlockState up = primer.getBlockState(x, y + 1, z);

								if (up != Blocks.AIR.getDefaultState())
								{
									break;
								}

								if (y >= 100 && y <= 100 + this.v.getCoastHeight() - 1)
								{
									primer.setBlockState(x, y, z, coastBlock);
								}

								break;
							}
						}
					}
				}
			}
		}
	}

	private Object[] shapeMagneticShafts(final MagneticHillsData data, final double magneticSample, final int x, final int z, final int chunkX,
			final int chunkZ)
	{
		final int worldX = (chunkX * 16) + x;
		final int worldZ = (chunkZ * 16) + z;

		double closestDistX = Double.MAX_VALUE;
		double closestDistZ = Double.MAX_VALUE;

		Object[] values = new Object[2];

		for (final MagneticHillPillar p : data.getMagneticPillars())
		{
			final double distX = Math.abs((p.getPos().getX() - worldX) * (1.0 / p.getRadius()));
			final double distZ = Math.abs((p.getPos().getZ() - worldZ) * (1.0 / p.getRadius()));

			if (distX + distZ < closestDistX + closestDistZ)
			{
				closestDistX = distX;
				closestDistZ = distZ;

				values[0] = p;
			}
		}

		final double closestDist = Math.sqrt(closestDistX * closestDistX + closestDistZ * closestDistZ);

		values[1] = magneticSample - closestDist;

		return values;
	}

}
