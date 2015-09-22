package com.gildedgames.aether.common.items.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockSubtypes extends ItemBlock
{
	public ItemBlockSubtypes(Block block)
	{
		super(block);

		this.setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int meta)
	{
		return meta;
	}
}
