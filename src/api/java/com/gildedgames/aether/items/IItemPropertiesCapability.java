package com.gildedgames.aether.items;

import com.gildedgames.aether.items.properties.ItemEquipmentType;
import com.gildedgames.aether.items.properties.ItemRarity;
import com.gildedgames.aether.registry.equipment.IEquipmentProperties;
import net.minecraft.item.ItemStack;

public interface IItemPropertiesCapability
{
	IEquipmentProperties getProperties();

	ItemRarity getRarity();

	ItemEquipmentType getEquipmentType();

	ItemStack getStack();

	boolean isEquippable();
}
