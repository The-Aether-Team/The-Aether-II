package com.gildedgames.aether.common.util.helpers;

import com.gildedgames.aether.api.player.IPlayerAether;
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

			for (int i = 0; i < aePlayer.getEquipmentModule().getInventory().getSizeInventory(); i++)
			{
				ItemStack stack = aePlayer.getEquipmentModule().getInventory().getStackInSlot(i);

				if (stack.getItem() == item)
				{
					found = true;

					break;
				}
			}

			for (ItemStack stack : aePlayer.getEntity().inventory.armorInventory)
			{
				if (stack.getItem() == item)
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
		if (emptyBucket.getCount() == 1)
		{
			player.inventory.setInventorySlotContents(player.inventory.currentItem, fillBucket);
		}
		else
		{
			emptyBucket.shrink(1);

			player.inventory.setInventorySlotContents(player.inventory.currentItem, emptyBucket);

			if (!player.inventory.addItemStackToInventory(fillBucket))
			{
				player.dropItem(fillBucket, false);
			}
		}
	}
}
