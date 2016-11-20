package com.gildedgames.aether.common.blocks.construction;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockAngelstone extends Block
{

	public BlockAngelstone()
	{
		super(Material.ROCK);

		this.setHardness(2.0F);

		this.setHarvestLevel("pickaxe", 0);
	}

}
