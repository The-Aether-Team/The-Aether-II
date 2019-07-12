package com.gildedgames.aether.common.world.spawning.conditions;

import com.gildedgames.aether.api.world.spawn.conditions.IConditionPosition;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CheckBlockUnderneath implements IConditionPosition
{

	private final Block[] blocksToCheckFor;

	public CheckBlockUnderneath(Block... blocksToCheckFor)
	{
		this.blocksToCheckFor = blocksToCheckFor;
	}

	@Override
	public boolean isMet(World world, BlockPos spawnAt, BlockPos underneath)
	{
		for (Block block : this.blocksToCheckFor)
		{
			BlockState check = world.getBlockState(underneath);

			if (block == check.getBlock())
			{
				return true;
			}
		}

		return false;
	}

}
