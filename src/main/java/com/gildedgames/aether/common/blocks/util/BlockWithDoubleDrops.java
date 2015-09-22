package com.gildedgames.aether.common.blocks.util;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;

/**
 * Skyroot tools will automatically perform double drops on blocks extending this class's
 * functionality.
 */
public class BlockWithDoubleDrops extends Block
{
	public static final PropertyBool PROPERTY_WAS_MINED = PropertyBool.create("was_mined");

	public BlockWithDoubleDrops(Material materialIn)
	{
		super(materialIn);
	}

	public boolean canBeDoubleDropped(IBlockState state)
	{
		return state.getValue(PROPERTY_WAS_MINED) == Boolean.FALSE;
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(PROPERTY_WAS_MINED) == Boolean.TRUE ? 1 : 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(PROPERTY_WAS_MINED, meta == 1);
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return 1;
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, PROPERTY_WAS_MINED);
	}
}
