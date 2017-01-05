package com.gildedgames.aether.api.items;

import com.gildedgames.aether.api.capabilites.items.properties.ItemRarity;
import com.gildedgames.aether.api.items.equipment.IEquipmentProperties;

public class ItemPropertiesBuilder
{
	private IEquipmentProperties equipment;

	private ItemRarity rarity;

	public ItemPropertiesBuilder setRarity(ItemRarity rarity)
	{
		this.rarity = rarity;

		return this;
	}

	public ItemPropertiesBuilder setEquipmentProperties(IEquipmentProperties equipment)
	{
		this.equipment = equipment;

		return this;
	}

	public IItemProperties build()
	{
		return new ItemProperties(this.equipment, this.rarity);
	}
}
