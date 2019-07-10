package com.gildedgames.aether.client.renderer.items;

import com.gildedgames.aether.common.items.other.ItemMoaFeather;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class MoaFeatherColorHandler implements IItemColor
{

	@Override
	public int colorMultiplier(final ItemStack stack, final int tintIndex)
	{
		if (stack.getItem() instanceof ItemMoaFeather)
		{
			if (tintIndex == 1)
			{
				return ItemMoaFeather.getColor(stack);
			}
		}

		return 0xFFFFFF;
	}

}
