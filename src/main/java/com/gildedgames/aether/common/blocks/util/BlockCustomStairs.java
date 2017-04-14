package com.gildedgames.aether.common.blocks.util;

import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

public class BlockCustomStairs extends BlockStairs
{

	public BlockCustomStairs(final IBlockState state)
	{
		super(state);

		final Block block = state.getBlock();

		BlocksAether.applyPostRegistration(() ->
				BlockCustomStairs.this.setHarvestLevel(block.getHarvestTool(state), block.getHarvestLevel(state)));

		this.useNeighborBrightness = true;
	}

}
