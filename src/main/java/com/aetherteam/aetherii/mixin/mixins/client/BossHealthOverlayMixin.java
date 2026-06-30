package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.client.event.hooks.RenderHooks;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BossHealthOverlay.class)
public class BossHealthOverlayMixin {
    @ModifyVariable(method = "render(Lnet/minecraft/client/gui/GuiGraphics;)V", at = @At("STORE"), ordinal = 0)
    private CustomizeGuiOverlayEvent.BossEventProgress event(CustomizeGuiOverlayEvent.BossEventProgress event) {
        if (RenderHooks.drawBossHealthBar(event.getGuiGraphics(), event.getX(), event.getY(), event.getBossEvent())) {
            event.setCanceled(true);
        }
        return event;
    }
}
