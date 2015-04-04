package com.gildedgames.aether.items;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.AetherCreativeTabs;
import com.gildedgames.aether.client.models.ModelsAether;

public class ItemsAether
{
	public Item skyroot_stick;

	public void preInit()
	{
		this.skyroot_stick = this.registerItem("skyroot_stick", new Item().setCreativeTab(AetherCreativeTabs.tabMaterials));
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

			models.registerItemRenderer(this.skyroot_stick, 0);
		}
	}
}
