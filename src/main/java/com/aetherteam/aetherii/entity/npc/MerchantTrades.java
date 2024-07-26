package com.aetherteam.aetherii.entity.npc;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;

public class MerchantTrades {
    public static final Int2ObjectMap<ItemListing[]> EDWARD_TRADES = toIntMap(
            ImmutableMap.of(
                    1,
                    new ItemListing[] {
                            new ItemsForGlint(AetherIIBlocks.SKYPINE_SAPLING.get(), 20, 1, 5)
                    }
            )
    );

    private static Int2ObjectMap<ItemListing[]> toIntMap(ImmutableMap<Integer, ItemListing[]> map) {
        return new Int2ObjectOpenHashMap<>(map);
    }

    public interface ItemListing {
        @Nullable
        MerchantOffer getOffer(Entity trader, RandomSource random);
    }

    static class ItemsForGlint implements ItemListing {
        private final ItemStack itemStack;
        private final int cost;
        private final int maxUses;
        private final float priceMultiplier;

        public ItemsForGlint(Block block, int cost, int numberOfItems, int maxUses) {
            this(new ItemStack(block), cost, numberOfItems, maxUses);
        }

        public ItemsForGlint(Block block, int cost, int numberOfItems) {
            this(new ItemStack(block), cost, numberOfItems, 12);
        }

        public ItemsForGlint(Item item, int cost, int numberOfItems, int maxUses) {
            this(new ItemStack(item), cost, numberOfItems, maxUses);
        }

        public ItemsForGlint(Item item, int cost, int numberOfItems) {
            this(new ItemStack(item), cost, numberOfItems, 12);
        }

        public ItemsForGlint(ItemStack itemStack, int cost, int numberOfItems, int maxUses) {
            this(itemStack, cost, numberOfItems, maxUses, 0.05F);
        }

        public ItemsForGlint(Item item, int cost, int numberOfItems, int maxUses, float priceMultiplier) {
            this(new ItemStack(item), cost, numberOfItems, maxUses, priceMultiplier);
        }

        public ItemsForGlint(ItemStack itemStack, int cost, int numberOfItems, int maxUses, float priceMultiplier) {
            this.itemStack = itemStack;
            this.cost = cost;
            this.itemStack.setCount(numberOfItems);
            this.maxUses = maxUses;
            this.priceMultiplier = priceMultiplier;
        }

        @Override
        public MerchantOffer getOffer(Entity trader, RandomSource random) {
            ItemStack itemstack = this.itemStack.copy();
            return new MerchantOffer(new ItemCost(AetherIIItems.AMBROSIUM_SHARD.get(), this.cost), itemstack, this.maxUses, 0, this.priceMultiplier);
        }
    }
}
