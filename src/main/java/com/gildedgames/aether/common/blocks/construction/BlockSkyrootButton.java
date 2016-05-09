package com.gildedgames.aether.common.blocks.construction;

import net.minecraft.block.Block;
import net.minecraft.block.BlockButtonWood;

public class BlockSkyrootButton extends BlockButtonWood
{
	public BlockSkyrootButton()
	{
		super();

		this.setStepSound(Block.soundTypeWood);

		this.setHardness(0.5f);

		this.disableStats();
	}
}
