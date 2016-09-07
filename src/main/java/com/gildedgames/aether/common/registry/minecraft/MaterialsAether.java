package com.gildedgames.aether.common.registry.minecraft;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class MaterialsAether
{
	public static ArmorMaterial VALKYRIE_ARMOR;

	public static ArmorMaterial NEPTUNE_ARMOR;

	public static ArmorMaterial PHOENIX_ARMOR;

	public static ArmorMaterial LEGENDARY_ARMOR;

	public static ToolMaterial LEGENDARY_TOOL;

	public static ToolMaterial ARKENIUM_TOOL;

	public static void preInit()
	{
		VALKYRIE_ARMOR = EnumHelper.addArmorMaterial("aether_valkyrie", "", 33, new int[] { 2, 5, 6, 2 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.5F);

		NEPTUNE_ARMOR = EnumHelper.addArmorMaterial("aether_neptune", "", 33, new int[] { 2, 5, 6, 2 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.5F);

		PHOENIX_ARMOR = EnumHelper.addArmorMaterial("aether_phoenix", "", 33, new int[] { 2, 5, 6, 2 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.5F);

		LEGENDARY_ARMOR = EnumHelper.addArmorMaterial("aether_legendary_armor", "", 30, new int[] { 2, 6, 5, 2 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 1.0F);

		LEGENDARY_TOOL = EnumHelper.addToolMaterial("aether_legendary", 3, 1000, 8.0F, 2.0F, 14);

		ARKENIUM_TOOL = EnumHelper.addToolMaterial("aether_arkenium", 2, 3192, 5.5F, 4.0F, 14);
	}
}
