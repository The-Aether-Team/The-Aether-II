package com.aetherteam.aetherii.mixin.mixins.client.accessor;

import net.minecraft.client.renderer.SubmitNodeCollection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SubmitNodeCollection.class)
public interface SubmitNodeCollectionAccessor {
    @Accessor("wasUsed")
    void aether_ii$setWasUsed(boolean wasUsed);
}
