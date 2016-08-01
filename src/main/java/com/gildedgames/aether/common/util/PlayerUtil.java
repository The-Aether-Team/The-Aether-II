package com.gildedgames.aether.common.util;

import com.gildedgames.aether.common.items.ItemsAether;
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

		PlayerAether aePlayer = (PlayerAether) PlayerAether.getPlayer(player);
		ItemStack gloveStack = aePlayer.getEquipmentInventory().getStackInSlot(2);

		if (aePlayer.getEquipmentInventory().getStackInSlot(2) != null)
		{
			if (gloveStack.getItem() == ItemsAether.obsidian_gloves || gloveStack.getItem() == ItemsAether.gravitite_gloves
					|| gloveStack.getItem() == ItemsAether.phoenix_gloves || gloveStack.getItem() == ItemsAether.neptune_gloves
					|| gloveStack.getItem() == ItemsAether.valkyrie_gloves || gloveStack.getItem() == ItemsAether.zanite_gloves)
			{
				return armorClass;
			}
		}

		return null;
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

	public static boolean wearingArmor(EntityPlayer player, int slot, Item item)
	{
		ItemStack stack = player.inventory.armorInventory[slot];

		if (stack != null)
		{
			if (stack.getItem() == item)
			{
				return true;
			}
		}

		return false;
	}
}
