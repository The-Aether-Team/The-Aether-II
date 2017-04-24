package com.gildedgames.aether.api.registry.recipes;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMetaPair
{
	private final Item item;

	private final int meta;

	public ItemMetaPair(ItemStack stack)
	{
		this(stack.getItem(), stack.isItemStackDamageable() ? stack.getItemDamage() : 0);
	}

	public ItemMetaPair(Item item, int meta)
	{
		this.item = item;
		this.meta = meta;
	}

	public Item getItem()
	{
		return this.item;
	}

	public int getMeta()
	{
		return this.meta;
	}

	@Override
	public int hashCode()
	{
		return Item.getIdFromItem(this.item);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof ItemMetaPair)
		{
			ItemMetaPair pair = (ItemMetaPair) obj;

			if (pair.item == this.item && pair.meta == this.meta)
			{
				return true;
			}
		}

		return false;
	}
}
