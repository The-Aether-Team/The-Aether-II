package com.gildedgames.aether.common.blocks.construction.skyroot_sign;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
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
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		if (!world.getBlockState(pos.down()).getBlock().getMaterial().isSolid())
		{
			this.dropBlockAsItem(world, pos, state, 0);

			world.setBlockToAir(pos);
		}

		super.onNeighborBlockChange(world, pos, state, neighborBlock);
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
	protected BlockState createBlockState()
	{
		return new BlockState(this, BlockStandingSkyrootSign.ROTATION);
	}
}
