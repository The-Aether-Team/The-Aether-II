package com.gildedgames.aether.common.blocks.util;

import net.minecraft.block.BlockIce;
import net.minecraft.block.SoundType;

public class BlockCustomIce extends BlockIce
{
	public BlockCustomIce()
	{
		super();

		this.setSoundType(SoundType.GLASS);
	}
}
