package com.aetherteam.aetherii.mixin.mixins.client.accessor;

import net.minecraft.client.model.player.PlayerModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerModel.class)
public interface PlayerModelAccessor {
    @Accessor("slim")
    boolean aether$getSlim();
}