package com.aetherteam.aetherii.item.components;

import java.util.Optional;

import com.aetherteam.aetherii.entity.passive.Moa;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.StreamCodec;

public record MoaVariant(Moa.KeratinColor keratinColor, Moa.EyeColor eyeColor, Moa.FeatherColor featherColor, Moa.FeatherShape featherShape, Optional<Moa.SpecialVariant> specialVariant) {
    public static final Codec<MoaVariant> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
            Moa.KeratinColor.CODEC.fieldOf("keratin_color").forGetter(MoaVariant::keratinColor),
            Moa.EyeColor.CODEC.fieldOf("eye_color").forGetter(MoaVariant::eyeColor),
            Moa.FeatherColor.CODEC.fieldOf("feather_color").forGetter(MoaVariant::featherColor),
            Moa.FeatherShape.CODEC.fieldOf("feather_shape").forGetter(MoaVariant::featherShape),
            Moa.SpecialVariant.INT_CODEC.optionalFieldOf("special_variant").forGetter(MoaVariant::specialVariant)
    ).apply(builder, MoaVariant::new));
    public static final StreamCodec<FriendlyByteBuf, MoaVariant> STREAM_CODEC = StreamCodec.of((buffer, value) -> {
        Moa.KeratinColor.STREAM_CODEC.encode(buffer, value.keratinColor());
        Moa.EyeColor.STREAM_CODEC.encode(buffer, value.eyeColor());
        Moa.FeatherColor.STREAM_CODEC.encode(buffer, value.featherColor());
        Moa.FeatherShape.STREAM_CODEC.encode(buffer, value.featherShape());
        buffer.writeBoolean(value.specialVariant().isPresent());
        value.specialVariant().ifPresent((variant) -> Moa.SpecialVariant.STREAM_CODEC.encode(buffer, variant));
    }, (buffer) -> new MoaVariant(
            Moa.KeratinColor.STREAM_CODEC.decode(buffer),
            Moa.EyeColor.STREAM_CODEC.decode(buffer),
            Moa.FeatherColor.STREAM_CODEC.decode(buffer),
            Moa.FeatherShape.STREAM_CODEC.decode(buffer),
            buffer.readBoolean() ? Optional.of(Moa.SpecialVariant.STREAM_CODEC.decode(buffer)) : Optional.empty()));

    public static final MoaVariant DEFAULT = new MoaVariant(Moa.KeratinColor.DEFAULT, Moa.EyeColor.DEFAULT, Moa.FeatherColor.DEFAULT, Moa.FeatherShape.DEFAULT, Optional.empty());

    public static MoaVariant defaultType() {
        return DEFAULT;
    }
}
