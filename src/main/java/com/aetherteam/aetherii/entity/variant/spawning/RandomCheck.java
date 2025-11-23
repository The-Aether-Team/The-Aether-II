package com.aetherteam.aetherii.entity.variant.spawning;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

import java.util.function.Function;

public record RandomCheck(SpawnCondition condition, int bound, int check) implements SpawnCondition {
    public static final Codec<SpawnCondition> CONDITION_CODEC = BuiltInRegistries.SPAWN_CONDITION_TYPE.byNameCodec().dispatch(SpawnCondition::codec, Function.identity());
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
