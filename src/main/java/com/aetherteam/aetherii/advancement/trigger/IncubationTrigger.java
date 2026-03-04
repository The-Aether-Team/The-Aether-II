package com.aetherteam.aetherii.advancement.trigger;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import java.util.Optional;

public class IncubationTrigger extends SimpleCriterionTrigger<IncubationTrigger.Instance> {
    @Override
    public Codec<Instance> codec() {
        return IncubationTrigger.Instance.CODEC;
    }

    public void trigger(ServerPlayer player, Entity entity) {
        this.trigger(player, (instance) -> instance.test(player, entity));
    }

    public record Instance(Optional<ContextAwarePredicate> player, Optional<EntityPredicate> entity) implements SimpleInstance {
        public static final Codec<IncubationTrigger.Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(IncubationTrigger.Instance::player),
                        EntityPredicate.CODEC.optionalFieldOf("entity").forGetter(IncubationTrigger.Instance::entity))
                .apply(instance, IncubationTrigger.Instance::new));

        public static Criterion<Instance> incubate() {
            return AetherIIAdvancementTriggers.INCUBATION.get().createCriterion(new IncubationTrigger.Instance(Optional.empty(), Optional.empty()));
        }

        public static Criterion<Instance> incubateEntity(EntityPredicate entity) {
            return AetherIIAdvancementTriggers.INCUBATION.get().createCriterion(new IncubationTrigger.Instance(Optional.empty(), Optional.of(entity)));
        }

        public boolean test(ServerPlayer serverPlayer, Entity entity) {
            if (this.entity.isPresent()) {
                return this.entity().get().matches(serverPlayer, entity);
            }
            return true;
        }
    }
}