package com.gildedgames.aether.items.armor;

import net.minecraft.item.ItemArmor.ArmorMaterial;

public enum EnumAetherArmorMaterial
{
	Zanite
	{
		@Override
		public ArmorMaterial getArmorMaterial()
		{
			return ArmorMaterial.IRON;
		}

		@Override
		public String getArmorResourceLocation(int armorType)
		{
			return armorType == 2 ? "aether:textures/armor/Zanite_2.png" : "aether:textures/armor/Zanite_1.png";
		}
	},

	Gravitite
	{
		@Override
		public ArmorMaterial getArmorMaterial()
		{
			return ArmorMaterial.DIAMOND;
		}

		@Override
		public String getArmorResourceLocation(int armorType)
		{
			return armorType == 2 ? "aether:textures/armor/Gravitite_2.png" : "aether:textures/armor/Gravitite_1.png";
		}
	};

	public abstract ArmorMaterial getArmorMaterial();

	public abstract String getArmorResourceLocation(int armorType);
}
