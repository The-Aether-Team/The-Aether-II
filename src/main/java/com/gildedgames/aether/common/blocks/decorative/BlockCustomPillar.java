package com.gildedgames.aether.common.blocks.decorative;

import com.gildedgames.aether.common.blocks.util.BlockRotatable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockCustomPillar extends BlockRotatable
{
	public BlockCustomPillar(final Material material)
	{
		super(material);
	}

	@Override
	public BlockCustomPillar setSoundType(final SoundType type)
	{
		super.setSoundType(type);

		return this;
	}
}
