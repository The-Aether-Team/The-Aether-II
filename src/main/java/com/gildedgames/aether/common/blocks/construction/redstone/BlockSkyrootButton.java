package com.gildedgames.aether.common.blocks.construction.redstone;

import net.minecraft.block.Block;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockSkyrootButton extends WoodButtonBlock
{
	public BlockSkyrootButton(Block.Properties properties)
	{
		super(properties.hardnessAndResistance(0.5f).doesNotBlockMovement());
	}
}
