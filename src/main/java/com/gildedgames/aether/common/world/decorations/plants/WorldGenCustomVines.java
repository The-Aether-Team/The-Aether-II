package com.gildedgames.aether.common.world.decorations.plants;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockVine;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenCustomVines extends WorldGenerator
{

	private final BlockState vines;

	public WorldGenCustomVines(final BlockState vines)
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
				for (final Direction enumfacing : Direction.Plane.HORIZONTAL.facings())
				{
					if (Blocks.VINE.canPlaceBlockOnSide(world, pos, enumfacing))
					{
						final BlockState iblockstate = this.vines.withProperty(BlockVine.NORTH,
								enumfacing == Direction.NORTH).withProperty(BlockVine.EAST,
								enumfacing == Direction.EAST).withProperty(BlockVine.SOUTH,
								enumfacing == Direction.SOUTH).withProperty(BlockVine.WEST, enumfacing == Direction.WEST);
						world.setBlockState(pos, iblockstate, 2 | 16);

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
