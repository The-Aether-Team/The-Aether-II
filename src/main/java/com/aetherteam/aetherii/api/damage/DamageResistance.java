package com.aetherteam.aetherii.api.damage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;

public record DamageResistance(EntityType<?> entityType, int slashValue, int impactValue, int pierceValue) {
    public static final Codec<DamageResistance> CODEC =
            RecordCodecBuilder.create(in -> in.group(
                    BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("entity_type").forGetter(DamageResistance::entityType),
                    Codec.INT.fieldOf("slash_value").forGetter(DamageResistance::slashValue),
                    Codec.INT.fieldOf("impact_value").forGetter(DamageResistance::impactValue),
                    Codec.INT.fieldOf("pierce_value").forGetter(DamageResistance::pierceValue)
            ).apply(in, DamageResistance::new));
}
