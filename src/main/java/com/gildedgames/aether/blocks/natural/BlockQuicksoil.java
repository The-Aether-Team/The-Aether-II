package com.gildedgames.aether.blocks.natural;

import com.gildedgames.aether.Aether;
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
		this.setCreativeTab(Aether.getCreativeTabs().tabBlocks);
	}
}
