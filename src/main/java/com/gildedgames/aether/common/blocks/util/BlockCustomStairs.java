package com.gildedgames.aether.common.blocks.util;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

public class BlockCustomStairs extends BlockStairs
{

	public BlockCustomStairs(final IBlockState state)
	{
		super(state);

		this.useNeighborBrightness = true;
	}

}
