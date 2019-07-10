package com.gildedgames.aether.client.renderer.items;

import com.gildedgames.aether.common.items.other.ItemWrappingPaper;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;

public class WrappingPaperColorHandler implements IItemColor
{

	@Override
	public int colorMultiplier(final ItemStack stack, final int tintIndex)
	{
		if (stack.getItem() instanceof ItemWrappingPaper)
		{
			final ItemWrappingPaper.PresentDyeData data = ItemWrappingPaper.getDyeData(stack);

			if (tintIndex == 0)
			{
				return ItemDye.DYE_COLORS[data.getBoxColor()];
			}
			else if (tintIndex == 2)
			{
				return ItemDye.DYE_COLORS[data.getBowColor()];
			}
		}

		return 0xFFFFFF;
	}

}
