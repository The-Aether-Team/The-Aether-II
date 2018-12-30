package com.gildedgames.aether.common.world.aether.features;

import com.gildedgames.aether.common.world.aether.island.gen.IslandBlockType;
import com.gildedgames.orbis_api.preparation.impl.ChunkMask;
import com.gildedgames.orbis_api.util.XoShiRoRandom;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Random;

public class WorldGenAetherCaves
{

	protected final int range = 8;

	protected final ThreadLocal<XoShiRoRandom> rand = ThreadLocal.withInitial(XoShiRoRandom::new);

	protected void addRoom(long seed, int originalX, int originalZ, ChunkMask mask, double dirX, double dirY, double dirZ, Biome[] biomes)
	{
		this.addTunnel(seed, originalX, originalZ, mask, dirX, dirY, dirZ, 1.0F + this.rand.get().nextFloat() * 6.0F,
				0.0F, 0.0F, -1, -1, 0.5D, biomes);
	}

	protected void addTunnel(long seed, int originalX, int originalZ, ChunkMask mask, double p_180702_6_, double p_180702_8_,
			double p_180702_10_, float p_180702_12_, float p_180702_13_, float p_180702_14_, int p_180702_15_, int p_180702_16_, double p_180702_17_,
			Biome[] biomes)
	{
		double d0 = (double) (originalX * 16 + 8);
		double d1 = (double) (originalZ * 16 + 8);
		float f = 0.0F;
		float f1 = 0.0F;
		XoShiRoRandom random = new XoShiRoRandom(seed);

		if (p_180702_16_ <= 0)
		{
			int i = this.range * 16 - 16;
			p_180702_16_ = i - random.nextInt(i / 4);
		}

		boolean flag2 = false;

		if (p_180702_15_ == -1)
		{
			p_180702_15_ = p_180702_16_ / 2;
			flag2 = true;
		}

		int j = random.nextInt(p_180702_16_ / 2) + p_180702_16_ / 4;

		for (boolean flag = random.nextInt(6) == 0; p_180702_15_ < p_180702_16_; ++p_180702_15_)
		{
			double d2 = 1.5D + (double) (MathHelper.sin((float) p_180702_15_ * (float) Math.PI / (float) p_180702_16_) * p_180702_12_);
			double d3 = d2 * p_180702_17_;
			float f2 = MathHelper.cos(p_180702_14_);
			float f3 = MathHelper.sin(p_180702_14_);
			p_180702_6_ += (double) (MathHelper.cos(p_180702_13_) * f2);
			p_180702_8_ += (double) f3;
			p_180702_10_ += (double) (MathHelper.sin(p_180702_13_) * f2);

			if (flag)
			{
				p_180702_14_ = p_180702_14_ * 0.92F;
			}
			else
			{
				p_180702_14_ = p_180702_14_ * 0.7F;
			}

			p_180702_14_ = p_180702_14_ + f1 * 0.1F;
			p_180702_13_ += f * 0.1F;
			f1 = f1 * 0.9F;
			f = f * 0.75F;
			f1 = f1 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
			f = f + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;

			if (!flag2 && p_180702_15_ == j && p_180702_12_ > 1.0F && p_180702_16_ > 0)
			{
				this.addTunnel(random.nextLong(), originalX, originalZ, mask, p_180702_6_, p_180702_8_, p_180702_10_,
						random.nextFloat() * 0.5F + 0.5F, p_180702_13_ - ((float) Math.PI / 2F), p_180702_14_ / 3.0F, p_180702_15_, p_180702_16_, 1.0D, biomes);
				this.addTunnel(random.nextLong(), originalX, originalZ, mask, p_180702_6_, p_180702_8_, p_180702_10_,
						random.nextFloat() * 0.5F + 0.5F, p_180702_13_ + ((float) Math.PI / 2F), p_180702_14_ / 3.0F, p_180702_15_, p_180702_16_, 1.0D, biomes);
				return;
			}

			if (flag2 || random.nextInt(4) != 0)
			{
				double d4 = p_180702_6_ - d0;
				double d5 = p_180702_10_ - d1;
				double d6 = (double) (p_180702_16_ - p_180702_15_);
				double d7 = (double) (p_180702_12_ + 2.0F + 16.0F);

				if (d4 * d4 + d5 * d5 - d6 * d6 > d7 * d7)
				{
					return;
				}

				if (p_180702_6_ >= d0 - 16.0D - d2 * 2.0D && p_180702_10_ >= d1 - 16.0D - d2 * 2.0D && p_180702_6_ <= d0 + 16.0D + d2 * 2.0D
						&& p_180702_10_ <= d1 + 16.0D + d2 * 2.0D)
				{
					int k2 = MathHelper.floor(p_180702_6_ - d2) - originalX * 16 - 1;
					int k = MathHelper.floor(p_180702_6_ + d2) - originalX * 16 + 1;
					int l2 = MathHelper.floor(p_180702_8_ - d3) - 1;
					int l = MathHelper.floor(p_180702_8_ + d3) + 1;
					int i3 = MathHelper.floor(p_180702_10_ - d2) - originalZ * 16 - 1;
					int i1 = MathHelper.floor(p_180702_10_ + d2) - originalZ * 16 + 1;

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

					boolean flag3 = false;

					for (int j1 = k2; !flag3 && j1 < k; ++j1)
					{
						for (int k1 = i3; !flag3 && k1 < i1; ++k1)
						{
							for (int l1 = l + 1; !flag3 && l1 >= l2 - 1; --l1)
							{
								if (l1 >= 0 && l1 < 256)
								{
									if (this.isOceanBlock(mask, j1, l1, k1, originalX, originalZ))
									{
										flag3 = true;
									}

									if (l1 != l2 - 1 && j1 != k2 && j1 != k - 1 && k1 != i3 && k1 != i1 - 1)
									{
										l1 = l2;
									}
								}
							}
						}
					}

					if (!flag3)
					{
						for (int j3 = k2; j3 < k; ++j3)
						{
							double d10 = ((double) (j3 + originalX * 16) + 0.5D - p_180702_6_) / d2;

							for (int i2 = i3; i2 < i1; ++i2)
							{
								double d8 = ((double) (i2 + originalZ * 16) + 0.5D - p_180702_10_) / d2;
								boolean flag1 = false;

								if (d10 * d10 + d8 * d8 < 1.0D)
								{
									for (int j2 = l; j2 > l2; --j2)
									{
										double d9 = ((double) (j2 - 1) + 0.5D - p_180702_8_) / d3;

										if (d9 > -0.7D && d10 * d10 + d9 * d9 + d8 * d8 < 1.0D)
										{
											int blockType1 = mask.getBlock(j3, j2, i2);
											int blockType2 = mask.getBlock(j3, j2 + 1, i2);

											if (this.isTopBlock(mask, biomes, j3, j2, i2, originalX, originalZ))
											{
												flag1 = true;
											}

											this.digBlock(mask, j3, j2, i2, originalX, originalZ, flag1, blockType1, blockType2);
										}
									}
								}
							}
						}

						if (flag2)
						{
							break;
						}
					}
				}
			}
		}
	}

	protected boolean canReplaceBlock(int state, int above)
	{
		return state == IslandBlockType.STONE_BLOCK.ordinal() || state == IslandBlockType.COAST_BLOCK.ordinal() || state == IslandBlockType.FERROSITE_BLOCK
				.ordinal();
	}

	protected boolean isOceanBlock(ChunkMask data, int x, int y, int z, int chunkX, int chunkZ)
	{
		return data.getBlock(x, y, z) == IslandBlockType.WATER_BLOCK.ordinal();
	}

	public void generate(World worldIn, int x, int z, ChunkMask mask, Biome[] biomes)
	{
		XoShiRoRandom rand = this.rand.get();
		rand.setSeed(worldIn.getSeed());

		int i = this.range;

		long j = rand.nextLong();
		long k = rand.nextLong();

		for (int l = x - i; l <= x + i; ++l)
		{
			for (int i1 = z - i; i1 <= z + i; ++i1)
			{
				long j1 = (long) l * j;
				long k1 = (long) i1 * k;

				rand.setSeed(j1 ^ k1 ^ worldIn.getSeed());

				this.recursiveGenerate(worldIn, l, i1, x, z, mask, biomes);
			}
		}
	}

	/**
	 * Recursively called by generate()
	 */
	protected void recursiveGenerate(World world, int chunkX, int chunkZ, int originalX, int originalZ, ChunkMask mask, Biome[] biomes)
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
			double y = (double) rand.nextInt(128);
			double z = (double) (chunkZ * 16 + rand.nextInt(16));

			int tunnels = 2;

			if (rand.nextInt(4) == 0)
			{
				this.addRoom(rand.nextLong(), originalX, originalZ, mask, x, y, z, biomes);
				tunnels += rand.nextInt(4);
			}

			for (int l = 0; l < tunnels; ++l)
			{
				float f = rand.nextFloat() * ((float) Math.PI * 2F);
				float f1 = (rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
				float f2 = rand.nextFloat() * 2.0F + rand.nextFloat();

				if (rand.nextInt(10) == 0)
				{
					f2 *= rand.nextFloat() * rand.nextFloat() * 3.0F + 1.0F;
				}

				this.addTunnel(rand.nextLong(), originalX, originalZ, mask, x, y, z, f2 * 2.0F, f, f1, 0, 0, 0.5D, biomes);
			}
		}
	}

	private boolean isTopBlock(ChunkMask data, Biome[] biomes, int x, int y, int z, int chunkX, int chunkZ)
	{
		return data.getBlock(x, y, z) == IslandBlockType.TOPSOIL_BLOCK.ordinal();
	}

	private void digBlock(ChunkMask data, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop, int state, int up)
	{
		if (this.canReplaceBlock(state, up) || state == IslandBlockType.TOPSOIL_BLOCK.ordinal() || state == IslandBlockType.SOIL_BLOCK.ordinal())
		{
			data.setBlock(x, y, z, IslandBlockType.AIR_BLOCK.ordinal());

			if (foundTop && data.getBlock(x, y - 1, z) == IslandBlockType.SOIL_BLOCK.ordinal())
			{
				data.setBlock(x, y - 1, z, IslandBlockType.TOPSOIL_BLOCK.ordinal());
			}
		}
	}
}
