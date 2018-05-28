package com.gildedgames.aether.common.world.aether.island.gen.highlands;

import com.gildedgames.aether.api.util.NoiseUtil;
import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandDataPartial;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.common.util.ChunkNoiseGenerator;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.aether.biomes.magnetic_hills.MagneticHillPillar;
import com.gildedgames.aether.common.world.aether.biomes.magnetic_hills.MagneticHillsData;
import com.gildedgames.aether.common.world.aether.island.gen.IslandBlockType;
import com.gildedgames.aether.common.world.aether.island.gen.IslandChunkMaskTransformer;
import com.gildedgames.aether.common.world.aether.island.gen.IslandVariables;
import com.gildedgames.orbis_api.preparation.impl.ChunkMask;
import com.gildedgames.orbis_api.processing.IBlockAccessExtended;
import com.gildedgames.orbis_api.util.ObjectFilter;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class IslandGeneratorHighlands implements IIslandGenerator
{
	private IslandVariables v;

	public IslandGeneratorHighlands(IslandVariables variables)
	{
		this.v = variables;
	}

	public static ChunkNoiseGenerator generateNoise(OpenSimplexNoise noise, IIslandDataPartial island, int chunkX, int chunkZ, int offset, double scale)
	{
		return new ChunkNoiseGenerator(noise, chunkX * 16, chunkZ * 16, 4, 5, island.getBounds().getMinX() + offset, island.getBounds().getMinZ() + offset, scale)
		{
			@Override
			protected double sample(double nx, double nz)
			{
				return NoiseUtil.genNoise(this.generator, nx, nz);
			}
		};
	}

	@Override
	public void genMask(Biome[] biomes, OpenSimplexNoise noise, IBlockAccessExtended access, ChunkMask mask, IIslandData island, int chunkX, int chunkZ)
	{
		ChunkNoiseGenerator heightMap = generateNoise(noise, island, chunkX, chunkZ, 0, 300.0D);
		ChunkNoiseGenerator terraceMap = this.v.hasTerraces() ? generateNoise(noise, island, chunkX, chunkZ, 1000, 300.0D) : null;

		MagneticHillsData magneticHillsData = ObjectFilter.getFirstFrom(island.getComponents(), MagneticHillsData.class);

		int posX = chunkX * 16;
		int posZ = chunkZ * 16;

		double centerX = island.getBounds().getCenterX();
		double centerZ = island.getBounds().getCenterZ();

		double radiusX = island.getBounds().getWidth() / 2.0;
		double radiusZ = island.getBounds().getLength() / 2.0;

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				int worldX = posX + x;
				int worldZ = posZ + z;

				double sample = heightMap.interpolate(x, z);

				double distX = Math.abs((centerX - worldX) * (1.0 / radiusX));
				double distZ = Math.abs((centerZ - worldZ) * (1.0 / radiusZ));

				// Get distance from center of Island
				double dist = Math.sqrt(distX * distX + distZ * distZ) / 1.0D;

				double heightSample = sample + 1.0 - dist;

				boolean magnetic = false;

				double bottomMaxY = 100;

				double cutoffPoint = 0.325;

				double normal = NoiseUtil.normalise(sample);
				double cutoffPointDist = Math.abs(cutoffPoint - heightSample);
				double diff = Math.max(0.0, cutoffPoint - cutoffPointDist) * 8.0;

				double bottomHeight = 100;

				double filteredSample = this.v.getHeightSampleFilter().transform(heightSample);

				if (this.v.hasTerraces())
				{
					double terraceSample = terraceMap.interpolate(x, z) + 1.0;

					filteredSample = NoiseUtil.lerp(heightSample, terraceSample - diff > 0.7 ? terraceSample - diff : heightSample, 0.7);
				}

				filteredSample = Math.pow(filteredSample, 1.0 + (this.v.getCoastSpread() * 0.25));

				double lakeX = (worldX) / this.v.getLakeScale();
				double lakeZ = (worldZ) / this.v.getLakeScale();

				double lakeSample = NoiseUtil.something(noise, lakeX, lakeZ);

				double dif = MathHelper.clamp(Math.abs(cutoffPoint - heightSample) * 10.0, 0.0, 1.0);

				double lakeNoise = (lakeSample + this.v.getLakeConcentrationModifier()) * dif;
				double lakeBottomValue = this.v.getLakeBottomValueFilter().transform(cutoffPoint);

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

				double topHeight = magnetic ? currentPillar.getTopHeight() : this.v.getMaxTerrainHeight();

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

						mask.setBlock(x, y, z, magnetic ? IslandBlockType.FERROSITE_BLOCK.ordinal() : IslandBlockType.STONE_BLOCK.ordinal());
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
							mask.setBlock(x, y, z, IslandBlockType.SNOW_BLOCK.ordinal());
						}
						else
						{
							mask.setBlock(x, y, z, magnetic ? IslandBlockType.FERROSITE_BLOCK.ordinal() : IslandBlockType.STONE_BLOCK.ordinal());
						}
					}

					if (lakeNoise >= this.v.getLakeThreshold())
					{
						for (int y = 100 + this.v.getCoastHeight() - 1; y >= 100; y--)
						{
							int found = mask.getBlock(x, y, z);

							if (found == IslandBlockType.STONE_BLOCK.ordinal())
							{
								if (mask.getBlock(x, y + 1, z) != 0)
								{
									break;
								}

								if (y >= 100 && y <= 100 + this.v.getCoastHeight() - 1)
								{
									mask.setBlock(x, y, z, IslandBlockType.COAST_BLOCK.ordinal());
								}

								break;
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
								mask.setBlock(x, y, z, IslandBlockType.SOIL_BLOCK.ordinal());
							}
							else
							{
								mask.setBlock(x, y, z, IslandBlockType.WATER_BLOCK.ordinal());
							}
						}
					}

					if (this.v.getCoastHeight() > 0)
					{
						for (int y = 100 + this.v.getCoastHeight() - 1; y >= 100; y--)
						{
							int found = mask.getBlock(x, y, z);

							if (found == IslandBlockType.STONE_BLOCK.ordinal())
							{
								if (mask.getBlock(x, y + 1, z) != 0)
								{
									break;
								}

								if (y >= 100 && y <= 100 + this.v.getCoastHeight() - 1)
								{
									mask.setBlock(x, y, z, IslandBlockType.COAST_BLOCK.ordinal());
								}

								break;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void genChunk(Biome[] biomes, OpenSimplexNoise noise, IBlockAccessExtended access, ChunkMask mask, ChunkPrimer primer, IIslandData island,
			int chunkX, int chunkZ)
	{
		BiomeAetherBase biome = (BiomeAetherBase) island.getBiome();

		IslandChunkMaskTransformer transformer = new IslandChunkMaskTransformer();
		transformer.setMaskValue(IslandBlockType.TOPSOIL_BLOCK, biome.topBlock);
		transformer.setMaskValue(IslandBlockType.SOIL_BLOCK, biome.fillerBlock);
		transformer.setMaskValue(IslandBlockType.COAST_BLOCK, biome.getCoastalBlock());

		mask.createChunk(primer, transformer);
	}

	private Object[] shapeMagneticShafts(MagneticHillsData data, double magneticSample, int x, int z, int chunkX,
			int chunkZ)
	{
		int worldX = (chunkX * 16) + x;
		int worldZ = (chunkZ * 16) + z;

		double closestDistX = Double.MAX_VALUE;
		double closestDistZ = Double.MAX_VALUE;

		Object[] values = new Object[2];

		for (MagneticHillPillar p : data.getMagneticPillars())
		{
			double distX = Math.abs((p.getPos().getX() - worldX) * (1.0 / p.getRadius()));
			double distZ = Math.abs((p.getPos().getZ() - worldZ) * (1.0 / p.getRadius()));

			if (distX + distZ < closestDistX + closestDistZ)
			{
				closestDistX = distX;
				closestDistZ = distZ;

				values[0] = p;
			}
		}

		double closestDist = Math.sqrt(closestDistX * closestDistX + closestDistZ * closestDistZ);

		values[1] = magneticSample - closestDist;

		return values;
	}

}
