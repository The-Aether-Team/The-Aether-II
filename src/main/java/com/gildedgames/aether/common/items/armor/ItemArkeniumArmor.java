package com.gildedgames.aether.common.items.armor;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ItemArkeniumArmor extends ItemAetherArmor
{
	public ItemArkeniumArmor(EntityEquipmentSlot armorType)
	{
		super(ArmorMaterial.DIAMOND, "arkenium", armorType);
	}

}
