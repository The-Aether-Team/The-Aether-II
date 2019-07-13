package com.gildedgames.aether.common.blocks.construction.signs;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.DirectionProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockWallSkyrootSign extends BlockSkyrootSign
{
	public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

	protected static final AxisAlignedBB SIGN_EAST_AABB = new AxisAlignedBB(0.0D, 0.28125D, 0.0D, 0.125D, 0.78125D, 1.0D),
			SIGN_WEST_AABB = new AxisAlignedBB(0.875D, 0.28125D, 0.0D, 1.0D, 0.78125D, 1.0D),
			SIGN_SOUTH_AABB = new AxisAlignedBB(0.0D, 0.28125D, 0.0D, 1.0D, 0.78125D, 0.125D),
			SIGN_NORTH_AABB = new AxisAlignedBB(0.0D, 0.28125D, 0.875D, 1.0D, 0.78125D, 1.0D);

	public BlockWallSkyrootSign(Block.Properties properties)
	{
		super(properties);

		this.setDefaultState(this.blockState.getBaseState().with(BlockWallSkyrootSign.FACING, Direction.NORTH));
	}

	@Override
	public AxisAlignedBB getBoundingBox(BlockState state, IBlockReader source, BlockPos pos)
	{
		switch (state.get(FACING))
		{
			case NORTH:
			default:
				return SIGN_NORTH_AABB;
			case SOUTH:
				return SIGN_SOUTH_AABB;
			case WEST:
				return SIGN_WEST_AABB;
			case EAST:
				return SIGN_EAST_AABB;
		}
	}

	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean p_220069_6_)
	{
		Direction enumfacing = state.get(FACING);

		if (!world.getBlockState(pos.offset(enumfacing.getOpposite())).getMaterial().isSolid())
		{
			this.dropBlockAsItem(world, pos, state, 0);

			world.removeBlock(pos, false);
		}

		super.neighborChanged(state, world, pos, block, fromPos, p_220069_6_);
	}

	@Override
	public BlockState getStateFromMeta(int meta)
	{
		Direction facing = Direction.byIndex(meta);

		if (facing.getAxis() == Direction.Axis.Y)
		{
			facing = Direction.NORTH;
		}

		return this.getDefaultState().with(BlockWallSkyrootSign.FACING, facing);
	}

	@Override
	public int getMetaFromState(BlockState state)
	{
		return state.get(BlockWallSkyrootSign.FACING).getIndex();
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(BlockWallSkyrootSign.FACING);
	}
}
