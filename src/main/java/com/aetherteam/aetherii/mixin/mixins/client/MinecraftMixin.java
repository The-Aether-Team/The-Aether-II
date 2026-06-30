package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.client.event.hooks.AudioHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.Music;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "getSituationalMusic()Lnet/minecraft/sounds/Music;", at = @At("RETURN"), cancellable = true)
    private void getSituationalMusic(CallbackInfoReturnable<Music> cir) {
        Music music = AudioHooks.getSituationalMusic();
        if (music != null) {
            cir.setReturnValue(music);
        }
    }
}
