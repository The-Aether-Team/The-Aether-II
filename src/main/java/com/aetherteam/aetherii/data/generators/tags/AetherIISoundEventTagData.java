package com.aetherteam.aetherii.data.generators.tags;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.KeyTagProvider;
import net.minecraft.sounds.SoundEvent;

import java.util.concurrent.CompletableFuture;

public class AetherIISoundEventTagData extends KeyTagProvider<SoundEvent> {
    public AetherIISoundEventTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Registries.SOUND_EVENT, registries, AetherII.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(AetherIITags.SoundEvents.MUSIC).addTag(AetherIITags.SoundEvents.BOSS_MUSIC).add(
                AetherIISoundEvents.MUSIC_AETHER.getKey(),
                AetherIISoundEvents.MUSIC_AETHER_AMBIENCE.getKey(),
                AetherIISoundEvents.MUSIC_AETHER_NIGHT.getKey(),
                AetherIISoundEvents.MUSIC_AETHER_SUNRISE.getKey(),
                AetherIISoundEvents.MUSIC_AETHER_SUNSET.getKey());
        this.tag(AetherIITags.SoundEvents.BOSS_MUSIC).add(
                AetherIISoundEvents.MUSIC_BOSS_SLIDER.getKey());
    }
}
