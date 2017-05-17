package com.gildedgames.aether.common.blocks.construction;

import com.gildedgames.aether.common.blocks.util.BlockCustom;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockSkyrootPlanks extends BlockCustom
{
	public BlockSkyrootPlanks()
	{
		super(Material.WOOD);

		this.setSoundType(SoundType.WOOD);
		this.setHardness(2f);
	}
}
