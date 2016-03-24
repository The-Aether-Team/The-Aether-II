package com.gildedgames.aether.common.blocks.construction.aether_walls;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAetherWall extends Block
{
	public static final PropertyBool UP = PropertyBool.create("up"),
			NORTH = PropertyBool.create("north"),
			EAST = PropertyBool.create("east"),
			SOUTH = PropertyBool.create("south"),
			WEST = PropertyBool.create("west");

	public BlockAetherWall(Block block, float hardness, float resistance)
	{
		super(block.getMaterial());

		this.setResistance(resistance / 3.0f);

		this.setHardness(hardness);

		this.setStepSound(block.stepSound);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
	{
		this.setBlockBoundsBasedOnState(worldIn, pos);
		this.maxY = 1.5D;

		return super.getCollisionBoundingBox(worldIn, pos, state);
	}

	public boolean canConnectTo(IBlockAccess world, BlockPos pos)
	{
		Block block = world.getBlockState(pos).getBlock();

		return block != Blocks.barrier &&
				(!(block != this && !(block instanceof BlockFenceGate)) || ((block.getMaterial().isOpaque() && block.isFullCube()) && block.getMaterial() != Material.gourd));
	}

	@Override
	public boolean isFullCube()
	{
		return false;
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
	{
		boolean connectedToNorth = this.canConnectTo(worldIn, pos.north());
		boolean connectedToSouth = this.canConnectTo(worldIn, pos.south());
		boolean connectedToWest = this.canConnectTo(worldIn, pos.west());
		boolean connectedToEast = this.canConnectTo(worldIn, pos.east());

		float minX = 0.25F;
		float maxX = 0.75F;
		float minZ = 0.25F;
		float maxZ = 0.75F;
		float maxY = 1.0F;

		if (connectedToNorth)
		{
			minZ = 0.0F;
		}

		if (connectedToSouth)
		{
			maxZ = 1.0F;
		}

		if (connectedToWest)
		{
			minX = 0.0F;
		}

		if (connectedToEast)
		{
			maxX = 1.0F;
		}

		if (connectedToNorth && connectedToSouth && !connectedToWest && !connectedToEast)
		{
			maxY = 0.8125F;
			minX = 0.3125F;
			maxX = 0.6875F;
		}
		else if (!connectedToNorth && !connectedToSouth && connectedToWest && connectedToEast)
		{
			maxY = 0.8125F;
			minZ = 0.3125F;
			maxZ = 0.6875F;
		}

		this.setBlockBounds(minX, 0.0F, minZ, maxX, maxY, maxZ);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState();
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return state.withProperty(BlockAetherWall.UP, !worldIn.isAirBlock(pos.up()))
				.withProperty(BlockAetherWall.NORTH, this.canConnectTo(worldIn, pos.north()))
				.withProperty(BlockAetherWall.EAST, this.canConnectTo(worldIn, pos.east()))
				.withProperty(BlockAetherWall.SOUTH, this.canConnectTo(worldIn, pos.south()))
				.withProperty(BlockAetherWall.WEST, this.canConnectTo(worldIn, pos.west()));
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, BlockAetherWall.UP, BlockAetherWall.NORTH, BlockAetherWall.EAST, BlockAetherWall.WEST, BlockAetherWall.SOUTH);
	}
}
