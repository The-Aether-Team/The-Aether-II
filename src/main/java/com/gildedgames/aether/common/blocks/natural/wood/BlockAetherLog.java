package com.gildedgames.aether.common.blocks.natural.wood;

import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;

public class BlockAetherLog extends RotatedPillarBlock
{
	private final AetherWoodType type;

	public BlockAetherLog(Block.Properties properties, AetherWoodType type)
	{
		super(properties);

		this.type = type;
	}

	public AetherWoodType getAetherWoodType()
	{
		return this.type;
	}
}
