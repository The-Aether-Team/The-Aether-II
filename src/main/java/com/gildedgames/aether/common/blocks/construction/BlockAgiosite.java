package com.gildedgames.aether.common.blocks.construction;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockAgiosite extends Block
{

	public BlockAgiosite()
	{
		super(Material.ROCK);

		this.setHardness(2.0F);

		this.setHarvestLevel("pickaxe", 0);
	}

}
