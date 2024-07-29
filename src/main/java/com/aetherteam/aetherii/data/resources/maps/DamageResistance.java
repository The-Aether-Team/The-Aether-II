package com.aetherteam.aetherii.data.resources.maps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public record DamageResistance(double slashValue, double impactValue, double pierceValue) {
    public static final Codec<DamageResistance> RESISTANCE_CODEC = Codec.DOUBLE.sizeLimitedListOf(3)
            .xmap((doubles) -> new DamageResistance(doubles.get(0), doubles.get(1), doubles.get(2)), (resistance) -> new ArrayList<>(List.of(resistance.slashValue(), resistance.impactValue(), resistance.pierceValue())));

    public static final Codec<DamageResistance> CODEC =
            Codec.withAlternative(RecordCodecBuilder.create(in -> in.group(
                    Codec.DOUBLE.fieldOf("slash_value").forGetter(DamageResistance::slashValue),
                    Codec.DOUBLE.fieldOf("impact_value").forGetter(DamageResistance::impactValue),
                    Codec.DOUBLE.fieldOf("pierce_value").forGetter(DamageResistance::pierceValue)
            ).apply(in, DamageResistance::new)), RESISTANCE_CODEC);
}
