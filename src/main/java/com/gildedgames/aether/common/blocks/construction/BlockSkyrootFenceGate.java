package com.gildedgames.aether.common.blocks.construction;

import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;

public class BlockSkyrootFenceGate extends FenceGateBlock
{
	public BlockSkyrootFenceGate()
	{
		super(BlockPlanks.EnumType.OAK);

		this.setHardness(2.0F);
		this.setResistance(5.0F);
		this.setSoundType(SoundType.WOOD);
	}
}

