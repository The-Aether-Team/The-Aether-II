package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.client.sound.instance.MusicSoundInstance;
import com.aetherteam.aetherii.mixin.MixinHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicManager.class)
public class MusicManagerMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    private int nextSongDelay;

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void aether_ii$replaceVanillaMusicForAether(CallbackInfo ci) {
        Music music = this.minecraft.getSituationalMusic();
        SoundInstance currentMusic = ((MusicManager) (Object) this).currentMusic;
        if (music != null && music.getEvent().is(AetherIITags.SoundEvents.MUSIC) && currentMusic != null && !(currentMusic instanceof MusicSoundInstance)) {
            this.minecraft.getSoundManager().stop(currentMusic);
            ((MusicManager) (Object) this).currentMusic = null;
            this.nextSongDelay = 0;
        }
    }

    @Inject(method = "startPlaying(Lnet/minecraft/sounds/Music;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sounds/SoundManager;play(Lnet/minecraft/client/resources/sounds/SoundInstance;)V", shift = At.Shift.BEFORE))
    private void forMusic(Music music, CallbackInfo ci) {
        Holder<SoundEvent> soundEvent = music.getEvent();
        MusicManager musicManager = (MusicManager) (Object) this;
        if (soundEvent.is(AetherIISoundEvents.MUSIC_MENU.getKey())) {
            musicManager.currentMusic = MusicSoundInstance.forMenuMusic(soundEvent.value());
        } else if (soundEvent.is(AetherIITags.SoundEvents.MUSIC)) {
            if (soundEvent.is(AetherIITags.SoundEvents.BOSS_MUSIC)) {
                musicManager.currentMusic = MusicSoundInstance.forBossMusic(soundEvent.value());
            } else {
                musicManager.currentMusic = MusicSoundInstance.forMusic(soundEvent.value());
            }
        }
    }

    @Inject(method = "startPlaying(Lnet/minecraft/sounds/Music;)V", at = @At("RETURN"))
    private void forMusicReturn(Music music, CallbackInfo ci) {
        SoundInstance currentMusic = ((MusicManager) (Object) this).currentMusic;
        MixinHooks.LAST_MUSIC = currentMusic;
    }
}
