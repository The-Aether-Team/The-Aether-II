package com.aetherteam.aetherii.api;

import com.aetherteam.aetherii.data.resources.registries.AetherIISkyrootLizardVariants;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public record SkyrootLizardVariant(ResourceLocation texture, Holder<Block> leafBlock) {
    public static final Codec<SkyrootLizardVariant> DIRECT_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter(SkyrootLizardVariant::texture),
            BuiltInRegistries.BLOCK.holderByNameCodec().fieldOf("leaf_block").forGetter(SkyrootLizardVariant::leafBlock)
    ).apply(instance, SkyrootLizardVariant::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, SkyrootLizardVariant> DIRECT_STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, SkyrootLizardVariant::texture,
            ByteBufCodecs.holderRegistry(Registries.BLOCK), SkyrootLizardVariant::leafBlock,
            SkyrootLizardVariant::new);
    public static final Codec<Holder<SkyrootLizardVariant>> CODEC = RegistryFileCodec.create(AetherIISkyrootLizardVariants.SKYROOT_LIZARD_VARIANT_REGISTRY_KEY, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<SkyrootLizardVariant>> STREAM_CODEC = ByteBufCodecs.holder(AetherIISkyrootLizardVariants.SKYROOT_LIZARD_VARIANT_REGISTRY_KEY, DIRECT_STREAM_CODEC);
}
