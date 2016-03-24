package com.gildedgames.aether.common.blocks.construction.skyroot_sign;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWallSkyrootSign extends BlockSkyrootSign
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public BlockWallSkyrootSign()
	{
		this.setDefaultState(this.blockState.getBaseState().withProperty(BlockWallSkyrootSign.FACING, EnumFacing.NORTH));
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
	{
		EnumFacing facing = worldIn.getBlockState(pos).getValue(BlockWallSkyrootSign.FACING);

		float minY = 0.28125F;
		float maxY = 0.78125F;
		float minX = 0.0F;
		float maxX = 1.0F;
		float padding = 0.125F;

		switch (facing)
		{
		case NORTH:
			this.setBlockBounds(minX, minY, 1.0F - padding, maxX, maxY, 1.0F);
			break;
		case SOUTH:
			this.setBlockBounds(minX, minY, 0.0F, maxX, maxY, padding);
			break;
		case WEST:
			this.setBlockBounds(1.0F - padding, minY, minX, 1.0F, maxY, maxX);
			break;
		case EAST:
			this.setBlockBounds(0.0F, minY, minX, padding, maxY, maxX);
		default:
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		EnumFacing enumfacing = state.getValue(BlockWallSkyrootSign.FACING);

		if (!world.getBlockState(pos.offset(enumfacing.getOpposite())).getBlock().getMaterial().isSolid())
		{
			this.dropBlockAsItem(world, pos, state, 0);

			world.setBlockToAir(pos);
		}

		super.onNeighborBlockChange(world, pos, state, neighborBlock);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing enumfacing = EnumFacing.getFront(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y)
		{
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(BlockWallSkyrootSign.FACING, enumfacing);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(BlockWallSkyrootSign.FACING).getIndex();
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, BlockWallSkyrootSign.FACING);
	}
}
