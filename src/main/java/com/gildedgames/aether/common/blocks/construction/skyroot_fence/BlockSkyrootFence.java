package com.gildedgames.aether.common.blocks.construction.skyroot_fence;

import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;

public class BlockSkyrootFence extends BlockFence
{
	public BlockSkyrootFence(Material materialIn)
	{
		super(materialIn);
		this.setHardness(2.0F);
		this.setResistance(5.0F);
		this.setStepSound(soundTypeWood);
	}
}
