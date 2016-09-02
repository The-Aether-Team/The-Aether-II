package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.common.capabilities.item.properties.IPhoenixChillable;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemPhoenixArmor extends ItemAetherArmor
{
	public ItemPhoenixArmor(EntityEquipmentSlot armorType)
	{
		super(ArmorMaterial.DIAMOND, "phoenix", armorType);
	}
}
