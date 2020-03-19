package com.gildedgames.aether.common.items.other;

import com.gildedgames.aether.common.items.IDropOnDeath;
import net.minecraft.item.Item;

public class ItemMoaFeed extends Item implements IDropOnDeath
{
    public final ItemMoaFeed.MoaFeedType feedType;

    public ItemMoaFeed(ItemMoaFeed.MoaFeedType feedType)
    {
        this.feedType = feedType;
    }

    public double getHealingAmount()
    {
        return this.feedType.getHealingAmount();
    }

    public enum MoaFeedType
    {
        BASIC(5.0D),
        BLUEBERRY(10.0D),
        ENCHANTED_BLUEBERRY(15.0D);

        private double healingAmount;

        MoaFeedType(double healingAmount)
        {
            this.healingAmount = healingAmount;
        }

        public double getHealingAmount()
        {
            return this.healingAmount;
        }
    }
}
