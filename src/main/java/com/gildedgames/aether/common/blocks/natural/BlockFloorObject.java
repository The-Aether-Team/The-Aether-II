package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.api.registrar.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class BlockFloorObject extends Block
{

	private static final VoxelShape AABB = Block.makeCuboidShape(0.1D, 0.0D, 0.1D, 0.9D, 0.3D, 0.9D);

	public BlockFloorObject(Block.Properties properties)
	{
		super(properties.doesNotBlockMovement());
	}

	@Override
	@Nullable
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return AABB;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public OffsetType getOffsetType()
	{
		return OffsetType.XZ;
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos)
	{
		BlockState otherState = world.getBlockState(pos.down());

		return otherState.getBlock() == BlocksAether.aether_grass || otherState.getBlock() == BlocksAether.aether_dirt
				|| otherState.getBlock() == BlocksAether.holystone;
	}

	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean p_220069_6_)
	{
		if (!this.isValidPosition(state, world, pos))
		{
			this.invalidateBlock(world, pos);
		}
	}

	private void invalidateBlock(World world, BlockPos pos)
	{
		world.removeBlock(pos, false);
	}

}
