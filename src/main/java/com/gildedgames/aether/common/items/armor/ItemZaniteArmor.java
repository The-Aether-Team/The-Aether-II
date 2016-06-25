package com.gildedgames.aether.common.items.armor;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ItemZaniteArmor extends ItemAetherArmor
{
	public ItemZaniteArmor(EntityEquipmentSlot armorType)
	{
		super(ArmorMaterial.IRON, "zanite", armorType);
	}

	@Override
	public float getExtraDamageReduction(ItemStack stack)
	{
		return ((float) stack.getItemDamage() / (float) stack.getMaxDamage()) * 0.8f;
	}
}
