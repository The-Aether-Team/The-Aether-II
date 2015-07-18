package com.gildedgames.aether.common.items.tools;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemZaniteTool extends ItemAetherTool
{
	public ItemZaniteTool(EnumToolType toolType)
	{
		super(ToolMaterial.IRON, toolType);

		this.setHarvestLevel(toolType.getToolClass(), 1);
	}

	@Override
	public float getStrVsBlock(ItemStack stack, Block block)
	{
		return super.getStrVsBlock(stack, block) * (2.0F * stack.getItemDamage() / stack.getItem().getMaxDamage() + 0.5F);
	}
}
