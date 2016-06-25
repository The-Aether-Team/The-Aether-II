package com.gildedgames.aether.common.blocks.construction;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockSkyrootPressurePlate extends BlockPressurePlate
{
	public BlockSkyrootPressurePlate()
	{
		super(Material.WOOD, Sensitivity.EVERYTHING);

		this.setSoundType(SoundType.WOOD);

		this.setHardness(0.5f);

		this.disableStats();
	}
}
