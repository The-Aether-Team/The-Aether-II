package com.aetherteam.aetherii.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import com.aetherteam.aetherii.item.components.JukeboxSong;

public record StoredMusic(Holder<Item> item, Holder<JukeboxSong> song) {
    public static final Codec<StoredMusic> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
            BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("item").forGetter(StoredMusic::item),
            JukeboxSong.CODEC.fieldOf("song").forGetter(StoredMusic::song)
    ).apply(builder, StoredMusic::new));
    public static final StreamCodec<FriendlyByteBuf, StoredMusic> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.holderRegistry(Registries.ITEM), StoredMusic::item,
            JukeboxSong.STREAM_CODEC, StoredMusic::song,
            StoredMusic::new);

    public Holder<SoundEvent> getSoundEvent() {
        return Holder.direct(SoundEvent.createVariableRangeEvent(this.song().value().soundEvent().value().getLocation()));
    }
}
