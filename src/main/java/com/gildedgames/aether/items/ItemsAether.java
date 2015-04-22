package com.gildedgames.aether.items;

import com.gildedgames.aether.Aether;
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
	public Item skyroot_stick;

	public Item ambrosium_shard, continuum_orb, zanite_gemstone;

	public Item skyroot_axe, holystone_axe, zanite_axe, gravitite_axe;

	public Item skyroot_pickaxe, holystone_pickaxe, zanite_pickaxe, gravitite_pickaxe;

	public Item skyroot_shovel, holystone_shovel, zanite_shovel, gravitite_shovel;

	public Item skyroot_sword, holystone_sword, zanite_sword, gravitite_sword;

	public Item zanite_helmet, zanite_chestplate, zanite_leggings, zanite_boots;

	public Item gravitite_helmet, gravitite_chestplate, gravitite_leggings, gravitite_boots;

	public void preInit()
	{

		this.skyroot_stick = this.registerItem("skyroot_stick", new Item().setCreativeTab(Aether.getCreativeTabs().tabMaterials));
		this.ambrosium_shard = this.registerItem("ambrosium_shard", new Item().setCreativeTab(Aether.getCreativeTabs().tabMaterials));
		this.continuum_orb = this.registerItem("continuum_orb", new Item().setCreativeTab(Aether.getCreativeTabs().tabMaterials));
		this.zanite_gemstone = this.registerItem("zanite_gemstone", new Item().setCreativeTab(Aether.getCreativeTabs().tabMaterials));

		this.skyroot_axe = this.registerItem("skyroot_axe", new ItemAetherAxe(EnumAetherToolMaterial.SKYROOT));
		this.skyroot_pickaxe = this.registerItem("skyroot_pickaxe", new ItemAetherPickaxe(EnumAetherToolMaterial.SKYROOT));
		this.skyroot_shovel = this.registerItem("skyroot_shovel", new ItemAetherShovel(EnumAetherToolMaterial.SKYROOT));
		this.skyroot_sword = this.registerItem("skyroot_sword", new ItemAetherSword(EnumAetherToolMaterial.SKYROOT));

		this.holystone_axe = this.registerItem("holystone_axe", new ItemAetherAxe(EnumAetherToolMaterial.HOLYSTONE));
		this.holystone_pickaxe = this.registerItem("holystone_pickaxe", new ItemAetherPickaxe(EnumAetherToolMaterial.HOLYSTONE));
		this.holystone_shovel = this.registerItem("holystone_shovel", new ItemAetherShovel(EnumAetherToolMaterial.HOLYSTONE));
		this.holystone_sword = this.registerItem("holystone_sword", new ItemAetherSword(EnumAetherToolMaterial.HOLYSTONE));

		this.zanite_axe = this.registerItem("zanite_axe", new ItemAetherAxe(EnumAetherToolMaterial.ZANITE));
		this.zanite_pickaxe = this.registerItem("zanite_pickaxe", new ItemAetherPickaxe(EnumAetherToolMaterial.ZANITE));
		this.zanite_shovel = this.registerItem("zanite_shovel", new ItemAetherShovel(EnumAetherToolMaterial.ZANITE));
		this.zanite_sword = this.registerItem("zanite_sword", new ItemAetherSword(EnumAetherToolMaterial.ZANITE));

		this.gravitite_axe = this.registerItem("gravitite_axe", new ItemAetherAxe(EnumAetherToolMaterial.GRAVITITE));
		this.gravitite_pickaxe = this.registerItem("gravitite_pickaxe", new ItemAetherPickaxe(EnumAetherToolMaterial.GRAVITITE));
		this.gravitite_shovel = this.registerItem("gravitite_shovel", new ItemAetherShovel(EnumAetherToolMaterial.GRAVITITE));
		this.gravitite_sword = this.registerItem("gravitite_sword", new ItemAetherSword(EnumAetherToolMaterial.GRAVITITE));

		this.zanite_helmet = this.registerItem("zanite_helmet", new ItemAetherArmor(EnumAetherArmorMaterial.ZANITE, 0, 0));
		this.zanite_chestplate = this.registerItem("zanite_chestplate", new ItemAetherArmor(EnumAetherArmorMaterial.ZANITE, 0, 1));
		this.zanite_leggings = this.registerItem("zanite_leggings", new ItemAetherArmor(EnumAetherArmorMaterial.ZANITE, 0, 2));
		this.zanite_boots = this.registerItem("zanite_boots", new ItemAetherArmor(EnumAetherArmorMaterial.ZANITE, 0, 3));

		this.gravitite_helmet = this.registerItem("gravitite_helmet", new ItemAetherArmor(EnumAetherArmorMaterial.GRAVITITE, 0, 0));
		this.gravitite_chestplate = this.registerItem("gravitite_chestplate", new ItemAetherArmor(EnumAetherArmorMaterial.GRAVITITE, 0, 1));
		this.gravitite_leggings = this.registerItem("gravitite_leggings", new ItemAetherArmor(EnumAetherArmorMaterial.GRAVITITE, 0, 2));
		this.gravitite_boots = this.registerItem("gravitite_boots", new ItemAetherArmor(EnumAetherArmorMaterial.GRAVITITE, 0, 3));
	}

	private Item registerItem(String name, Item item)
	{
		item.setUnlocalizedName(name);
		GameRegistry.registerItem(item, name);

		return item;
	}
}
