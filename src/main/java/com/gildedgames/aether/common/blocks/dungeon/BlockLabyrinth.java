package com.gildedgames.aether.common.blocks.dungeon;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLabyrinth extends Block
{
	public BlockLabyrinth()
	{
		super(Material.ROCK);

		this.setHardness(2.0F);
		this.setHarvestLevel("pickaxe", 2);
		this.setResistance(10.0F);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public BlockLabyrinth setLightLevel(float lightLevel)
	{
		super.setLightLevel(lightLevel);

		return this;
	}

}
