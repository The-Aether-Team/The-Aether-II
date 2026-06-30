package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.advancement.AetherIIAdvancementSoundOverrides;
import net.minecraft.client.gui.components.toasts.AdvancementToast;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.client.gui.components.toasts.ToastComponent$ToastInstance")
public class ToastComponentToastInstanceMixin {
    @Shadow
    @Final
    private Toast toast;

    @Redirect(method = "render(ILnet/minecraft/client/gui/GuiGraphics;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/toasts/Toast$Visibility;playSound(Lnet/minecraft/client/sounds/SoundManager;)V"))
    private void playAetherAdvancementToastSound(Toast.Visibility visibility, SoundManager soundManager) {
        if (visibility == Toast.Visibility.SHOW && this.toast instanceof AdvancementToast advancementToast) {
            SoundEvent soundOverride = AetherIIAdvancementSoundOverrides.retrieveOverride(advancementToast.advancement);
            if (soundOverride == null) {
                visibility.playSound(soundManager);
            } else if (soundOverride != SoundEvents.EMPTY) {
                soundManager.play(SimpleSoundInstance.forUI(soundOverride, 1.0F));
            }
        } else {
            visibility.playSound(soundManager);
        }
    }
}
