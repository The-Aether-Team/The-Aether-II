package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.common.registry.content.MaterialsAether;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ItemPhoenixArmor extends ItemAetherArmor
{
	public ItemPhoenixArmor(EntityEquipmentSlot armorType)
	{
		super(MaterialsAether.PHOENIX_ARMOR, "phoenix", armorType);
	}
}
