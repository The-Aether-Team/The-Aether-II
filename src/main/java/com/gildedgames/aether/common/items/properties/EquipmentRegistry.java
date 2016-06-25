package com.gildedgames.aether.common.items.properties;

import com.gildedgames.aether.api.items.properties.ItemEquipmentType;
import com.gildedgames.aether.api.items.properties.ItemRarity;
import com.gildedgames.aether.api.registry.equipment.IEquipmentProperties;
import com.gildedgames.aether.api.registry.equipment.IEquipmentRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class EquipmentRegistry implements IEquipmentRegistry
{
	private final HashMap<ResourceLocation, IEquipmentProperties> registeredEntries = new HashMap<>();

	@Override
	public void register(Item item, ItemRarity rarity, ItemEquipmentType type)
	{
		this.registeredEntries.put(item.getRegistryName(), new EquipmentProperties(rarity, type));
	}

	@Override
	public IEquipmentProperties getProperties(Item item)
	{
		return this.registeredEntries.get(item.getRegistryName());
	}

	public static class EquipmentProperties implements IEquipmentProperties
	{
		private ItemRarity rarity;

		private ItemEquipmentType equipmentType;

		public EquipmentProperties(ItemRarity rarity, ItemEquipmentType equipmentType)
		{
			this.rarity = rarity;
			this.equipmentType = equipmentType;
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
