package com.gildedgames.aether.common.items.tools;

public class ItemSkyrootTool extends ItemAetherTool
{
	public ItemSkyrootTool(EnumToolType toolType)
	{
		super(ToolMaterial.WOOD, toolType);

		this.setHarvestLevel(toolType.getToolClass(), 0);
	}

	// TODO: Double drops.
}
