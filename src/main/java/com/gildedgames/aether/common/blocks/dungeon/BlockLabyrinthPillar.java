package com.gildedgames.aether.common.blocks.dungeon;

import com.gildedgames.aether.common.blocks.util.BlockRotatable;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLabyrinthPillar extends BlockRotatable
{
	public BlockLabyrinthPillar()
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

	public BlockLabyrinthPillar setGlows(boolean glows)
	{
		this.setLightLevel(glows ? 0.75f : 0.0f);

		return this;
	}
}
