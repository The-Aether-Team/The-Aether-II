package com.gildedgames.aether.common.items.armor;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemWinterHat extends Item
{
    @Nullable
    public EntityEquipmentSlot getEquipmentSlot(ItemStack stack)
    {
        return EntityEquipmentSlot.HEAD;
    }
}
