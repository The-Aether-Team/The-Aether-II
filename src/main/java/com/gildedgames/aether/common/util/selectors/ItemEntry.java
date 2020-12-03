package com.gildedgames.aether.common.util.selectors;

import net.minecraft.item.Item;
import net.minecraft.util.WeightedRandom;

public class ItemEntry extends WeightedRandom.Item
{
    private final Item item;

    public ItemEntry(Item item, int itemWeightIn)
    {
        super(itemWeightIn);
        this.item = item;
    }

    public Item getItem()
    {
        return this.item;
    }
}
