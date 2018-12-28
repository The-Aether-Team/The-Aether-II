package com.gildedgames.aether.common.world.spawning.util;

import com.gildedgames.aether.api.world.PositionSelector;
import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UndergroundPositionSelector implements PositionSelector
{
	@Override
	public int getPosY(World world, int posX, int posZ)
	{
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(posX, world.rand.nextInt(world.getActualHeight()), posZ);

		boolean airFlag = false;
		boolean blockFlag = false;

		while (pos.getY() > 0)
		{
			if (world.isAirBlock(pos))
			{
				blockFlag = false;
				airFlag = true;
			}
			else
			{
				if (blockFlag)
				{
					airFlag = false;
				}

				blockFlag = true;
			}

			if (blockFlag && airFlag)
			{
				IBlockState state = world.getBlockState(pos);

				if (state.isFullBlock() && state.getBlock() != BlocksAether.aether_grass)
				{
					return pos.getY() + 1;
				}
			}

			pos.move(EnumFacing.DOWN);
		}

		return pos.getY() + 1;
	}

	@Override
	public int getScatter(World world)
	{
		return 4;
	}
}
