package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.AetherCreativeTabs;
import net.minecraft.item.ItemTool;

public class ItemAetherTool extends ItemTool
{
	public ItemAetherTool(ToolMaterial material, EnumToolType toolType)
	{
		super(toolType.getBaseDamage(), material, toolType.getEffectiveBlocks());

		this.setCreativeTab(AetherCreativeTabs.tabTools);
	}
}
