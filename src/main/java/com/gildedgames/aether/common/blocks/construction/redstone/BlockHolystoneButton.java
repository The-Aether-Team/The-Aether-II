package com.gildedgames.aether.common.blocks.construction.redstone;

import net.minecraft.block.BlockButtonStone;
import net.minecraft.block.SoundType;

public class BlockHolystoneButton extends BlockButtonStone
{
	public BlockHolystoneButton()
	{
		super();

		this.setSoundType(SoundType.STONE);

		this.setHardness(0.5f);

		this.disableStats();
	}
}
