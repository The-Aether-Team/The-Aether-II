package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.AetherMaterials;

public class ItemValkyrieTool extends ItemAetherTool
{
	public ItemValkyrieTool(EnumToolType toolType, float attackDamage, float attackSpeed)
	{
		super(AetherMaterials.LEGENDARY_TOOL, "valkyrie", toolType, attackDamage, attackSpeed);

		this.setHarvestLevel(toolType.getToolClass(), 3);
	}
}
