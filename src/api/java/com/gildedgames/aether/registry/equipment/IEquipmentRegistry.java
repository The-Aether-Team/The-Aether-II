package com.gildedgames.aether.registry.equipment;

import com.gildedgames.aether.items.properties.ItemEquipmentType;
import com.gildedgames.aether.items.properties.ItemRarity;
import net.minecraft.item.Item;

public interface IEquipmentRegistry
{
	void register(Item item, ItemRarity rarity, ItemEquipmentType type);

	IEquipmentProperties getProperties(Item item);
}
