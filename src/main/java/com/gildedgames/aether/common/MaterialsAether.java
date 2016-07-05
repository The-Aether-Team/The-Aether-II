package com.gildedgames.aether.common;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class MaterialsAether
{
	public static ArmorMaterial OBSIDIAN_ARMOR;

	public static ArmorMaterial VALKYRIE_ARMOR;

	public static ArmorMaterial LEGENDARY_ARMOR;

	public static ToolMaterial LEGENDARY_TOOL;

	public static ToolMaterial ARKENIUM_TOOL;

	public static void preInit()
	{
		OBSIDIAN_ARMOR = EnumHelper.addArmorMaterial("aether_obsidian", "", 33, new int[] { 4, 7, 6, 2 }, 8, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);

		VALKYRIE_ARMOR = EnumHelper.addArmorMaterial("aether_valkyrie", "", 30, new int[] { 2, 6, 5, 2 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.5F);

		LEGENDARY_ARMOR = EnumHelper.addArmorMaterial("aether_legendary_armor", "", 30, new int[] { 2, 6, 5, 2 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 1.0F);

		LEGENDARY_TOOL = EnumHelper.addToolMaterial("aether_legendary", 3, 1000, 8.0F, 2.0F, 14);

		ARKENIUM_TOOL = EnumHelper.addToolMaterial("aether_arkenium", 2, 3192, 5.5F, 4.0F, 14);
	}
}
