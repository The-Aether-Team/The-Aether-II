package com.gildedgames.aether.common.items.armor;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;

public class ItemArkeniumArmor extends ItemAetherArmor
{
	public ItemArkeniumArmor(EquipmentSlotType slot, Item.Properties properties)
	{
		super(ArmorMaterial.DIAMOND, slot, properties);
	}

}
