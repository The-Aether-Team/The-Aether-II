package com.aetherteam.aetherii.entity.variant;

import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public record SkyrootLizardVariant(ResourceLocation texture, Holder<Block> leafBlock) {
    public static final Codec<SkyrootLizardVariant> DIRECT_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter(SkyrootLizardVariant::texture),
            BuiltInRegistries.BLOCK.holderByNameCodec().fieldOf("leaf_block").forGetter(SkyrootLizardVariant::leafBlock)
    ).apply(instance, SkyrootLizardVariant::new));
    public static final StreamCodec<FriendlyByteBuf, SkyrootLizardVariant> DIRECT_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.RESOURCE_LOCATION, SkyrootLizardVariant::texture,
            ByteBufCodecs.holderRegistry(Registries.BLOCK), SkyrootLizardVariant::leafBlock,
            SkyrootLizardVariant::new);
    public static final Codec<Holder<SkyrootLizardVariant>> CODEC = RegistryFileCodec.create(AetherIIRegistries.SKYROOT_LIZARD_VARIANT, DIRECT_CODEC);
    public static final StreamCodec<FriendlyByteBuf, Holder<SkyrootLizardVariant>> STREAM_CODEC = ByteBufCodecs.holder(AetherIIRegistries.SKYROOT_LIZARD_VARIANT, DIRECT_STREAM_CODEC);
}
