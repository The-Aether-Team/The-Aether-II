package com.gildedgames.aether.common.blocks.natural.leaves;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.plants.saplings.BlockAetherGreatrootSapling;
import com.gildedgames.aether.common.blocks.natural.wood.BlockAetherLog;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockGreatrootLeaves extends BlockColoredLeaves
{
	public BlockGreatrootLeaves(Block.Properties properties, Color color)
	{
		super(properties, color);
	}

	@Override
	protected ItemStack getSaplingItem()
	{
		switch (this.getLeafColor())
		{
			case GREEN:
				return new ItemStack(BlocksAether.greatroot_sapling, 1, BlockAetherGreatrootSapling.GREEN.getMeta());
			case BLUE:
				return new ItemStack(BlocksAether.greatroot_sapling, 1, BlockAetherGreatrootSapling.BLUE.getMeta());
			case DARK_BLUE:
				return new ItemStack(BlocksAether.greatroot_sapling, 1, BlockAetherGreatrootSapling.DARK_BLUE.getMeta());
		}

		return null;
	}

	@Override
	public BlockAetherLog getWoodBlock()
	{
		return (BlockAetherLog) BlocksAether.dark_skyroot_log;
	}
}
