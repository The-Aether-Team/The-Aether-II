package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    @Shadow private boolean crouching;

    @Inject(method = "aiStep()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/ClientInput;tick()V", shift = At.Shift.BEFORE))
    private void aiStep(CallbackInfo ci) {
        LocalPlayer localPlayer = (LocalPlayer) (Object) this;
        ItemStack useStack = localPlayer.getUseItem();
        Boolean special = useStack.get(AetherIIDataComponents.CROSSBOW_SPECIAL);
        if (special != null && special) {
            if (!localPlayer.getAbilities().flying && !localPlayer.isSwimming() && !localPlayer.isPassenger()) {
                this.crouching = true;
            }
        }
    }
}
