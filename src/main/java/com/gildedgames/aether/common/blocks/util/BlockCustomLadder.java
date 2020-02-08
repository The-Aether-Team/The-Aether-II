package com.gildedgames.aether.common.blocks.util;

import com.gildedgames.aether.common.blocks.IBlockWithItem;
import com.gildedgames.aether.common.items.blocks.ItemBlockCustomLadder;
import com.gildedgames.aether.common.items.blocks.ItemBlockCustomSlab;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.SoundType;
import net.minecraft.item.ItemBlock;

public class BlockCustomLadder extends BlockLadder implements IBlockWithItem
{

	public BlockCustomLadder()
	{
		super();

		this.setSoundType(SoundType.WOOD);

		this.setHardness(0.4f);

		this.disableStats();
	}

	@Override
	public ItemBlock createItemBlock()
	{
		return new ItemBlockCustomLadder(this);
	}
}
