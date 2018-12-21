package com.gildedgames.aether.common.blocks.natural.plants;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockPlumproot extends Block
{
	public BlockPlumproot()
	{
		super(Material.GOURD, MapColor.ADOBE);

		this.setSoundType(SoundType.WOOD);
		this.setHardness(1.0F);
	}
}
