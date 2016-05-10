package com.gildedgames.aether.player.inventory;

import com.gildedgames.aether.items.properties.ItemEquipmentType;
import com.gildedgames.util.core.nbt.NBT;
import net.minecraft.inventory.IInventory;

public interface IInventoryEquipment extends IInventory, NBT
{
	/**
	 * Returns the slot ID of the first available inventory slot for equipment of {@param type}.
	 *
	 * @param type The slot type to search for
	 * @return A positive integer corresponding to next available inventory slot.
	 */
	int getNextEmptySlotForType(ItemEquipmentType type);
}
