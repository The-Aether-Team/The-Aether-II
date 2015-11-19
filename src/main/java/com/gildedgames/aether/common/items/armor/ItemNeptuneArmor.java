package com.gildedgames.aether.common.items.armor;

import net.minecraft.item.ItemStack;

public class ItemNeptuneArmor extends ItemAetherArmor
{
	public ItemNeptuneArmor(ArmorMaterial material, int armorType)
	{
		super(material, "neptune", armorType);
	}
	@Override
	public boolean getIsRepairable(ItemStack target, ItemStack stack)
	{
		return false;
	}
}
