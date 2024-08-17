package com.aetherteam.aetherii.effect.buildup;

import com.aetherteam.aetherii.effect.AetherIIEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.function.Function;
import java.util.function.Supplier;

public class EffectBuildupPresets {
    public static Preset TOXIN = new Preset(AetherIIEffects.TOXIN, (e) -> new MobEffectInstance(e, 1000, 0, false, false), 1000);
    public static Preset VENOM = new Preset(AetherIIEffects.VENOM, (e) -> new MobEffectInstance(e, 1000, 0, false, false), 1000);

    public static final class Preset {
        private final Holder<MobEffect> type;
        private final Supplier<MobEffectInstance> instanceBuilder;
        private final int buildupCap;

        public Preset(Holder<MobEffect> type, Function<Holder<MobEffect>, MobEffectInstance> instanceBuilder, int buildupCap) { //todo all stats... removalIncrement? buildupIncrement?
            this.type = type;
            this.instanceBuilder = () -> instanceBuilder.apply(type);
            this.buildupCap = buildupCap;
        }

        public Holder<MobEffect> type() {
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
