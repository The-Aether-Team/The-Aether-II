package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.advancement.AetherIIAdvancementSoundOverrides;
import net.minecraft.client.gui.components.toasts.AdvancementToast;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AdvancementToast.class)
public class AdvancementToastMixin {
    @Redirect(method = "render(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/components/toasts/ToastComponent;J)Lnet/minecraft/client/gui/components/toasts/Toast$Visibility;", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sounds/SoundManager;play(Lnet/minecraft/client/resources/sounds/SoundInstance;)V"))
    private void suppressDefaultChallengeToastSound(SoundManager soundManager, SoundInstance soundInstance) {
        SoundEvent soundOverride = AetherIIAdvancementSoundOverrides.retrieveOverride(((AdvancementToast) (Object) this).advancement);
        if (soundOverride == null) {
            soundManager.play(soundInstance);
        } else if (soundOverride == SoundEvents.EMPTY) {
            return;
        }
    }
}
