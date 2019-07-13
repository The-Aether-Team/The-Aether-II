package com.gildedgames.aether.common.items.armor;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemZaniteArmor extends ItemAetherArmor
{
	public ItemZaniteArmor(EquipmentSlotType slot, Item.Properties properties)
	{
		super(ArmorMaterial.IRON, slot, properties);
	}

	@Override
	public float getExtraDamageReduction(ItemStack stack)
	{
		return ((float) stack.getDamage() / (float) stack.getMaxDamage()) * 0.8f;
	}
}
