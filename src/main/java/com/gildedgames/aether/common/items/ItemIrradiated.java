package com.gildedgames.aether.common.items;

import com.gildedgames.aether.common.crafting.loot.IItemSelector;

public class ItemIrradiated extends ItemIrradiatedVisuals
{

    private IItemSelector itemSelector;


    public ItemIrradiated(IItemSelector itemSelector)
    {
        this.itemSelector = itemSelector;
    }

    public IItemSelector getItemSelector()
    {
        return this.itemSelector;
    }

}