package com.gildedgames.aether.common.blocks.natural.ores;

import com.gildedgames.aether.common.AetherCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockGravititeOre extends Block
{

	public BlockGravititeOre()
	{
		super(Material.rock);

		this.setHardness(3.0f);
		this.setResistance(5.0f);
		this.setHarvestLevel("pickaxe", 2);

		this.setStepSound(soundTypeStone);

		this.setCreativeTab(AetherCreativeTabs.tabBlocks);
	}

}
