package com.gildedgames.aether.common.blocks.natural;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockCloudwoolBlock extends Block
{
	public BlockCloudwoolBlock()
	{
		super(Material.CLOTH);

		this.setHardness(0.8f);

		this.setSoundType(SoundType.CLOTH);
	}
}
