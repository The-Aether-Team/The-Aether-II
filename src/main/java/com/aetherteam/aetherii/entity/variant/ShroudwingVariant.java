package com.aetherteam.aetherii.entity.variant;

import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.data.resources.registries.AetherIIShroudwingVariants;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import com.aetherteam.aetherii.entity.variant.PriorityProvider;
import com.aetherteam.aetherii.entity.variant.SpawnCondition;
import com.aetherteam.aetherii.entity.variant.SpawnContext;
import com.aetherteam.aetherii.entity.variant.SpawnPrioritySelectors;

import java.util.List;
import java.util.Optional;

public record ShroudwingVariant(ResourceLocation texture, Optional<ResourceLocation> emissiveTexture, SpawnPrioritySelectors spawnConditions) implements PriorityProvider<SpawnContext, SpawnCondition> {
    public static final Codec<ShroudwingVariant> DIRECT_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter(ShroudwingVariant::texture),
            ResourceLocation.CODEC.optionalFieldOf("emissive_texture").forGetter(ShroudwingVariant::emissiveTexture),
            SpawnPrioritySelectors.CODEC.fieldOf("spawn_conditions").fieldOf("spawn_biomes").forGetter(ShroudwingVariant::spawnConditions)
    ).apply(instance, ShroudwingVariant::new));
    public static final StreamCodec<FriendlyByteBuf, ShroudwingVariant> DIRECT_STREAM_CODEC = ByteBufCodecs.registry(AetherIIRegistries.SHROUDWING_VARIANT);
    public static final Codec<Holder<ShroudwingVariant>> CODEC = RegistryFileCodec.create(AetherIIRegistries.SHROUDWING_VARIANT, DIRECT_CODEC);
    public static final StreamCodec<FriendlyByteBuf, Holder<ShroudwingVariant>> STREAM_CODEC = ByteBufCodecs.holder(AetherIIRegistries.SHROUDWING_VARIANT, DIRECT_STREAM_CODEC);

    public List<Selector<SpawnContext, SpawnCondition>> selectors() {
        return this.spawnConditions.selectors();
    }
}
