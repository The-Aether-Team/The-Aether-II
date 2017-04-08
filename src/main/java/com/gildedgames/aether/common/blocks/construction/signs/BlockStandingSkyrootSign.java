package com.gildedgames.aether.common.blocks.construction.signs;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockStandingSkyrootSign extends BlockSkyrootSign
{
	public static final PropertyInteger ROTATION = PropertyInteger.create("rotation", 0, 15);

	public BlockStandingSkyrootSign()
	{
		super();
		this.setDefaultState(this.blockState.getBaseState().withProperty(BlockStandingSkyrootSign.ROTATION, 0));
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos fromPos)
	{
		if (!world.getBlockState(pos.down()).getMaterial().isSolid())
		{
			this.dropBlockAsItem(world, pos, state, 0);

			world.setBlockToAir(pos);
		}

		super.neighborChanged(state, world, pos, neighborBlock, fromPos);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(BlockStandingSkyrootSign.ROTATION, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(BlockStandingSkyrootSign.ROTATION);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BlockStandingSkyrootSign.ROTATION);
	}
}
