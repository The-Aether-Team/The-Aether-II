package com.aetherteam.aetherii.effect.buildup;

import com.aetherteam.aetherii.effect.AetherIIEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.function.Function;
import java.util.function.Supplier;

public class EffectBuildupPresets {
    public static Preset WOUND = new Preset(AetherIIEffects.WOUND, (e) -> new MobEffectInstance(e, 1, 1, false, false));
    public static Preset FRACTURE = new Preset(AetherIIEffects.FRACTURE, (e) -> new MobEffectInstance(e, 1000, 0, false, false));
    public static Preset TOXIN = new Preset(AetherIIEffects.TOXIN, (e) -> new MobEffectInstance(e, 1000, 0, false, false));
    public static Preset VENOM = new Preset(AetherIIEffects.VENOM, (e) -> new MobEffectInstance(e, 1000, 0, false, false));

    public static final class Preset {
        private final Holder<MobEffect> type;
        private final Supplier<MobEffectInstance> instanceBuilder;

        public Preset(Holder<MobEffect> type, Function<Holder<MobEffect>, MobEffectInstance> instanceBuilder) { //todo all stats... removalIncrement? buildupIncrement?
            this.type = type;
            this.instanceBuilder = () -> instanceBuilder.apply(type);
        }

        public Holder<MobEffect> type() {
            return this.type;
        }

        public Supplier<MobEffectInstance> instanceBuilder() {
            return this.instanceBuilder;
        }
    }
}
