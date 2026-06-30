package com.aetherteam.aetherii.entity.variant.spawning;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.aetherteam.aetherii.entity.variant.SpawnCondition;
import com.aetherteam.aetherii.entity.variant.SpawnContext;

public record RandomCheck(SpawnCondition condition, int bound, int check) implements SpawnCondition {
    public static final Codec<SpawnCondition> CONDITION_CODEC = SpawnCondition.CODEC;
    public static final MapCodec<RandomCheck> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            CONDITION_CODEC.fieldOf("condition").forGetter(RandomCheck::condition),
            Codec.INT.fieldOf("bound").forGetter(RandomCheck::bound),
            Codec.INT.fieldOf("check").forGetter(RandomCheck::check)
    ).apply(instance, RandomCheck::new));

    @Override
    public boolean test(SpawnContext spawnContext) {
        return this.condition.test(spawnContext) && spawnContext.level().getRandom().nextInt(this.bound()) >= this.check();
    }

    @Override
    public MapCodec<RandomCheck> codec() {
        return MAP_CODEC;
    }
}
