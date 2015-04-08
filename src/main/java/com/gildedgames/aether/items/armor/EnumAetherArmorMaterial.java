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
	},

	Gravitite
	{
		@Override
		public ArmorMaterial getArmorMaterial()
		{
			return ArmorMaterial.DIAMOND;
		}
	};

	public abstract ArmorMaterial getArmorMaterial();
}
