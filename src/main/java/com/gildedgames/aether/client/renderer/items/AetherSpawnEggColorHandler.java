package com.gildedgames.aether.client.renderer.items;

import com.gildedgames.aether.common.entities.EntitiesAether;
import com.gildedgames.aether.common.entities.util.AetherSpawnEggInfo;
import com.gildedgames.aether.common.items.misc.ItemAetherSpawnEgg;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class AetherSpawnEggColorHandler implements IItemColor
{

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex)
	{
		if (stack != null && stack.getItem() instanceof ItemAetherSpawnEgg)
		{
			AetherSpawnEggInfo info = EntitiesAether.entityEggs.get(ItemAetherSpawnEgg.getEntityIdFromItem(stack));

			if (info != null)
			{
				if (tintIndex == 0)
				{
					return info.getPrimaryColor();
				}
				else if (tintIndex == 1)
				{
					return info.getSecondaryColor();
				}
			}
		}

		return 0xFFFFFF;
	}

}
