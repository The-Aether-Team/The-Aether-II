package com.gildedgames.aether.common;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class AetherMaterials
{
	public static ArmorMaterial OBSIDIAN_ARMOR;

	public static ArmorMaterial VALKYRIE_ARMOR;

	public static ToolMaterial LEGENDARY_TOOL;

	public static void preInit()
	{
		OBSIDIAN_ARMOR = EnumHelper.addArmorMaterial("aether_obsidian", "", 33, new int[] { 2, 6, 5, 2 }, 8);

		VALKYRIE_ARMOR = EnumHelper.addArmorMaterial("aether_valkyrie", "", 30, new int[] { 2, 6, 5, 2 }, 10);

		LEGENDARY_TOOL = EnumHelper.addToolMaterial("aether_legendary", 2, 1000, 8.0F, 2.0F, 14);
	}
}
