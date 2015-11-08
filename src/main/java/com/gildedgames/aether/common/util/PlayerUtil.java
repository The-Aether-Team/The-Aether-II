package com.gildedgames.aether.common.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PlayerUtil
{

	public static boolean isWearingFullSet(EntityPlayer player, Class<? extends Item> cls)
	{
		for (ItemStack stack : player.inventory.armorInventory)
		{
			if (stack == null || !(stack.getItem().getClass().isAssignableFrom(cls)))
			{
				return false;
			}
		}

		return true;
	}

}
