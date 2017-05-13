package com.gildedgames.aether.common.blocks.util;

import net.minecraft.block.BlockLadder;
import net.minecraft.block.SoundType;

public class BlockCustomLadder extends BlockLadder
{

	public BlockCustomLadder()
	{
		super();

		this.setSoundType(SoundType.WOOD);

		this.setHardness(0.4f);

		this.disableStats();
	}

}
