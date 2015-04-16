package com.gildedgames.aether.blocks.natural;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import com.gildedgames.aether.Aether;

public class BlockContinuumOre extends Block
{

	public BlockContinuumOre()
	{
		super(Material.rock);

		this.setHardness(3.0F);
		this.setStepSound(soundTypeStone);
		this.setHarvestLevel("pickaxe", 3);

		this.setCreativeTab(Aether.getCreativeTabs().tabBlocks);

	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Aether.getItems().continuum_orb;
	}

}
