package com.gildedgames.aether.items.armor;

import net.minecraft.item.ItemArmor.ArmorMaterial;

public enum EnumAetherArmorMaterial
{
	ZANITE
	{
		@Override
		public ArmorMaterial getArmorMaterial()
		{
			return ArmorMaterial.IRON;
		}

		@Override
		public String getArmorResourceLocation(int armorType)
		{
			return armorType == 2 ? "aether:textures/armor/zanite_layer_2.png" : "aether:textures/armor/zanite_layer_1.png";
		}
	},

	GRAVITITE
	{
		@Override
		public ArmorMaterial getArmorMaterial()
		{
			return ArmorMaterial.DIAMOND;
		}

		@Override
		public String getArmorResourceLocation(int armorType)
		{
			return armorType == 2 ? "aether:textures/armor/gravitite_layer_2.png" : "aether:textures/armor/gravitite_layer_1.png";
		}
	};

	abstract ArmorMaterial getArmorMaterial();

	abstract String getArmorResourceLocation(int armorType);
}
