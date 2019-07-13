package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.api.registrar.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;

public class ItemArkeniumShears extends ShearsItem
{

	public ItemArkeniumShears(Properties builder)
	{
		super(builder);
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state)
	{
		Block block = state.getBlock();

		if (block == BlocksAether.cloudwool_block)
		{
			return 5.0f;
		}

		return super.getDestroySpeed(stack, state);
	}
}
