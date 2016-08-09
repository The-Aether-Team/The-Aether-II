package com.gildedgames.aether.common.blocks.util;

import com.google.common.base.Supplier;
import net.minecraft.block.BlockBed;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCustomBed extends BlockBed
{

	private Supplier<Item> bedItem;

	public BlockCustomBed(Supplier<Item> bedItem, SoundType soundType)
	{
		super();

		this.setSoundType(soundType);

		this.bedItem = bedItem;

		this.setHardness(0.2F);

		this.disableStats();
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return state.getValue(PART) == BlockBed.EnumPartType.HEAD ? null : this.bedItem.get();
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return new ItemStack(this.bedItem.get());
	}

}
