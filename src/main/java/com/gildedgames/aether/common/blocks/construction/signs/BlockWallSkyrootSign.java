package com.gildedgames.aether.common.blocks.construction.signs;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockWallSkyrootSign extends BlockSkyrootSign
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing", Direction.Plane.HORIZONTAL);

	protected static final AxisAlignedBB SIGN_EAST_AABB = new AxisAlignedBB(0.0D, 0.28125D, 0.0D, 0.125D, 0.78125D, 1.0D),
			SIGN_WEST_AABB = new AxisAlignedBB(0.875D, 0.28125D, 0.0D, 1.0D, 0.78125D, 1.0D),
			SIGN_SOUTH_AABB = new AxisAlignedBB(0.0D, 0.28125D, 0.0D, 1.0D, 0.78125D, 0.125D),
			SIGN_NORTH_AABB = new AxisAlignedBB(0.0D, 0.28125D, 0.875D, 1.0D, 0.78125D, 1.0D);

	public BlockWallSkyrootSign()
	{
		this.setDefaultState(this.blockState.getBaseState().withProperty(BlockWallSkyrootSign.FACING, Direction.NORTH));
	}

	@Override
	public AxisAlignedBB getBoundingBox(BlockState state, IBlockReader source, BlockPos pos)
	{
		switch (state.getValue(FACING))
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
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		Direction enumfacing = state.getValue(FACING);

		if (!worldIn.getBlockState(pos.offset(enumfacing.getOpposite())).getMaterial().isSolid())
		{
			this.dropBlockAsItem(worldIn, pos, state, 0);

			worldIn.setBlockToAir(pos);
		}

		super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
	}

	@Override
	public BlockState getStateFromMeta(int meta)
	{
		Direction facing = Direction.byIndex(meta);

		if (facing.getAxis() == Direction.Axis.Y)
		{
			facing = Direction.NORTH;
		}

		return this.getDefaultState().withProperty(BlockWallSkyrootSign.FACING, facing);
	}

	@Override
	public int getMetaFromState(BlockState state)
	{
		return state.getValue(BlockWallSkyrootSign.FACING).getIndex();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BlockWallSkyrootSign.FACING);
	}
}
