package com.gildedgames.aether.common.items;

import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;

public interface IUsesCustomSound
{
    boolean usesCustomSound(ItemStack stack);

    SoundEvent getDefaultSound();

    SoundEvent getCustomSound();
}
