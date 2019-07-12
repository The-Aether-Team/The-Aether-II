package com.gildedgames.aether.common.blocks.util;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockState;

public class BlockCustomStairs extends BlockStairs
{

	public BlockCustomStairs(final BlockState state)
	{
		super(state);

		this.useNeighborBrightness = true;
	}

}
