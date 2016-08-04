package com.gildedgames.aether.common.items.armor;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ItemNeptuneArmor extends ItemAetherArmor
{
	public ItemNeptuneArmor(EntityEquipmentSlot armorType)
	{
		super(ArmorMaterial.DIAMOND, "neptune", armorType);
	}

	@Override
	public boolean getIsRepairable(ItemStack target, ItemStack stack)
	{
		return false;
	}
}
