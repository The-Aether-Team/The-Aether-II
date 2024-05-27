package com.aetherteam.aetherii.effect.buildup;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import java.util.function.Supplier;

public class EffectBuildupPresets {
    public static Preset EXAMPLE = new Preset(MobEffects.POISON, () -> new MobEffectInstance(MobEffects.POISON, 100), 1000);
    public static Preset EXAMPLE_2 = new Preset(MobEffects.FIRE_RESISTANCE, () -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100), 1000);

    public record Preset(MobEffect type, Supplier<MobEffectInstance> instanceBuilder, int buildupCap) { } //todo all stats... removalIncrement? buildupIncrement?
}
