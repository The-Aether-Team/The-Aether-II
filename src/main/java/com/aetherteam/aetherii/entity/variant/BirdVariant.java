package com.aetherteam.aetherii.entity.variant;

import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.entity.passive.Bird;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.entity.variant.PriorityProvider;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;

import java.util.List;
import java.util.Optional;

public record BirdVariant(Identifier texture, Bird.BirdType type, SpawnPrioritySelectors spawnConditions) implements PriorityProvider<SpawnContext, SpawnCondition> {
    public static final Codec<BirdVariant> DIRECT_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Identifier.CODEC.fieldOf("texture").forGetter(BirdVariant::texture),
            Bird.BirdType.CODEC.fieldOf("type").forGetter(BirdVariant::type),
            SpawnPrioritySelectors.CODEC.fieldOf("spawn_conditions").fieldOf("spawn_biomes").forGetter(BirdVariant::spawnConditions)
    ).apply(instance, BirdVariant::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, BirdVariant> DIRECT_STREAM_CODEC = ByteBufCodecs.registry(AetherIIRegistries.BIRD_VARIANT);
    public static final Codec<Holder<BirdVariant>> CODEC = RegistryFileCodec.create(AetherIIRegistries.BIRD_VARIANT, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<BirdVariant>> STREAM_CODEC = ByteBufCodecs.holder(AetherIIRegistries.BIRD_VARIANT, DIRECT_STREAM_CODEC);

    public List<Selector<SpawnContext, SpawnCondition>> selectors() {
        return this.spawnConditions.selectors();
    }
}
