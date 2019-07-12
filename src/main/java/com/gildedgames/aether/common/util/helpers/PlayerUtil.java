package com.gildedgames.aether.common.util.helpers;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerEquipmentModule;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;

public class PlayerUtil
{
	public static boolean isWearingEquipment(IPlayerAether aePlayer, Item... items)
	{
		PlayerEquipmentModule equipmentModule = aePlayer.getModule(PlayerEquipmentModule.class);

		IInventory equipmentInventory = equipmentModule.getInventory();
		NonNullList<ItemStack> armorInventory = aePlayer.getEntity().inventory.armorInventory;

		for (Item item : items)
		{
			boolean found = false;

			for (int i = 0; i < equipmentInventory.getSizeInventory(); i++)
			{
				ItemStack stack = equipmentInventory.getStackInSlot(i);

				if (stack.getItem() == item)
				{
					found = true;

					break;
				}
			}

			for (ItemStack stack : armorInventory)
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

	public static void fillBucketInHand(PlayerEntity player, Hand hand, ItemStack emptyBucket, ItemStack fillBucket)
	{
		final int invSlot = hand == Hand.MAIN_HAND ? player.inventory.currentItem : 40;

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
