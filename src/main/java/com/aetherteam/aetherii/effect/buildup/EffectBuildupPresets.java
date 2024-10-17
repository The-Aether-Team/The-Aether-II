package com.aetherteam.aetherii.effect.buildup;

import com.aetherteam.aetherii.effect.AetherIIEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.function.Function;
import java.util.function.Supplier;

public class EffectBuildupPresets {
    public static Preset WOUND = new Preset(AetherIIEffects.WOUND, (e) -> new MobEffectInstance(e, 1, 0, false, false), 2);
    public static Preset STUN = new Preset(AetherIIEffects.STUN, (e) -> new MobEffectInstance(e, 1000, 0, false, false), 2);
    public static Preset AMBROSIUM_POISONING = new Preset(AetherIIEffects.AMBROSIUM_POISONING, (e) -> new MobEffectInstance(e, 1000, 0, false, false), 2);
    public static Preset FRACTURE = new Preset(AetherIIEffects.FRACTURE, (e) -> new MobEffectInstance(e, 1000, 0, false, false), 1);
    public static Preset TOXIN = new Preset(AetherIIEffects.TOXIN, (e) -> new MobEffectInstance(e, 1000, 0, false, false), 2);
    public static Preset VENOM = new Preset(AetherIIEffects.VENOM, (e) -> new MobEffectInstance(e, 1000, 0, false, false), 2);
    public static Preset CHARGED = new Preset(AetherIIEffects.CHARGED, (e) -> new MobEffectInstance(e, 1000, 0, false, false), 2);
    public static Preset WEBBED = new Preset(AetherIIEffects.WEBBED, (e) -> new MobEffectInstance(e, 500, 0, false, false), 2);
    public static Preset IMMOLATION = new Preset(AetherIIEffects.IMMOLATION, (e) -> new MobEffectInstance(e, 1000, 0, false, false), 2);
    public static Preset FROSTBITE = new Preset(AetherIIEffects.FROSTBITE, (e) -> new MobEffectInstance(e, 1000, 0, false, false), 2);
    public static Preset FUNGAL_ROT = new Preset(AetherIIEffects.FUNGAL_ROT, (e) -> new MobEffectInstance(e, 1000, 0, false, false), 2);
    public static Preset CRYSTALLIZED = new Preset(AetherIIEffects.CRYSTALLIZED, (e) -> new MobEffectInstance(e, 1000, 0, false, false), 2);

    public static final class Preset {
        private final Holder<MobEffect> type;
        private final Supplier<MobEffectInstance> instanceBuilder;
        private final int buildupReductionRate;

        public Preset(Holder<MobEffect> type, Function<Holder<MobEffect>, MobEffectInstance> instanceBuilder, int buildupReductionRate) {
            this.type = type;
            this.instanceBuilder = () -> instanceBuilder.apply(type);
            this.buildupReductionRate = buildupReductionRate;
        }

        public Holder<MobEffect> type() {
            return this.type;
        }

        public Supplier<MobEffectInstance> instanceBuilder() {
            return this.instanceBuilder;
        }

        public int buildupReductionRate() {
            return this.buildupReductionRate;
        }
    }
}
