package com.gildedgames.aether.common.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.gildedgames.aether.common.items.ItemPropertiesBase;
import com.gildedgames.aether.common.player.PlayerAether;

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
	
	public static boolean isAccessoryInFirstSlot(EntityPlayer player, ItemStack stack)
	{
		PlayerAether aePlayer = PlayerAether.get(player);
		
		if (!(stack.getItem() instanceof ItemPropertiesBase))
		{
			return false;
		}

		for (int index = 0; index < 8; index++)
		{
			ItemStack slotStack = aePlayer.getEquipment().getInventory()[index];
			
			if (slotStack == stack)
			{
				return true;
			}
			else if (slotStack != null && slotStack.getItem() instanceof ItemPropertiesBase)
			{
				ItemPropertiesBase acc = (ItemPropertiesBase)slotStack.getItem();
				
				if (acc.getEquipmentType() == ((ItemPropertiesBase)stack.getItem()).getEquipmentType())
				{
					return false;
				}
			}
		}

		return false;
	}

	public static boolean wearingAccessory(EntityPlayer player, Item item)
	{
		PlayerAether aePlayer = PlayerAether.get(player);

		return aePlayer.isItemEquipped(item);
	}
	
	public static int getAccessoryCount(EntityPlayer player, Item itemID)
	{
		PlayerAether aePlayer = PlayerAether.get(player);
		
		int count = 0;

		for (int index = 0; index < 8; index++)
		{
			if (aePlayer.getEquipment().getInventory()[index] != null && aePlayer.getEquipment().getInventory()[index].getItem() == itemID)
			{
				count++;
			}
		}

		return count;
	}
	
}
