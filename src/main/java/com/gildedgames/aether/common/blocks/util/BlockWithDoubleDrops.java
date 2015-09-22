package com.gildedgames.aether.common.blocks.util;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * Skyroot tools will automatically perform double drops on blocks extending this class's
 * functionality.
 */
public class BlockWithDoubleDrops extends Block
{
	public static final PropertyBool PROPERTY_WAS_PLACED = PropertyBool.create("was_placed");

	public BlockWithDoubleDrops(Material material)
	{
		super(material);
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getStateFromMeta(meta).withProperty(PROPERTY_WAS_PLACED, Boolean.TRUE);
	}

	public boolean canBeDoubleDropped(IBlockState state)
	{
		return state.getValue(PROPERTY_WAS_PLACED) == Boolean.FALSE;
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
