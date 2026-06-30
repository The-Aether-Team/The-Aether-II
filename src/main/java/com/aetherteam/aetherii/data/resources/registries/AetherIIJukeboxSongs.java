package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.Util;
import com.aetherteam.aetherii.item.components.JukeboxSong;

import static com.aetherteam.aetherii.util.RegistryObjectUtil.holder;

public class AetherIIJukeboxSongs {
    public static ResourceKey<JukeboxSong> ASCENDING_DAWN = create("ascending_dawn");
    public static ResourceKey<JukeboxSong> AERWHALE = create("aerwhale");
    public static ResourceKey<JukeboxSong> APPROACHES = create("approaches");
    public static ResourceKey<JukeboxSong> DEMISE = create("demise");
    public static ResourceKey<JukeboxSong> CHINCHILLA = create("chinchilla");
    public static ResourceKey<JukeboxSong> HIGH = create("high");
    public static ResourceKey<JukeboxSong> REVOLUTIONS = create("revolutions");

    private static ResourceKey<JukeboxSong> create(String pName) {
        return ResourceKey.create(JukeboxSong.REGISTRY_KEY, new ResourceLocation(AetherII.MODID, pName));
    }

    public static void bootstrap(BootstapContext<JukeboxSong> context) {
        register(context, ASCENDING_DAWN, holder(AetherIISoundEvents.ITEM_ENGRAVED_DISC_ASCENDING_DAWN), 350, 2);
        register(context, AERWHALE, holder(AetherIISoundEvents.ITEM_ENGRAVED_DISC_AERWHALE), 178, 3);
        register(context, APPROACHES, holder(AetherIISoundEvents.ITEM_ENGRAVED_DISC_APPROACHES), 274, 4);
        register(context, DEMISE, holder(AetherIISoundEvents.ITEM_ENGRAVED_DISC_DEMISE), 300, 5);
        register(context, CHINCHILLA, holder(AetherIISoundEvents.ITEM_ENGRAVED_DISC_CHINCHILLA), 163, 6);
        register(context, HIGH, holder(AetherIISoundEvents.ITEM_ENGRAVED_DISC_HIGH), 186, 7);
        register(context, REVOLUTIONS, holder(AetherIISoundEvents.ITEM_ENGRAVED_DISC_REVOLUTIONS), 221, 8);
    }

    private static void register(BootstapContext<JukeboxSong> context, ResourceKey<JukeboxSong> key, Holder<SoundEvent> soundEvent, int lengthInSeconds, int comparatorOutput) {
        context.register(key, new JukeboxSong(soundEvent, Component.translatable(Util.makeDescriptionId("jukebox_song", key.location())), (float) lengthInSeconds, comparatorOutput));
    }
}
