package com.gildedgames.aether.common.blocks.natural.leaves;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.plants.saplings.BlockAetherSkyrootSapling;
import com.gildedgames.aether.common.blocks.natural.wood.BlockAetherLog;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockSkyrootLeaves extends BlockColoredLeaves
{
	public BlockSkyrootLeaves(Block.Properties properties, Color color)
	{
		super(properties, color);
	}

	@Override
	protected ItemStack getSaplingItem()
	{
		switch (this.getLeafColor())
		{
			case GREEN:
				return new ItemStack(BlocksAether.skyroot_sapling, 1, BlockAetherSkyrootSapling.GREEN.getMeta());
			case BLUE:
				return new ItemStack(BlocksAether.skyroot_sapling, 1, BlockAetherSkyrootSapling.BLUE.getMeta());
			case DARK_BLUE:
				return new ItemStack(BlocksAether.skyroot_sapling, 1, BlockAetherSkyrootSapling.DARK_BLUE.getMeta());
		}

		return null;
	}

	@Override
	public BlockAetherLog getWoodBlock()
	{
		return (BlockAetherLog) BlocksAether.skyroot_log;
	}
}
