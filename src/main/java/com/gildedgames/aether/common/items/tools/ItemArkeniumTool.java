package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.registry.minecraft.MaterialsAether;

public class ItemArkeniumTool extends ItemAetherTool
{
	public ItemArkeniumTool(EnumToolType toolType, float attackDamage, float attackSpeed)
	{
		super(MaterialsAether.ARKENIUM_TOOL, "arkenium", toolType, attackDamage, attackSpeed);

		this.setHarvestLevel(toolType.getToolClass(), 2);
	}

	@Override
	protected boolean hasAbility()
	{
		return true;
	}
}
