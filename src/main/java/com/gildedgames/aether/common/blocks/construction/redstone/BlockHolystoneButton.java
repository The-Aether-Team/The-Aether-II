package com.gildedgames.aether.common.blocks.construction.redstone;

import net.minecraft.block.Block;
import net.minecraft.block.StoneButtonBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class BlockHolystoneButton extends StoneButtonBlock
{
	public BlockHolystoneButton(Block.Properties properties)
	{
		super(properties.hardnessAndResistance(0.5f).doesNotBlockMovement());
	}
}
