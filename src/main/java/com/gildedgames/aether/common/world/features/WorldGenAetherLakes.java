package com.gildedgames.aether.common.world.features;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.util.BlockPosUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenAetherLakes extends WorldGenerator
{

	private final IBlockState liquid;

	public WorldGenAetherLakes(IBlockState block)
	{
		this.liquid = block;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos position)
	{
		BlockPos.MutableBlockPos pos = BlockPosUtil.toMutable(position.add(-8, 0, -8));

		while (pos.getY() > 5 && world.isAirBlock(pos))
		{
			pos = BlockPosUtil.down(pos, 1);
		}

		if (pos.getY() <= 4)
		{
			return false;
		}

		pos = BlockPosUtil.down(pos, 4);

		boolean[] aboolean = new boolean[2048];
		int i = rand.nextInt(4) + 4;
		int x;

		for (x = 0; x < i; ++x)
		{
			double d0 = rand.nextDouble() * 6.0D + 3.0D;
			double d1 = rand.nextDouble() * 4.0D + 2.0D;
			double d2 = rand.nextDouble() * 6.0D + 3.0D;
			double d3 = rand.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
			double d4 = rand.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
			double d5 = rand.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;

			for (int l = 1; l < 15; ++l)
			{
				for (int i1 = 1; i1 < 15; ++i1)
				{
					for (int j1 = 1; j1 < 7; ++j1)
					{
						double d6 = ((double) l - d3) / (d0 / 2.0D);
						double d7 = ((double) j1 - d4) / (d1 / 2.0D);
						double d8 = ((double) i1 - d5) / (d2 / 2.0D);
						double d9 = d6 * d6 + d7 * d7 + d8 * d8;

						if (d9 < 1.0D)
						{
							aboolean[(l * 16 + i1) * 8 + j1] = true;
						}
					}
				}
			}
		}

		int y;
		int z;
		boolean flag;

		for (x = 0; x < 16; ++x)
		{
			for (z = 0; z < 16; ++z)
			{
				for (y = 0; y < 8; ++y)
				{
					flag = !aboolean[(x * 16 + z) * 8 + y] && (x < 15 && aboolean[((x + 1) * 16 + z) * 8 + y] || x > 0 && aboolean[((x - 1) * 16 + z) * 8 + y] || z < 15 && aboolean[(x * 16 + z + 1) * 8 + y] || z > 0 && aboolean[(x * 16 + (z - 1)) * 8 + y] || y < 7 && aboolean[(x * 16 + z) * 8 + y + 1] || y > 0 && aboolean[(x * 16 + z) * 8 + (y - 1)]);

					if (flag)
					{
						Material material = world.getBlockState(pos.add(x, y, z)).getMaterial();

						if (y >= 4 && material.isLiquid())
						{
							return false;
						}

						if (y < 4 && !material.isSolid() && world.getBlockState(pos.add(x, y, z)).getBlock() != this.liquid)
						{
							return false;
						}
					}
				}
			}
		}

		for (x = 0; x < 16; ++x)
		{
			for (z = 0; z < 16; ++z)
			{
				for (y = 0; y < 8; ++y)
				{
					if (aboolean[(x * 16 + z) * 8 + y])
					{
						world.setBlockState(pos.add(x, y, z), y >= 4 ? Blocks.AIR.getDefaultState() : this.liquid, 2);
					}
				}
			}
		}

		for (x = 0; x < 16; ++x)
		{
			for (z = 0; z < 16; ++z)
			{
				for (y = 4; y < 8; ++y)
				{
					if (aboolean[(x * 16 + z) * 8 + y])
					{
						BlockPos nextPos = pos.add(x, y - 1, z);

						if (world.getBlockState(nextPos).getBlock() == BlocksAether.aether_dirt && world.getLightFor(EnumSkyBlock.SKY, pos.add(x, y, z)) > 0)
						{
							Biome biome = world.getBiome(new BlockPos(pos.getX(), 0, pos.getZ()));

							world.setBlockState(nextPos, biome.topBlock, 2);
						}
					}
				}
			}
		}

		return true;
	}
}
