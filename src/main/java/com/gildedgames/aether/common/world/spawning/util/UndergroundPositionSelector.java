package com.gildedgames.aether.common.world.spawning.util;

import com.gildedgames.aether.api.world.PositionSelector;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UndergroundPositionSelector implements PositionSelector
{
	@Override
	public int getPosY(World world, int posX, int posZ)
	{
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(posX, world.rand.nextInt(world.getActualHeight()), posZ);

		while (world.isAirBlock(pos) && pos.getY() > 0)
		{
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
