package com.gildedgames.aether.common.blocks.natural.ores;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockArkeniumOre extends BlockAetherOre
{

	public BlockArkeniumOre()
	{
		super(Material.ROCK);

		this.setHardness(3.0f);
		this.setResistance(5.0f);
		this.setHarvestLevel("pickaxe", 2);

		this.setSoundType(SoundType.STONE);
	}

}
