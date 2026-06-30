package com.aetherteam.aetherii.client;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public interface AetherIIDyeableClientItemExtensions extends IClientItemExtensions {
    int getDefaultDyeColor(ItemStack stack);

    static int getColorOrDefault(ItemStack stack, int defaultColor) {
        CompoundTag displayTag = stack.getTagElement("display");
        if (displayTag != null && displayTag.contains("color", 99)) {
            return 0xFF000000 | displayTag.getInt("color");
        }
        return defaultColor;
    }

    static int getDefaultDyeColor(ItemStack stack, int fallbackColor) {
        IClientItemExtensions extensions = IClientItemExtensions.of(stack);
        if (extensions instanceof AetherIIDyeableClientItemExtensions dyeableExtensions) {
            return dyeableExtensions.getDefaultDyeColor(stack);
        }
        return getColorOrDefault(stack, fallbackColor);
    }
}
