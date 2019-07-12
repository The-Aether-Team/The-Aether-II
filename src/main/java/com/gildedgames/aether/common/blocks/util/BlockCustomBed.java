package com.gildedgames.aether.common.blocks.util;

import com.gildedgames.aether.common.blocks.IInternalBlock;
import com.gildedgames.aether.common.entities.tiles.TileEntitySkyrootBed;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Supplier;

public class BlockCustomBed extends BedBlock implements IInternalBlock
{

	private final Supplier<Item> bedItem;

	public BlockCustomBed(Supplier<Item> bedItem, SoundType soundType)
	{
		super();

		this.setSoundType(soundType);

		this.bedItem = bedItem;

		this.setHardness(0.2F);

		this.disableStats();
	}

	@Override
	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.MODEL;
	}

	@Override
	public Item getItemDropped(BlockState state, Random rand, int fortune)
	{
		return state.getValue(PART) == EnumPartType.HEAD ? null : this.bedItem.get();
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, World world, BlockPos pos, PlayerEntity player)
	{
		return new ItemStack(this.bedItem.get());
	}

	@Override
	public boolean isBed(BlockState state, IBlockReader world, BlockPos pos, Entity player)
	{
		return true;
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, BlockState state)
	{
		return new ItemStack(this.bedItem.get());
	}

	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, BlockState state, float chance, int fortune)
	{
		if (state.getValue(PART) == BedBlock.EnumPartType.HEAD)
		{
			spawnAsEntity(worldIn, pos, new ItemStack(this.bedItem.get()));
		}
	}

	@Override
	public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, TileEntity te, ItemStack stack)
	{
		if (state.getValue(PART) == BedBlock.EnumPartType.HEAD && te instanceof TileEntitySkyrootBed)
		{
			spawnAsEntity(worldIn, pos, new ItemStack(this.bedItem.get()));
		}
		else
		{
			super.harvestBlock(worldIn, player, pos, state, null, stack);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntitySkyrootBed();
	}

}
