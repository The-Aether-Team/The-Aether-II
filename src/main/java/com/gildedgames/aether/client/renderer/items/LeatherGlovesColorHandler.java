package com.gildedgames.aether.client.renderer.items;

import com.gildedgames.aether.common.items.armor.ItemLeatherGloves;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class LeatherGlovesColorHandler implements IItemColor
{

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex)
	{
		if (tintIndex == 0)
		{
			return ItemLeatherGloves.getColor(stack);
		}

		return 0xFFFFFF;
	}

}
