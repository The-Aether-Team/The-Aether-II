package com.gildedgames.aether.common.items.weapons;

import com.gildedgames.aether.common.AetherCreativeTabs;
import net.minecraft.item.ItemSword;

public class ItemAetherSword extends ItemSword
{
	public ItemAetherSword(ToolMaterial material)
	{
		super(material);

		this.setCreativeTab(AetherCreativeTabs.tabWeapons);
	}
}
