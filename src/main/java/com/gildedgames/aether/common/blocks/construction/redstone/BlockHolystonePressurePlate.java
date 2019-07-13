package com.gildedgames.aether.common.blocks.construction.redstone;

import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockHolystonePressurePlate extends PressurePlateBlock
{
	public BlockHolystonePressurePlate(Block.Properties properties)
	{
		super(Sensitivity.EVERYTHING, properties.hardnessAndResistance(0.5f).doesNotBlockMovement());
	}
}
