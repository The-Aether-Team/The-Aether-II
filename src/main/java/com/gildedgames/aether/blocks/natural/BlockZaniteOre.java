package com.gildedgames.aether.blocks.natural;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.AetherCreativeTabs;

public class BlockZaniteOre extends Block
{

	public BlockZaniteOre()
	{
		super(Material.rock);

		this.setHardness(3.0F);
		this.setStepSound(soundTypeStone);
		this.setHarvestLevel("pickaxe", 1);

		this.setCreativeTab(AetherCreativeTabs.tabBlocks);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Aether.getItems().zanite_gemstone;
	}

}
