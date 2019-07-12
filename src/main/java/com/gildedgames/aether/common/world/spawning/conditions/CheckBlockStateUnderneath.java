package com.gildedgames.aether.common.world.spawning.conditions;

import com.gildedgames.aether.api.world.spawn.conditions.IConditionPosition;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CheckBlockStateUnderneath implements IConditionPosition
{

	private final BlockState[] statesToCheckFor;

	public CheckBlockStateUnderneath(BlockState... statesToCheckFor)
	{
		this.statesToCheckFor = statesToCheckFor;
	}

	@Override
	public boolean isMet(World world, BlockPos spawnAt, BlockPos underneath)
	{
		for (BlockState state : this.statesToCheckFor)
		{
			BlockState check = world.getBlockState(underneath);

			if (state == check)
			{
				return true;
			}
		}

		return false;
	}

}
