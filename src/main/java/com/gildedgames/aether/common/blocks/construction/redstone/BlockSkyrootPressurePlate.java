package com.gildedgames.aether.common.blocks.construction.redstone;

import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockSkyrootPressurePlate extends PressurePlateBlock
{
	public BlockSkyrootPressurePlate(Block.Properties properties)
	{
		super(Sensitivity.EVERYTHING, properties.hardnessAndResistance(0.5f).doesNotBlockMovement());
	}
}
