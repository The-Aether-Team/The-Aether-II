package com.gildedgames.aether.common.blocks.construction;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockHolystoneBookshelf extends Block
{
	public BlockHolystoneBookshelf()
	{
		super(Material.ROCK);

		this.setHardness(2.0F);

		this.setHarvestLevel("pickaxe", 0);
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 3;
	}

	@Override
	@Nullable
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Items.BOOK;
	}
}
