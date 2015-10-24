package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.common.blocks.util.BlockSkyrootMinable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockQuicksoil extends BlockSkyrootMinable
{
	public BlockQuicksoil()
	{
		super(Material.sand);

		this.slipperiness = 1.23f;

		this.setHardness(1.5f);

		this.setStepSound(Block.soundTypeSand);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_WAS_PLACED, Boolean.FALSE));
	}
}
