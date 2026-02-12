package com.aetherteam.aetherii.entity.variant.spawning;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

public record LightCheck(MinMaxBounds.Ints range) implements SpawnCondition {
    public static final MapCodec<LightCheck> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            MinMaxBounds.Ints.CODEC.fieldOf("range").forGetter(LightCheck::range)
    ).apply(instance, LightCheck::new));

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
