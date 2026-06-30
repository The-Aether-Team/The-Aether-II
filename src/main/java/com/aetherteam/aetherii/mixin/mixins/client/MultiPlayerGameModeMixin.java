package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.item.miscellaneous.ToggleItem;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin {
    @Inject(method = "releaseUsingItem(Lnet/minecraft/world/entity/player/Player;)V", at = @At("HEAD"), cancellable = true)
    private void releaseUsingItem(Player player, CallbackInfo ci) {
        if (player.getUseItem().getItem() instanceof ToggleItem && AetherIIDataAttachments.get(player, AetherIIDataAttachments.PLAYER).isUseToggled()) {
            ci.cancel();
        }
    }
}
