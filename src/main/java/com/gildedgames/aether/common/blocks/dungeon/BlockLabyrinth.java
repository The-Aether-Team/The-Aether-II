package com.gildedgames.aether.common.blocks.dungeon;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLabyrinth extends Block
{
	public BlockLabyrinth()
	{
		super(Material.rock);

		this.setHardness(2.0F);
		this.setHarvestLevel("pickaxe", 2);
		this.setResistance(10.0F);
	}

	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.CUTOUT;
	}

	public BlockLabyrinth setGlows(boolean glows)
	{
		this.setLightLevel(glows ? 0.75f : 0.0f);

		return this;
	}
}
