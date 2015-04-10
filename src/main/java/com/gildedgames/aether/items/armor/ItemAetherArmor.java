package com.gildedgames.aether.items.armor;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.AetherCreativeTabs;

public class ItemAetherArmor extends ItemArmor
{

	public ItemAetherArmor(EnumAetherArmorMaterial material, int renderIndex, int armorType)
	{
		super(material.getArmorMaterial(), renderIndex, armorType);

		this.setCreativeTab(AetherCreativeTabs.tabArmor);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		if (stack.getItem() == Aether.getItems().zanite_helmet || stack.getItem() == Aether.getItems().zanite_chestplate || stack.getItem() == Aether.getItems().zanite_boots)
		{
			return "aether:textures/armor/Zanite_1.png";
		}
		if (stack.getItem() == Aether.getItems().zanite_leggings)
		{
			return "aether:textures/armor/Zanite_2.png";
		}
		if (stack.getItem() == Aether.getItems().gravitite_helmet || stack.getItem() == Aether.getItems().gravitite_chestplate || stack.getItem() == Aether.getItems().gravitite_boots)
		{
			return "aether:textures/armor/Gravitite_1.png";
		}
		if (stack.getItem() == Aether.getItems().gravitite_leggings)
		{
			return "aether:textures/armor/Gravitite_2.png";
		}
		return "aether:textures/armor/Zanite_1.png";
	}

}
