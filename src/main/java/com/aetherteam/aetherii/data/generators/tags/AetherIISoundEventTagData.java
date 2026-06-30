package com.aetherteam.aetherii.data.generators.tags;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class AetherIISoundEventTagData extends TagsProvider<SoundEvent> {
    public AetherIISoundEventTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, Registries.SOUND_EVENT, registries, AetherII.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(AetherIITags.SoundEvents.PORTAL_SOUNDS).add(
                AetherIISoundEvents.BLOCK_AETHER_PORTAL_AMBIENT.getKey(),
                AetherIISoundEvents.BLOCK_AETHER_PORTAL_TRIGGER.getKey(),
                AetherIISoundEvents.BLOCK_AETHER_PORTAL_TRAVEL.getKey()
        );
        this.tag(AetherIITags.SoundEvents.AMBIENT_PORTAL_SOUNDS).add(
                AetherIISoundEvents.BLOCK_AETHER_PORTAL_AMBIENT.getKey()
        );
        this.tag(AetherIITags.SoundEvents.ACTIVATED_PORTAL_SOUNDS).add(
                AetherIISoundEvents.BLOCK_AETHER_PORTAL_TRIGGER.getKey(),
                AetherIISoundEvents.BLOCK_AETHER_PORTAL_TRAVEL.getKey()
        );
        this.tag(AetherIITags.SoundEvents.ACHIEVEMENT_SOUNDS);
        this.tag(AetherIITags.SoundEvents.MUSIC).add(
                AetherIISoundEvents.MUSIC_AETHER.getKey(),
                AetherIISoundEvents.MUSIC_AETHER_CAVES.getKey(),
                AetherIISoundEvents.MUSIC_AETHER_NIGHT.getKey(),
                AetherIISoundEvents.MUSIC_AETHER_SUNRISE.getKey(),
                AetherIISoundEvents.MUSIC_AETHER_SUNSET.getKey()
        ).addTags(
                AetherIITags.SoundEvents.BOSS_MUSIC
        );
        this.tag(AetherIITags.SoundEvents.BOSS_MUSIC).add(
                AetherIISoundEvents.MUSIC_BOSS_SLIDER.getKey()
        );
    }
}
