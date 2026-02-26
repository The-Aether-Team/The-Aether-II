package com.aetherteam.aetherii.mixin.mixins.client.accessor;

import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import net.minecraft.client.gui.screens.recipebook.GhostSlots;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GhostSlots.class)
public interface GhostSlotsAccessor {
    @Accessor("ingredients")
    Reference2ObjectMap<Slot, GhostSlots.GhostSlot> aether_ii$getIngredients();
}
