package com.gildedgames.aether.api.player.inventory;

import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.orbis_api.util.mc.NBT;
import net.minecraft.inventory.IInventory;

public interface IInventoryEquipment extends IInventory, NBT
{
	/**
	 * Returns the slot ID of the first available inventory slot for equipment of {@param type}.
	 *
	 * @param type The slot type to search for
	 * @return A positive integer corresponding to next available inventory slot. Returns -1 if there are no eligible slots.
	 */
	int getNextEmptySlotForType(ItemEquipmentSlot type);
}
