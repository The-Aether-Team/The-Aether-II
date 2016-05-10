package com.gildedgames.aether.common.items.properties;

import com.gildedgames.aether.items.properties.ItemEquipmentType;
import com.gildedgames.aether.items.properties.ItemRarity;
import com.gildedgames.aether.registry.equipment.IEquipmentProperties;
import com.gildedgames.aether.registry.equipment.IEquipmentRegistry;
import net.minecraft.item.Item;

import java.util.HashMap;

public class EquipmentRegistry implements IEquipmentRegistry
{
	private final HashMap<String, IEquipmentProperties> registeredEntries = new HashMap<>();

	@Override
	public void register(Item item, ItemRarity rarity, ItemEquipmentType type)
	{
		this.registeredEntries.put(item.getRegistryName(), new EquipmentProperties(item, rarity, type));
	}

	@Override
	public IEquipmentProperties getProperties(Item item)
	{
		return this.registeredEntries.get(item.getRegistryName());
	}

	public static class EquipmentProperties implements IEquipmentProperties
	{
		private Item item;

		private ItemRarity rarity;

		private ItemEquipmentType equipmentType;

		public EquipmentProperties(Item item, ItemRarity rarity, ItemEquipmentType equipmentType)
		{
			this.item = item;
			this.rarity = rarity;
			this.equipmentType = equipmentType;
		}

		@Override
		public Item getItem()
		{
			return this.item;
		}

		@Override
		public ItemRarity getRarity()
		{
			return this.rarity;
		}

		@Override
		public ItemEquipmentType getEquipmentType()
		{
			return this.equipmentType;
		}
	}
}
