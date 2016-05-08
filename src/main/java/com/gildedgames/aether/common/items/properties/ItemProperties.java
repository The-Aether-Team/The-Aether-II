package com.gildedgames.aether.common.items.properties;

import com.gildedgames.aether.common.items.ItemEquipmentType;
import com.gildedgames.aether.common.items.ItemRarity;
import com.google.common.collect.Lists;
import net.minecraft.item.Item;

import java.util.List;

public class ItemProperties implements ItemPropertiesBase
{
	public static class RegistrationEntry
	{
		private Item item;

		private ItemRarity rarity;

		private ItemEquipmentType equipmentType;

		public RegistrationEntry(Item item, ItemRarity rarity, ItemEquipmentType equipmentType)
		{
			this.item = item;
			this.rarity = rarity;
			this.equipmentType = equipmentType;
		}

		public Item getItem()
		{
			return this.item;
		}

		public ItemRarity getRarity()
		{
			return this.rarity;
		}

		public ItemEquipmentType getEquipmentType()
		{
			return this.equipmentType;
		}

	}

	private static List<RegistrationEntry> registeredEntries = Lists.newArrayList();

	private ItemEquipmentType equipmentType;

	private ItemRarity rarity;

	public ItemProperties(ItemRarity rarity, ItemEquipmentType equipmentType)
	{
		this.rarity = rarity;
		this.equipmentType = equipmentType;
	}

	public static List<RegistrationEntry> getRegistrationEntries()
	{
		return ItemProperties.registeredEntries;
	}

	public static void register(Item item, ItemRarity rarity)
	{
		ItemProperties.register(item, rarity, null);
	}

	public static void register(Item item, ItemRarity rarity, ItemEquipmentType type)
	{
		ItemProperties.registeredEntries.add(new RegistrationEntry(item, rarity, type));
	}

	@Override
	public void setEquipmentType(ItemEquipmentType type)
	{
		this.equipmentType = type;
	}

	@Override
	public ItemEquipmentType getEquipmentType()
	{
		return this.equipmentType;
	}

	@Override
	public ItemRarity getRarity()
	{
		return this.rarity;
	}

	@Override
	public void setRarity(ItemRarity rarity)
	{
		this.rarity = rarity;
	}

	@Override
	public boolean isEquippable()
	{
		return this.equipmentType != null;
	}
}

