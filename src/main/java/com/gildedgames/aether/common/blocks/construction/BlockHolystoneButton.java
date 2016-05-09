package com.gildedgames.aether.common.blocks.construction;

import net.minecraft.block.Block;
import net.minecraft.block.BlockButtonStone;

public class BlockHolystoneButton extends BlockButtonStone
{
	public BlockHolystoneButton()
	{
		super();

		this.setStepSound(Block.soundTypeStone);

		this.setHardness(0.5f);

		this.disableStats();
	}
}
