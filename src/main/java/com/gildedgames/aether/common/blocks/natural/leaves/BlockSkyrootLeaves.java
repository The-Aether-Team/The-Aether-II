package com.gildedgames.aether.common.blocks.natural.leaves;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.plants.saplings.BlockAetherSkyrootSapling;
import com.gildedgames.aether.common.blocks.natural.wood.BlockAetherLog;
import net.minecraft.item.ItemStack;

public class BlockSkyrootLeaves extends BlockColoredLeaves
{
	public BlockSkyrootLeaves(Color color)
	{
		super(color);
	}

	@Override
	protected ItemStack getSaplingItem()
	{
		switch (this.getLeafColor())
		{
			case GREEN:
				return new ItemStack(BlocksAether.aether_skyroot_sapling, 1, BlockAetherSkyrootSapling.GREEN.getMeta());
			case BLUE:
				return new ItemStack(BlocksAether.aether_skyroot_sapling, 1, BlockAetherSkyrootSapling.BLUE.getMeta());
			case DARK_BLUE:
				return new ItemStack(BlocksAether.aether_skyroot_sapling, 1, BlockAetherSkyrootSapling.DARK_BLUE.getMeta());
		}

		return null;
	}

	@Override
	public BlockAetherLog getWoodBlock()
	{
		return BlocksAether.skyroot_log;
	}
}
