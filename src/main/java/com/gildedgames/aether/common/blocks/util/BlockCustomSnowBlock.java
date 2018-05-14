package com.gildedgames.aether.common.blocks.util;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockCustomSnowBlock extends Block
{
	public BlockCustomSnowBlock()
	{
		super(Material.CRAFTED_SNOW);
		this.setTickRandomly(true);
	}
}
