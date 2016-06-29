package com.gildedgames.aether.common.blocks.construction.redstone;

import net.minecraft.block.BlockButtonWood;
import net.minecraft.block.SoundType;

public class BlockSkyrootButton extends BlockButtonWood
{
	public BlockSkyrootButton()
	{
		super();

		this.setSoundType(SoundType.WOOD);

		this.setHardness(0.5f);

		this.disableStats();
	}
}
