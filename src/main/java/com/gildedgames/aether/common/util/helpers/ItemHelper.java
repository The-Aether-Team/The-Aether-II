package com.gildedgames.aether.common.util.helpers;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemHelper
{
	public static int getHashForItemStack(ItemStack stack, boolean hashDamage)
	{
		if (stack == null)
		{
			return 0;
		}

		int id = Item.getIdFromItem(stack.getItem());

		int hash = (id & 0xFFFF) << 16;

		if (!stack.isItemStackDamageable() && stack.getItemDamage() != OreDictionary.WILDCARD_VALUE && hashDamage)
		{
			hash = hash | (stack.getItemDamage() & 0xFFFF);
		}

		return hash;
	}

	public static int getHashForItemStack(ItemStack stack)
	{
		return getHashForItemStack(stack, true);
	}

	public static boolean areEqual(ItemStack s1, ItemStack s2)
	{
		return ItemHelper.getHashForItemStack(s1) == ItemHelper.getHashForItemStack(s2);
	}
}
