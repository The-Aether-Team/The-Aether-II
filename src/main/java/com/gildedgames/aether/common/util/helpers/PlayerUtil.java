package com.gildedgames.aether.common.util.helpers;

import com.gildedgames.aether.api.player.IPlayerAether;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

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

	public static void fillBucketInHand(EntityPlayer player, EnumHand hand, ItemStack emptyBucket, ItemStack fillBucket)
	{
		final int invSlot = hand == EnumHand.MAIN_HAND ? player.inventory.currentItem : 40;

		if (emptyBucket.getCount() == 1)
		{
			player.inventory.setInventorySlotContents(invSlot, fillBucket);
		}
		else
		{
			emptyBucket.shrink(1);

			player.inventory.setInventorySlotContents(invSlot, emptyBucket);

			if (!player.inventory.addItemStackToInventory(fillBucket))
			{
				player.dropItem(fillBucket, false);
			}
		}
	}
}
