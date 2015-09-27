package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.AetherCreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class ItemAetherTool extends ItemTool
{
	protected final EnumToolType toolType;

	public ItemAetherTool(ToolMaterial material, EnumToolType toolType)
	{
		super(toolType.getBaseDamage(), material, toolType.getEffectiveBlocks());

		this.toolType = toolType;

		this.setCreativeTab(AetherCreativeTabs.tabTools);
	}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass)
	{
		int level = super.getHarvestLevel(stack, toolClass);

		if (level == -1 && toolClass != null && toolClass.equals(this.toolType.getToolClass()))
		{
			return this.toolMaterial.getHarvestLevel();
		}
		else
		{
			return level;
		}
	}
}
