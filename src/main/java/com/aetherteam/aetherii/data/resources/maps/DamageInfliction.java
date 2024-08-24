package com.aetherteam.aetherii.data.resources.maps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.ArrayList;
import java.util.List;

public record DamageInfliction(double slashValue, double impactValue, double pierceValue) {
    public static final Codec<DamageInfliction> INFLICTION_CODEC = Codec.DOUBLE.sizeLimitedListOf(3)
            .xmap((doubles) -> new DamageInfliction(doubles.get(0), doubles.get(1), doubles.get(2)), (infliction) -> new ArrayList<>(List.of(infliction.slashValue(), infliction.impactValue(), infliction.pierceValue())));

    public static final Codec<DamageInfliction> CODEC =
            Codec.withAlternative(RecordCodecBuilder.create(in -> in.group(
                    Codec.DOUBLE.fieldOf("slash_value").forGetter(DamageInfliction::slashValue),
                    Codec.DOUBLE.fieldOf("impact_value").forGetter(DamageInfliction::impactValue),
                    Codec.DOUBLE.fieldOf("pierce_value").forGetter(DamageInfliction::pierceValue)
            ).apply(in, DamageInfliction::new)), INFLICTION_CODEC);
}
