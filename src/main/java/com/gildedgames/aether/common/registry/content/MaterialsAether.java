package com.gildedgames.aether.common.registry.content;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class MaterialsAether
{
	public static final ArmorMaterial VALKYRIE_ARMOR = EnumHelper.addArmorMaterial("aether_valkyrie", "", 33, new int[] { 2, 5, 6, 2 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.5F);

	public static final ArmorMaterial NEPTUNE_ARMOR = EnumHelper.addArmorMaterial("aether_neptune", "", 33, new int[] { 2, 5, 6, 2 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.5F);

	public static final ArmorMaterial PHOENIX_ARMOR = EnumHelper.addArmorMaterial("aether_phoenix", "", 33, new int[] { 2, 5, 6, 2 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.5F);

	public static final ArmorMaterial LEGENDARY_ARMOR = EnumHelper.addArmorMaterial("aether_legendary_armor", "", 30, new int[] { 2, 6, 5, 2 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 1.0F);

	public static final ToolMaterial LEGENDARY_TOOL = EnumHelper.addToolMaterial("aether_legendary", 3, 1000, 8.0F, 2.0F, 14);

	public static final ToolMaterial SKYROOT_TOOL = EnumHelper.addToolMaterial("aether_skyroot", 0, 59, 2.0F, 0.0F, 15);

	public static final ToolMaterial HOLYSTONE_TOOL = EnumHelper.addToolMaterial("aether_holystone", 2, 131, 4.0F, 1.0F, 5);

	public static final ToolMaterial ARKENIUM_TOOL = EnumHelper.addToolMaterial("aether_arkenium", 1, 3192, 5.5F, 4.0F, 14);

	public static final ToolMaterial ZANITE_TOOL = EnumHelper.addToolMaterial("aether_zanite", 2, 250, 6.0F, 2.0F, 14);

	public static final ToolMaterial GRAVITITE_TOOL = EnumHelper.addToolMaterial("aether_gravitite", 3, 1561, 8.0F, 3.0F, 10);
}
