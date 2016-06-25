package com.gildedgames.aether.common.blocks.construction;

import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockHolystonePressurePlate extends BlockPressurePlate
{
	public BlockHolystonePressurePlate()
	{
		super(Material.ROCK, Sensitivity.EVERYTHING);

		this.setSoundType(SoundType.STONE);

		this.setHardness(0.5f);

		this.disableStats();
	}
}
