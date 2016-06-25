package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemGravititeArmor extends ItemAetherArmor
{
	public ItemGravititeArmor(EntityEquipmentSlot armorType)
	{
		super(ArmorMaterial.DIAMOND, "gravitite", armorType);
	}

	@Override
	public boolean getIsRepairable(ItemStack target, ItemStack stack)
	{
		return stack.getItem() == Item.getItemFromBlock(BlocksAether.enchanted_gravitite);
	}
}
