package com.aetherteam.aetherii.item.components;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.util.ComponentSerialization;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public record JukeboxSong(Holder<SoundEvent> soundEvent, Component description, float lengthInSeconds, int comparatorOutput) {
    public static final ResourceKey<net.minecraft.core.Registry<JukeboxSong>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(AetherII.MODID, "jukebox_song"));
    public static final Codec<JukeboxSong> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BuiltInRegistries.SOUND_EVENT.holderByNameCodec().fieldOf("sound_event").forGetter(JukeboxSong::soundEvent),
            ComponentSerialization.CODEC.fieldOf("description").forGetter(JukeboxSong::description),
            Codec.FLOAT.fieldOf("length_in_seconds").forGetter(JukeboxSong::lengthInSeconds),
            Codec.intRange(0, 15).fieldOf("comparator_output").forGetter(JukeboxSong::comparatorOutput)
    ).apply(instance, JukeboxSong::new));
    public static final StreamCodec<FriendlyByteBuf, JukeboxSong> DIRECT_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.holderRegistry(net.minecraft.core.registries.Registries.SOUND_EVENT), JukeboxSong::soundEvent,
            ComponentSerialization.STREAM_CODEC, JukeboxSong::description,
            ByteBufCodecs.FLOAT, JukeboxSong::lengthInSeconds,
            ByteBufCodecs.VAR_INT, JukeboxSong::comparatorOutput,
            JukeboxSong::new);
    public static final Codec<Holder<JukeboxSong>> CODEC = RegistryFileCodec.create(REGISTRY_KEY, DIRECT_CODEC);
    public static final StreamCodec<FriendlyByteBuf, Holder<JukeboxSong>> STREAM_CODEC = ByteBufCodecs.holder(REGISTRY_KEY, DIRECT_STREAM_CODEC);
}
