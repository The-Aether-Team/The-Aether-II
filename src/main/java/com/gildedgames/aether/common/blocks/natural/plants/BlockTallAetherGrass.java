package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.common.blocks.IBlockMultiName;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockTallAetherGrass extends BlockAetherPlant implements IShearable, IBlockMultiName
{
	public static final BlockVariant SHORT = new BlockVariant(0, "short"),
			NORMAL = new BlockVariant(1, "normal"),
			LONG = new BlockVariant(2, "long");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", SHORT, NORMAL, LONG);

	private static final AxisAlignedBB GRASS_SHORT_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.3D, 0.9D);

	private static final AxisAlignedBB GRASS_NORMAL_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.6D, 0.9D);

	private static final AxisAlignedBB GRASS_LONG_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.9D, 0.9D);

	public BlockTallAetherGrass()
	{
		super(Material.PLANTS);

		this.setSoundType(SoundType.PLANT);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_VARIANT, SHORT));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(final CreativeTabs tab, final NonNullList<ItemStack> list)
	{
		for (final BlockVariant variant : PROPERTY_VARIANT.getAllowedValues())
		{
			list.add(new ItemStack(this, 1, variant.getMeta()));
		}
	}

	@Override
	public Item getItemDropped(final IBlockState state, final Random rand, final int fortune)
	{
		return null;
	}

	@Override
	public boolean isShearable(final ItemStack item, final IBlockAccess world, final BlockPos pos)
	{
		return true;
	}

	@Override
	public List<ItemStack> onSheared(final ItemStack item, final IBlockAccess world, final BlockPos pos, final int fortune)
	{
		final List<ItemStack> drops = new ArrayList<>();
		drops.add(new ItemStack(this, 1, this.getMetaFromState(world.getBlockState(pos))));

		return drops;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Block.EnumOffsetType getOffsetType()
	{
		return EnumOffsetType.XZ;
	}

	@Override
	public AxisAlignedBB getBoundingBox(final IBlockState state, final IBlockAccess source, final BlockPos pos)
	{
		if (state.getValue(PROPERTY_VARIANT) == SHORT)
		{
			return GRASS_SHORT_AABB;
		}
		else if (state.getValue(PROPERTY_VARIANT) == NORMAL)
		{
			return GRASS_NORMAL_AABB;
		}
		else if (state.getValue(PROPERTY_VARIANT) == LONG)
		{
			return GRASS_LONG_AABB;
		}

		return super.getBoundingBox(state, source, pos);
	}

	@Override
	public IBlockState getStateFromMeta(final int meta)
	{
		final BlockVariant variant = PROPERTY_VARIANT.fromMeta(meta);

		return this.getDefaultState().withProperty(PROPERTY_VARIANT, variant);
	}

	@Override
	public boolean isReplaceable(final IBlockAccess worldIn, final BlockPos pos)
	{
		return true;
	}

	@Override
	public int getMetaFromState(final IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_VARIANT);
	}

	@Override
	public String getUnlocalizedName(final ItemStack stack)
	{
		return PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName();
	}

	@Override
	public int damageDropped(final IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

}
