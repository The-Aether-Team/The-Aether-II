package com.gildedgames.aether.common.blocks.construction;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockRockGlass extends Block
{
	public BlockRockGlass()
	{
		super(Material.GLASS);

		this.setHardness(1f);
		this.setResistance(2000f);

		this.setSoundType(SoundType.GLASS);
	}
}
