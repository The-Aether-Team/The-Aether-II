package com.gildedgames.aether.common.world.aether.features;

import com.gildedgames.aether.common.world.aether.island.gen.IslandBlockType;
import com.gildedgames.aether.common.world.preparation.ChunkMask;
import com.gildedgames.orbis.lib.util.random.XoRoShiRoRandom;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class WorldGenUndergroundVeins
{
	protected final int chunkRange = 12;

	protected final ThreadLocal<XoRoShiRoRandom> rand = ThreadLocal.withInitial(XoRoShiRoRandom::new);

	protected void addTunnel(long seed, int chunkX, int chunkZ, ChunkMask mask, double posX, double posY, double posZ, float nodeSizeMultiplier,
			float angleBetweenNodes, float distBetweenNodes, int nodeIndex, int noOfNodes, double startingNodeHeightMult)
	{
		double chunkBlockCenterX = (double) (chunkX * 16 + 8);
		double chunkBlockCenterZ = (double) (chunkZ * 16 + 8);
		float nodeAngleMult = 0.0F;
		float nodeDistMult = 0.0F;
		XoRoShiRoRandom random = new XoRoShiRoRandom(seed);

		// Generates number of nodes the tunnel is going to have based on the chunkRange
		if (noOfNodes <= 0)
		{
			int i = this.chunkRange * 16 - 16;
			noOfNodes = i - random.nextInt(i / 4);
		}

		// Gets a random value between (noOfNodes/4, noOfNodes/4 + noOfNodes/2 - 1)
		int branchNodeIndex = random.nextInt(noOfNodes / 2) + noOfNodes / 4;
		// branchNodeIndex = nodeIndex + (noOfNodes - nodeIndex)/ 2; -- Custom code to generate branches every half of a tunnel and branch

		for (boolean higherDist = random.nextInt(6) == 0; nodeIndex < noOfNodes; ++nodeIndex)
		{
			double nodeHeight = 1.5D + (double) (MathHelper.sin((float) nodeIndex * (float) Math.PI / (float) noOfNodes) * nodeSizeMultiplier);
			double d3 = nodeHeight * startingNodeHeightMult;
			float f2 = MathHelper.cos(distBetweenNodes);
			float f3 = MathHelper.sin(distBetweenNodes);

			// Determines the position of the next node of the tunnel
			posX += (double) (MathHelper.cos(angleBetweenNodes) * f2);
			posY += (double) f3;
			posZ += (double) (MathHelper.sin(angleBetweenNodes) * f2);

			// Changes distance between nodes based on higherDist for even more random generation (not sure why specifically those 2 values, though)
			distBetweenNodes *= (higherDist ? 0.92F : 0.7F);

			// Ensures that the next node is on a different position, so the tunnel isn't on the same Y level
			distBetweenNodes += nodeDistMult * 0.1F;
			angleBetweenNodes += nodeAngleMult * 0.1F;

			// Even more randomness introduced
			nodeDistMult = nodeDistMult * 0.9F;
			nodeAngleMult = nodeAngleMult * 0.75F;
			nodeDistMult = nodeDistMult + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
			nodeAngleMult = nodeAngleMult + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;

			if (random.nextInt(4) != 0)
			{
				double d4 = posX - chunkBlockCenterX;
				double d5 = posZ - chunkBlockCenterZ;
				double nodesLeft = (double) (noOfNodes - nodeIndex);
				double d7 = (double) (nodeSizeMultiplier + 18.0F);

				if (d4 * d4 + d5 * d5 - nodesLeft * nodesLeft > d7 * d7)
				{
					return;
				}

				if (posX >= chunkBlockCenterX - 16.0D - nodeHeight * 2.0D && posZ >= chunkBlockCenterZ - 16.0D - nodeHeight * 2.0D
						&& posX <= chunkBlockCenterX + 16.0D + nodeHeight * 2.0D
						&& posZ <= chunkBlockCenterZ + 16.0D + nodeHeight * 2.0D)
				{
					int k2 = MathHelper.floor(posX - nodeHeight) - chunkX * 16 - 1;
					int k = MathHelper.floor(posX + nodeHeight) - chunkX * 16 + 1;
					int l2 = MathHelper.floor(posY - d3) - 1;
					int l = MathHelper.floor(posY + d3) + 1;
					int i3 = MathHelper.floor(posZ - nodeHeight) - chunkZ * 16 - 1;
					int i1 = MathHelper.floor(posZ + nodeHeight) - chunkZ * 16 + 1;

					if (k2 < 0)
					{
						k2 = 0;
					}

					if (k > 16)
					{
						k = 16;
					}

					if (l2 < 1)
					{
						l2 = 1;
					}

					if (l > 248)
					{
						l = 248;
					}

					if (i3 < 0)
					{
						i3 = 0;
					}

					if (i1 > 16)
					{
						i1 = 16;
					}

					boolean isBlockWater = false;

					for (int j1 = k2; !isBlockWater && j1 < k; ++j1)
					{
						for (int k1 = i3; !isBlockWater && k1 < i1; ++k1)
						{
							for (int l1 = l + 1; !isBlockWater && l1 >= l2 - 1; --l1)
							{
								if (l1 >= 0 && l1 < 256)
								{
									if (this.isOceanBlock(mask, j1, l1, k1))
									{
										isBlockWater = true;
									}

									if (l1 != l2 - 1 && j1 != k2 && j1 != k - 1 && k1 != i3 && k1 != i1 - 1)
									{
										l1 = l2;
									}
								}
							}
						}
					}

					if (!isBlockWater)
					{
						for (int j3 = k2; j3 < k; ++j3)
						{
							double d10 = ((double) (j3 + chunkX * 16) + 0.5D - posX) / nodeHeight;

							for (int i2 = i3; i2 < i1; ++i2)
							{
								double d8 = ((double) (i2 + chunkZ * 16) + 0.5D - posZ) / nodeHeight;
								boolean foundTop = false;

								if (d10 * d10 + d8 * d8 < 1.0D)
								{
									for (int j2 = l; j2 > l2; --j2)
									{
										double d9 = ((double) (j2 - 1) + 0.5D - posY) / d3;

										if (d9 > -0.7D && d10 * d10 + d9 * d9 + d8 * d8 < 1.0D)
										{
											int block = mask.getBlock(j3, j2, i2);

											if (this.isTopBlock(mask, j3, j2, i2))
											{
												foundTop = true;
											}
											this.digBlock(mask, j3, j2, i2, foundTop, block);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	protected boolean isOceanBlock(ChunkMask data, int x, int y, int z)
	{
		return data.getBlock(x, y, z) == IslandBlockType.WATER_BLOCK.ordinal();
	}

	public void generate(long seed, int x, int z, ChunkMask mask)
	{

		XoRoShiRoRandom rand = this.rand.get();
		rand.setSeed(seed);

		int i = this.chunkRange;

		long j = rand.nextLong();
		long k = rand.nextLong();

		for (int l = x - i; l <= x + i; ++l)
		{
			for (int i1 = z - i; i1 <= z + i; ++i1)
			{
				long j1 = (long) l * j;
				long k1 = (long) i1 * k;

				rand.setSeed(j1 ^ k1 ^ seed);

				this.recursiveGenerate(l, i1, x, z, mask);
			}
		}
	}

	/**
	 * Recursively called by generate()
	 */
	private void recursiveGenerate(int chunkX, int chunkZ, int originalX, int originalZ, ChunkMask mask)
	{
		Random rand = this.rand.get();

		int i = rand.nextInt(rand.nextInt(rand.nextInt(10) + 1) + 1);

		if (rand.nextInt(5) != 0)
		{
			i = 0;
		}

		for (int j = 0; j < i; ++j)
		{
			double x = (double) (chunkX * 16 + rand.nextInt(16));
			double y = (double) rand.nextInt(64);
			double z = (double) (chunkZ * 16 + rand.nextInt(16));

			int tunnels = 2;

			for (int l = 0; l < tunnels; ++l)
			{
				float f = rand.nextFloat() * ((float) Math.PI * 2F);
				float f1 = (rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
				float f2 = rand.nextFloat() * 2.0F + rand.nextFloat();

				if (rand.nextInt(8) == 0)
				{
					this.addTunnel(rand.nextLong(), originalX, originalZ, mask, x, y, z, f2, f, f1, 0, 0, 0.5D);
					tunnels += rand.nextInt(2);
				}

				if (rand.nextInt(20) == 0)
				{
					f2 *= rand.nextFloat() * rand.nextFloat() * 3.0F + 1.0F;
				}

				this.addTunnel(rand.nextLong(), originalX, originalZ, mask, x, y, z, f2, f, f1, 0, 0, 0.5D);
			}

		}
	}

	private boolean isTopBlock(ChunkMask data, int x, int y, int z)
	{
		return data.getBlock(x, y, z) == IslandBlockType.TOPSOIL_BLOCK.ordinal();
	}

	private void digBlock(ChunkMask data, int x, int y, int z, boolean foundTop, int state)
	{
		if ((state == IslandBlockType.STONE_BLOCK.ordinal() || state == IslandBlockType.COAST_BLOCK.ordinal() || state == IslandBlockType.FERROSITE_BLOCK
				.ordinal()))
		{
			data.setBlock(x, y, z, IslandBlockType.VEIN_BLOCK.ordinal());

			if (foundTop && data.getBlock(x, y - 1, z) == IslandBlockType.SOIL_BLOCK.ordinal())
			{
				data.setBlock(x, y - 1, z, IslandBlockType.TOPSOIL_BLOCK.ordinal());
			}
		}
	}
}
