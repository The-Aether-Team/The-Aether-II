package com.gildedgames.aether.common.blocks.decorative;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BlockCandyCane extends RotatedPillarBlock
{
	public static final EnumProperty<EnumAxis> BLOCK_AXIS = EnumProperty.create("axis", BlockCandyCane.EnumAxis.class);

	public BlockCandyCane(Block.Properties properties)
	{
		super(properties);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return this.getDefaultState().with(BLOCK_AXIS, BlockCandyCane.EnumAxis.fromFacingAxis(context.getFace().getAxis()));
	}

	@Override
	public void onEntityWalk(World world, BlockPos pos, Entity entity)
	{
		super.onEntityWalk(world, pos, entity);

		if (world.isRaining())
		{
			Vec3d motion = entity.getMotion();
			entity.setMotion(motion.mul(0.1D, 1.0D, 0.1D));
		}
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot)
	{
		switch (rot)
		{
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:

				switch (state.get(BLOCK_AXIS))
				{
					case X:
						return state.with(BLOCK_AXIS, BlockCandyCane.EnumAxis.Z);
					case Z:
						return state.with(BLOCK_AXIS, BlockCandyCane.EnumAxis.X);
					default:
						return state;
				}

			default:
				return state;
		}
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(BLOCK_AXIS);
	}

	public enum EnumAxis implements IStringSerializable
	{
		X("x"),
		Y("y"),
		Z("z"),
		NONE("none");

		private final String name;

		EnumAxis(String name)
		{
			this.name = name;
		}

		public String toString()
		{
			return this.name;
		}

		public static BlockCandyCane.EnumAxis fromFacingAxis(Direction.Axis axis)
		{
			switch (axis)
			{
				case X:
					return X;
				case Y:
					return Y;
				case Z:
					return Z;
				default:
					return NONE;
			}
		}

		@Override
		public String getName()
		{
			return this.name;
		}
	}
}
