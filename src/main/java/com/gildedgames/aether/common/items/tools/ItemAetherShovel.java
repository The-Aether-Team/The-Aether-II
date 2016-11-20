package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import net.minecraft.item.ItemSpade;

public class ItemAetherShovel extends ItemSpade
{
	public ItemAetherShovel(ToolMaterial material)
	{
		super(material);

		this.setHarvestLevel("shovel", material.getHarvestLevel());

		this.setCreativeTab(CreativeTabsAether.TOOLS);
	}
}
