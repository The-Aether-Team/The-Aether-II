package com.gildedgames.aether.common.blocks.construction;

import com.gildedgames.aether.common.blocks.util.BlockBuilder;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockTherawoodPlanks extends BlockBuilder
{
	public BlockTherawoodPlanks()
	{
		super(Material.WOOD);

		this.setSoundType(SoundType.WOOD);
		this.setHardness(2f);
		this.setResistance(5.0f);
	}
}
