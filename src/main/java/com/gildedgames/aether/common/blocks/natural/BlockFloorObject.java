package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.api.registrar.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockFloorObject extends Block
{

	private static final AxisAlignedBB AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.3D, 0.9D);

	public BlockFloorObject(Block.Properties properties)
	{
		super(properties.doesNotBlockMovement());
	}

	@Override
	public VoxelShape getBlockFaceShape(IBlockReader worldIn, BlockState state, BlockPos pos, Direction face)
	{
		return VoxelShape.UNDEFINED;
	}

	@Override
	public void onBlockClicked(World world, BlockPos pos, PlayerEntity player)
	{
		this.invalidateBlock(world, pos);
	}

	@Override
	public boolean isPassable(IBlockReader world, BlockPos pos)
	{
		return true;
	}

	@Override
	public boolean isOpaqueCube(BlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(BlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullBlock(BlockState state)
	{
		return false;
	}

	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(BlockState blockState, IBlockReader worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}

	@Override
	public Item getItemDropped(BlockState state, Random rand, int fortune)
	{
		return null;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public EnumOffsetType getOffsetType()
	{
		return EnumOffsetType.XZ;
	}

	@Override
	public AxisAlignedBB getBoundingBox(BlockState state, IBlockReader source, BlockPos pos)
	{
		return AABB;
	}

	@Override
	public boolean isReplaceable(IBlockReader worldIn, BlockPos pos)
	{
		return true;
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		BlockState state = world.getBlockState(pos.down());

		return state.getBlock() == BlocksAether.aether_grass || state.getBlock() == BlocksAether.aether_dirt
				|| state.getBlock() == BlocksAether.holystone;
	}

	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean p_220069_6_)
	{
		this.validatePosition(world, pos);
	}

	public void validatePosition(World world, BlockPos pos)
	{
		if (!this.canPlaceBlockAt(world, pos))
		{
			this.invalidateBlock(world, pos);
		}
	}

	protected void invalidateBlock(World world, BlockPos pos)
	{
		world.removeBlock(pos, false);
	}

}
