package com.gildedgames.aether.common.blocks.construction.signs;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class BlockWallSkyrootSign extends BlockSkyrootSign
{
	public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

	protected static final VoxelShape SIGN_EAST_AABB = Block.makeCuboidShape(0.0D, 0.28125D, 0.0D, 0.125D, 0.78125D, 1.0D),
			SIGN_WEST_AABB = Block.makeCuboidShape(0.875D, 0.28125D, 0.0D, 1.0D, 0.78125D, 1.0D),
			SIGN_SOUTH_AABB = Block.makeCuboidShape(0.0D, 0.28125D, 0.0D, 1.0D, 0.78125D, 0.125D),
			SIGN_NORTH_AABB = Block.makeCuboidShape(0.0D, 0.28125D, 0.875D, 1.0D, 0.78125D, 1.0D);

	public BlockWallSkyrootSign(Block.Properties properties)
	{
		super(properties);

		this.setDefaultState(this.getStateContainer().getBaseState().with(BlockWallSkyrootSign.FACING, Direction.NORTH));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
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
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.offset(state.get(FACING).getOpposite())).getMaterial().isSolid();
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(BlockWallSkyrootSign.FACING);
	}
}
