package com.gildedgames.aether.client.renderer.items;

import com.gildedgames.aether.common.entities.genes.moa.MoaGenePool;
import com.gildedgames.aether.common.items.other.ItemMoaEgg;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class ItemMoaEggColorHandler implements IItemColor
{

	@Override
	public int colorMultiplier(final ItemStack stack, final int tintIndex)
	{
		final MoaGenePool genePool = ItemMoaEgg.getGenePool(stack);

		if (genePool != null && genePool.getFeathers() != null)
		{
			if (tintIndex == 0)
			{
				return genePool.getFeathers().gene().data().getRGB();
			}
			else if (tintIndex == 1)
			{
				return genePool.getKeratin().gene().data().getRGB();
			}
			else if (tintIndex == 2)
			{
				return genePool.getEyes().gene().data().getRGB();
			}
			else if (tintIndex == 3)
			{
				return genePool.getFeathers().gene().data().darker().getRGB();
			}
		}

		return 0xFFFFFF;
	}

}
