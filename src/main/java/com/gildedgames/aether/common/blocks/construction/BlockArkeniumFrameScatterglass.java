package com.gildedgames.aether.common.blocks.construction;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockArkeniumFrameScatterglass extends BlockGlass
{
	public BlockArkeniumFrameScatterglass()
	{
		super(Material.GLASS, false);

		this.setHardness(0.3F);
		this.setSoundType(SoundType.GLASS);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}
}
