package com.aetherteam.aetherii.entity.variant;

import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.entity.passive.Cherub;
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

public record CherubVariant(Identifier texture, Cherub.CherubType type, SpawnPrioritySelectors spawnConditions) implements PriorityProvider<SpawnContext, SpawnCondition> {
    public static final Codec<CherubVariant> DIRECT_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Identifier.CODEC.fieldOf("texture").forGetter(CherubVariant::texture),
            Cherub.CherubType.CODEC.fieldOf("type").forGetter(CherubVariant::type),
            SpawnPrioritySelectors.CODEC.fieldOf("spawn_conditions").fieldOf("spawn_biomes").forGetter(CherubVariant::spawnConditions)
    ).apply(instance, CherubVariant::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, CherubVariant> DIRECT_STREAM_CODEC = ByteBufCodecs.registry(AetherIIRegistries.CHERUB_VARIANT);
    public static final Codec<Holder<CherubVariant>> CODEC = RegistryFileCodec.create(AetherIIRegistries.CHERUB_VARIANT, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<CherubVariant>> STREAM_CODEC = ByteBufCodecs.holder(AetherIIRegistries.CHERUB_VARIANT, DIRECT_STREAM_CODEC);

    public List<Selector<SpawnContext, SpawnCondition>> selectors() {
        return this.spawnConditions.selectors();
    }
}
