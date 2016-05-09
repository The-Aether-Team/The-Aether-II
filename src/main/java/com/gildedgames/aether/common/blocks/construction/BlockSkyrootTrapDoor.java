package com.gildedgames.aether.common.blocks.construction;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;

public class BlockSkyrootTrapDoor extends BlockTrapDoor
{
	public BlockSkyrootTrapDoor()
	{
		super(Material.wood);

		this.setStepSound(Block.soundTypeWood);

		this.setHardness(3.0f);

		this.disableStats();
	}
}
