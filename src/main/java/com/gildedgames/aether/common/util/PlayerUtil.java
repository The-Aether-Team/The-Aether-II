package com.gildedgames.aether.common.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PlayerUtil
{
	public static boolean isWearingFullSet(EntityPlayer player, Class<? extends Item> cls)
	{
		return findArmorSet(player) == cls;
	}

	public static Class<? extends Item> findArmorSet(EntityPlayer player)
	{
		Class<? extends Item> armorClass = null;

		for (ItemStack stack : player.inventory.armorInventory)
		{
			if (stack == null)
			{
				return null;
			}

			Class<? extends Item> stackClass = stack.getItem().getClass();

			if (armorClass == null)
			{
				armorClass = stackClass;
			}
			else if (!stackClass.isAssignableFrom(stackClass))
			{
				return null;
			}
		}

		return armorClass;
	}
}
