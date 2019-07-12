package com.gildedgames.aether.common.world.spawning.util;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.world.spawn.IPositionSelector;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UndergroundPositionSelector implements IPositionSelector
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
				BlockState state = world.getBlockState(pos);

				if (state.isFullBlock() && state.getBlock() != BlocksAether.aether_grass)
				{
					return pos.getY() + 1;
				}
			}

			pos.move(Direction.DOWN);
		}

		return pos.getY() + 1;
	}

	@Override
	public int getScatter(World world)
	{
		return 4;
	}
}
