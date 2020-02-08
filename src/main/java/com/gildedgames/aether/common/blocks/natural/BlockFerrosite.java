package com.gildedgames.aether.common.blocks.natural;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockFerrosite extends Block
{
	public BlockFerrosite()
	{
		super(Material.ROCK);

		this.setHardness(1.5f);
		this.setResistance(10.0F);

		this.setSoundType(SoundType.STONE);
	}
}
