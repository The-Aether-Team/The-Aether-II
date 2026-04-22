package com.aetherteam.aetherii.mixin.mixins.client.accessor;

import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemDisplayContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemStackRenderState.class)
public interface ItemStackRenderStateAccessor {
    @Accessor("displayContext")
    ItemDisplayContext aether_ii$getDisplayContext();
}
