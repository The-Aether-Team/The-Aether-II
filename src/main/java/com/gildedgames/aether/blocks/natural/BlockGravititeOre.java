package com.gildedgames.aether.blocks.natural;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import com.gildedgames.aether.Aether;

public class BlockGravititeOre extends Block
{

	public BlockGravititeOre()
	{
		super(Material.rock);

		this.setHardness(3.0F);
		this.setStepSound(soundTypeStone);
		this.setHarvestLevel("pickaxe", 2);

		this.setCreativeTab(Aether.getCreativeTabs().tabBlocks);
	}

}
