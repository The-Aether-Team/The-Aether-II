package com.aetherteam.aetherii.mixin.mixins.client.accessor;

import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MouseHandler.class)
public interface MouseHandlerAccessor {
    @Accessor("mouseGrabbed")
    void aether_ii$setMouseGrabbed(boolean mouseGrabbed);

}
