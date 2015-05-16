package com.gildedgames.aether.items;

import com.gildedgames.aether.AetherCreativeTabs;
import com.gildedgames.aether.items.armor.EnumAetherArmorMaterial;
import com.gildedgames.aether.items.armor.ItemAetherArmor;
import com.gildedgames.aether.items.tools.EnumAetherToolMaterial;
import com.gildedgames.aether.items.tools.ItemAetherAxe;
import com.gildedgames.aether.items.tools.ItemAetherPickaxe;
import com.gildedgames.aether.items.tools.ItemAetherShovel;
import com.gildedgames.aether.items.tools.ItemAetherSword;
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
	}

	private static Item registerItem(String name, Item item)
	{
		item.setUnlocalizedName(name);
		GameRegistry.registerItem(item, name);

		return item;
	}
}
