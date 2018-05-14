package com.gildedgames.aether.common.blocks.util;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
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
	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		switch (rot)
		{
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:
				switch (state.getValue(PROPERTY_AXIS))
				{
					case X:
						return state.withProperty(PROPERTY_AXIS, EnumFacing.Axis.Z);
					case Z:
						return state.withProperty(PROPERTY_AXIS, EnumFacing.Axis.X);
					default:
						return state;
				}
			default:
				return state;
		}
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer,
			EnumHand hand)
	{
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(PROPERTY_AXIS, facing.getAxis());
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
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_AXIS);
	}
}
