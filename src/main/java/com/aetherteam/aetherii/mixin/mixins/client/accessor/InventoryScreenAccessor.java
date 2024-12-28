package com.aetherteam.aetherii.mixin.mixins.client.accessor;

import net.minecraft.client.gui.screens.inventory.EffectsInInventory;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(InventoryScreen.class)
public interface InventoryScreenAccessor {
    @Accessor("effects")
    EffectsInInventory aether_ii$getEffects();
}
