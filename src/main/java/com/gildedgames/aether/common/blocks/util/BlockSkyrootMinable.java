package com.gildedgames.aether.common.blocks.util;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BlockSkyrootMinable extends Block implements ISkyrootMinable
{
	public static final PropertyBool PROPERTY_WAS_PLACED = PropertyBool.create("was_placed");

	public BlockSkyrootMinable(Material material)
	{
		super(material);
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getStateFromMeta(meta).withProperty(PROPERTY_WAS_PLACED, Boolean.TRUE);
	}

	@Override
	public boolean canBlockDropDoubles(EntityLivingBase player, ItemStack stack, IBlockState state)
	{
		return state.getValue(PROPERTY_WAS_PLACED) == Boolean.FALSE;
	}

	@Override
	public Collection<ItemStack> getAdditionalDrops(World world, BlockPos pos, IBlockState state, EntityLivingBase living)
	{
		return this.getDrops(world, pos, state, EnchantmentHelper.getFortuneModifier(living));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(PROPERTY_WAS_PLACED) == Boolean.TRUE ? 1 : 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(PROPERTY_WAS_PLACED, meta == 1);
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, PROPERTY_WAS_PLACED);
	}
}
