package com.gildedgames.aether.blocks.natural;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gildedgames.aether.blocks.util.IAetherBlockWithVariants;
import com.gildedgames.aether.blocks.util.blockstates.BlockVariant;
import com.gildedgames.aether.blocks.util.blockstates.PropertyVariant;
import com.gildedgames.aether.creativetabs.AetherCreativeTabs;

public class BlockHolystone extends Block implements IAetherBlockWithVariants
{
	public static final BlockVariant
			NORMAL_HOLYSTONE = new BlockVariant(0, "holystone"),
			MOSSY_HOLYSTONE = new BlockVariant(1, "mossy_holystone"),
			BLOOD_MOSS_HOLYSTONE = new BlockVariant(2, "blood_holystone");

	public static final PropertyVariant HOLYSTONE_TYPE = PropertyVariant.create("variant", NORMAL_HOLYSTONE, MOSSY_HOLYSTONE, BLOOD_MOSS_HOLYSTONE);

	public BlockHolystone()
	{
		super(Material.rock);
		this.setHardness(2.0F);
		this.setStepSound(Block.soundTypeStone);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(HOLYSTONE_TYPE, NORMAL_HOLYSTONE));
		this.setCreativeTab(AetherCreativeTabs.tabBlocks);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
	{
		for (BlockVariant variant : HOLYSTONE_TYPE.getAllowedValues())
		{
			list.add(new ItemStack(itemIn, 1, variant.getMeta()));
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(HOLYSTONE_TYPE, HOLYSTONE_TYPE.getVariantFromMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((BlockVariant) state.getValue(HOLYSTONE_TYPE)).getMeta();
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { HOLYSTONE_TYPE });
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return ((BlockVariant) state.getValue(HOLYSTONE_TYPE)).getMeta();
	}

	@Override
	public String getVariantNameFromStack(ItemStack stack)
	{
		return HOLYSTONE_TYPE.getVariantFromMeta(stack.getMetadata()).getName();
	}
}
