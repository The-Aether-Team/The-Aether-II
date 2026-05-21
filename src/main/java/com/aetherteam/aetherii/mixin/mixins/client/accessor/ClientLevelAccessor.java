package com.aetherteam.aetherii.mixin.mixins.client.accessor;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelEventHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(ClientLevel.class)
public interface ClientLevelAccessor {
    @Accessor("levelEventHandler")
    LevelEventHandler aether_ii$getLevelEventHandler();
}
