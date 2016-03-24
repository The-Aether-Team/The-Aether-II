package com.gildedgames.aether.common.items.tools;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class ItemZaniteTool extends ItemAetherTool
{
	public ItemZaniteTool(EnumToolType toolType)
	{
		super(ToolMaterial.IRON, "zanite", toolType);

		this.setHarvestLevel(toolType.getToolClass(), 1);
	}

	@Override
	public float getDigSpeed(ItemStack stack, IBlockState state)
	{
		for (String type : this.getToolClasses(stack))
		{
			if (state.getBlock().isToolEffective(type, state))
			{
				return this.efficiencyOnProperMaterial * (2.0F * stack.getItemDamage() / stack.getItem().getMaxDamage() + 0.5F);
			}
		}

		return super.getDigSpeed(stack, state);
	}
}
