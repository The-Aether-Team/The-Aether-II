package com.gildedgames.aether.common.blocks.construction;

import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockSkyrootTrapDoor extends BlockTrapDoor
{
	public BlockSkyrootTrapDoor()
	{
		super(Material.WOOD);

		this.setSoundType(SoundType.WOOD);

		this.setHardness(3.0f);

		this.disableStats();
	}
}
