package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.common.capabilities.item.properties.IPhoenixChillable;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.registry.minecraft.MaterialsAether;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemPhoenixArmor extends ItemAetherArmor
{
	public ItemPhoenixArmor(EntityEquipmentSlot armorType)
	{
		super(MaterialsAether.PHOENIX_ARMOR, "phoenix", armorType);
	}
}
