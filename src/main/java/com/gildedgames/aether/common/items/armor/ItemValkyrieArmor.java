package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.common.registry.minecraft.MaterialsAether;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ItemValkyrieArmor extends ItemAetherArmor
{
	public ItemValkyrieArmor(EntityEquipmentSlot slot)
	{
		super(MaterialsAether.VALKYRIE_ARMOR, "valkyrie", slot);
	}
}
