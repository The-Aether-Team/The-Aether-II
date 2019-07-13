package com.gildedgames.aether.common.items.irradiated;

import com.gildedgames.aether.api.items.loot.Loot;
import net.minecraft.item.Item;

public class ItemIrradiated extends ItemIrradiatedVisuals
{

	private final Loot itemSelector;

	public ItemIrradiated(Loot itemSelector, Item.Properties properties)
	{
		super(properties);

		this.itemSelector = itemSelector;
	}

	public Loot getItemSelector()
	{
		return this.itemSelector;
	}

}
