package com.gildedgames.aether.common.blocks.util;

import com.google.common.base.Supplier;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCustomDoor extends BlockDoor
{
	private Supplier<Item> doorItem;

	public BlockCustomDoor(Material material, Supplier<Item> doorItem, SoundType soundType)
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
		return state.getValue(HALF) == EnumDoorHalf.UPPER ? null : this.doorItem.get();
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(this.doorItem.get());
	}

}
