package com.aetherteam.aetherii.effect.buildup;

import com.aetherteam.aetherii.effect.AetherIIEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.function.Function;
import java.util.function.Supplier;

public class EffectBuildupPresets {
    public static Preset TOXIN = new Preset(AetherIIEffects.TOXIN.get(), (e) -> new MobEffectInstance(e, 500), 1000);

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
