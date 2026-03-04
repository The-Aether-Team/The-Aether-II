package com.aetherteam.aetherii.advancement.trigger;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class OutpostCampfireTrigger extends SimpleCriterionTrigger<OutpostCampfireTrigger.Instance> {
    @Override
    public Codec<Instance> codec() {
        return Instance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, (instance) -> true);
    }

    public record Instance(Optional<ContextAwarePredicate> player) implements SimpleInstance {
        public static final Codec<Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(OutpostCampfireTrigger.Instance::player))
                .apply(instance, OutpostCampfireTrigger.Instance::new));

        public static Criterion<Instance> setSpawn() {
            return AetherIIAdvancementTriggers.OUTPOST_CAMPFIRE.get().createCriterion(new OutpostCampfireTrigger.Instance(Optional.empty()));
        }
    }
}