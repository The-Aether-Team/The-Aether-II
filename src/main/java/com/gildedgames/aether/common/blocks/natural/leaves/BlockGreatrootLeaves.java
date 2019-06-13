package com.gildedgames.aether.common.blocks.natural.leaves;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.plants.saplings.BlockAetherGreatrootSapling;
import com.gildedgames.aether.common.blocks.natural.wood.BlockAetherLog;
import net.minecraft.item.ItemStack;

public class BlockGreatrootLeaves extends BlockColoredLeaves
{
	public BlockGreatrootLeaves(Color color)
	{
		super(color);
	}

	@Override
	protected ItemStack getSaplingItem()
	{
		switch (this.getLeafColor())
		{
			case GREEN:
				return new ItemStack(BlocksAether.aether_greatroot_sapling, 1, BlockAetherGreatrootSapling.GREEN.getMeta());
			case BLUE:
				return new ItemStack(BlocksAether.aether_greatroot_sapling, 1, BlockAetherGreatrootSapling.BLUE.getMeta());
			case DARK_BLUE:
				return new ItemStack(BlocksAether.aether_greatroot_sapling, 1, BlockAetherGreatrootSapling.DARK_BLUE.getMeta());
		}

		return null;
	}

	@Override
	public BlockAetherLog getWoodBlock()
	{
		return BlocksAether.dark_skyroot_log;
	}
}
