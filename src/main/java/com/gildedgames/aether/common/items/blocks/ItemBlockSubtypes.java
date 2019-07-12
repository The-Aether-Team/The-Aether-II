package com.gildedgames.aether.common.items.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class ItemBlockSubtypes extends BlockItem
{
	public ItemBlockSubtypes(Block block)
	{
		super(block);

		this.setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}
}
