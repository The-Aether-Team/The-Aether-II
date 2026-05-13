package com.aetherteam.aetherii.advancement.trigger;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class FallOnGroundTrigger extends SimpleCriterionTrigger<FallOnGroundTrigger.Instance> {
    @Override
    public Codec<FallOnGroundTrigger.Instance> codec() {
        return FallOnGroundTrigger.Instance.CODEC;
    }

    public void trigger(ServerPlayer player, double distance, double remainingHealth) {
        this.trigger(player, (instance) -> instance.distance().matches(distance) && instance.remainingHealth().matches(remainingHealth));
    }

    public record Instance(Optional<ContextAwarePredicate> player, MinMaxBounds.Doubles distance, MinMaxBounds.Doubles remainingHealth) implements SimpleInstance {
        public static final Codec<FallOnGroundTrigger.Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(FallOnGroundTrigger.Instance::player),
                MinMaxBounds.Doubles.CODEC.optionalFieldOf("distance", MinMaxBounds.Doubles.ANY).forGetter(FallOnGroundTrigger.Instance::distance),
                MinMaxBounds.Doubles.CODEC.optionalFieldOf("remaining_health", MinMaxBounds.Doubles.ANY).forGetter(FallOnGroundTrigger.Instance::remainingHealth)
        ).apply(instance, FallOnGroundTrigger.Instance::new));

        public static Criterion<FallOnGroundTrigger.Instance> forValue(MinMaxBounds.Doubles distance, MinMaxBounds.Doubles remainingHealth) {
            return AetherIIAdvancementTriggers.FALL_ON_GROUND.get().createCriterion(new FallOnGroundTrigger.Instance(Optional.empty(), distance, remainingHealth));
        }

        public static Criterion<FallOnGroundTrigger.Instance> forValue(EntityPredicate.Builder player, MinMaxBounds.Doubles distance, MinMaxBounds.Doubles remainingHealth) {
            return AetherIIAdvancementTriggers.FALL_ON_GROUND.get().createCriterion(new FallOnGroundTrigger.Instance(Optional.of(EntityPredicate.wrap(player)), distance, remainingHealth));
        }
    }
}
