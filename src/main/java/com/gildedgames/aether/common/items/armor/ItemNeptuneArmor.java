package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.common.registry.minecraft.MaterialsAether;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ItemNeptuneArmor extends ItemAetherArmor
{
	public ItemNeptuneArmor(EntityEquipmentSlot armorType)
	{
		super(MaterialsAether.NEPTUNE_ARMOR, "neptune", armorType);
	}

	@Override
	public boolean getIsRepairable(ItemStack target, ItemStack stack)
	{
		return false;
	}
}
