package com.gildedgames.aether.common.blocks.natural;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockQuicksoil extends Block
{
	public BlockQuicksoil()
	{
		super(Material.sand);

		this.slipperiness = 1.23f;

		this.setHardness(1.5f);

		this.setStepSound(Block.soundTypeSand);
	}
}
