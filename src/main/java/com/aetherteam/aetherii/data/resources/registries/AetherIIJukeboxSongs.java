package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.world.item.JukeboxSong;

public class AetherIIJukeboxSongs {
    public static ResourceKey<JukeboxSong> ASCENDING_DAWN = create("ascending_dawn");
    public static ResourceKey<JukeboxSong> AERWHALE = create("aerwhale");
    public static ResourceKey<JukeboxSong> APPROACHES = create("approaches");
    public static ResourceKey<JukeboxSong> DEMISE = create("demise");
    public static ResourceKey<JukeboxSong> CHINCHILLA = create("chinchilla");
    public static ResourceKey<JukeboxSong> HIGH = create("high");
    public static ResourceKey<JukeboxSong> REVOLUTIONS = create("revolutions");

    private static ResourceKey<JukeboxSong> create(String pName) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, Identifier.fromNamespaceAndPath(AetherII.MODID, pName));
    }

    public static void bootstrap(BootstrapContext<JukeboxSong> context) {
        register(context, ASCENDING_DAWN, (Holder.Reference<SoundEvent>) AetherIISoundEvents.ITEM_ENGRAVED_DISC_ASCENDING_DAWN.getDelegate(), 350, 2);
        register(context, AERWHALE, (Holder.Reference<SoundEvent>) AetherIISoundEvents.ITEM_ENGRAVED_DISC_AERWHALE.getDelegate(), 178, 3);
        register(context, APPROACHES, (Holder.Reference<SoundEvent>) AetherIISoundEvents.ITEM_ENGRAVED_DISC_APPROACHES.getDelegate(), 274, 4);
        register(context, DEMISE, (Holder.Reference<SoundEvent>) AetherIISoundEvents.ITEM_ENGRAVED_DISC_DEMISE.getDelegate(), 300, 5);
        register(context, CHINCHILLA, (Holder.Reference<SoundEvent>) AetherIISoundEvents.ITEM_ENGRAVED_DISC_CHINCHILLA.getDelegate(), 163, 6);
        register(context, HIGH, (Holder.Reference<SoundEvent>) AetherIISoundEvents.ITEM_ENGRAVED_DISC_HIGH.getDelegate(), 186, 7);
        register(context, REVOLUTIONS, (Holder.Reference<SoundEvent>) AetherIISoundEvents.ITEM_ENGRAVED_DISC_REVOLUTIONS.getDelegate(), 221, 8);
    }

    private static void register(BootstrapContext<JukeboxSong> context, ResourceKey<JukeboxSong> key, Holder.Reference<SoundEvent> soundEvent, int lengthInSeconds, int comparatorOutput) {
        context.register(key, new JukeboxSong(soundEvent, Component.translatable(Util.makeDescriptionId("jukebox_song", key.location())), (float) lengthInSeconds, comparatorOutput));
    }
}