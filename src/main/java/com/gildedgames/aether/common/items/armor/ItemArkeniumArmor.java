package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.common.init.MaterialsAether;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ItemArkeniumArmor extends ItemAetherArmor
{
	public ItemArkeniumArmor(EntityEquipmentSlot armorType)
	{
		super(MaterialsAether.ARKENIUM_ARMOR, "arkenium", armorType);
	}
}
