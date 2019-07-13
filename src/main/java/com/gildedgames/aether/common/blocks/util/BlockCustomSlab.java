package com.gildedgames.aether.common.blocks.util;

import com.gildedgames.aether.common.blocks.IBlockWithItem;
import com.gildedgames.aether.common.items.blocks.ItemBlockCustomSlab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCustomSlab extends Block implements IBlockWithItem
{
	public static final EnumProperty<EnumSlabPart> PROPERTY_SLAB_STATE = EnumProperty.create("state", EnumSlabPart.class);

	private static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);

	private static final AxisAlignedBB AABB_TOP_HALF = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);

	public BlockCustomSlab(final Block.Properties properties)
	{
		super(properties);

		this.setLightOpacity(0);

		this.useNeighborBrightness = true;
	}

	@Override
	public int getLightOpacity(final BlockState state)
	{
		return state.get(PROPERTY_SLAB_STATE) == EnumSlabPart.FULL_BLOCK ? 255 : 0;
	}

	@Override
	public boolean doesSideBlockRendering(final BlockState state, final IBlockReader world, final BlockPos pos, final Direction face)
	{
		if (state.isOpaqueCube())
		{
			return true;
		}

		final EnumSlabPart slabState = state.get(PROPERTY_SLAB_STATE);

		return (slabState == EnumSlabPart.TOP_HALF && face == Direction.UP) || (slabState == EnumSlabPart.BOTTOM_HALF
				&& face == Direction.DOWN);
	}

	@Override
	public BlockState getStateFromMeta(final int meta)
	{
		return this.getDefaultState().with(PROPERTY_SLAB_STATE, EnumSlabPart.VALUES[meta]);
	}

	@Override
	public int getMetaFromState(final BlockState state)
	{
		return state.get(PROPERTY_SLAB_STATE).ordinal();
	}

	@Override
	@Deprecated
	public boolean isFullBlock(final BlockState state)
	{
		return state.get(PROPERTY_SLAB_STATE) == EnumSlabPart.FULL_BLOCK;
	}

	@Override
	public boolean isFullCube(final BlockState state)
	{
		return state.get(PROPERTY_SLAB_STATE) == EnumSlabPart.FULL_BLOCK;
	}

	@Override
	public boolean isOpaqueCube(final BlockState state)
	{
		return state.get(PROPERTY_SLAB_STATE) == EnumSlabPart.FULL_BLOCK;
	}

	@Override
	public int quantityDropped(final BlockState state, final int fortune, final Random random)
	{
		return state.get(PROPERTY_SLAB_STATE) == EnumSlabPart.FULL_BLOCK ? 2 : 1;
	}

	@Override
	public AxisAlignedBB getBoundingBox(final BlockState state, final IBlockReader source, final BlockPos pos)
	{
		switch (state.get(PROPERTY_SLAB_STATE))
		{
			case BOTTOM_HALF:
				return AABB_BOTTOM_HALF;
			case TOP_HALF:
				return AABB_TOP_HALF;
			default:
				return FULL_BLOCK_AABB;
		}
	}

	@Override
	public BlockState getStateForPlacement(final World world, final BlockPos pos, final Direction facing, final float hitX, final float hitY,
			final float hitZ, final int meta,
			final LivingEntity placer)
	{
		final BlockState state = this.getDefaultState().with(PROPERTY_SLAB_STATE, EnumSlabPart.BOTTOM_HALF);

		if (state.get(PROPERTY_SLAB_STATE) == EnumSlabPart.FULL_BLOCK)
		{
			return state;
		}
		else
		{
			if (facing != Direction.DOWN && (facing == Direction.UP || (double) hitY <= 0.5D))
			{
				return state.with(PROPERTY_SLAB_STATE, EnumSlabPart.BOTTOM_HALF);
			}
			else
			{
				return state.with(PROPERTY_SLAB_STATE, EnumSlabPart.TOP_HALF);
			}
		}
	}

	@Override
	public VoxelShape getBlockFaceShape(IBlockReader worldIn, BlockState state, BlockPos pos, Direction face)
	{
		if (state.get(PROPERTY_SLAB_STATE) == EnumSlabPart.FULL_BLOCK)
		{
			return VoxelShape.SOLID;
		}
		else if (face == Direction.UP && state.get(PROPERTY_SLAB_STATE) == EnumSlabPart.TOP_HALF)
		{
			return VoxelShape.SOLID;
		}
		else if (face == Direction.DOWN && state.get(PROPERTY_SLAB_STATE) == EnumSlabPart.BOTTOM_HALF)
		{
			return VoxelShape.SOLID;
		}

		return VoxelShape.UNDEFINED;
	}

	@Override
	public boolean isTopSolid(BlockState state)
	{
		return state.get(PROPERTY_SLAB_STATE) == EnumSlabPart.TOP_HALF || state.get(PROPERTY_SLAB_STATE) == EnumSlabPart.FULL_BLOCK;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(PROPERTY_SLAB_STATE);
	}

	@Override
	public BlockItem createItemBlock()
	{
		return new ItemBlockCustomSlab(this);
	}

	public enum EnumSlabPart implements IStringSerializable
	{
		BOTTOM_HALF("bottom"),
		TOP_HALF("top"),
		FULL_BLOCK("full");

		public static final EnumSlabPart[] VALUES = EnumSlabPart.values();

		private final String name;

		EnumSlabPart(final String name)
		{
			this.name = name;
		}

		@Override
		public String getName()
		{
			return this.name;
		}
	}
}
