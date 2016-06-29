package com.gildedgames.aether.common.blocks.construction;

import net.minecraft.block.BlockFence;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockSkyrootFence extends BlockFence
{
	public BlockSkyrootFence()
	{
		super(Material.WOOD, MapColor.WOOD);

		this.setHardness(2.0F);
		this.setResistance(5.0F);
		this.setSoundType(SoundType.WOOD);
	}
}
