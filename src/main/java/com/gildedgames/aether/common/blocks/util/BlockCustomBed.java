package com.gildedgames.aether.common.blocks.util;

import com.gildedgames.aether.common.entities.tiles.TileEntitySkyrootBed;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import java.util.function.Supplier;

public class BlockCustomBed extends BedBlock
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
	public ItemStack getItem(IBlockReader world, BlockPos pos, BlockState state)
	{
		return new ItemStack(this.bedItem.get());
	}

	@Override
	public boolean isBed(BlockState state, IBlockReader world, BlockPos pos, Entity player)
	{
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader reader)
	{
		return new TileEntitySkyrootBed();
	}

}
