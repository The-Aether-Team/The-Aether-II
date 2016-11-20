package com.gildedgames.aether.common.blocks.construction;

import com.gildedgames.aether.common.blocks.util.BlockRotatable;
import net.minecraft.block.material.Material;

public class BlockAngelstonePillar extends BlockRotatable
{

	public BlockAngelstonePillar()
	{
		super(Material.ROCK);

		this.setHardness(2.0F);

		this.setHarvestLevel("pickaxe", 0);
	}

}
