package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.registry.minecraft.MaterialsAether;

public class ItemValkyrieTool extends ItemAetherTool
{
	public ItemValkyrieTool(EnumToolType toolType, float attackDamage, float attackSpeed)
	{
		super(MaterialsAether.LEGENDARY_TOOL, "valkyrie", toolType, attackDamage, attackSpeed);

		this.setHarvestLevel(toolType.getToolClass(), 3);
	}
}
