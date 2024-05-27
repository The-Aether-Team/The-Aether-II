package com.aetherteam.aetherii.effect.buildup;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import java.util.function.Function;
import java.util.function.Supplier;

public class EffectBuildupPresets {
    public static Preset EXAMPLE = new Preset(MobEffects.POISON, (e) -> new MobEffectInstance(e, 100), 1000);
    public static Preset EXAMPLE_2 = new Preset(MobEffects.FIRE_RESISTANCE, (e) -> new MobEffectInstance(e, 100), 1000);

    public static final class Preset {
        private final MobEffect type;
        private final Supplier<MobEffectInstance> instanceBuilder;
        private final int buildupCap;

        public Preset(MobEffect type, Function<MobEffect, MobEffectInstance> instanceBuilder, int buildupCap) { //todo all stats... removalIncrement? buildupIncrement?
            this.type = type;
            this.instanceBuilder = () -> instanceBuilder.apply(type);
            this.buildupCap = buildupCap;
        }

        public MobEffect type() {
            return this.type;
        }

        public Supplier<MobEffectInstance> instanceBuilder() {
            return this.instanceBuilder;
        }

        public int buildupCap() {
            return this.buildupCap;
        }
    }
}
