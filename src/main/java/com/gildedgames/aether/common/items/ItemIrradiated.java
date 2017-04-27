package com.gildedgames.aether.common.items;

import com.gildedgames.aether.api.items.loot.Loot;

public class ItemIrradiated extends ItemIrradiatedVisuals
{

	private Loot itemSelector;

	public ItemIrradiated(Loot itemSelector)
	{
		this.itemSelector = itemSelector;
	}

	public Loot getItemSelector()
	{
		return this.itemSelector;
	}

}
