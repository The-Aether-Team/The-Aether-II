package com.gildedgames.aether.common.world.aether.island.gen.types;

import com.gildedgames.aether.api.util.NoiseUtil;
import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.IAetherChunkColumnInfo;
import com.gildedgames.aether.api.world.islands.IIslandBounds;
import com.gildedgames.aether.api.world.islands.IIslandChunkColumnInfo;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.api.world.noise.IChunkNoiseBuffer2D;
import com.gildedgames.aether.api.world.noise.INoiseGenerator2D;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.world.aether.biomes.irradiated_forests.CrackLineSegment;
import com.gildedgames.aether.common.world.aether.biomes.irradiated_forests.IrradiatedForestsData;
import com.gildedgames.aether.common.world.aether.chunk.ChunkDataGenerator2D;
import com.gildedgames.aether.common.world.aether.chunk.NoiseSampleData2D;
import com.gildedgames.aether.common.world.aether.island.gen.AbstractIslandChunkColumnInfo;
import com.gildedgames.aether.common.world.aether.island.gen.IslandBlockType;
import com.gildedgames.aether.common.world.aether.island.gen.IslandChunkMaskTransformer;
import com.gildedgames.aether.common.world.aether.noise.NoiseGeneratorIslandTerrain;
import com.gildedgames.orbis.lib.preparation.IChunkMaskTransformer;
import com.gildedgames.orbis.lib.preparation.impl.ChunkMask;
import com.gildedgames.orbis.lib.util.ObjectFilter;
import net.minecraft.util.math.MathHelper;

import java.util.Collection;

public class IslandGeneratorIrradiatedForests implements IIslandGenerator
{
	private static final int BOTTOM_HEIGHT = 100;

	private static final double CUTOFF_POINT = 0.325;

	private static final double TOP_HEIGHT = 80;

	private static final double ISLAND_EDGE_BLEND_RANGE = 0.1;
	private static final double ISLAND_BOTTOM_BLEND_RANGE = 0.25;

	private static final double ISLAND_EDGE = 0.75;

	@Override
	public void generateChunkSegment(IAetherChunkColumnInfo info, ChunkMask mask, IIslandData island, int chunkX, int chunkZ)
	{
		IrradiatedForestsChunkColumnData column = info.getIslandData(0, IrradiatedForestsChunkColumnData.class);

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				double heightSample = column.heightSample_xz.get(x, z);
				double cutoffPoint = 0.325;

				if (heightSample <= cutoffPoint)
				{
					continue;
				}

				float topHeight = column.topHeight.get(x, z);
				float bottomHeight = column.bottomHeight.get(x, z);

				double closestCrackDist = column.distToCrack.get(x, z);

				if (closestCrackDist < 10.0D)
				{
					double f = Math.max(0.0, (closestCrackDist - 5.0D) / 5.0D);

					topHeight *= f;
					bottomHeight *= f;
				}

				int top = (int) topHeight;
				int bottom = (int) bottomHeight;

				if (top + bottom == 0)
				{
					continue;
				}

				final int minY = Math.max(BOTTOM_HEIGHT - bottom, 0);
				final int maxY = Math.min(BOTTOM_HEIGHT + top, 255);

				boolean mossy = column.distToCrack.get(x, z) < 12.0D;

				for (int y = minY; y <= maxY; y++)
				{
					mask.setBlock(x, y, z, mossy ? IslandBlockType.STONE_MOSSY_BLOCK.ordinal() : IslandBlockType.STONE_BLOCK.ordinal());
				}
			}
		}
	}

	@Override
	public IChunkMaskTransformer createMaskTransformer(IIslandData island,
			int chunkX, int chunkZ)
	{
		IslandChunkMaskTransformer transformer = new IslandChunkMaskTransformer();
		transformer.setMaskValue(IslandBlockType.TOPSOIL_BLOCK,
				BlocksAether.aether_grass.getDefaultState().withProperty(BlockAetherGrass.PROPERTY_VARIANT, BlockAetherGrass.IRRADIATED));

		return transformer;
	}

	@Override
	public IIslandChunkColumnInfo generateColumnInfo(OpenSimplexNoise noise, IIslandData island, int chunkX, int chunkZ)
	{
		ChunkDataGeneratorIrradiatedForests generator = new ChunkDataGeneratorIrradiatedForests(noise, island);

		return new IrradiatedForestsChunkColumnData(noise, generator.generate(chunkX, chunkZ));
	}

	private class IrradiatedForestsChunkColumnData extends AbstractIslandChunkColumnInfo
	{
		final IChunkNoiseBuffer2D topHeight;
		final IChunkNoiseBuffer2D bottomHeight;
		final IChunkNoiseBuffer2D heightSample_xz;
		final IChunkNoiseBuffer2D topSample_xz;
		final IChunkNoiseBuffer2D distToCrack;

		IrradiatedForestsChunkColumnData(OpenSimplexNoise noise, NoiseDataIrradiatedForests data)
		{
			super(noise, data.chunkX, data.chunkZ);
			
			this.topHeight = data.topHeight.createInterpolatedNoiseBuffer();
			this.bottomHeight = data.bottomHeight.createInterpolatedNoiseBuffer();

			this.distToCrack = data.distToCrack_xz.createInterpolatedNoiseBuffer();

			this.heightSample_xz = data.heightSample_xz.createInterpolatedNoiseBuffer();
			this.topSample_xz = data.topSample_xz.createInterpolatedNoiseBuffer();
		}
	}

	private class ChunkDataGeneratorIrradiatedForests extends ChunkDataGenerator2D<NoiseDataIrradiatedForests>
	{
		private static final int NOISE_RESOLUTION = 3;

		private final INoiseGenerator2D terrain, terrace;

		private final IIslandData island;

		private final double centerX, centerZ;

		private final double radiusX, radiusZ;

		public ChunkDataGeneratorIrradiatedForests(OpenSimplexNoise noise, IIslandData island)
		{
			super(NOISE_RESOLUTION);

			this.island = island;

			IIslandBounds bounds = island.getBounds();

			this.centerX = bounds.getCenterX();
			this.centerZ = bounds.getCenterZ();

			this.radiusX = bounds.getWidth() / 2.0;
			this.radiusZ = bounds.getLength() / 2.0;

			this.terrain = new NoiseGeneratorIslandTerrain(noise, island.getBounds());
			this.terrace = new NoiseGeneratorIslandTerrain(noise, island.getBounds(), 1000);
		}

		@Override
		protected NoiseDataIrradiatedForests prepare(int chunkX, int chunkZ)
		{
			return new NoiseDataIrradiatedForests(this.island, this.noiseScaleFactor, this.noiseSampleCount, chunkX, chunkZ);
		}

		@Override
		protected void generate(NoiseDataIrradiatedForests data, int x, int z, double worldX, double worldZ)
		{
			final double sample = this.terrain.generate(worldX, worldZ) * 0.7;

			final double distX = Math.abs((this.centerX - worldX) * (1.0 / this.radiusX));
			final double distZ = Math.abs((this.centerZ - worldZ) * (1.0 / this.radiusZ));

			// Get distance from center of Island
			final double dist = Math.sqrt(distX * distX + distZ * distZ) / 1.0D;

			final double heightSample = sample + 1.0 - dist;

			double closestCrackDist = Double.POSITIVE_INFINITY;

			for (CrackLineSegment edge : data.cracks)
			{
				double d = edge.distanceToPoint(worldX, worldZ);

				if (d < closestCrackDist)
				{
					closestCrackDist = d;
				}
			}

			final double normal = NoiseUtil.normalise(sample);
			final double cutoffPointDist = Math.abs(CUTOFF_POINT - heightSample);
			final double diff = Math.max(0.0, CUTOFF_POINT - cutoffPointDist) * 8.0;

			final double terraceSample = this.terrace.generate(worldX, worldZ) + 1.0;

			final double topSample = NoiseUtil.lerp(heightSample, ((terraceSample - diff) > 0.7) ? (terraceSample - diff) : heightSample, 0.7);

			double bottomSample = Math.min(1.0D, normal + 0.25);

			double islandBottom = (bottomSample * 0.25) + 0.75;

			if (heightSample < CUTOFF_POINT + ISLAND_EDGE_BLEND_RANGE)
			{
				double thresh = (heightSample - CUTOFF_POINT);

				double blend = thresh * (1.0 / ISLAND_EDGE_BLEND_RANGE);

				bottomSample = NoiseUtil.lerp(0.0, ISLAND_EDGE, blend);
			}
			else if (heightSample < CUTOFF_POINT + ISLAND_BOTTOM_BLEND_RANGE + ISLAND_EDGE_BLEND_RANGE)
			{
				double thresh = (heightSample - CUTOFF_POINT - ISLAND_EDGE_BLEND_RANGE);

				double blend = thresh * (1.0 / ISLAND_BOTTOM_BLEND_RANGE);

				bottomSample = NoiseUtil.lerp(ISLAND_EDGE, islandBottom, blend);
			}

			double topHeight = (topSample - CUTOFF_POINT) * TOP_HEIGHT;
			double bottomHeight = BOTTOM_HEIGHT * bottomSample;

			topHeight = MathHelper.clamp(topHeight, 0.0D, 254.0D);
			bottomHeight = MathHelper.clamp(bottomHeight, 0.0D, 254.0D);

			data.topHeight.set(x, z, (float) topHeight);
			data.bottomHeight.set(x, z, (float) bottomHeight);

			data.distToCrack_xz.set(x, z, (float) closestCrackDist);

			data.heightSample_xz.set(x, z, (float) heightSample);
			data.topSample_xz.set(x, z, (float) topSample);
		}
	}
	
	private class NoiseDataIrradiatedForests
	{
		final NoiseSampleData2D topHeight;
		final NoiseSampleData2D bottomHeight;

		final NoiseSampleData2D heightSample_xz;
		final NoiseSampleData2D topSample_xz;

		final NoiseSampleData2D distToCrack_xz;

		final Collection<CrackLineSegment> cracks;

		final int chunkX, chunkZ;

		public NoiseDataIrradiatedForests(IIslandData island, double noiseScaleFactor, int noiseResolution, int chunkX, int chunkZ)
		{
			this.chunkX = chunkX;
			this.chunkZ = chunkZ;

			this.topHeight = new NoiseSampleData2D(noiseScaleFactor, noiseResolution);
			this.bottomHeight = new NoiseSampleData2D(noiseScaleFactor, noiseResolution);

			this.distToCrack_xz = new NoiseSampleData2D(noiseScaleFactor, noiseResolution);

			this.heightSample_xz = new NoiseSampleData2D(noiseScaleFactor, noiseResolution);
			this.topSample_xz = new NoiseSampleData2D(noiseScaleFactor, noiseResolution);

			IrradiatedForestsData data = ObjectFilter.getFirstFrom(island.getComponents(), IrradiatedForestsData.class);

			if (data == null)
			{
				throw new RuntimeException("IrradiatedForestsData could not be found");
			}

			long start = System.nanoTime();

			if (data.checkInit())
			{
				AetherCore.LOGGER.info("Data generation took {}ns", (System.nanoTime() - start));
			}

			this.cracks = data.getCracksInRegion(chunkX, chunkZ, 1);
		}
	}
}
