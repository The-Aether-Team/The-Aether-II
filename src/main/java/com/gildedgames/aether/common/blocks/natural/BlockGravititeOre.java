package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.common.AetherCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockGravititeOre extends Block
{

	public BlockGravititeOre()
	{
		super(Material.rock);

		this.setHardness(3.0F);
		this.setStepSound(soundTypeStone);
		this.setHarvestLevel("pickaxe", 2);

		this.setCreativeTab(AetherCreativeTabs.tabBlocks);
	}

}
