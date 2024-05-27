package com.aetherteam.aetherii.effect.buildup;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class EffectBuildupPresets {
    public static Preset EXAMPLE = new Preset(new MobEffectInstance(MobEffects.POISON, 100), 1000);
    public static Preset EXAMPLE_2 = new Preset(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100), 1000);

    public record Preset(MobEffectInstance effect, int buildupCap) { } //todo all stats... removalIncrement? buildupIncrement?
}
