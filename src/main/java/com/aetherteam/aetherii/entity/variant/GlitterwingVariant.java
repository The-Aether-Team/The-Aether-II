package com.aetherteam.aetherii.entity.variant;

import com.aetherteam.aetherii.data.resources.registries.AetherIIGlitterwingVariants;
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

public record GlitterwingVariant(ResourceLocation texture, Optional<ResourceLocation> emissiveTexture, SpawnPrioritySelectors spawnConditions, float wingXOffset, float wingZRotation) implements PriorityProvider<SpawnContext, SpawnCondition> {
    public static final Codec<GlitterwingVariant> DIRECT_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter(GlitterwingVariant::texture),
            ResourceLocation.CODEC.optionalFieldOf("emissive_texture").forGetter(GlitterwingVariant::emissiveTexture),
            SpawnPrioritySelectors.CODEC.fieldOf("spawn_conditions").fieldOf("spawn_biomes").forGetter(GlitterwingVariant::spawnConditions),
            Codec.FLOAT.fieldOf("wing_x_offset").forGetter(GlitterwingVariant::wingXOffset),
            Codec.FLOAT.fieldOf("wing_z_rotation").forGetter(GlitterwingVariant::wingZRotation)
    ).apply(instance, GlitterwingVariant::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, GlitterwingVariant> DIRECT_STREAM_CODEC = ByteBufCodecs.registry(AetherIIGlitterwingVariants.GLITTERWING_VARIANT_REGISTRY_KEY);
    public static final Codec<Holder<GlitterwingVariant>> CODEC = RegistryFileCodec.create(AetherIIGlitterwingVariants.GLITTERWING_VARIANT_REGISTRY_KEY, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<GlitterwingVariant>> STREAM_CODEC = ByteBufCodecs.holder(AetherIIGlitterwingVariants.GLITTERWING_VARIANT_REGISTRY_KEY, DIRECT_STREAM_CODEC);

    public List<Selector<SpawnContext, SpawnCondition>> selectors() {
        return this.spawnConditions.selectors();
    }
}
