package com.aetherteam.aetherii.data.resources.maps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;

public record MimicTransform(ResourceKey<EntityType<?>> entityType) {
    public static final Codec<MimicTransform> ENTITY_TYPE_CODEC = ResourceKey.codec(Registries.ENTITY_TYPE).xmap(MimicTransform::new, MimicTransform::entityType);

    public static final Codec<MimicTransform> CODEC = RecordCodecBuilder.create((instance) -> instance.group(ResourceKey.codec(Registries.ENTITY_TYPE).fieldOf("entity_type").forGetter(MimicTransform::entityType)).apply(instance, MimicTransform::new));
}
