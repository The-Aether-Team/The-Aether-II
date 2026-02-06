package com.aetherteam.aetherii.advancement.trigger;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class CurrencyTrigger extends SimpleCriterionTrigger<CurrencyTrigger.Instance> {
    @Override
    public Codec<CurrencyTrigger.Instance> codec() {
        return CurrencyTrigger.Instance.CODEC;
    }

    public void trigger(ServerPlayer player, int amount) {
        this.trigger(player, (instance) -> amount >= instance.amount());
    }

    public record Instance(Optional<ContextAwarePredicate> player, int amount) implements SimpleInstance {
        public static final Codec<CurrencyTrigger.Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(CurrencyTrigger.Instance::player),
                        Codec.INT.fieldOf("amount").forGetter(CurrencyTrigger.Instance::amount))
                .apply(instance, CurrencyTrigger.Instance::new));

        public static Criterion<CurrencyTrigger.Instance> forValue(int amount) {
            return AetherIIAdvancementTriggers.CURRENCY.get().createCriterion(new CurrencyTrigger.Instance(Optional.empty(), amount));
        }
    }
}
