package com.gildedgames.aether.common.blocks.util;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockRotatable extends Block
{
	public static final PropertyEnum<EnumFacing.Axis> PROPERTY_AXIS = PropertyEnum.create("axis", EnumFacing.Axis.class);

	public BlockRotatable(Material material)
	{
		super(material);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_AXIS, EnumFacing.Axis.Y));
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(PROPERTY_AXIS, facing.getAxis());
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing.Axis axis;

		switch (meta & 7)
		{
		case 1:
			axis = EnumFacing.Axis.X;
			break;
		case 2:
			axis = EnumFacing.Axis.Z;
			break;
		default:
			axis = EnumFacing.Axis.Y;
			break;
		}

		return this.getDefaultState().withProperty(PROPERTY_AXIS, axis);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int meta = 0;

		switch (state.getValue(PROPERTY_AXIS))
		{
		case X:
			meta |= 1;
			break;
		case Z:
			meta |= 2;
			break;
		default:
			meta |= 0;
			break;
		}

		return meta;
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return 0;
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, PROPERTY_AXIS);
	}
}
