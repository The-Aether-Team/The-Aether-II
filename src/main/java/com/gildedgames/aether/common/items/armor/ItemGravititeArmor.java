package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.api.registrar.BlocksAether;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemGravititeArmor extends ItemAetherArmor
{
	public ItemGravititeArmor(EntityEquipmentSlot armorType)
	{
		super(ArmorMaterial.DIAMOND, "gravitite", armorType);
	}
}
