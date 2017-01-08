package com.gildedgames.aether.common.world.dimensions.aether.island.logic;

import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorOctaves;

import java.util.Random;

public class WorldGeneratorIslandNew
{

	private double[][] noiseFields;

	private NoiseGeneratorOctaves[] octaveNoiseGenerators;

	private final Random rand;

	private final World world;

	public WorldGeneratorIslandNew(World world)
	{
		this.world = world;
		this.rand = new Random(world.getSeed());

		this.noiseFields = new double[9][];

		this.noiseFields[1] = new double[256];
		this.noiseFields[2] = new double[256];
		this.noiseFields[3] = new double[256];

		this.octaveNoiseGenerators = new NoiseGeneratorOctaves[7];

		this.octaveNoiseGenerators[0] = new NoiseGeneratorOctaves(this.rand, 16);
		this.octaveNoiseGenerators[1] = new NoiseGeneratorOctaves(this.rand, 16);
		this.octaveNoiseGenerators[2] = new NoiseGeneratorOctaves(this.rand, 32);
		this.octaveNoiseGenerators[3] = new NoiseGeneratorOctaves(this.rand, 64);
		this.octaveNoiseGenerators[4] = new NoiseGeneratorOctaves(this.rand, 4);
		this.octaveNoiseGenerators[5] = new NoiseGeneratorOctaves(this.rand, 10);
		this.octaveNoiseGenerators[6] = new NoiseGeneratorOctaves(this.rand, 16);
	}

	public void setBlocksInChunk(ChunkPrimer primer, IslandData data, IslandSector sector, int chunkX, int chunkZ)
	{
		final int dimXZ = 2;
		final int dimXZPlusOne = dimXZ + 1;

		final int dimY = 32;
		final int dimYPlusOne = dimY + 1;

		double width = (double) data.getBounds().width;
		double height = data.getHeight();
		double length = (double) data.getBounds().height;

		this.noiseFields[0] = this.initializeNoiseField(this.noiseFields[0],
				chunkX * dimXZ, 0, chunkZ * dimXZ, dimXZPlusOne, dimYPlusOne, dimXZPlusOne);

		int posX = chunkX * 16;
		int posZ = chunkZ * 16;

		for (int x = 0; x < dimXZ; x++)
		{
			for (int z = 0; z < dimXZ; z++)
			{
				for (int y = 0; y < dimY; y++)
				{
					double minXMinZ = this.noiseFields[0][(x * dimXZPlusOne + z) * dimYPlusOne + y];
					double minXMaxZ = this.noiseFields[0][(x * dimXZPlusOne + z + 1) * dimYPlusOne + y];
					double maxXMinZ = this.noiseFields[0][((x + 1) * dimXZPlusOne + z) * dimYPlusOne + y];
					double maxXMaxZ = this.noiseFields[0][((x + 1) * dimXZPlusOne + z + 1) * dimYPlusOne + y];

					double dMinXMinZ = (this.noiseFields[0][(x * dimXZPlusOne + z) * dimYPlusOne + y + 1] - minXMinZ) / 4;
					double dMinXMaxZ = (this.noiseFields[0][(x * dimXZPlusOne + z + 1) * dimYPlusOne + y + 1] - minXMaxZ) / 4;
					double dMaxXMinZ = (this.noiseFields[0][((x + 1) * dimXZPlusOne + z) * dimYPlusOne + y + 1] - maxXMinZ) / 4;
					double dMaxXMaxZ = (this.noiseFields[0][((x + 1) * dimXZPlusOne + z + 1) * dimYPlusOne + y + 1] - maxXMaxZ) / 4;

					for (int yIter = 0; yIter < 4; yIter++)
					{
						double d10 = minXMinZ;
						double d11 = minXMaxZ;
						double d12 = (maxXMinZ - minXMinZ) / 8;
						double d13 = (maxXMaxZ - minXMaxZ) / 8;

						for (int xIter = 0; xIter < 8; xIter++)
						{
							//Small essay about indices.
							//If you look inside the ChunkPrimer class, you'll see it can contain 65536 elements.
							//So yeah... 16 x 256 x 16!

							double d15 = d10;
							double d16 = (d11 - d10) / 8;

							for (int zIter = 0; zIter < 8; zIter++)
							{
								int blockX = xIter + x * 8;
								int blockY = 40 + (yIter + y * 4);
								int blockZ = zIter + z * 8;

								IBlockState fillBlock = Blocks.AIR.getDefaultState();

								/*double distNX = blockX - (double) (sector.getSectorX() * IslandSector.CHUNK_WIDTH_PER_SECTOR) - (data.getBounds().getWidth() / 2); // Subtract sector coords from nx/ny so that the noise is within range of the island center
								double distNZ = blockZ - (double) (sector.getSectorY() * IslandSector.CHUNK_WIDTH_PER_SECTOR) - (data.getBounds().getHeight() / 2);
								double ny = blockY - (data.getHeight() / 2);

								double dist = Math.sqrt((distNX * distNX) + (ny * ny) + (distNZ * distNZ)); // Get distance from center of Island

								double volume = (data.getHeight() + data.getBounds().getWidth()) / 2;

								double value = d15 - (dist / 300);*/

								double stepX = (double) posX + blockX;
								double stepZ = (double) posZ + blockZ;

								double nx = (stepX + data.getBounds().getMinX()) / 300.0; // normalize coords
								double nz = (stepZ + data.getBounds().getMinY()) / 300.0;

								//double flat = GenUtil.octavedNoise(this.simplex, 4, 0.7D, 2.5D, nx, nz);

								double distNX = ((stepX - data.getBounds().getMinX()) / width)
										- 0.5; // Subtract sector coords from nx/ny so that the noise is within range of the island center
								double distNZ = ((stepZ - data.getBounds().getMinY()) / length) - 0.5;

								double dist = 2.0 * Math.sqrt((distNX * distNX) + (distNZ * distNZ)); // Get distance from center of Island

								//d15 = (d15 + 0.0) - (0.7 * Math.pow(dist, 4));

								if (d15 - (25 * dist) > -15.8D)
								{
									fillBlock = BlocksAether.holystone.getDefaultState();
								}

								primer.setBlockState(blockX, blockY, blockZ, fillBlock);
								d15 += d16;
							}

							d10 += d12;
							d11 += d13;
						}

						minXMinZ += dMinXMinZ;
						minXMaxZ += dMinXMaxZ;
						maxXMinZ += dMaxXMinZ;
						maxXMaxZ += dMaxXMaxZ;
					}
				}
			}
		}
	}

	private double[] initializeNoiseField(double[] inputDoubles, int x, int y, int z, int width, int height, int length)
	{
		if (inputDoubles == null)
		{
			inputDoubles = new double[width * height * length];
		}

		double const1 = 684.41200000000003D * 2.0D;
		double const2 = 684.41200000000003D;

		this.noiseFields[4] = this.octaveNoiseGenerators[2].generateNoiseOctaves(this.noiseFields[4], x, y, z, width, height, length,
				const1 / 20D, const2 / 160D, const1 / 80D);
		this.noiseFields[5] = this.octaveNoiseGenerators[0].generateNoiseOctaves(this.noiseFields[5], x, y, z, width, height, length, const1, const2, const1);
		this.noiseFields[6] = this.octaveNoiseGenerators[1].generateNoiseOctaves(this.noiseFields[6], x, y, z, width, height, length, const1, const2, const1);

		this.noiseFields[7] = this.octaveNoiseGenerators[5].generateNoiseOctaves(this.noiseFields[7], x, z, width, length, 1.121D, 1.121D, 0.5D);//Note: The last argument is never used
		this.noiseFields[8] = this.octaveNoiseGenerators[6].generateNoiseOctaves(this.noiseFields[8], x, z, width, length, 20D, 20D, 0.5D);

		int index = 0;

		for (int x1 = 0; x1 < width; x1++)
		{
			for (int z1 = 0; z1 < length; z1++)
			{
				for (int y1 = 0; y1 < height; y1++)
				{
					double finalHeight;

					double sample1 = this.noiseFields[5][index] / 512D;
					double sample2 = this.noiseFields[6][index] / 512D;
					double sample3 = (this.noiseFields[4][index] / 10D + 1.0D) / 2D;

					if (sample3 < 0.0D)
					{
						finalHeight = sample1;
					}
					else if (sample3 > 1.0D)
					{
						finalHeight = sample2;
					}
					else
					{
						finalHeight = sample1 + (sample2 - sample1) * sample3;
					}

					finalHeight -= 20D;

					if (y1 > height - 32)//If y1 > 1
					{
						double dy = (y1 - (height - 32)) / 31D;
						finalHeight = finalHeight * (1.0D - dy) + -30D * dy;//
					}

					if (y1 < 8)
					{
						double dy = (8 - y1) / 7D;
						finalHeight = finalHeight * (1.0D - dy) + -30D * dy;
					}

					inputDoubles[index] = finalHeight;
					index++;
				}
			}
		}

		return inputDoubles;
	}

	public void genIslandForChunk(ChunkPrimer primer, IslandData data, IslandSector sector, int chunkX, int chunkZ)
	{
		this.setBlocksInChunk(primer, data, sector, chunkX, chunkZ);
	}

}
