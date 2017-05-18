package com.gildedgames.aether.common.blocks.util;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockBuilder extends Block
{
	public BlockBuilder(Material material)
	{
		super(material);
	}

	@Override
	public BlockBuilder setSoundType(SoundType type)
	{
		super.setSoundType(type);

		return this;
	}
}
