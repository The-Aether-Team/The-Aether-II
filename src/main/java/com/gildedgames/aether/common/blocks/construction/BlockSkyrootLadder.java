package com.gildedgames.aether.common.blocks.construction;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.material.Material;

public class BlockSkyrootLadder extends BlockLadder
{
	public BlockSkyrootLadder(Material material)
	{
		super();

		this.setStepSound(Block.soundTypeWood);

		this.setHardness(0.4f);

		this.disableStats();
	}
}
