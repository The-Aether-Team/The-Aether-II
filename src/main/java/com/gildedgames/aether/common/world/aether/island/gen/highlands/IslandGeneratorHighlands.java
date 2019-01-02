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
import com.gildedgames.orbis_api.preparation.IChunkMaskTransformer;
import com.gildedgames.orbis_api.preparation.impl.ChunkSegmentMask;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;

public class IslandGeneratorHighlands implements IIslandGenerator
{
	private final IslandVariables vars;

	public IslandGeneratorHighlands(IslandVariables variables)
	{
		this.vars = variables;
	}

	public static ChunkNoiseGenerator generateNoise(OpenSimplexNoise noise, IIslandData island, int chunkX, int chunkZ, int offset, double scale)
	{
		return new ChunkNoiseGenerator(chunkX * 16, chunkZ * 16, 4, 5, island.getBounds().getMinX() + offset, island.getBounds().getMinZ() + offset,
				scale)
		{
			@Override
			protected double getSample(double nx, double nz)
			{
				return NoiseUtil.genNoise(noise, nx, nz);
			}
		};
	}

	public static ChunkNoiseGenerator generateLakeNoise(OpenSimplexNoise noise, IIslandData island, int chunkX, int chunkZ, int offset, double scale)
	{
		return new ChunkNoiseGenerator(chunkX * 16, chunkZ * 16, 4, 5, island.getBounds().getMinX() + offset, island.getBounds().getMinZ() + offset,
				scale)
		{
			@Override
			protected double getSample(double nx, double nz)
			{
				return NoiseUtil.something(noise, nx, nz);
			}
		};
	}

	@Override
	public void genMask(IAetherChunkColumnInfo columnInfo, ChunkSegmentMask mask, IIslandData island, int chunkX, int chunkY, int chunkZ)
	{
		HighlandsChunkColumnInfo info = columnInfo.getIslandData(0, HighlandsChunkColumnInfo.class);

		int yOffset = chunkY * 16;

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				int maxY = (int) info.maxY_xz.get(x, z);

				if (maxY <= 0)
				{
					continue;
				}

				int bottomMaxY = (int) info.bottomMaxY_xz.get(x, z);
				int bottomHeight = (int) info.bottomHeight_xz.get(x, z);

				boolean snow = info.snow_xz.get(x, z);

				int m0a = Math.min(bottomMaxY - yOffset, 15);
				int m0b = Math.max(bottomMaxY - bottomHeight - yOffset, 0);

				for (int y = m0a; y >= m0b; y--)
				{
					mask.setBlock(x, y, z, IslandBlockType.STONE_BLOCK.ordinal());
				}

				int m1a = Math.max(bottomMaxY - yOffset, 0);
				int m1b = Math.min(maxY - yOffset, 15);

				int snowThreshold = maxY - yOffset - 8;

				for (int y = m1a; y <= m1b; y++)
				{
					if (snow && y > snowThreshold)
					{
						mask.setBlock(x, y, z, IslandBlockType.SNOW_BLOCK.ordinal());
					}
					else
					{
						mask.setBlock(x, y, z, IslandBlockType.STONE_BLOCK.ordinal());
					}
				}

				double lakeDepth = info.lakeDepth_xz.get(x, z);

				if (lakeDepth > 0.0D)
				{
					int waterLevel = 100;

					int minY = (int) (waterLevel - lakeDepth - 3);

					int m2a = Math.min(waterLevel - yOffset, 15);
					int m2b = Math.max(minY - yOffset, 0);

					for (int y = m2a; y >= m2b; y--)
					{
						if (y <= minY + 3)
						{
							mask.setBlock(x, y, z, IslandBlockType.COAST_BLOCK.ordinal());
						}
						else
						{
							mask.setBlock(x, y, z, IslandBlockType.WATER_BLOCK.ordinal());
						}
					}
				}

				if (this.vars.getCoastHeight() > 0)
				{
					int coastMaxY = 100 + (this.vars.getCoastHeight() / 2);
					int coastMinY = coastMaxY - this.vars.getCoastHeight();

					int m3a = Math.min(coastMaxY - yOffset, 15);
					int m3b = Math.max(coastMinY - yOffset, 0);

					for (int y = m3a; y > m3b; y--)
					{
						final int state = mask.getBlock(x, y, z);

						if (state == IslandBlockType.STONE_BLOCK.ordinal() || state == IslandBlockType.FERROSITE_BLOCK.ordinal())
						{
							mask.setBlock(x, y, z, IslandBlockType.COAST_BLOCK.ordinal());
						}
					}
				}
			}
		}
	}

	@Override
	public IChunkMaskTransformer createMaskTransformer(IIslandData island, int chunkX, int chunkZ)
	{
		BiomeAetherBase biome = (BiomeAetherBase) island.getBiome();

		IslandChunkMaskTransformer transformer = new IslandChunkMaskTransformer();
		transformer.setMaskValue(IslandBlockType.TOPSOIL_BLOCK, biome.topBlock);
		transformer.setMaskValue(IslandBlockType.SOIL_BLOCK, biome.fillerBlock);
		transformer.setMaskValue(IslandBlockType.COAST_BLOCK, biome.getCoastalBlock());

		return transformer;
	}

	@Override
	public IIslandChunkColumnInfo genInfo(Biome[] biomes, OpenSimplexNoise noise, IIslandData island, int chunkX, int chunkZ)
	{
		HighlandsChunkColumnInfo info = new HighlandsChunkColumnInfo(noise, chunkX, chunkZ);

		final ChunkNoiseGenerator heightMap = generateNoise(noise, island, chunkX, chunkZ, 0, 300.0D);

		ChunkNoiseGenerator terraceMap = null;
		ChunkNoiseGenerator lakeMap = null;

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

				double sample = heightMap.getNoiseValue(x, z);

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

					if (this.vars.hasTerraces())
					{
						if (terraceMap == null)
						{
							terraceMap = generateNoise(noise, island, chunkX, chunkZ, 1000, 300.0D);
						}

						double terraceSample = terraceMap.getNoiseValue(x, z) + 1.0;

						filteredSample = NoiseUtil.lerp(heightSample, terraceSample - diff > 0.7 ? terraceSample - diff : heightSample, 0.7);
					}

					filteredSample = Math.pow(filteredSample, 1.0 + (this.vars.getCoastSpread() * 0.25));

					if (lakeMap == null)
					{
						lakeMap = generateLakeNoise(noise, island, chunkX, chunkZ, 10000, this.vars.getLakeScale());
					}

					double lakeSample = lakeMap.getNoiseValue(x, z);

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
