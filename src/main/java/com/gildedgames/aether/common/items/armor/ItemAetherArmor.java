package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.common.AetherCreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemAetherArmor extends ItemArmor
{
	private EnumAetherArmorMaterial armorMaterial;

	public ItemAetherArmor(EnumAetherArmorMaterial material, int renderIndex, int armorType)
	{
		super(material.getArmorMaterial(), renderIndex, armorType);

		this.setCreativeTab(AetherCreativeTabs.tabArmor);
		this.armorMaterial = material;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		return this.armorMaterial.getArmorResourceLocation(slot);
	}

}
