package com.gildedgames.aether.items;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.AetherCreativeTabs;
import com.gildedgames.aether.client.models.ModelsAether;
import com.gildedgames.aether.items.tools.EnumAetherToolMaterial;
import com.gildedgames.aether.items.tools.ItemAetherAxe;
import com.gildedgames.aether.items.tools.ItemAetherPickaxe;
import com.gildedgames.aether.items.tools.ItemAetherShovel;

public class ItemsAether
{
	public Item skyroot_stick;

	public Item ambrosium_shard;

	public ItemAetherAxe skyroot_axe, holystone_axe, zanite_axe, gravitite_axe;

	public ItemAetherPickaxe skyroot_pickaxe, holystone_pickaxe, zanite_pickaxe, gravitite_pickaxe;

	public ItemAetherShovel skyroot_shovel, holystone_shovel, zanite_shovel, gravitite_shovel;

	public void preInit()
	{
		this.skyroot_stick = this.registerItem("skyroot_stick", new Item().setCreativeTab(AetherCreativeTabs.tabMaterials));
		this.ambrosium_shard = this.registerItem("ambrosium_shard", new Item().setCreativeTab(AetherCreativeTabs.tabMaterials));

		this.skyroot_axe = this.registerItem("skyroot_axe", new ItemAetherAxe(EnumAetherToolMaterial.SKYROOT));
		this.holystone_axe = this.registerItem("holystone_axe", new ItemAetherAxe(EnumAetherToolMaterial.HOLYSTONE));
		this.zanite_axe = this.registerItem("zanite_axe", new ItemAetherAxe(EnumAetherToolMaterial.ZANITE));
		this.gravitite_axe = this.registerItem("gravitite_axe", new ItemAetherAxe(EnumAetherToolMaterial.GRAVITITE));

		this.skyroot_pickaxe = this.registerItem("skyroot_pickaxe", new ItemAetherPickaxe(EnumAetherToolMaterial.SKYROOT));
		this.holystone_pickaxe = this.registerItem("holystone_pickaxe", new ItemAetherPickaxe(EnumAetherToolMaterial.HOLYSTONE));
		this.zanite_pickaxe = this.registerItem("zanite_pickaxe", new ItemAetherPickaxe(EnumAetherToolMaterial.ZANITE));
		this.gravitite_pickaxe = this.registerItem("gravitite_pickaxe", new ItemAetherPickaxe(EnumAetherToolMaterial.GRAVITITE));

		this.skyroot_shovel = this.registerItem("skyroot_shovel", new ItemAetherShovel(EnumAetherToolMaterial.SKYROOT));
		this.holystone_shovel = this.registerItem("holystone_shovel", new ItemAetherShovel(EnumAetherToolMaterial.HOLYSTONE));
		this.zanite_shovel = this.registerItem("zanite_shovel", new ItemAetherShovel(EnumAetherToolMaterial.ZANITE));
		this.gravitite_shovel = this.registerItem("gravitite_shovel", new ItemAetherShovel(EnumAetherToolMaterial.GRAVITITE));
	}

	private <T extends Item> T registerItem(String name, T item)
	{
		item.setUnlocalizedName(name);
		GameRegistry.registerItem(item, name);

		return item;
	}

	public void init()
	{
		if (Aether.PROXY.getModels() != null)
		{
			ModelsAether models = Aether.PROXY.getModels();

			models.registerItemRenderer(0, this.skyroot_stick);
			models.registerItemRenderer(0, this.ambrosium_shard);

			models.registerItemRenderers(0, this.skyroot_pickaxe, this.skyroot_axe, this.skyroot_shovel);
			models.registerItemRenderers(0, this.holystone_pickaxe, this.holystone_axe, this.holystone_shovel);
			models.registerItemRenderers(0, this.zanite_pickaxe, this.zanite_axe, this.zanite_shovel);
			models.registerItemRenderers(0, this.gravitite_pickaxe, this.gravitite_axe, this.gravitite_shovel);
		}
	}
}
