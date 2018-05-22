package com.gildedgames.aether.common.blocks.util;

import net.minecraft.block.BlockSnowBlock;
import net.minecraft.block.SoundType;

public class BlockCustomSnowBlock extends BlockSnowBlock
{
	public BlockCustomSnowBlock()
	{
		super();

		this.setHardness(0.2F);
		this.setSoundType(SoundType.SNOW);
	}
}
