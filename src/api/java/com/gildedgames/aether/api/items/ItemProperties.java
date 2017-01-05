package com.gildedgames.aether.api.items;

import com.gildedgames.aether.api.capabilites.items.properties.ItemRarity;
import com.gildedgames.aether.api.items.equipment.IEquipmentProperties;

import java.util.Optional;

/**
 * Stateless, immutable container for item information.
 */
public class ItemProperties implements IItemProperties
{
	private final IEquipmentProperties equipment;

	private final ItemRarity rarity;

	public ItemProperties(IEquipmentProperties equipment, ItemRarity rarity)
	{
		this.equipment = equipment;
		this.rarity = rarity;
	}

	public Optional<IEquipmentProperties> getEquipmentProperties()
	{
		return Optional.ofNullable(this.equipment);
	}

	public Optional<ItemRarity> getRarity()
	{
		return Optional.ofNullable(this.rarity);
	}

	public static ItemPropertiesBuilder builder()
	{
		return new ItemPropertiesBuilder();
	}
}
