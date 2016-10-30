package com.gildedgames.aether.common.blocks.natural.plants;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockTallAetherGrass extends BlockAetherPlant implements IShearable
{

	private static final AxisAlignedBB GRASS_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.3D, 0.9D);

	public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, 3);

	public static final int NORMAL = 0, ENCHANTED = 1, FROSTROOT = 2, BLIGHTED = 3;

	public BlockTallAetherGrass()
	{
		super(Material.PLANTS);

		this.setSoundType(SoundType.PLANT);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(TYPE, 0));
	}

	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		//IBlockState block = worldIn.getBlockState(pos.down());

		int type = NORMAL;

		/*if (block == BlocksAether.aether_grass.getStateFromMeta(BlockAetherGrass.ENCHANTED_AETHER_GRASS.getMeta()))
		{
			type = ENCHANTED;
		}

		if (block == BlocksAether.aether_grass.getStateFromMeta(BlockAetherGrass.FROSTROOT.getMeta()))
		{
			type = FROSTROOT;
		}

		if (block == BlocksAether.aether_grass.getStateFromMeta(BlockAetherGrass.BLIGHTED.getMeta()))
		{
			type = BLIGHTED;
		}*/

		return state.withProperty(TYPE, type);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return null;
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos)
	{
		return true;
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
	{
		List<ItemStack> drops = new ArrayList<>();
		drops.add(new ItemStack(this));

		return drops;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Block.EnumOffsetType getOffsetType()
	{
		return EnumOffsetType.XZ;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return GRASS_AABB;
	}

	@Override
	public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos)
	{
		return true;
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, TYPE);
	}

}
