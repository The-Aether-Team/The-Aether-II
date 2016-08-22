package com.gildedgames.aether.api.registry.equipment;

import com.gildedgames.aether.api.capabilites.items.properties.ItemEquipmentType;
import com.gildedgames.aether.api.capabilites.items.properties.ItemRarity;

public interface IEquipmentProperties
{
	ItemEquipmentType getEquipmentType();

	ItemRarity getRarity();
}
