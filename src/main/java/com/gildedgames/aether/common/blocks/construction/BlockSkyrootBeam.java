package com.gildedgames.aether.common.blocks.construction;

import com.gildedgames.aether.common.blocks.util.BlockRotatable;
import net.minecraft.block.material.Material;

public class BlockSkyrootBeam extends BlockRotatable
{

	public BlockSkyrootBeam()
	{
		super(Material.WOOD);

		this.setHardness(2.0F);

		this.setHarvestLevel("axe", 0);
	}

}
