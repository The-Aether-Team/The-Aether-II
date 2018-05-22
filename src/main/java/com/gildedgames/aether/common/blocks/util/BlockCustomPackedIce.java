package com.gildedgames.aether.common.blocks.util;

import net.minecraft.block.BlockPackedIce;
import net.minecraft.block.SoundType;

public class BlockCustomPackedIce extends BlockPackedIce
{
	public BlockCustomPackedIce()
	{
		super();

		this.setSoundType(SoundType.GLASS);
	}
}
