package com.gildedgames.aether.client.ui.minecraft.util.inventory.data_structure;

public interface InventoryListener<T>
{

	void onChange(int slotIndex, T contents);
	
}
