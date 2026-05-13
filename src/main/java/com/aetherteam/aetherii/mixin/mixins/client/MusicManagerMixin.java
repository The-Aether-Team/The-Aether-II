package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.client.sound.instance.MusicSoundInstance;
import com.aetherteam.aetherii.mixin.MixinHooks;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(MusicManager.class)
public class MusicManagerMixin {
    @Shadow
    @Nullable
    private SoundInstance currentMusic;

    @Inject(method = "startPlaying(Lnet/minecraft/sounds/Music;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sounds/SoundManager;play(Lnet/minecraft/client/resources/sounds/SoundInstance;)Lnet/minecraft/client/sounds/SoundEngine$PlayResult;", shift = At.Shift.BEFORE))
    public void forMusic(Music music, CallbackInfo ci) {
        Holder<SoundEvent> soundEvent = music.sound();
        if (soundEvent.is(AetherIISoundEvents.MUSIC_MENU)) {
            this.currentMusic = MusicSoundInstance.forMenuMusic(soundEvent.value());
        } else if (soundEvent.is(AetherIITags.SoundEvents.MUSIC)) {
            if (soundEvent.is(AetherIITags.SoundEvents.BOSS_MUSIC)) {
                this.currentMusic = MusicSoundInstance.forBossMusic(soundEvent.value());
            } else {
                this.currentMusic = MusicSoundInstance.forMusic(soundEvent.value());
            }
        }
    }

    @Inject(method = "startPlaying(Lnet/minecraft/sounds/Music;)V", at = @At(value = "RETURN"))
    public void forMusicReturn(Music music, CallbackInfo ci) {
        MixinHooks.LAST_MUSIC = this.currentMusic;
    }
}
