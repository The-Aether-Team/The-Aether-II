package com.gildedgames.aether.common.util;

import com.gildedgames.aether.common.player.PlayerAether;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Set;

public class PlayerUtil
{
	public static boolean isWearingEquipment(PlayerAether aePlayer, Item... items)
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

			for (ItemStack stack : aePlayer.getPlayer().inventory.armorInventory)
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
