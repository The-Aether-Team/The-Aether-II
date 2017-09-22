package com.gildedgames.aether.common.world.aether.features;

import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenCustomVines extends WorldGenerator
{

	private final IBlockState vines;

	public WorldGenCustomVines(final IBlockState vines)
	{
		this.vines = vines;
	}

	@Override
	public boolean generate(final World world, final Random rand, BlockPos pos)
	{
		while (pos.getY() < 128)
		{
			if (world.isAirBlock(pos))
			{
				for (final EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL.facings())
				{
					if (Blocks.VINE.canPlaceBlockOnSide(world, pos, enumfacing))
					{
						final IBlockState iblockstate = this.vines.withProperty(BlockVine.NORTH,
								enumfacing == EnumFacing.NORTH).withProperty(BlockVine.EAST,
								enumfacing == EnumFacing.EAST).withProperty(BlockVine.SOUTH,
								enumfacing == EnumFacing.SOUTH).withProperty(BlockVine.WEST, enumfacing == EnumFacing.WEST);
						world.setBlockState(pos, iblockstate, 2);

						break;
					}
				}
			}
			else
			{
				pos = pos.add(rand.nextInt(4) - rand.nextInt(4), 0, rand.nextInt(4) - rand.nextInt(4));
			}

			pos = pos.up();
		}

		return true;
	}
}
