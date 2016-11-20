package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import net.minecraft.item.ItemPickaxe;

public class ItemAetherPickaxe extends ItemPickaxe
{
	public ItemAetherPickaxe(ToolMaterial material)
	{
		super(material);

		this.setHarvestLevel("pickaxe", material.getHarvestLevel());

		this.setCreativeTab(CreativeTabsAether.TOOLS);
	}
}
