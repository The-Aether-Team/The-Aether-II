package com.gildedgames.aether.common.items.armor;

import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

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
			},

	OBSIDIAN(EnumHelper.addArmorMaterial("AETHER_OBSIDIAN", "", 33, new int[] { 2, 6, 5, 2 }, 8))
		    {
				@Override
				public String getArmorResourceLocation(int armorType)
				{
					return armorType == 2 ? "aether:textures/armor/obsidian_layer_2.png" : "aether:textures/armor/obsidian_layer_1.png";
				}
		    };

	private final ArmorMaterial armorMaterial;

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
