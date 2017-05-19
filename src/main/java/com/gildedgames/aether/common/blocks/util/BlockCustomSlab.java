package com.gildedgames.aether.common.blocks.util;

import com.gildedgames.aether.common.blocks.IBlockWithItem;
import com.gildedgames.aether.common.items.blocks.ItemBlockCustomSlab;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCustomSlab extends Block implements IBlockWithItem
{
	private static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);

	private static final AxisAlignedBB AABB_TOP_HALF = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);

	public static final PropertyEnum<EnumSlabPart> PROPERTY_SLAB_STATE = PropertyEnum.create("state", EnumSlabPart.class);

	public BlockCustomSlab(Material material)
	{
		super(material);

		this.setLightOpacity(0);

		this.useNeighborBrightness = true;
	}

	@Override
	public BlockCustomSlab setSoundType(SoundType type)
	{
		super.setSoundType(type);

		return this;
	}

	@Override
	public int getLightOpacity(IBlockState state)
	{
		return state.getValue(PROPERTY_SLAB_STATE) == EnumSlabPart.FULL_BLOCK ? 255 : 0;
	}

	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		if (state.isOpaqueCube())
		{
			return true;
		}

		EnumSlabPart slabState = state.getValue(PROPERTY_SLAB_STATE);

		return (slabState == EnumSlabPart.TOP_HALF && face == EnumFacing.UP) || (slabState == EnumSlabPart.BOTTOM_HALF
				&& face == EnumFacing.DOWN);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(PROPERTY_SLAB_STATE, EnumSlabPart.VALUES[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(PROPERTY_SLAB_STATE).ordinal();
	}

	@Override
	@Deprecated
	public boolean isFullBlock(IBlockState state)
	{
		return state.getValue(PROPERTY_SLAB_STATE) == EnumSlabPart.FULL_BLOCK;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return state.getValue(PROPERTY_SLAB_STATE) == EnumSlabPart.FULL_BLOCK;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return state.getValue(PROPERTY_SLAB_STATE) == EnumSlabPart.FULL_BLOCK;
	}

	@Override
	public boolean isFullyOpaque(IBlockState state)
	{
		return state.getValue(PROPERTY_SLAB_STATE) == EnumSlabPart.FULL_BLOCK;
	}

	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random)
	{
		return state.getValue(PROPERTY_SLAB_STATE) == EnumSlabPart.FULL_BLOCK ? 2 : 1;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		switch (state.getValue(PROPERTY_SLAB_STATE))
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
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta,
			EntityLivingBase placer)
	{
		IBlockState state = this.getDefaultState().withProperty(PROPERTY_SLAB_STATE, EnumSlabPart.BOTTOM_HALF);

		if (state.getValue(PROPERTY_SLAB_STATE) == EnumSlabPart.FULL_BLOCK)
		{
			return state;
		}
		else
		{
			if (facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double) hitY <= 0.5D))
			{
				return state.withProperty(PROPERTY_SLAB_STATE, EnumSlabPart.BOTTOM_HALF);
			}
			else
			{
				return state.withProperty(PROPERTY_SLAB_STATE, EnumSlabPart.TOP_HALF);
			}
		}
	}

	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_SLAB_STATE);
	}

	@Override
	public ItemBlock createItemBlock()
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

		EnumSlabPart(String name)
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
