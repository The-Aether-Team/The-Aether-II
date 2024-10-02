package com.aetherteam.aetherii.api.entity;

import com.aetherteam.aetherii.data.resources.registries.AetherIIMoaFeatherShapes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public record MoaFeatherShape(double speed, double stamina, double strength, ResourceLocation texture) {
    public static final Codec<MoaFeatherShape> DIRECT_CODEC =
            RecordCodecBuilder.create(in -> in.group(
                    Codec.DOUBLE.fieldOf("speed").forGetter(MoaFeatherShape::speed),
                    Codec.DOUBLE.fieldOf("stamina").forGetter(MoaFeatherShape::stamina),
                    Codec.DOUBLE.fieldOf("strength").forGetter(MoaFeatherShape::strength),
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(MoaFeatherShape::texture)
            ).apply(in, MoaFeatherShape::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<MoaFeatherShape>> STREAM_CODEC = ByteBufCodecs.holderRegistry(AetherIIMoaFeatherShapes.MOA_FEATHER_SHAPE_REGISTRY_KEY);
}