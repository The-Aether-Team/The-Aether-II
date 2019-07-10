package com.gildedgames.aether.common.world.spawning.conditions;

import com.gildedgames.aether.api.world.spawn.conditions.IConditionPosition;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CheckIsUnderground implements IConditionPosition
{

	public CheckIsUnderground()
	{

	}

	@Override
	public boolean isMet(World world, BlockPos spawnAt, BlockPos underneath)
	{
		return world.getTopSolidOrLiquidBlock(spawnAt).getY() > spawnAt.getY();
	}
}
