package com.gildedgames.aether.common.blocks.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.state.EnumProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRotatable extends Block
{
	public static final EnumProperty<Direction.Axis> PROPERTY_AXIS = EnumProperty.create("axis", Direction.Axis.class);

	public BlockRotatable(Material material)
	{
		super(material);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_AXIS, Direction.Axis.Y));
	}

	@Override
	public BlockState withRotation(BlockState state, Rotation rot)
	{
		switch (rot)
		{
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:
				switch (state.getValue(PROPERTY_AXIS))
				{
					case X:
						return state.withProperty(PROPERTY_AXIS, Direction.Axis.Z);
					case Z:
						return state.withProperty(PROPERTY_AXIS, Direction.Axis.X);
					default:
						return state;
				}
			default:
				return state;
		}
	}

	@Override
	public BlockState getStateForPlacement(World world, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, LivingEntity placer,
			Hand hand)
	{
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(PROPERTY_AXIS, facing.getAxis());
	}

	@Override
	public BlockState getStateFromMeta(int meta)
	{
		Direction.Axis axis;

		switch (meta & 7)
		{
			case 1:
				axis = Direction.Axis.X;
				break;
			case 2:
				axis = Direction.Axis.Z;
				break;
			default:
				axis = Direction.Axis.Y;
				break;
		}

		return this.getDefaultState().withProperty(PROPERTY_AXIS, axis);
	}

	@Override
	public int getMetaFromState(BlockState state)
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
	public int damageDropped(BlockState state)
	{
		return 0;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_AXIS);
	}
}
