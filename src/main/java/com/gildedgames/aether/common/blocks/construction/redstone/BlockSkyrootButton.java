package com.gildedgames.aether.common.blocks.construction.redstone;

import com.gildedgames.aether.common.blocks.IBlockWithItem;
import com.gildedgames.aether.common.items.blocks.ItemBlockCustomLadder;
import com.gildedgames.aether.common.items.blocks.ItemBlockSkyrootButton;
import net.minecraft.block.BlockButtonWood;
import net.minecraft.block.SoundType;
import net.minecraft.item.ItemBlock;

public class BlockSkyrootButton extends BlockButtonWood implements IBlockWithItem
{
	public BlockSkyrootButton()
	{
		super();

		this.setSoundType(SoundType.WOOD);

		this.setHardness(0.5f);

		this.disableStats();
	}

	@Override
	public ItemBlock createItemBlock()
	{
		return new ItemBlockSkyrootButton(this);
	}
}
