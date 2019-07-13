package com.gildedgames.aether.common.blocks.util;

import com.gildedgames.aether.common.blocks.IInternalBlock;
import com.gildedgames.aether.common.entities.tiles.TileEntitySkyrootBed;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Supplier;

public class BlockCustomBed extends BedBlock implements IInternalBlock
{

	private final Supplier<Item> bedItem;

	public BlockCustomBed(Block.Properties properties, Supplier<Item> bedItem)
	{
		// TODO: dyecolor
		super(DyeColor.BLUE, properties.hardnessAndResistance(0.2f).sound(SoundType.CLOTH));

		this.bedItem = bedItem;
	}

	@Override
	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.MODEL;
	}

	@Override
	public Item getItemDropped(BlockState state, Random rand, int fortune)
	{
		return state.get(PART) == EnumPartType.HEAD ? null : this.bedItem.get();
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
		if (state.get(PART) == BedBlock.EnumPartType.HEAD)
		{
			spawnAsEntity(worldIn, pos, new ItemStack(this.bedItem.get()));
		}
	}

	@Override
	public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, TileEntity te, ItemStack stack)
	{
		if (state.get(PART) == BedBlock.EnumPartType.HEAD && te instanceof TileEntitySkyrootBed)
		{
			spawnAsEntity(worldIn, pos, new ItemStack(this.bedItem.get()));
		}
		else
		{
			super.harvestBlock(worldIn, player, pos, state, null, stack);
		}
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader reader)
	{
		return new TileEntitySkyrootBed();
	}

}
