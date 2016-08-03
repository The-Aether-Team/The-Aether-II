package com.gildedgames.aether.common.blocks.construction;

import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;
import java.util.concurrent.Callable;

public class BlockCustomDoor extends BlockDoor
{

	private Callable<Item> doorItem;

	public BlockCustomDoor(Material material, Callable<Item> doorItem, SoundType soundType)
	{
		super(material);

		this.doorItem = doorItem;

		this.setSoundType(soundType);

		this.setHardness(3.0f);

		this.disableStats();
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		try
		{
			return state.getValue(HALF) == EnumDoorHalf.UPPER ? null : this.doorItem.call();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		try
		{
			return new ItemStack(this.doorItem.call());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

}
