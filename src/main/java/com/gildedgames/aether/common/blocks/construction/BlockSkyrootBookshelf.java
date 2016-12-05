package com.gildedgames.aether.common.blocks.construction;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import javax.annotation.Nullable;
import java.util.Random;

public class BlockSkyrootBookshelf extends Block
{
	public BlockSkyrootBookshelf()
	{
		super(Material.WOOD);

		this.setHardness(2.0F);

		this.setHarvestLevel("axe", 0);
	}

	public int quantityDropped(Random random)
	{
		return 3;
	}

	@Nullable
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Items.BOOK;
	}
}
