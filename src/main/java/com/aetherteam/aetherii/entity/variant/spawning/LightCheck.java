package com.aetherteam.aetherii.entity.variant.spawning;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.MinMaxBounds;
import com.aetherteam.aetherii.entity.variant.SpawnCondition;
import com.aetherteam.aetherii.entity.variant.SpawnContext;

import java.util.Optional;
import java.util.function.Function;

public record LightCheck(MinMaxBounds.Ints range) implements SpawnCondition {
    private static final Codec<MinMaxBounds.Ints> RANGE_OBJECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf("min").forGetter(bounds -> Optional.ofNullable(bounds.getMin())),
            Codec.INT.optionalFieldOf("max").forGetter(bounds -> Optional.ofNullable(bounds.getMax()))
    ).apply(instance, LightCheck::range));
    private static final Codec<MinMaxBounds.Ints> RANGE_CODEC = Codec.either(Codec.INT, RANGE_OBJECT_CODEC).xmap(
            either -> either.map(MinMaxBounds.Ints::exactly, Function.identity()),
            bounds -> bounds.getMin() != null && bounds.getMin().equals(bounds.getMax())
                    ? Either.left(bounds.getMin())
                    : Either.right(bounds)
    );
    public static final MapCodec<LightCheck> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            RANGE_CODEC.fieldOf("range").forGetter(LightCheck::range)
    ).apply(instance, LightCheck::new));

    private static MinMaxBounds.Ints range(Optional<Integer> min, Optional<Integer> max) {
        if (min.isPresent() && max.isPresent()) {
            return MinMaxBounds.Ints.between(min.get(), max.get());
        } else if (min.isPresent()) {
            return MinMaxBounds.Ints.atLeast(min.get());
        } else if (max.isPresent()) {
            return MinMaxBounds.Ints.atMost(max.get());
        }
        return MinMaxBounds.Ints.ANY;
    }

    @Override
    public boolean test(SpawnContext context) {
        int lightLevel = context.level().getLevel().isThundering()
                ? context.level().getMaxLocalRawBrightness(context.pos(), 10)
                : context.level().getMaxLocalRawBrightness(context.pos());
        return this.range().matches(lightLevel);
    }

    @Override
    public MapCodec<LightCheck> codec() {
        return MAP_CODEC;
    }
}
