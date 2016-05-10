package com.gildedgames.aether.registry.equipment;

import com.gildedgames.aether.items.properties.ItemEquipmentType;
import com.gildedgames.aether.items.properties.ItemRarity;
import net.minecraft.item.Item;

public interface IEquipmentProperties
{
	Item getItem();

	ItemEquipmentType getEquipmentType();

	ItemRarity getRarity();
}
