package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.AetherIISoundEvents;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;

public class AetherIIJukeboxSongs {
    public static ResourceKey<JukeboxSong> AETHER_TUNE = create("aether_tune");
    public static ResourceKey<JukeboxSong> ASCENDING_DAWN = create("ascending_dawn");
    public static ResourceKey<JukeboxSong> AERWHALE = create("aerwhale");
    public static ResourceKey<JukeboxSong> APPROACHES = create("approaches");
    public static ResourceKey<JukeboxSong> DEMISE = create("demise");
    public static ResourceKey<JukeboxSong> RECORDING_892 = create("recording_892");

    private static ResourceKey<JukeboxSong> create(String pName) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, pName));
    }

    public static void bootstrap(BootstrapContext<JukeboxSong> context) {
        register(context, AETHER_TUNE, (Holder.Reference<SoundEvent>) AetherIISoundEvents.ITEM_MUSIC_DISC_AETHER_TUNE.getDelegate(), 149, 1);
        register(context, ASCENDING_DAWN, (Holder.Reference<SoundEvent>) AetherIISoundEvents.ITEM_MUSIC_DISC_ASCENDING_DAWN.getDelegate(), 350, 2);
        register(context, AERWHALE, (Holder.Reference<SoundEvent>) AetherIISoundEvents.ITEM_MUSIC_DISC_AERWHALE.getDelegate(), 178, 3);
        register(context, APPROACHES, (Holder.Reference<SoundEvent>) AetherIISoundEvents.ITEM_MUSIC_DISC_APPROACHES.getDelegate(), 274, 4);
        register(context, DEMISE, (Holder.Reference<SoundEvent>) AetherIISoundEvents.ITEM_MUSIC_DISC_DEMISE.getDelegate(), 300, 5);
        register(context, RECORDING_892, (Holder.Reference<SoundEvent>) AetherIISoundEvents.ITEM_RECORDING_892.getDelegate(), 97, 6);
    }

    private static void register(BootstrapContext<JukeboxSong> context, ResourceKey<JukeboxSong> key, Holder.Reference<SoundEvent> soundEvent, int lengthInSeconds, int comparatorOutput) {
        context.register(key, new JukeboxSong(soundEvent, Component.translatable(Util.makeDescriptionId("jukebox_song", key.location())), (float)lengthInSeconds, comparatorOutput));
    }
}