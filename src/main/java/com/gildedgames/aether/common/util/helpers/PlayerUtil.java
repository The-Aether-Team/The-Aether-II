package com.gildedgames.aether.common.util.helpers;

import com.gildedgames.aether.api.capabilites.entity.IPlayerAether;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PlayerUtil
{
	public static boolean isWearingEquipment(IPlayerAether aePlayer, Item... items)
	{
		for (Item item : items)
		{
			boolean found = false;

			for (int i = 0; i < aePlayer.getEquipmentInventory().getSizeInventory(); i++)
			{
				ItemStack stack = aePlayer.getEquipmentInventory().getStackInSlot(i);

				if (stack != null && stack.getItem() == item)
				{
					found = true;

					break;
				}
			}

			for (ItemStack stack : aePlayer.getEntity().inventory.armorInventory)
			{
				if (stack != null && stack.getItem() == item)
				{
					found = true;

					break;
				}
			}

			if (!found)
			{
				return false;
			}
		}

		return true;
	}

	public static void fillBucketInHand(EntityPlayer player, ItemStack emptyBucket, ItemStack fillBucket)
	{
		if (emptyBucket.stackSize == 1)
		{
			player.inventory.setInventorySlotContents(player.inventory.currentItem, fillBucket);
		}
		else
		{
			emptyBucket.stackSize--;

			player.inventory.setInventorySlotContents(player.inventory.currentItem, emptyBucket);
			
			if (!player.inventory.addItemStackToInventory(fillBucket))
			{
				player.dropItem(fillBucket, false);
			}
		}
	}
}
