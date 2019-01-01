package com.gildedgames.aether.common.world.aether.island.gen.highlands;

import com.gildedgames.aether.api.util.NoiseUtil;
import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.IAetherChunkColumnInfo;
import com.gildedgames.aether.api.world.islands.IIslandChunkColumnInfo;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.common.util.ChunkNoiseGenerator;
import com.gildedgames.aether.common.world.aether.biomes.BiomeAetherBase;
import com.gildedgames.aether.common.world.aether.island.gen.AbstractIslandChunkColumnInfo;
import com.gildedgames.aether.common.world.aether.island.gen.IslandBlockType;
import com.gildedgames.aether.common.world.aether.island.gen.IslandChunkMaskTransformer;
import com.gildedgames.aether.common.world.aether.island.gen.IslandVariables;
import com.gildedgames.aether.common.world.util.data.ChunkBooleanSegment;
import com.gildedgames.aether.common.world.util.data.ChunkDoubleSegment;
import com.gildedgames.orbis_api.preparation.impl.ChunkMask;
import com.gildedgames.orbis_api.processing.IBlockAccessExtended;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class IslandGeneratorHighlands implements IIslandGenerator
{
	private final IslandVariables vars;

	public IslandGeneratorHighlands(IslandVariables variables)
	{
		this.vars = variables;
	}

	public static ChunkNoiseGenerator generateNoise(OpenSimplexNoise noise, IIslandData island, int chunkX, int chunkZ, int offset, double scale)
	{
		return new ChunkNoiseGenerator(noise, chunkX * 16, chunkZ * 16, 4, 5, island.getBounds().getMinX() + offset, island.getBounds().getMinZ() + offset,
				scale)
		{
			@Override
			protected double sample(double nx, double nz)
			{
				return NoiseUtil.genNoise(this.generator, nx, nz);
			}
		};
	}

	public static ChunkNoiseGenerator generateLakeNoise(OpenSimplexNoise noise, IIslandData island, int chunkX, int chunkZ, int offset, double scale)
	{
		return new ChunkNoiseGenerator(noise, chunkX * 16, chunkZ * 16, 4, 5, island.getBounds().getMinX() + offset, island.getBounds().getMinZ() + offset,
				scale)
		{
			@Override
			protected double sample(double nx, double nz)
			{
				return NoiseUtil.something(noise, nx, nz);
			}
		};
	}

	@Override
	public void genMask(IAetherChunkColumnInfo columnInfo, ChunkMask mask, IIslandData island, int chunkX, int chunkY, int chunkZ)
	{
		HighlandsChunkColumnInfo info = columnInfo.getIslandData(0, HighlandsChunkColumnInfo.class);

		int boundsMinY = chunkY * 16;
		int boundsMaxY = boundsMinY + 15;

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				double maxY = info.maxY_xz.get(x, z);

				if (maxY < 0.0D)
				{
					continue;
				}

				double bottomMaxY = info.bottomMaxY_xz.get(x, z);
				double bottomHeight = info.bottomHeight_xz.get(x, z);
				double lakeDepth = info.lakeDepth_xz.get(x, z);

				boolean snow = info.snow_xz.get(x, z);

				int stone = IslandBlockType.STONE_BLOCK.ordinal();


				for (int y = Math.min((int) bottomMaxY, boundsMaxY); y > Math.max(bottomMaxY - bottomHeight, boundsMinY - 1); y--)
				{
					mask.setBlock(x, y - boundsMinY, z, stone);
				}

				for (int y = Math.max((int) bottomMaxY, boundsMinY); y <= Math.min(maxY, boundsMaxY); y++)
				{
					if (snow && y > maxY - 8)
					{
						mask.setBlock(x, y - boundsMinY, z, IslandBlockType.SNOW_BLOCK.ordinal());
					}
					else
					{
						mask.setBlock(x, y - boundsMinY, z, stone);
					}
				}

				int waterLevel = 100;

				if (lakeDepth > 0.0D)
				{
					double minY = waterLevel - lakeDepth - 3;

					for (int y = Math.min(waterLevel, boundsMaxY); y >= Math.max(minY, boundsMinY); y--)
					{
						if (y <= minY + 3)
						{
							mask.setBlock(x, y - boundsMinY, z, IslandBlockType.COAST_BLOCK.ordinal());
						}
						else
						{
							mask.setBlock(x, y - boundsMinY, z, IslandBlockType.WATER_BLOCK.ordinal());
						}
					}
				}

				if (this.vars.getCoastHeight() > 0)
				{
					int coastMaxY = 100 + (this.vars.getCoastHeight() / 2);

					double coastMinY = coastMaxY - this.vars.getCoastHeight();

					for (int y = Math.min(coastMaxY, boundsMaxY); y >= Math.max(coastMinY, boundsMinY); y--)
					{
						final int state = mask.getBlock(x, y - boundsMinY, z);

						if (state != IslandBlockType.STONE_BLOCK.ordinal() && state != IslandBlockType.FERROSITE_BLOCK.ordinal())
						{
							continue;
						}

						mask.setBlock(x, y - boundsMinY, z, IslandBlockType.COAST_BLOCK.ordinal());
					}
				}
			}
		}
	}

	@Override
	public void genChunk(Biome[] biomes, OpenSimplexNoise noise, IBlockAccessExtended access, ChunkMask mask, ChunkPrimer primer, IIslandData island,
			int chunkX, int chunkY, int chunkZ)
	{
		BiomeAetherBase biome = (BiomeAetherBase) island.getBiome();

		IslandChunkMaskTransformer transformer = new IslandChunkMaskTransformer();
		transformer.setMaskValue(IslandBlockType.TOPSOIL_BLOCK, biome.topBlock);
		transformer.setMaskValue(IslandBlockType.SOIL_BLOCK, biome.fillerBlock);
		transformer.setMaskValue(IslandBlockType.COAST_BLOCK, biome.getCoastalBlock());

		mask.createChunk(primer, transformer);
	}

	@Override
	public IIslandChunkColumnInfo genInfo(Biome[] biomes, OpenSimplexNoise noise, IIslandData island, int chunkX, int chunkZ)
	{
		HighlandsChunkColumnInfo info = new HighlandsChunkColumnInfo(noise, chunkX, chunkZ);

		ChunkNoiseGenerator heightMap = generateNoise(noise, island, chunkX, chunkZ, 0, 300.0D);
		ChunkNoiseGenerator terraceMap = this.vars.hasTerraces() ? generateNoise(noise, island, chunkX, chunkZ, 1000, 300.0D) : null;
		ChunkNoiseGenerator lakeMap = generateLakeNoise(noise, island, chunkX, chunkZ, 10000, this.vars.getLakeScale());

		int posX = chunkX * 16;
		int posZ = chunkZ * 16;

		double centerX = island.getBounds().getCenterX();
		double centerZ = island.getBounds().getCenterZ();

		double radiusX = island.getBounds().getWidth() / 2.0;
		double radiusZ = island.getBounds().getLength() / 2.0;

		for (int x = 0; x < 16; x++)
		{
			int worldX = posX + x;

			double distX = Math.abs((centerX - worldX) * (1.0 / radiusX));

			for (int z = 0; z < 16; z++)
			{
				int worldZ = posZ + z;

				double sample = heightMap.interpolate(x, z);

				double distZ = Math.abs((centerZ - worldZ) * (1.0 / radiusZ));

				// Get distance from center of Island
				double dist = Math.sqrt(distX * distX + distZ * distZ) / 1.0D;

				double heightSample = sample + 1.0 - dist;
				double cutoffPoint = 0.325;

				if (heightSample > cutoffPoint)
				{
					double bottomMaxY = 100;

					double normal = NoiseUtil.normalise(sample);
					double cutoffPointDist = Math.abs(cutoffPoint - heightSample);
					double diff = Math.max(0.0, cutoffPoint - cutoffPointDist) * 8.0;

					double bottomHeight = 100;

					double filteredSample = this.vars.getHeightSampleFilter().transform(heightSample);

					if (terraceMap != null)
					{
						double terraceSample = terraceMap.interpolate(x, z) + 1.0;

						filteredSample = NoiseUtil.lerp(heightSample, terraceSample - diff > 0.7 ? terraceSample - diff : heightSample, 0.7);
					}

					filteredSample = Math.pow(filteredSample, 1.0 + (this.vars.getCoastSpread() * 0.25));

					double lakeSample = lakeMap.interpolate(x, z);

					double dif = MathHelper.clamp(Math.abs(cutoffPoint - heightSample) * 10.0, 0.0, 1.0);

					double lakeNoise = (lakeSample + this.vars.getLakeConcentrationModifier()) * dif;
					double lakeBottomValue = this.vars.getLakeBottomValueFilter().transform(cutoffPoint);

					boolean water = false;

					if (lakeNoise > this.vars.getLakeThreshold())
					{
						if (lakeNoise < this.vars.getLakeThreshold() + this.vars.getLakeBlendRange())
						{
							double blend = (lakeNoise - this.vars.getLakeThreshold()) * (1.0 / this.vars.getLakeBlendRange());

							filteredSample = NoiseUtil.lerp(filteredSample, lakeBottomValue, blend);
						}
						else
						{
							water = true;
						}
					}

					double topHeight = this.vars.getMaxTerrainHeight();

					double bottomSample = Math.min(1.0D, normal + 0.25);

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

					double maxY = this.vars.getMaxYFilter().maxY(bottomMaxY, filteredSample, cutoffPoint, topHeight);

					if (water)
					{
						maxY = bottomMaxY;
					}

					if (maxY > 254.0D)
					{
						maxY = 254.0D;
					}

					if (water)
					{
						double depth = ((Math.max(0.0, lakeNoise - (this.vars.getLakeThreshold() + this.vars.getLakeBlendRange()))) * this.vars.getLakeDepth());

						maxY -= depth;
						bottomMaxY -= depth;

						info.lakeDepth_xz.set(x, z, depth);
					}

					info.setHeight(x, z, (int) Math.max(maxY, bottomMaxY));

					info.maxY_xz.set(x, z, maxY);
					info.bottomHeight_xz.set(x, z, bottomHeight * bottomSample);
					info.bottomMaxY_xz.set(x, z, bottomMaxY);

					info.snow_xz.set(x, z, this.vars.hasSnowCaps() && (filteredSample > 0.7));
				}
				else
				{
					info.setHeight(x, z, -1);
				}
			}
		}

		return info;
	}

	private class HighlandsChunkColumnInfo extends AbstractIslandChunkColumnInfo
	{
		final ChunkDoubleSegment bottomHeight_xz = new ChunkDoubleSegment();
		final ChunkDoubleSegment bottomMaxY_xz = new ChunkDoubleSegment();

		final ChunkDoubleSegment maxY_xz = new ChunkDoubleSegment();

		final ChunkDoubleSegment lakeDepth_xz = new ChunkDoubleSegment();

		final ChunkBooleanSegment snow_xz = new ChunkBooleanSegment();

		HighlandsChunkColumnInfo(OpenSimplexNoise noise, int chunkX, int chunkZ)
		{
			super(noise, chunkX, chunkZ);
		}
	}
}
