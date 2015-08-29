package com.gildedgames.aether.common.items.tools;

import net.minecraft.item.ItemTool;

import com.gildedgames.aether.common.AetherCore;

public class ItemAetherTool extends ItemTool
{
	public ItemAetherTool(ToolMaterial material, EnumToolType toolType)
	{
		super(toolType.getBaseDamage(), material, toolType.getEffectiveBlocks());

		this.setCreativeTab(AetherCore.locate().tabTools);
	}
}
