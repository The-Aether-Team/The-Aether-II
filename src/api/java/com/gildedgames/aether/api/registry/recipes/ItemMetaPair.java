package com.gildedgames.aether.api.registry.recipes;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMetaPair
{
	private final Item item;

	private final int meta;

	public ItemMetaPair(ItemStack stack)
	{
		this(stack.getItem(), stack.getItemDamage());
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
		int hash = (Item.getIdFromItem(this.item) & 0xFFFF) << 16;
		hash = hash | (this.meta & 0xFFFF);

		return hash;
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
