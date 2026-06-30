package com.aetherteam.aetherii.mixin.mixins.client.accessor;

import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(InventoryScreen.class)
public interface InventoryScreenAccessor {
    @Accessor("xMouse")
    void aether_ii$setXMouse(float xMouse);

    @Accessor("yMouse")
    void aether_ii$setYMouse(float yMouse);
}
