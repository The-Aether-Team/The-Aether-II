package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockCrudeScatterglass extends BlockBreakable
{

	public BlockCrudeScatterglass()
	{
		super(Material.ROCK, false);

		this.setHardness(1f);
		this.setResistance(2000f);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(BlocksAether.scatterglass);
	}

}
