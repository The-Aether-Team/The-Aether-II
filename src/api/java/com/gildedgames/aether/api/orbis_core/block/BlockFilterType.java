package com.gildedgames.aether.api.orbis_core.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public enum BlockFilterType
{

	ALL
			{
				@Override
				public boolean filter(final IBlockState blockToFilter, final List<BlockDataWithConditions> requiredBlocks, final World world,
						final Random random)
				{
					return true;
				}

			},
	ALL_EXCEPT
			{
				@Override
				public boolean filter(final IBlockState blockToFilter, final List<BlockDataWithConditions> blackListedBlocks, final World world,
						final Random random)
				{
					for (final BlockDataWithConditions block : blackListedBlocks)
					{
						if (block.getBlockState() == blockToFilter || block.getBlock() == blockToFilter.getBlock())
						{
							return false;
						}
					}

					return true;
				}

			},
	ONLY
			{
				@Override
				public boolean filter(final IBlockState blockToFilter, final List<BlockDataWithConditions> requiredBlocks, final World world,
						final Random random)
				{
					for (final BlockDataWithConditions block : requiredBlocks)
					{
						if ((block.getBlock() == blockToFilter.getBlock() || block.getBlockState() == blockToFilter) && block.getRequiredCondition()
								.isMet(random, world))
						{
							return true;
						}
					}

					return false;
				}

			};

	public abstract boolean filter(IBlockState blockToFilter, List<BlockDataWithConditions> originalBlocks, World world, Random random);
}
