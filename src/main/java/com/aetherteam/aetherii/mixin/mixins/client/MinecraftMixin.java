package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.event.hooks.PlayerHooks;
import com.aetherteam.aetherii.network.packet.serverbound.ItemUseHeldPacket;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "handleKeybinds()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;startUseItem()V", shift = At.Shift.AFTER))
    private void handleKeybinds(CallbackInfo ci) {
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.getData(AetherIIDataAttachments.PLAYER).setItemUseKeyHeld(true);
            ClientPacketDistributor.sendToServer(new ItemUseHeldPacket(true));
        }
    }
}
