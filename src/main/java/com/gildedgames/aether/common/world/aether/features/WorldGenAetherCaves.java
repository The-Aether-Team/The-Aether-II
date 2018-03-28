package com.gildedgames.aether.common.world.aether.features;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.google.common.base.MoreObjects;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;

import java.util.Random;

public class WorldGenAetherCaves extends MapGenBase
{
	protected static final IBlockState BLK_AIR = Blocks.AIR.getDefaultState();

	protected void addRoom(
			final long p_180705_1_, final int p_180705_3_, final int p_180705_4_, final ChunkPrimer p_180705_5_, final double p_180705_6_,
			final double p_180705_8_, final double p_180705_10_)
	{
		this.addTunnel(p_180705_1_, p_180705_3_, p_180705_4_, p_180705_5_, p_180705_6_, p_180705_8_, p_180705_10_, 1.0F + this.rand.nextFloat() * 6.0F, 0.0F,
				0.0F, -1, -1, 0.5D);
	}

	protected void addTunnel(final long p_180704_1_, final int p_180704_3_, final int p_180704_4_, final ChunkPrimer p_180704_5_, double p_180704_6_,
			double p_180704_8_, double p_180704_10_, final float p_180704_12_, float p_180704_13_, float p_180704_14_, int p_180704_15_, int p_180704_16_,
			final double p_180704_17_)
	{
		final double d0 = (double) (p_180704_3_ * 16 + 8);
		final double d1 = (double) (p_180704_4_ * 16 + 8);
		float f = 0.0F;
		float f1 = 0.0F;
		final Random random = new Random(p_180704_1_);

		if (p_180704_16_ <= 0)
		{
			final int i = this.range * 16 - 16;
			p_180704_16_ = i - random.nextInt(i / 4);
		}

		boolean flag1 = false;

		if (p_180704_15_ == -1)
		{
			p_180704_15_ = p_180704_16_ / 2;
			flag1 = true;
		}

		final int j = random.nextInt(p_180704_16_ / 2) + p_180704_16_ / 4;

		for (final boolean flag = random.nextInt(6) == 0; p_180704_15_ < p_180704_16_; ++p_180704_15_)
		{
			final double d2 = 1.5D + (double) (MathHelper.sin((float) p_180704_15_ * (float) Math.PI / (float) p_180704_16_) * p_180704_12_);
			final double d3 = d2 * p_180704_17_;
			final float f2 = MathHelper.cos(p_180704_14_);
			final float f3 = MathHelper.sin(p_180704_14_);
			p_180704_6_ += (double) (MathHelper.cos(p_180704_13_) * f2);
			p_180704_8_ += (double) f3;
			p_180704_10_ += (double) (MathHelper.sin(p_180704_13_) * f2);

			if (flag)
			{
				p_180704_14_ = p_180704_14_ * 0.92F;
			}
			else
			{
				p_180704_14_ = p_180704_14_ * 0.7F;
			}

			p_180704_14_ = p_180704_14_ + f1 * 0.1F;
			p_180704_13_ += f * 0.1F;
			f1 = f1 * 0.9F;
			f = f * 0.75F;
			f1 = f1 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
			f = f + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;

			if (!flag1 && p_180704_15_ == j && p_180704_12_ > 1.0F)
			{
				this.addTunnel(random.nextLong(), p_180704_3_, p_180704_4_, p_180704_5_, p_180704_6_, p_180704_8_, p_180704_10_,
						random.nextFloat() * 0.5F + 0.5F, p_180704_13_ - ((float) Math.PI / 2F), p_180704_14_ / 3.0F, p_180704_15_, p_180704_16_, 1.0D);
				this.addTunnel(random.nextLong(), p_180704_3_, p_180704_4_, p_180704_5_, p_180704_6_, p_180704_8_, p_180704_10_,
						random.nextFloat() * 0.5F + 0.5F, p_180704_13_ + ((float) Math.PI / 2F), p_180704_14_ / 3.0F, p_180704_15_, p_180704_16_, 1.0D);
				return;
			}

			if (flag1 || random.nextInt(4) != 0)
			{
				final double d4 = p_180704_6_ - d0;
				final double d5 = p_180704_10_ - d1;
				final double d6 = (double) (p_180704_16_ - p_180704_15_);
				final double d7 = (double) (p_180704_12_ + 2.0F + 16.0F);

				if (d4 * d4 + d5 * d5 - d6 * d6 > d7 * d7)
				{
					return;
				}

				if (p_180704_6_ >= d0 - 16.0D - d2 * 2.0D && p_180704_10_ >= d1 - 16.0D - d2 * 2.0D && p_180704_6_ <= d0 + 16.0D + d2 * 2.0D
						&& p_180704_10_ <= d1 + 16.0D + d2 * 2.0D)
				{
					int j2 = MathHelper.floor(p_180704_6_ - d2) - p_180704_3_ * 16 - 1;
					int k = MathHelper.floor(p_180704_6_ + d2) - p_180704_3_ * 16 + 1;
					int k2 = MathHelper.floor(p_180704_8_ - d3) - 1;
					int l = MathHelper.floor(p_180704_8_ + d3) + 1;
					int l2 = MathHelper.floor(p_180704_10_ - d2) - p_180704_4_ * 16 - 1;
					int i1 = MathHelper.floor(p_180704_10_ + d2) - p_180704_4_ * 16 + 1;

					if (j2 < 0)
					{
						j2 = 0;
					}

					if (k > 16)
					{
						k = 16;
					}

					if (k2 < 1)
					{
						k2 = 1;
					}

					if (l > 200)
					{
						l = 200;
					}

					if (l2 < 0)
					{
						l2 = 0;
					}

					if (i1 > 16)
					{
						i1 = 16;
					}

					for (int i3 = j2; i3 < k; ++i3)
					{
						final double d10 = ((double) (i3 + p_180704_3_ * 16) + 0.5D - p_180704_6_) / d2;

						for (int j3 = l2; j3 < i1; ++j3)
						{
							final double d8 = ((double) (j3 + p_180704_4_ * 16) + 0.5D - p_180704_10_) / d2;

							for (int i2 = l; i2 > k2; --i2)
							{
								final double d9 = ((double) (i2 - 1) + 0.5D - p_180704_8_) / d3;

								if (d9 > -0.7D && d10 * d10 + d9 * d9 + d8 * d8 < 1.0D)
								{
									final IBlockState iblockstate1 = p_180704_5_.getBlockState(i3, i2, j3);
									final IBlockState iblockstate2 = (IBlockState) MoreObjects.firstNonNull(p_180704_5_.getBlockState(i3, i2 + 1, j3), BLK_AIR);

									if (this.isTopBlock(p_180704_5_, i3, i2, j3, p_180704_3_, p_180704_4_))
									{
										flag1 = true;
									}

									this.digBlock(p_180704_5_, i3, i2, j3, p_180704_3_, p_180704_4_, flag1, iblockstate1, iblockstate2);
								}
							}
						}
					}
				}
			}
		}
	}

	protected boolean canReplaceBlock(final IBlockState state, final IBlockState above)
	{
		if (state.getBlock() == Blocks.SNOW_LAYER)
		{
			return true;
		}
		else if (state.getBlock() == Blocks.PACKED_ICE)
		{
			return true;
		}
		else if (state.getBlock() == Blocks.ICE)
		{
			return true;
		}

		return state.getBlock() == BlocksAether.holystone || state.getBlock() == BlocksAether.quicksoil;
	}

	/**
	 * Recursively called by generate()
	 */
	@Override
	protected void recursiveGenerate(final World worldIn, final int chunkX, final int chunkZ, final int originalX, final int originalZ,
			final ChunkPrimer primer)
	{
		int i = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(10) + 1) + 1);

		if (this.rand.nextInt(2) != 0)
		{
			i = 0;
		}

		for (int j = 0; j < i; ++j)
		{
			final double d0 = (double) (chunkX * 16 + this.rand.nextInt(16));
			final double d1 = (double) this.rand.nextInt(128);
			final double d2 = (double) (chunkZ * 16 + this.rand.nextInt(16));
			int k = 1;

			if (this.rand.nextInt(4) == 0)
			{
				this.addRoom(this.rand.nextLong(), originalX, originalZ, primer, d0, d1, d2);
				k += this.rand.nextInt(4);
			}

			for (int l = 0; l < k; ++l)
			{
				final float f = this.rand.nextFloat() * ((float) Math.PI * 2F);
				final float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
				final float f2 = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();
				this.addTunnel(this.rand.nextLong(), originalX, originalZ, primer, d0, d1, d2, f2 * 2.0F, f, f1, 0, 0, 0.5D);
			}
		}
	}

	private boolean isTopBlock(final ChunkPrimer data, final int x, final int y, final int z, final int chunkX, final int chunkZ)
	{
		final Biome biome = this.world.getBiome(new BlockPos(x + chunkX * 16, 0, z + chunkZ * 16));

		return data.getBlockState(x, y, z) == biome.topBlock;
	}

	protected void digBlock(
			final ChunkPrimer data, final int x, final int y, final int z, final int chunkX, final int chunkZ, final boolean foundTop, final IBlockState state,
			final IBlockState up)
	{
		final Biome biome = this.world.getBiome(new BlockPos(x + chunkX * 16, 0, z + chunkZ * 16));

		final IBlockState top = biome.topBlock;
		final IBlockState filler = biome.fillerBlock;

		if (this.canReplaceBlock(state, up) || state.getBlock() == top.getBlock() || state.getBlock() == filler.getBlock())
		{
			data.setBlockState(x, y, z, BLK_AIR);

			if (foundTop && data.getBlockState(x, y - 1, z).getBlock() == filler.getBlock())
			{
				data.setBlockState(x, y - 1, z, top);
			}
		}
	}
}
