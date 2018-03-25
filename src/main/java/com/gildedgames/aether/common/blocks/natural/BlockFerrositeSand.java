package com.gildedgames.aether.common.blocks.natural;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockFerrositeSand extends Block
{
	public BlockFerrositeSand()
	{
		super(Material.SAND);

		this.setHardness(1.5f);

		this.setSoundType(SoundType.SAND);
	}
}
