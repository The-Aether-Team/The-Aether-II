package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.common.AetherCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAerogel extends Block
{
	public BlockAerogel()
	{
		super(Material.rock);

		this.setHardness(1f);
		this.setResistance(2000f);

		this.setLightOpacity(3);

		this.setCreativeTab(AetherCreativeTabs.tabBlocks);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.TRANSLUCENT;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
}
