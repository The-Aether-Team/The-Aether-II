package com.gildedgames.aether.blocks.natural;

import java.util.List;

import com.gildedgames.aether.blocks.util.IAetherBlockVariant;
import com.gildedgames.aether.blocks.util.IAetherBlockWithVariants;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockHolystone extends Block implements IAetherBlockWithVariants
{
	public enum HolystoneVariant implements IStringSerializable, IAetherBlockVariant
	{
		NORMAL(0, "holystone"),
		MOSSY(1, "mossy_holystone"),
		BLOOD(2, "blood_holystone");

		private static final HolystoneVariant[] metaLookup = new HolystoneVariant[HolystoneVariant.values().length];

		static
		{
			for (HolystoneVariant variant : HolystoneVariant.values())
			{
				metaLookup[variant.getMetadata()] = variant;
			}
		}

		private int metadata;

		private String name;

		HolystoneVariant(int metadata, String name)
		{
			this.metadata = metadata;
			this.name = name;
		}

		@Override
		public String getName()
		{
			return this.name;
		}

		@Override
		public int getMetadata()
		{
			return this.metadata;
		}

		public static HolystoneVariant getVariantFromMetadata(int meta)
		{
			return HolystoneVariant.metaLookup[meta];
		}
	}

	public static final PropertyEnum HOLYSTONE_TYPE = PropertyEnum.create("variant", HolystoneVariant.class);

	public BlockHolystone()
	{
		super(Material.rock);
		this.setHardness(2.0F);
		this.setStepSound(Block.soundTypeStone);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(HOLYSTONE_TYPE, HolystoneVariant.NORMAL));
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
	{
		for (HolystoneVariant type : HolystoneVariant.values())
		{
			list.add(new ItemStack(itemIn, 1, type.getMetadata()));
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(HOLYSTONE_TYPE, HolystoneVariant.getVariantFromMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((HolystoneVariant) state.getValue(HOLYSTONE_TYPE)).getMetadata();
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { HOLYSTONE_TYPE });
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return ((HolystoneVariant) state.getValue(HOLYSTONE_TYPE)).getMetadata();
	}

	@Override
	public String getVariantNameFromStack(ItemStack stack)
	{
		return HolystoneVariant.getVariantFromMetadata(stack.getMetadata()).getName();
	}
}
