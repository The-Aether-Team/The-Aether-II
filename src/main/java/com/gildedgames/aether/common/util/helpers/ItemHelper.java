package com.gildedgames.aether.common.util.helpers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemHelper
{
	public static int getKeyForItemStack(ItemStack stack, boolean hashDamage)
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

	public static int getKeyForItemStack(ItemStack stack)
	{
		return getKeyForItemStack(stack, true);
	}

	public static boolean areEqual(ItemStack s1, ItemStack s2)
	{
		return ItemHelper.getKeyForItemStack(s1) == ItemHelper.getKeyForItemStack(s2);
	}

	public static int getSlotFor(ItemStack stack, EntityPlayer player)
	{
		for (int i = 0; i < player.inventory.mainInventory.size(); ++i)
		{
			if (!(player.inventory.mainInventory.get(i)).isEmpty() && stackEqualExact(stack, player.inventory.mainInventory.get(i)))
			{
				return i;
			}
		}

		return -1;
	}

	private static boolean stackEqualExact(ItemStack stack1, ItemStack stack2)
	{
		return stack1.getItem() == stack2.getItem() && (!stack1.getHasSubtypes() || stack1.getMetadata() == stack2.getMetadata()) && ItemStack.areItemStackTagsEqual(stack1, stack2);
	}
}
