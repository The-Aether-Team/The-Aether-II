package com.aetherteam.aetherii.mixin.mixins.client.accessor;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelEventHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientLevel.class)
public interface ClientLevelAccessor {
    @Accessor("levelEventHandler")
    LevelEventHandler aether_ii$getLevelEventHandler();
}
