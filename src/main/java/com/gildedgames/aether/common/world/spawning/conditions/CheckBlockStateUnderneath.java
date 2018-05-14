package com.gildedgames.aether.common.world.spawning.conditions;

import com.gildedgames.aether.api.world.PosCondition;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CheckBlockStateUnderneath implements PosCondition
{

	private IBlockState[] statesToCheckFor;

	public CheckBlockStateUnderneath(IBlockState... statesToCheckFor)
	{
		this.statesToCheckFor = statesToCheckFor;
	}

	@Override
	public boolean isMet(World world, BlockPos spawnAt, BlockPos underneath)
	{
		for (IBlockState state : this.statesToCheckFor)
		{
			IBlockState check = world.getBlockState(underneath);

			if (state == check)
			{
				return true;
			}
		}

		return false;
	}

}
