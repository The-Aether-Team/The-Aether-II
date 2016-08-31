package com.gildedgames.aether.api.registry.equipment;

import com.gildedgames.aether.api.capabilites.entity.properties.ElementalDamageSource;
import com.gildedgames.aether.api.capabilites.items.properties.ItemEquipmentType;
import com.gildedgames.aether.api.capabilites.items.properties.ItemRarity;

import java.util.List;

public interface IEquipmentProperties
{
	ItemEquipmentType getEquipmentType();

	ItemRarity getRarity();
}
