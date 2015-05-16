package com.gildedgames.aether.items.armor;

import net.minecraft.item.ItemArmor.ArmorMaterial;

public enum EnumAetherArmorMaterial
{
	ZANITE(ArmorMaterial.IRON)
			{
				@Override
				public String getArmorResourceLocation(int armorType)
				{
					return armorType == 2 ? "aether:textures/armor/zanite_layer_2.png" : "aether:textures/armor/zanite_layer_1.png";
				}
			},

	GRAVITITE(ArmorMaterial.DIAMOND)
			{
				@Override
				public String getArmorResourceLocation(int armorType)
				{
					return armorType == 2 ? "aether:textures/armor/gravitite_layer_2.png" : "aether:textures/armor/gravitite_layer_1.png";
				}
			};

	ArmorMaterial armorMaterial;

	EnumAetherArmorMaterial(ArmorMaterial armorMaterial)
	{
		this.armorMaterial = armorMaterial;
	}

	public ArmorMaterial getArmorMaterial()
	{
		return this.armorMaterial;
	}

	abstract String getArmorResourceLocation(int armorType);
}
