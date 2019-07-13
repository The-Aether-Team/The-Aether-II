package com.gildedgames.aether.common.blocks.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;

public class BlockCustomStairs extends StairsBlock
{

	public BlockCustomStairs(final BlockState state, final Block.Properties properties)
	{
		super(state, properties);

		this.useNeighborBrightness = true;
	}

}
