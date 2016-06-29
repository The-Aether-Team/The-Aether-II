package com.gildedgames.aether.common.blocks.construction.walls;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockAetherWall extends Block
{
	public static final PropertyBool UP = PropertyBool.create("up"),
			NORTH = PropertyBool.create("north"),
			EAST = PropertyBool.create("east"),
			SOUTH = PropertyBool.create("south"),
			WEST = PropertyBool.create("west");

	protected static final AxisAlignedBB[] AABB_BY_INDEX = new AxisAlignedBB[] {
			new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D),
			new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D),
			new AxisAlignedBB(0.0D, 0.0D, 0.25D, 0.75D, 1.0D, 1.0D),
			new AxisAlignedBB(0.25D, 0.0D, 0.0D, 0.75D, 1.0D, 0.75D),
			new AxisAlignedBB(0.3125D, 0.0D, 0.0D, 0.6875D, 0.875D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 0.75D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 1.0D),
			new AxisAlignedBB(0.25D, 0.0D, 0.25D, 1.0D, 1.0D, 0.75D),
			new AxisAlignedBB(0.25D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.3125D, 1.0D, 0.875D, 0.6875D),
			new AxisAlignedBB(0.0D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D),
			new AxisAlignedBB(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D),
			new AxisAlignedBB(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)
	};

	protected static final AxisAlignedBB[] CLIP_AABB_BY_INDEX = new AxisAlignedBB[] {
			AABB_BY_INDEX[0].setMaxY(1.5D),
			AABB_BY_INDEX[1].setMaxY(1.5D),
			AABB_BY_INDEX[2].setMaxY(1.5D),
			AABB_BY_INDEX[3].setMaxY(1.5D),
			AABB_BY_INDEX[4].setMaxY(1.5D),
			AABB_BY_INDEX[5].setMaxY(1.5D),
			AABB_BY_INDEX[6].setMaxY(1.5D),
			AABB_BY_INDEX[7].setMaxY(1.5D),
			AABB_BY_INDEX[8].setMaxY(1.5D),
			AABB_BY_INDEX[9].setMaxY(1.5D),
			AABB_BY_INDEX[10].setMaxY(1.5D),
			AABB_BY_INDEX[11].setMaxY(1.5D),
			AABB_BY_INDEX[12].setMaxY(1.5D),
			AABB_BY_INDEX[13].setMaxY(1.5D),
			AABB_BY_INDEX[14].setMaxY(1.5D),
			AABB_BY_INDEX[15].setMaxY(1.5D)
	};


	public BlockAetherWall(IBlockState state, float hardness, float resistance)
	{
		super(state.getMaterial());

		this.setResistance(resistance / 3.0f);

		this.setHardness(hardness);

		this.setSoundType(state.getBlock().getSoundType());
	}

	public BlockAetherWall setGlows(boolean glows)
	{
		this.setLightLevel(glows ? 0.75f : 0.0f);

		return this;
	}

	public boolean canConnectTo(IBlockAccess world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);

		return state.getBlock() != Blocks.BARRIER &&
				(!(state.getBlock() != this && !(state.getBlock() instanceof BlockFenceGate)) || ((state.getMaterial().isOpaque() && state.isFullCube()) && state.getMaterial() != Material.GOURD));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		state = this.getActualState(state, source, pos);

		return AABB_BY_INDEX[getAABBIndex(state)];
	}

	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World world, BlockPos pos)
	{
		state = this.getActualState(state, world, pos);

		return CLIP_AABB_BY_INDEX[getAABBIndex(state)];
	}

	private static int getAABBIndex(IBlockState state)
	{
		int i = 0;

		if (state.getValue(NORTH))
		{
			i |= 1 << EnumFacing.NORTH.getHorizontalIndex();
		}

		if (state.getValue(EAST))
		{
			i |= 1 << EnumFacing.EAST.getHorizontalIndex();
		}

		if (state.getValue(SOUTH))
		{
			i |= 1 << EnumFacing.SOUTH.getHorizontalIndex();
		}

		if (state.getValue(WEST))
		{
			i |= 1 << EnumFacing.WEST.getHorizontalIndex();
		}

		return i;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
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
		boolean flag = this.canConnectTo(worldIn, pos.north());
		boolean flag1 = this.canConnectTo(worldIn, pos.east());
		boolean flag2 = this.canConnectTo(worldIn, pos.south());
		boolean flag3 = this.canConnectTo(worldIn, pos.west());
		boolean flag4 = flag && !flag1 && flag2 && !flag3 || !flag && flag1 && !flag2 && flag3;
		return state.withProperty(UP, !flag4 || !worldIn.isAirBlock(pos.up())).withProperty(NORTH, flag).withProperty(EAST, flag1).withProperty(SOUTH, flag2).withProperty(WEST, flag3);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BlockAetherWall.UP, BlockAetherWall.NORTH, BlockAetherWall.EAST, BlockAetherWall.WEST, BlockAetherWall.SOUTH);
	}
}
