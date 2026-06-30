package com.aetherteam.aetherii.advancement.trigger;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class FallOnGroundTrigger extends SimpleCriterionTrigger<FallOnGroundTrigger.Instance> {
    public static final ResourceLocation ID = AetherIIAdvancementTriggers.id("fall_on_ground");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected Instance createInstance(JsonObject json, ContextAwarePredicate player, DeserializationContext context) {
        return new Instance(player, MinMaxBounds.Doubles.fromJson(json.get("distance")), MinMaxBounds.Doubles.fromJson(json.get("remaining_health")));
    }

    public void trigger(ServerPlayer player, double distance, double remainingHealth) {
        this.trigger(player, instance -> instance.distance.matches(distance) && instance.remainingHealth.matches(remainingHealth));
    }

    public static class Instance extends AbstractCriterionTriggerInstance {
        private final MinMaxBounds.Doubles distance;
        private final MinMaxBounds.Doubles remainingHealth;

        public Instance(ContextAwarePredicate player, MinMaxBounds.Doubles distance, MinMaxBounds.Doubles remainingHealth) {
            super(ID, player);
            this.distance = distance;
            this.remainingHealth = remainingHealth;
        }

        public static Criterion forValue(MinMaxBounds.Doubles distance, MinMaxBounds.Doubles remainingHealth) {
            return new Criterion(new Instance(ContextAwarePredicate.ANY, distance, remainingHealth));
        }

        public static Criterion forValue(EntityPredicate.Builder player, MinMaxBounds.Doubles distance, MinMaxBounds.Doubles remainingHealth) {
            return new Criterion(new Instance(EntityPredicate.wrap(player.build()), distance, remainingHealth));
        }

        @Override
        public JsonObject serializeToJson(SerializationContext conditions) {
            JsonObject jsonObject = super.serializeToJson(conditions);
            jsonObject.add("distance", this.distance.serializeToJson());
            jsonObject.add("remaining_health", this.remainingHealth.serializeToJson());
            return jsonObject;
        }
    }
}
