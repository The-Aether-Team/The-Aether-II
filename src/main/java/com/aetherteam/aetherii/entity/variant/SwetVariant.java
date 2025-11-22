package com.aetherteam.aetherii.entity.variant;

import com.aetherteam.aetherii.data.resources.registries.AetherIISwetVariants;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;

public record SwetVariant(ResourceLocation texture, Holder<Item> gelItem, HolderSet<Biome> biomes, ResourceLocation left1, ResourceLocation left2, ResourceLocation right1, ResourceLocation right2) {
    public static final Codec<SwetVariant> DIRECT_CODEC = RecordCodecBuilder.create((p_332779_) -> p_332779_.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter(SwetVariant::texture),
            BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("gel_item").forGetter(SwetVariant::gelItem),
            RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(SwetVariant::biomes),
            ResourceLocation.CODEC.fieldOf("overlay_left_1").forGetter(SwetVariant::left1),
            ResourceLocation.CODEC.fieldOf("overlay_left_2").forGetter(SwetVariant::left2),
            ResourceLocation.CODEC.fieldOf("overlay_right_1").forGetter(SwetVariant::right1),
            ResourceLocation.CODEC.fieldOf("overlay_right_2").forGetter(SwetVariant::right2)
    ).apply(p_332779_, SwetVariant::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, SwetVariant> DIRECT_STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, SwetVariant::texture,
            ByteBufCodecs.holderRegistry(Registries.ITEM), SwetVariant::gelItem,
            ByteBufCodecs.holderSet(Registries.BIOME), SwetVariant::biomes,
            ResourceLocation.STREAM_CODEC, SwetVariant::left1,
            ResourceLocation.STREAM_CODEC, SwetVariant::left2,
            ResourceLocation.STREAM_CODEC, SwetVariant::right1,
            ResourceLocation.STREAM_CODEC, SwetVariant::right2,
            SwetVariant::new);
    public static final Codec<Holder<SwetVariant>> CODEC = RegistryFileCodec.create(AetherIISwetVariants.SWET_VARIANT_REGISTRY_KEY, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<SwetVariant>> STREAM_CODEC = ByteBufCodecs.holder(AetherIISwetVariants.SWET_VARIANT_REGISTRY_KEY, DIRECT_STREAM_CODEC);
}
