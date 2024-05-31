package com.aetherteam.aetherii.mixin.mixins.common.accessor;

import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PerlinNoise.class)
public interface PerlinNoiseAccessor {

    @Invoker
    default double callMaxValue() {
        throw new AssertionError();
    }
}