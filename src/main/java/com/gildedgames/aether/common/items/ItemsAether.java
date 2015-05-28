package com.gildedgames.aether.common.items;

import com.gildedgames.aether.common.AetherCreativeTabs;
import com.gildedgames.aether.common.items.armor.EnumAetherArmorMaterial;
import com.gildedgames.aether.common.items.armor.ItemAetherArmor;
import com.gildedgames.aether.common.items.tools.EnumAetherToolMaterial;
import com.gildedgames.aether.common.items.tools.ItemAetherAxe;
import com.gildedgames.aether.common.items.tools.ItemAetherPickaxe;
import com.gildedgames.aether.common.items.tools.ItemAetherShovel;
import com.gildedgames.aether.common.items.tools.ItemAetherSword;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemsAether
{
	public static Item skyroot_stick;

	public static Item ambrosium_shard, continuum_orb, zanite_gemstone;

	public static Item skyroot_axe, holystone_axe, zanite_axe, gravitite_axe;

	public static Item skyroot_pickaxe, holystone_pickaxe, zanite_pickaxe, gravitite_pickaxe;

	public static Item skyroot_shovel, holystone_shovel, zanite_shovel, gravitite_shovel;

	public static Item skyroot_sword, holystone_sword, zanite_sword, gravitite_sword;

	public static Item zanite_helmet, zanite_chestplate, zanite_leggings, zanite_boots;

	public static Item gravitite_helmet, gravitite_chestplate, gravitite_leggings, gravitite_boots;

	public static Item obsidian_helmet, obsidian_chestplate, obsidian_leggings, obsidian_boots;

	public static Item neptune_helmet, neptune_chestplate, neptune_leggings, neptune_boots;

	public static Item phoenix_helmet, phoenix_chestplate, phoenix_leggings, phoenix_boots;

	public static void preInit()
	{
		skyroot_stick = registerItem("skyroot_stick", new Item().setCreativeTab(AetherCreativeTabs.tabMaterials));
		ambrosium_shard = registerItem("ambrosium_shard", new Item().setCreativeTab(AetherCreativeTabs.tabMaterials));
		continuum_orb = registerItem("continuum_orb", new Item().setCreativeTab(AetherCreativeTabs.tabMaterials));
		zanite_gemstone = registerItem("zanite_gemstone", new Item().setCreativeTab(AetherCreativeTabs.tabMaterials));

		skyroot_axe = registerItem("skyroot_axe", new ItemAetherAxe(EnumAetherToolMaterial.SKYROOT));
		skyroot_pickaxe = registerItem("skyroot_pickaxe", new ItemAetherPickaxe(EnumAetherToolMaterial.SKYROOT));
		skyroot_shovel = registerItem("skyroot_shovel", new ItemAetherShovel(EnumAetherToolMaterial.SKYROOT));
		skyroot_sword = registerItem("skyroot_sword", new ItemAetherSword(EnumAetherToolMaterial.SKYROOT));

		holystone_axe = registerItem("holystone_axe", new ItemAetherAxe(EnumAetherToolMaterial.HOLYSTONE));
		holystone_pickaxe = registerItem("holystone_pickaxe", new ItemAetherPickaxe(EnumAetherToolMaterial.HOLYSTONE));
		holystone_shovel = registerItem("holystone_shovel", new ItemAetherShovel(EnumAetherToolMaterial.HOLYSTONE));
		holystone_sword = registerItem("holystone_sword", new ItemAetherSword(EnumAetherToolMaterial.HOLYSTONE));

		zanite_axe = registerItem("zanite_axe", new ItemAetherAxe(EnumAetherToolMaterial.ZANITE));
		zanite_pickaxe = registerItem("zanite_pickaxe", new ItemAetherPickaxe(EnumAetherToolMaterial.ZANITE));
		zanite_shovel = registerItem("zanite_shovel", new ItemAetherShovel(EnumAetherToolMaterial.ZANITE));
		zanite_sword = registerItem("zanite_sword", new ItemAetherSword(EnumAetherToolMaterial.ZANITE));

		gravitite_axe = registerItem("gravitite_axe", new ItemAetherAxe(EnumAetherToolMaterial.GRAVITITE));
		gravitite_pickaxe = registerItem("gravitite_pickaxe", new ItemAetherPickaxe(EnumAetherToolMaterial.GRAVITITE));
		gravitite_shovel = registerItem("gravitite_shovel", new ItemAetherShovel(EnumAetherToolMaterial.GRAVITITE));
		gravitite_sword = registerItem("gravitite_sword", new ItemAetherSword(EnumAetherToolMaterial.GRAVITITE));

		zanite_helmet = registerItem("zanite_helmet", new ItemAetherArmor(EnumAetherArmorMaterial.ZANITE, 0, 0));
		zanite_chestplate = registerItem("zanite_chestplate", new ItemAetherArmor(EnumAetherArmorMaterial.ZANITE, 0, 1));
		zanite_leggings = registerItem("zanite_leggings", new ItemAetherArmor(EnumAetherArmorMaterial.ZANITE, 0, 2));
		zanite_boots = registerItem("zanite_boots", new ItemAetherArmor(EnumAetherArmorMaterial.ZANITE, 0, 3));

		gravitite_helmet = registerItem("gravitite_helmet", new ItemAetherArmor(EnumAetherArmorMaterial.GRAVITITE, 0, 0));
		gravitite_chestplate = registerItem("gravitite_chestplate", new ItemAetherArmor(EnumAetherArmorMaterial.GRAVITITE, 0, 1));
		gravitite_leggings = registerItem("gravitite_leggings", new ItemAetherArmor(EnumAetherArmorMaterial.GRAVITITE, 0, 2));
		gravitite_boots = registerItem("gravitite_boots", new ItemAetherArmor(EnumAetherArmorMaterial.GRAVITITE, 0, 3));

		obsidian_helmet = registerItem("obsidian_helmet", new ItemAetherArmor(EnumAetherArmorMaterial.OBSIDIAN, 0, 0));
		obsidian_chestplate = registerItem("obsidian_chestplate", new ItemAetherArmor(EnumAetherArmorMaterial.OBSIDIAN, 0, 1));
		obsidian_leggings = registerItem("obsidian_leggings", new ItemAetherArmor(EnumAetherArmorMaterial.OBSIDIAN, 0, 2));
		obsidian_boots = registerItem("obsidian_boots", new ItemAetherArmor(EnumAetherArmorMaterial.OBSIDIAN, 0, 3));

		neptune_helmet = registerItem("neptune_helmet", new ItemAetherArmor(EnumAetherArmorMaterial.NEPTUNE, 0, 0));
		neptune_chestplate = registerItem("neptune_chestplate", new ItemAetherArmor(EnumAetherArmorMaterial.NEPTUNE, 0, 1));
		neptune_leggings = registerItem("neptune_leggings", new ItemAetherArmor(EnumAetherArmorMaterial.NEPTUNE, 0, 2));
		neptune_boots = registerItem("neptune_boots", new ItemAetherArmor(EnumAetherArmorMaterial.NEPTUNE, 0, 3));
		
		phoenix_helmet = registerItem("phoenix_helmet", new ItemAetherArmor(EnumAetherArmorMaterial.PHOENIX, 0, 0));
		phoenix_chestplate = registerItem("phoenix_chestplate", new ItemAetherArmor(EnumAetherArmorMaterial.PHOENIX, 0, 1));
		phoenix_leggings = registerItem("phoenix_leggings", new ItemAetherArmor(EnumAetherArmorMaterial.PHOENIX, 0, 2));
		phoenix_boots = registerItem("phoenix_boots", new ItemAetherArmor(EnumAetherArmorMaterial.PHOENIX, 0, 3));
	}

	private static Item registerItem(String name, Item item)
	{
		item.setUnlocalizedName(name);
		GameRegistry.registerItem(item, name);

		return item;
	}
}
