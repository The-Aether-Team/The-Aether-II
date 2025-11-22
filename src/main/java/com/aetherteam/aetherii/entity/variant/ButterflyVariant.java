package com.aetherteam.aetherii.entity.variant;

import com.aetherteam.aetherii.data.resources.registries.AetherIIButterflyVariants;
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

public record ButterflyVariant(ResourceLocation texture, Optional<ResourceLocation> emissiveTexture, SpawnPrioritySelectors spawnConditions, float wingXOffset, float wingZRotation) implements PriorityProvider<SpawnContext, SpawnCondition> {
    public static final Codec<ButterflyVariant> DIRECT_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter(ButterflyVariant::texture),
            ResourceLocation.CODEC.optionalFieldOf("emissive_texture").forGetter(ButterflyVariant::emissiveTexture),
            SpawnPrioritySelectors.CODEC.fieldOf("spawn_conditions").fieldOf("spawn_biomes").forGetter(ButterflyVariant::spawnConditions),
            Codec.FLOAT.fieldOf("wing_x_offset").forGetter(ButterflyVariant::wingXOffset),
            Codec.FLOAT.fieldOf("wing_z_rotation").forGetter(ButterflyVariant::wingZRotation)
    ).apply(instance, ButterflyVariant::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ButterflyVariant> DIRECT_STREAM_CODEC = ByteBufCodecs.registry(AetherIIButterflyVariants.BUTTERFLY_VARIANT_REGISTRY_KEY);
    public static final Codec<Holder<ButterflyVariant>> CODEC = RegistryFileCodec.create(AetherIIButterflyVariants.BUTTERFLY_VARIANT_REGISTRY_KEY, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<ButterflyVariant>> STREAM_CODEC = ByteBufCodecs.holder(AetherIIButterflyVariants.BUTTERFLY_VARIANT_REGISTRY_KEY, DIRECT_STREAM_CODEC);

    public List<Selector<SpawnContext, SpawnCondition>> selectors() {
        return this.spawnConditions.selectors();
    }
}
