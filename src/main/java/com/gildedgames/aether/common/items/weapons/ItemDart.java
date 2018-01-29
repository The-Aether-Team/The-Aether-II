package com.gildedgames.aether.common.items.weapons;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDart extends Item
{
	private static final ItemDartType[] ITEM_VARIANTS = new ItemDartType[] { ItemDartType.GOLDEN, ItemDartType.ENCHANTED,
			ItemDartType.POISON };

	public ItemDart()
	{
		this.setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(final CreativeTabs tab, final NonNullList<ItemStack> subItems)
	{
		if (!this.isInCreativeTab(tab))
		{
			return;
		}

		for (final ItemDartType type : ITEM_VARIANTS)
		{
			subItems.add(new ItemStack(this, 1, type.ordinal()));
		}
	}

	@Override
	public String getUnlocalizedName(final ItemStack stack)
	{
		return super.getUnlocalizedName(stack) + "." + ItemDartType.fromOrdinal(stack.getMetadata()).getID();
	}
}
