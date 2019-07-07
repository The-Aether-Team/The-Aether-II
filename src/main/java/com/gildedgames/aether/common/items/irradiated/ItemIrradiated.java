package com.gildedgames.aether.common.items.irradiated;

import com.gildedgames.aether.api.items.loot.Loot;
import com.gildedgames.aether.common.items.IDropOnDeath;

public class ItemIrradiated extends ItemIrradiatedVisuals implements IDropOnDeath
{

	private final Loot itemSelector;

	public ItemIrradiated(Loot itemSelector)
	{
		this.itemSelector = itemSelector;
	}

	public Loot getItemSelector()
	{
		return this.itemSelector;
	}

}
