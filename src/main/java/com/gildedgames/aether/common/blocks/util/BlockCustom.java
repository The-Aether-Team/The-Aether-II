package com.gildedgames.aether.common.blocks.util;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockCustom extends Block
{
	public BlockCustom(Material blockMaterialIn)
	{
		super(blockMaterialIn);
	}

	public BlockCustom setSoundType(SoundType type)
	{
		super.setSoundType(type);

		return this;
	}
}
