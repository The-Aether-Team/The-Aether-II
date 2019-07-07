package com.gildedgames.aether.common.blocks.natural.leaves;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.plants.saplings.BlockAetherWisprootSapling;
import com.gildedgames.aether.common.blocks.natural.wood.BlockAetherLog;
import net.minecraft.item.ItemStack;

public class BlockWisprootLeaves extends BlockColoredLeaves
{
	public BlockWisprootLeaves(Color color)
	{
		super(color);
	}

	@Override
	protected ItemStack getSaplingItem()
	{
		switch (this.getLeafColor())
		{
			case GREEN:
				return new ItemStack(BlocksAether.wisproot_sapling, 1, BlockAetherWisprootSapling.GREEN.getMeta());
			case BLUE:
				return new ItemStack(BlocksAether.wisproot_sapling, 1, BlockAetherWisprootSapling.BLUE.getMeta());
			case DARK_BLUE:
				return new ItemStack(BlocksAether.wisproot_sapling, 1, BlockAetherWisprootSapling.DARK_BLUE.getMeta());
		}

		return null;
	}


	@Override
	public BlockAetherLog getWoodBlock()
	{
		return (BlockAetherLog) BlocksAether.light_skyroot_log;
	}
}
