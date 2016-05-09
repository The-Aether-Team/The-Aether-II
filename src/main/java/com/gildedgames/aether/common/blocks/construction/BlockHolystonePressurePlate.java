package com.gildedgames.aether.common.blocks.construction;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;

public class BlockHolystonePressurePlate extends BlockPressurePlate
{
	public BlockHolystonePressurePlate()
	{
		super(Material.rock, Sensitivity.EVERYTHING);

		this.setStepSound(Block.soundTypeStone);

		this.setHardness(0.5f);

		this.disableStats();
	}
}
