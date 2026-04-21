package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.mixin.wrappers.client.IrradiatedDataWrapper;
import net.minecraft.client.renderer.SubmitNodeStorage;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Debug
@Mixin(SubmitNodeStorage.ItemSubmit.class)
public class ItemSubmitMixin implements IrradiatedDataWrapper {
    @Unique
    private boolean aether_ii$isIrradiated;

    @Override
    public void aether_ii$setIrradiated(boolean irradiated) {
        this.aether_ii$isIrradiated = irradiated;
    }

    @Override
    public boolean aether_ii$getIrradiated() {
        return this.aether_ii$isIrradiated;
    }
}
