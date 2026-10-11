package com.aetherteam.aetherii.item.components;

import com.aetherteam.aetherii.entity.passive.Moa;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Optional;

public record MoaVariant(Moa.KeratinColor keratinColor, Moa.EyeColor eyeColor, Moa.FeatherColor featherColor, Moa.FeatherShape featherShape, Optional<Moa.SpecialVariant> specialVariant) {
    public static final Codec<MoaVariant> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
            Moa.KeratinColor.CODEC.fieldOf("keratin_color").forGetter(MoaVariant::keratinColor),
            Moa.EyeColor.CODEC.fieldOf("eye_color").forGetter(MoaVariant::eyeColor),
            Moa.FeatherColor.CODEC.fieldOf("feather_color").forGetter(MoaVariant::featherColor),
            Moa.FeatherShape.CODEC.fieldOf("feather_shape").forGetter(MoaVariant::featherShape),
            Moa.SpecialVariant.INT_CODEC.optionalFieldOf("special_variant").forGetter(MoaVariant::specialVariant)
    ).apply(builder, MoaVariant::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, MoaVariant> STREAM_CODEC = StreamCodec.composite(
            Moa.KeratinColor.STREAM_CODEC,
            MoaVariant::keratinColor,
            Moa.EyeColor.STREAM_CODEC,
            MoaVariant::eyeColor,
            Moa.FeatherColor.STREAM_CODEC,
            MoaVariant::featherColor,
            Moa.FeatherShape.STREAM_CODEC,
            MoaVariant::featherShape,
            Moa.SpecialVariant.STREAM_CODEC.apply(ByteBufCodecs::optional),
            MoaVariant::specialVariant,
            MoaVariant::new);

    public static final MoaVariant DEFAULT = new MoaVariant(Moa.KeratinColor.DEFAULT, Moa.EyeColor.DEFAULT, Moa.FeatherColor.DEFAULT, Moa.FeatherShape.DEFAULT, Optional.empty());

    public static MoaVariant defaultType() {
        return DEFAULT;
    }
}
