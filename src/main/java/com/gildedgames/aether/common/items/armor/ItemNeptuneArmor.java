package com.gildedgames.aether.common.items.armor;

import net.minecraft.item.ItemStack;

public class ItemNeptuneArmor extends ItemAetherArmor
{
	public ItemNeptuneArmor(int armorType)
	{
		super(ArmorMaterial.DIAMOND, "neptune", armorType);
	}
	@Override
	public boolean getIsRepairable(ItemStack target, ItemStack stack)
	{
		return false;
	}
}
