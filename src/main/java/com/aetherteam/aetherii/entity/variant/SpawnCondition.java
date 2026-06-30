package com.aetherteam.aetherii.entity.variant;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

public interface SpawnCondition {
    Codec<SpawnCondition> CODEC = Codec.unit(new SpawnCondition() {
        @Override
        public boolean test(SpawnContext context) {
            return true;
        }

        @Override
        public MapCodec<? extends SpawnCondition> codec() {
            return MapCodec.unit(this);
        }
    });

    boolean test(SpawnContext context);

    MapCodec<? extends SpawnCondition> codec();
}
