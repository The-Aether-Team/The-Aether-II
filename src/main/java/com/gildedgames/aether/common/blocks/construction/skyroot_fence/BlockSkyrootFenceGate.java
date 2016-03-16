package com.gildedgames.aether.common.blocks.construction.skyroot_fence;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;

public class BlockSkyrootFenceGate extends BlockFenceGate
{
	public BlockSkyrootFenceGate()
	{
		super(BlockPlanks.EnumType.OAK);
		this.setHardness(2.0F);
		this.setResistance(5.0F);
		this.setStepSound(soundTypeWood);
	}
}

