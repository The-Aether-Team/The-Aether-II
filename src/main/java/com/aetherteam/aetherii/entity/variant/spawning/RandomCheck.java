package com.aetherteam.aetherii.entity.variant.spawning;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

public record RandomCheck(int bound, int check) implements SpawnCondition {
    public static final MapCodec<RandomCheck> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("bound").forGetter(RandomCheck::bound),
            Codec.INT.fieldOf("check").forGetter(RandomCheck::check)
    ).apply(instance, RandomCheck::new));

    @Override
    public boolean test(SpawnContext spawnContext) {
        return spawnContext.level().getRandom().nextInt(this.bound()) > this.check();
    }

    @Override
    public MapCodec<RandomCheck> codec() {
        return MAP_CODEC;
    }
}
