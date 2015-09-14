package com.gildedgames.aether.common.blocks.construction;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockQuicksoilGlass extends BlockBreakable
{

	public BlockQuicksoilGlass() 
	{
		super(Material.glass, false);

		this.slipperiness = 1.0F;
		this.setLightLevel(1.0F);
		this.setHardness(0.3F);
		this.setStepSound(Block.soundTypeGlass);
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

	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}

	@Override
    protected boolean canSilkHarvest()
    {
        return true;
    }

}
