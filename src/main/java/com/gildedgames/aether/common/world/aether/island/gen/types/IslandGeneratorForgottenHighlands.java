package com.gildedgames.aether.common.world.aether.island.gen.types;

import com.gildedgames.aether.api.util.NoiseUtil;
import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.IAetherChunkColumnInfo;
import com.gildedgames.aether.api.world.islands.IIslandChunkColumnInfo;
import com.gildedgames.aether.api.world.islands.IIslandData;
import com.gildedgames.aether.api.world.islands.IIslandGenerator;
import com.gildedgames.aether.api.world.noise.IChunkNoiseBuffer3D;
import com.gildedgames.aether.common.world.aether.chunk.ChunkDataGenerator3D;
import com.gildedgames.aether.common.world.aether.chunk.NoiseSampleData3D;
import com.gildedgames.aether.common.world.aether.island.gen.AbstractIslandChunkColumnInfo;
import com.gildedgames.aether.common.world.aether.island.gen.IslandBlockType;
import com.gildedgames.aether.common.world.aether.island.gen.IslandChunkMaskTransformer;
import com.gildedgames.orbis_api.preparation.IChunkMaskTransformer;
import com.gildedgames.orbis_api.preparation.impl.ChunkMask;

import javax.annotation.Nonnull;

public class IslandGeneratorForgottenHighlands implements IIslandGenerator
{
	private class ChunkDataGeneratorForgottenHighlands extends ChunkDataGenerator3D<ForgottenHighlandsNoiseData>
	{
		private final double minX, minZ;

		private final double centerX, centerY, centerZ;

		private final double radiusX, radiusY, radiusZ;

		private final double offset = 0;

		private final double scaleXZ = 300.0D, scaleY = 0.5D;

		private final OpenSimplexNoise noise;

		private final boolean centerGradient = false;

		public ChunkDataGeneratorForgottenHighlands(OpenSimplexNoise noise, IIslandData island)
		{
			super(3, 15);

			this.noise = noise;

			this.minX = island.getBounds().getMinX();
			this.minZ = island.getBounds().getMinZ();

			this.centerX = island.getBounds().getCenterX();
			this.centerY = 100;
			this.centerZ = island.getBounds().getCenterZ();

			this.radiusX = island.getBounds().getWidth() / 2.0;
			this.radiusY = 180 / 2.0;
			this.radiusZ = island.getBounds().getLength() / 2.0;
		}

		@Override
		protected ForgottenHighlandsNoiseData prepare(int chunkX, int chunkZ)
		{
			return new ForgottenHighlandsNoiseData(chunkX, chunkZ, this.noiseScaleFactorXZ, this.noiseScaleFactorY, this.noiseSampleCountXZ, this.noiseSampleCountY);
		}

		@Override
		protected void generate(ForgottenHighlandsNoiseData data, int x, int y, int z, double worldX, double worldY, double worldZ)
		{
			final double nx = (worldX + this.minX + this.offset) / this.scaleXZ;
			final double ny = (worldY + this.offset) / this.scaleY;
			final double nz = (worldZ + this.minZ + this.offset) / this.scaleXZ;

			final double distX = Math.abs((this.centerX - worldX) * (1.0 / this.radiusX));
			final double distY = Math.abs((this.centerY - worldY) * (1.0 / this.radiusY));
			final double distZ = Math.abs((this.centerZ - worldZ) * (1.0 / this.radiusZ));

			// Get distance from center of Island
			final double dist = Math.sqrt(distX * distX + distY * distY + distZ * distZ);

			final double sample = NoiseUtil.genNoise(this.noise, nx, ny, nz);

			final double height = sample - (this.centerGradient ? dist : 0);

			data.samples.set(x, y, z, (float) (height + 1.0 - dist));
		}
	}

	private class ForgottenHighlandsNoiseData
	{
		private final NoiseSampleData3D samples;

		private final int chunkX, chunkZ;

		private ForgottenHighlandsNoiseData(int chunkX, int chunkZ, double noiseScaleFactorXZ, double noiseScaleFactorY, int sampleCountXZ, int sampleCountY)
		{
			this.chunkX = chunkX;
			this.chunkZ = chunkZ;

			this.samples = new NoiseSampleData3D(noiseScaleFactorXZ, noiseScaleFactorY, sampleCountXZ, sampleCountY);
		}
	}

	private class ForgottenHighlandsColumnInfo extends AbstractIslandChunkColumnInfo
	{
		public final IChunkNoiseBuffer3D samples;

		protected ForgottenHighlandsColumnInfo(OpenSimplexNoise noise, int chunkX, int chunkZ, ForgottenHighlandsNoiseData noiseData)
		{
			super(noise, chunkX, chunkZ);

			this.samples = noiseData.samples.createInterpolatedNoiseBuffer();
		}
	}

	@Override
	public void generateChunkSegment(IAetherChunkColumnInfo info, ChunkMask mask, IIslandData island, int chunkX, int chunkZ)
	{
		ForgottenHighlandsColumnInfo column = info.getIslandData(0, ForgottenHighlandsColumnInfo.class);

		double k = 1.0 / 16.0;

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				for (int y = 0; y < 15; y++)
				{
					float a = column.samples.get(x, y, z);
					float b = column.samples.get(x, y + 1, z);

					float t = 0.0f;

					for (int y2 = 0; y2 < 16; y2++, t += k)
					{
						float result = a + (b - a) * t;

						if (result > 0.2f)
						{
							mask.setBlock(x, (y * 16) + y2, z, IslandBlockType.STONE_BLOCK.ordinal());
						}
					}
				}
			}
		}
	}

	@Nonnull
	@Override
	public IIslandChunkColumnInfo generateColumnInfo(OpenSimplexNoise noise, IIslandData island, int chunkX, int chunkZ)
	{
		ForgottenHighlandsNoiseData noiseData = new ChunkDataGeneratorForgottenHighlands(noise, island)
				.generate(chunkX, chunkZ);

		return new ForgottenHighlandsColumnInfo(noise, chunkX, chunkZ, noiseData);
	}

	@Override
	public IChunkMaskTransformer createMaskTransformer(IIslandData island, int chunkX, int chunkZ)
	{
		return new IslandChunkMaskTransformer();
	}
}
