package com.gildedgames.aether.common.blocks.natural.plants;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockWovenSticks extends Block
{
	
	public BlockWovenSticks()
	{
		super(Material.GROUND);
		
		this.setSoundType(SoundType.GROUND);
		this.setHardness(0.5F);
	}
	
}
