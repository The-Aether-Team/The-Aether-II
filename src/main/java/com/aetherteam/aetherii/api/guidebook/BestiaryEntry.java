package com.aetherteam.aetherii.api.guidebook;

import com.aetherteam.aetherii.data.resources.registries.AetherIIBestiaryEntries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public record BestiaryEntry(Holder<EntityType<?>> entityType, ResourceLocation icon, ResourceLocation iconUndiscovered) {
    public static final Codec<BestiaryEntry> DIRECT_CODEC =
            RecordCodecBuilder.create(in -> in.group(
                    BuiltInRegistries.ENTITY_TYPE.holderByNameCodec().fieldOf("entity_type").forGetter(BestiaryEntry::entityType),
                    ResourceLocation.CODEC.fieldOf("icon").forGetter(BestiaryEntry::icon),
                    ResourceLocation.CODEC.fieldOf("icon_undiscovered").forGetter(BestiaryEntry::iconUndiscovered)
            ).apply(in, BestiaryEntry::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<BestiaryEntry>> STREAM_CODEC = ByteBufCodecs.holderRegistry(AetherIIBestiaryEntries.BESTIARY_ENTRY_REGISTRY_KEY);
}
