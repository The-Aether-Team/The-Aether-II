package com.gildedgames.aether.common.util;

import com.gildedgames.aether.common.items.accessories.AccessoryType;
import com.gildedgames.aether.common.items.accessories.ItemAccessory;
import com.gildedgames.aether.common.player.PlayerAether;
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

	public static void fillBucketInHand(EntityPlayer player, ItemStack fillBucket)
	{
		ItemStack stack = player.getHeldItem();

		if (stack.stackSize == 1)
		{
			player.inventory.setInventorySlotContents(player.inventory.currentItem, fillBucket);
		}
		else
		{
			if (!player.capabilities.isCreativeMode)
			{
				stack.stackSize--;

				player.inventory.setInventorySlotContents(player.inventory.currentItem, stack);
			}

			if (!player.inventory.addItemStackToInventory(fillBucket))
			{
				player.dropPlayerItemWithRandomChoice(fillBucket, false);
			}
		}
	}

	public static boolean wearingArmor(EntityPlayer player, int slot, Item item)
	{
		ItemStack stack = player.inventory.armorInventory[slot];

		if (stack != null)
		{
			if (stack.getItem() ==  item)
			{
				return true;
			}
		}

		return false;
	}

	public static boolean wearingAccessory(EntityPlayer player, Item item)
	{
		PlayerAether aePlayer = PlayerAether.get(player);

		return aePlayer.isAccessoryEquipped(item);
	}
}
