package com.gildedgames.aether.common.blocks.util;

import com.gildedgames.aether.common.blocks.IInternalBlock;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Supplier;

public class BlockCustomDoor extends BlockDoor implements IInternalBlock
{
	private final Supplier<Item> doorItem;

	public BlockCustomDoor(Material material, Supplier<Item> doorItem, SoundType soundType)
	{
		super(material);

		this.doorItem = doorItem;

		this.setSoundType(soundType);

		this.setHardness(3.0f);

		this.disableStats();
	}

	@Override
	public Item getItemDropped(BlockState state, Random rand, int fortune)
	{
		return state.getValue(HALF) == EnumDoorHalf.UPPER ? null : this.doorItem.get();
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, World world, BlockPos pos, PlayerEntity player)
	{
		return new ItemStack(this.doorItem.get());
	}

}
