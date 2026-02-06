package com.aetherteam.aetherii.advancement.trigger;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.HolderSetCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;

import java.util.Optional;

public class EffectBuildupTrigger extends SimpleCriterionTrigger<EffectBuildupTrigger.Instance> {
    @Override
    public Codec<EffectBuildupTrigger.Instance> codec() {
        return EffectBuildupTrigger.Instance.CODEC;
    }

    public void trigger(ServerPlayer player, Entity source, Entity target, Holder<MobEffect> effect, boolean triggered) {
        this.trigger(player, (instance) -> instance.test(player, source, target, effect, triggered));
    }

    public record Instance(Optional<ContextAwarePredicate> player, Optional<EntityPredicate> directSource, Optional<EntityPredicate> target, HolderSet<MobEffect> mobEffects, boolean triggered) implements SimpleInstance {
        public static final Codec<EffectBuildupTrigger.Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(EffectBuildupTrigger.Instance::player),
                        EntityPredicate.CODEC.optionalFieldOf("direct_source").forGetter(EffectBuildupTrigger.Instance::directSource),
                        EntityPredicate.CODEC.optionalFieldOf("target").forGetter(EffectBuildupTrigger.Instance::target),
                        HolderSetCodec.create(Registries.MOB_EFFECT, MobEffect.CODEC, false).fieldOf("mob_effects").forGetter(EffectBuildupTrigger.Instance::mobEffects),
                        Codec.BOOL.fieldOf("triggered").forGetter(EffectBuildupTrigger.Instance::triggered))
                .apply(instance, EffectBuildupTrigger.Instance::new));

        public static Criterion<EffectBuildupTrigger.Instance> effect(Optional<EntityPredicate> directSource, Optional<EntityPredicate> target, HolderSet<MobEffect> mobEffects, boolean triggered) {
            return AetherIIAdvancementTriggers.EFFECT_BUILDUP.get().createCriterion(new EffectBuildupTrigger.Instance(Optional.empty(), directSource, target, mobEffects, triggered));
        }

        public boolean test(ServerPlayer serverPlayer, Entity directSource, Entity target, Holder<MobEffect> effect, boolean triggered) {
            if (this.directSource().isPresent() && !this.directSource().get().matches(serverPlayer, directSource)) {
                return false;
            }
            if (this.target().isPresent() && !this.target().get().matches(serverPlayer, target)) {
                return false;
            }
            if (!this.mobEffects().contains(effect)) {
                return false;
            }
            return this.triggered() == triggered;
        }
    }
}
