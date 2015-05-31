package com.gildedgames.aether.common.items.armor;

import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class AetherArmorMaterials
{
	public static ArmorMaterial OBSIDIAN;

	public static ArmorMaterial VALKYRIE;

	public static void preInit()
	{
		OBSIDIAN = EnumHelper.addArmorMaterial("aether_obsidian", "", 33, new int[] { 2, 6, 5, 2 }, 8);

		VALKYRIE = EnumHelper.addArmorMaterial("aether_valkyrie", "", 30, new int[] { 2, 6, 5, 2 }, 10);
	}
}
