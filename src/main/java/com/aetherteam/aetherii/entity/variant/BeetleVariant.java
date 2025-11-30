package com.aetherteam.aetherii.entity.variant;

import com.aetherteam.aetherii.data.resources.registries.AetherIIBeetleVariants;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.variant.PriorityProvider;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;

import java.util.List;
import java.util.Optional;

public record BeetleVariant(ResourceLocation texture, Optional<ResourceLocation> emissiveTexture, SpawnPrioritySelectors spawnConditions) implements PriorityProvider<SpawnContext, SpawnCondition> {
    public static final Codec<BeetleVariant> DIRECT_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter(BeetleVariant::texture),
            ResourceLocation.CODEC.optionalFieldOf("emissive_texture").forGetter(BeetleVariant::emissiveTexture),
            SpawnPrioritySelectors.CODEC.fieldOf("spawn_conditions").fieldOf("spawn_biomes").forGetter(BeetleVariant::spawnConditions)
    ).apply(instance, BeetleVariant::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, BeetleVariant> DIRECT_STREAM_CODEC = ByteBufCodecs.registry(AetherIIBeetleVariants.BEETLE_VARIANT_REGISTRY_KEY);
    public static final Codec<Holder<BeetleVariant>> CODEC = RegistryFileCodec.create(AetherIIBeetleVariants.BEETLE_VARIANT_REGISTRY_KEY, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<BeetleVariant>> STREAM_CODEC = ByteBufCodecs.holder(AetherIIBeetleVariants.BEETLE_VARIANT_REGISTRY_KEY, DIRECT_STREAM_CODEC);

    public List<Selector<SpawnContext, SpawnCondition>> selectors() {
        return this.spawnConditions.selectors();
    }
}
