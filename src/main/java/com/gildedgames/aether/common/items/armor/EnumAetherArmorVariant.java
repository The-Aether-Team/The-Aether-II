package com.gildedgames.aether.common.items.armor;

import com.gildedgames.aether.common.Aether;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;

public enum EnumAetherArmorVariant
{
	ZANITE, GRAVITITE, OBSIDIAN, NEPTUNE, PHOENIX, VALKYRIE;

	public String getResourceForSlot(int layerIndex)
	{
		return Aether.getResource("textures/armor/" + this.name().toLowerCase() + "_layer_" + (layerIndex == 2 ? 2 : 1) + ".png");
	}

	public ArmorMaterial getArmorMaterial()
	{
		switch (this)
		{
		case ZANITE:
			return ArmorMaterial.IRON;
		case GRAVITITE:
			return ArmorMaterial.DIAMOND;
		case OBSIDIAN:
			return AetherArmorMaterials.OBSIDIAN;
		case NEPTUNE:
			return ArmorMaterial.DIAMOND;
		case PHOENIX:
			return ArmorMaterial.DIAMOND;
		case VALKYRIE:
			return AetherArmorMaterials.VALKYRIE;
		default:
			return ArmorMaterial.LEATHER;
		}
	}

	public Item getRepairItem()
	{
		switch (this)
		{
		case ZANITE:
			return ItemsAether.zanite_gemstone;
		case OBSIDIAN:
			return Item.getItemFromBlock(Blocks.obsidian);
		default:
			return null;
		}
	}
}
