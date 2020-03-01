package com.gildedgames.aether.common.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public class MaterialsAether
{
	public static final ArmorMaterial VALKYRIE_ARMOR = EnumHelper.addArmorMaterial("AETHER_VALKYRIE", "", 33, new int[] { 2, 5, 6,
			2 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.5F);

	public static final ArmorMaterial NEPTUNE_ARMOR = EnumHelper.addArmorMaterial("AETHER_NEPTUNE", "", 33, new int[] { 2, 5, 6,
			2 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.5F);

	public static final ArmorMaterial PHOENIX_ARMOR = EnumHelper.addArmorMaterial("AETHER_PHOENIX", "", 33, new int[] { 2, 5, 6,
			2 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.5F);

	public static final ArmorMaterial LEGENDARY_ARMOR = EnumHelper.addArmorMaterial("AETHER_LEGENDARY", "", 30, new int[] { 2, 6, 5,
			2 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 1.0F);

	public static final ArmorMaterial ARKENIUM_ARMOR = EnumHelper.addArmorMaterial("AETHER_ARKENIUM", "", 44,
			new int[] { 3, 6, 8, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F);

	public static final ArmorMaterial WINTER_ARMOR = EnumHelper.addArmorMaterial("winter", "", 8,
			new int[] { 0, 0, 0, 0 }, 25, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0F).setRepairItem(new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE));

	public static final ToolMaterial LEGENDARY_TOOL = EnumHelper.addToolMaterial("AETHER_LEGENDARY", 3, 1000, 8.0F, 2.0F, 14);

	public static final ToolMaterial SKYROOT_TOOL = EnumHelper.addToolMaterial("AETHER_SKYROOT", 0, 59, 2.0F, 0.0F, 15);

	public static final ToolMaterial HOLYSTONE_TOOL = EnumHelper.addToolMaterial("AETHER_HOLYSTONE", 1, 131, 4.0F, 1.0F, 5);

	public static final ToolMaterial ARKENIUM_TOOL = EnumHelper.addToolMaterial("AETHER_ARKENIUM", 2, 3192, 4.0F, 4.0F, 14);

	public static final ToolMaterial ZANITE_TOOL = EnumHelper.addToolMaterial("AETHER_ZANITE", 2, 250, 6.0F, 2.0F, 14);

	public static final ToolMaterial GRAVITITE_TOOL = EnumHelper.addToolMaterial("AETHER_GRAVITITE", 3, 1561, 8.0F, 3.0F, 10);
}
