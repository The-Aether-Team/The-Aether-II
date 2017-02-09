package com.gildedgames.aether.common.world.spawning.conditions;

import com.gildedgames.aether.common.world.spawning.PosCondition;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CheckBlockStateAt implements PosCondition
{

	private final IBlockState[] statesToCheckFor;

	public CheckBlockStateAt(final IBlockState... statesToCheckFor)
	{
		this.statesToCheckFor = statesToCheckFor;
	}

	@Override
	public boolean isMet(final World world, final BlockPos spawnAt, final BlockPos underneath)
	{
		for (final IBlockState state : this.statesToCheckFor)
		{
			final IBlockState check = world.getBlockState(spawnAt);

			if (state == check)
			{
				return true;
			}
		}

		return false;
	}

}
