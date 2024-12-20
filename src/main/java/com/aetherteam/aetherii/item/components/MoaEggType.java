package com.aetherteam.aetherii.item.components;

import com.aetherteam.aetherii.entity.passive.Moa;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record MoaEggType(Moa.KeratinColor keratinColor, Moa.EyeColor eyeColor, Moa.FeatherColor featherColor, Moa.FeatherShape featherShape) {
    public static final Codec<MoaEggType> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
            Moa.KeratinColor.CODEC.fieldOf("keratin_color").forGetter(MoaEggType::keratinColor),
            Moa.EyeColor.CODEC.fieldOf("eye_color").forGetter(MoaEggType::eyeColor),
            Moa.FeatherColor.CODEC.fieldOf("feather_color").forGetter(MoaEggType::featherColor),
            Moa.FeatherShape.CODEC.fieldOf("feather_shape").forGetter(MoaEggType::featherShape)
    ).apply(builder, MoaEggType::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, MoaEggType> STREAM_CODEC = StreamCodec.composite(
            Moa.KeratinColor.STREAM_CODEC,
            MoaEggType::keratinColor,
            Moa.EyeColor.STREAM_CODEC,
            MoaEggType::eyeColor,
            Moa.FeatherColor.STREAM_CODEC,
            MoaEggType::featherColor,
            Moa.FeatherShape.STREAM_CODEC,
            MoaEggType::featherShape,
            MoaEggType::new);

    public static MoaEggType defaultType() {
        return new MoaEggType(Moa.KeratinColor.GRAY, Moa.EyeColor.BLUE, Moa.FeatherColor.LIGHT_BLUE, Moa.FeatherShape.CURVED);
    }
}
