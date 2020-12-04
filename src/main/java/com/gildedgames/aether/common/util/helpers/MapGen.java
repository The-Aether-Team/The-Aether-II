package com.gildedgames.aether.common.util.helpers;

import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class MapGen
{
    public static Map<ItemStack, Integer> genMap(ItemStack stack, int integer)
    {
        Map<ItemStack, Integer> map = new HashMap<>();
        map.put(stack, integer);
        return map;
    }
}
