package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;

public class ItemArkeniumShears extends ItemShears
{

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state)
	{
		Block block = state.getBlock();

		if (block == BlocksAether.cloudwool_block)
		{
			return 5.0f;
		}

		return super.getDestroySpeed(stack, state);
	}
}
