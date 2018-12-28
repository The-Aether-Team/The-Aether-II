package com.gildedgames.aether.common.world.spawning.conditions;

import com.gildedgames.aether.api.world.PosCondition;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CheckBlockAtPosition implements PosCondition
{

	private final Block[] blocksToCheckFor;

	public CheckBlockAtPosition(Block... blocksToCheckFor)
	{
		this.blocksToCheckFor = blocksToCheckFor;
	}

	@Override
	public boolean isMet(World world, BlockPos spawnAt, BlockPos underneath)
	{
		for (Block block : this.blocksToCheckFor)
		{
			IBlockState check = world.getBlockState(spawnAt);

			if (block == check.getBlock())
			{
				return true;
			}
		}

		return false;
	}

}
