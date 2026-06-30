package com.aetherteam.aetherii.entity.variant;

import com.mojang.serialization.Codec;

import java.util.List;

public record SpawnPrioritySelectors(List<PriorityProvider.Selector<SpawnContext, SpawnCondition>> selectors) {
    public static final Codec<SpawnPrioritySelectors> CODEC = Codec.unit(fallback(1));

    public static SpawnPrioritySelectors fallback(int priority) {
        return new SpawnPrioritySelectors(List.of(new PriorityProvider.Selector<>(new SpawnCondition() {
            @Override
            public boolean test(SpawnContext context) {
                return true;
            }

            @Override
            public com.mojang.serialization.MapCodec<? extends SpawnCondition> codec() {
                return com.mojang.serialization.MapCodec.unit(this);
            }
        }, priority)));
    }
}
