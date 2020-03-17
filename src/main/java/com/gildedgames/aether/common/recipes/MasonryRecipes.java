package com.gildedgames.aether.common.recipes;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.decorative.BlockHolystoneDecorative;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class MasonryRecipes
{
    private static final MasonryRecipes MASONRY_BASE = new MasonryRecipes();

    private final Map<ItemStack, ItemStack[]> masonryList = Maps.newHashMap();

    public static MasonryRecipes instance()
    {
        return MASONRY_BASE;
    }

    public void addMasonry(Block input, ItemStack... stack)
    {
        this.addMasonryRecipe(new ItemStack(input, 1, 32767), stack);
    }
    public void addMasonryItem(Item input, ItemStack... stack)
    {
        this.addMasonryRecipe(new ItemStack(input, 1, 32767), stack);
    }

    public void addMasonryRecipe(ItemStack input, ItemStack... stack)
    {
        this.masonryList.put(input, stack);
    }

    public ItemStack[] getOutput(ItemStack stack)
    {
        for (Map.Entry<ItemStack, ItemStack[]> entry : this.masonryList.entrySet())
        {
            if (this.compareItemStacks(stack, entry.getKey()))
            {
                return entry.getValue();
            }
        }

        return null;
    }

    public Map<ItemStack, ItemStack[]> getMasonryList()
    {
        return this.masonryList;
    }

    private boolean compareItemStacks(ItemStack stack, ItemStack toCompare)
    {
        return toCompare.getItem() == stack.getItem() && (toCompare.getMetadata() == 32767 || toCompare.getMetadata() == stack.getMetadata());
    }
}
