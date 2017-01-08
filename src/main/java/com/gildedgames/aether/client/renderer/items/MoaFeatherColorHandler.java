package com.gildedgames.aether.client.renderer.items;

import com.gildedgames.aether.common.items.misc.ItemMoaFeather;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class MoaFeatherColorHandler implements IItemColor
{

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex)
	{
		if (stack != null && stack.getItem() instanceof ItemMoaFeather)
		{
			if (tintIndex == 1)
			{
				return ItemMoaFeather.getColor(stack);
			}
		}

		return 0xFFFFFF;
	}

}
