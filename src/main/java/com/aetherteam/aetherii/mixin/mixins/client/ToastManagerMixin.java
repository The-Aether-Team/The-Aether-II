package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.advancement.AetherIIAdvancementSoundOverrides;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.AdvancementToastAccessor;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.AdvancementToast;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(ToastManager.class)
public class ToastManagerMixin {
    @Shadow
    @Final
    private Set<SoundEvent> playedToastSounds;

    @Inject(method = "lambda$update$1", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/gui/components/toasts/Toast;getSoundEvent()Lnet/minecraft/sounds/SoundEvent;"), cancellable = true)
    private void init(Toast toast, CallbackInfoReturnable<Boolean> cir, @Local SoundEvent soundEvent) {
        if (Minecraft.getInstance().player != null && toast instanceof AdvancementToast advancementToast) {
            AdvancementHolder advancementHolder = ((AdvancementToastAccessor) advancementToast).aether_ii$getAdvancement();
            SoundEvent soundOverride = AetherIIAdvancementSoundOverrides.retrieveOverride(advancementHolder);
            if (soundOverride != null && soundOverride != SoundEvents.EMPTY) {
                if (this.playedToastSounds.add(soundOverride)) {
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(soundOverride, 1.0F));
                }
                cir.setReturnValue(true);
            }
        }
    }
}
