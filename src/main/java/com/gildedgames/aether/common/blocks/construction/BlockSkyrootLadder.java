package com.gildedgames.aether.common.blocks.construction;

import net.minecraft.block.BlockLadder;
import net.minecraft.block.SoundType;

public class BlockSkyrootLadder extends BlockLadder
{
	public BlockSkyrootLadder()
	{
		super();

		this.setSoundType(SoundType.WOOD);

		this.setHardness(0.4f);

		this.disableStats();
	}
}
