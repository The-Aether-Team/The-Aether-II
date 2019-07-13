package com.gildedgames.aether.common.blocks.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRotatable extends Block
{
	public static final EnumProperty<Direction.Axis> PROPERTY_AXIS = EnumProperty.create("axis", Direction.Axis.class);

	public BlockRotatable(Block.Properties properties)
	{
		super(properties);

		this.setDefaultState(this.getStateContainer().getBaseState().with(PROPERTY_AXIS, Direction.Axis.Y));
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot)
	{
		switch (rot)
		{
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:
				switch (state.get(PROPERTY_AXIS))
				{
					case X:
						return state.with(PROPERTY_AXIS, Direction.Axis.Z);
					case Z:
						return state.with(PROPERTY_AXIS, Direction.Axis.X);
					default:
						return state;
				}
			default:
				return state;
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return this.getDefaultState().with(PROPERTY_AXIS, context.getFace().getAxis());
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(PROPERTY_AXIS);
	}
}
