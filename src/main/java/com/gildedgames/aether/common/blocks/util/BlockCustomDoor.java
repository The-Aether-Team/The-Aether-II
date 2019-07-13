package com.gildedgames.aether.common.blocks.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import java.util.function.Supplier;

public class BlockCustomDoor extends DoorBlock
{
	private final Supplier<Item> doorItem;

	public BlockCustomDoor(Block.Properties properties, Supplier<Item> doorItem)
	{
		super(properties);

		this.doorItem = doorItem;
	}

	@Override
	public ItemStack getItem(IBlockReader world, BlockPos pos, BlockState state)
	{
		return new ItemStack(this.doorItem.get());
	}

}
