package com.gildedgames.aether.common.blocks.util;

import com.gildedgames.aether.common.registry.minecraft.CreativeTabsAether;
import com.gildedgames.aether.common.ReflectionAether;
import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.Random;

public class BlockCustomSlab extends Block
{
	protected static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);

	protected static final AxisAlignedBB AABB_TOP_HALF = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);

	public static final PropertyEnum<SlabState> PROPERTY_SLAB_STATE = PropertyEnum.create("state", SlabState.class);

	public BlockCustomSlab(final IBlockState state)
	{
		super(state.getMaterial());

		this.setCreativeTab(CreativeTabsAether.BLOCKS);

		final Block block = state.getBlock();

		BlocksAether.applyPostRegistration(() -> BlockCustomSlab.this.setHarvestLevel(block.getHarvestTool(state), block.getHarvestLevel(state)));

		this.setLightOpacity(0);

		this.setSoundType(block.getSoundType());

		float blockHardness = ObfuscationReflectionHelper.getPrivateValue(Block.class, block, ReflectionAether.BLOCK_HARDNESS.getMappings());

		this.setHardness(blockHardness);
	}

	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		if (state.isOpaqueCube())
		{
			return true;
		}

		SlabState slabState = state.getValue(PROPERTY_SLAB_STATE);

		return (slabState == SlabState.TOP_HALF && face == EnumFacing.UP) || (slabState == SlabState.BOTTOM_HALF && face == EnumFacing.DOWN);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(PROPERTY_SLAB_STATE, SlabState.VALUES[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(PROPERTY_SLAB_STATE).ordinal();
	}

	@Deprecated
	public boolean isFullBlock(IBlockState state)
	{
		return state.getValue(PROPERTY_SLAB_STATE) == SlabState.FULL_BLOCK;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return state.getValue(PROPERTY_SLAB_STATE) == SlabState.FULL_BLOCK;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return state.getValue(PROPERTY_SLAB_STATE) == SlabState.FULL_BLOCK;
	}

	@Override
	public boolean isFullyOpaque(IBlockState state)
	{
		return state.getValue(PROPERTY_SLAB_STATE) == SlabState.FULL_BLOCK;
	}

	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random)
	{
		return state.getValue(PROPERTY_SLAB_STATE) == SlabState.FULL_BLOCK ? 2 : 1;
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
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		IBlockState state = this.getDefaultState().withProperty(PROPERTY_SLAB_STATE, SlabState.BOTTOM_HALF);

		if (state.getValue(PROPERTY_SLAB_STATE) == SlabState.FULL_BLOCK)
		{
			return state;
		}
		else
		{
			if (facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double) hitY <= 0.5D))
			{
				return state.withProperty(PROPERTY_SLAB_STATE, SlabState.BOTTOM_HALF);
			}
			else
			{
				return state.withProperty(PROPERTY_SLAB_STATE, SlabState.TOP_HALF);
			}
		}
	}

	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_SLAB_STATE);
	}

	public enum SlabState implements IStringSerializable
	{
		BOTTOM_HALF("bottom"),
		TOP_HALF("top"),
		FULL_BLOCK("full");

		public static final SlabState[] VALUES = SlabState.values();

		private final String name;

		SlabState(String name)
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
