package com.aetherteam.aetherii.advancement.trigger;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class CurrencyTrigger extends SimpleCriterionTrigger<CurrencyTrigger.Instance> {
    public static final ResourceLocation ID = AetherIIAdvancementTriggers.id("currency");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected Instance createInstance(JsonObject json, ContextAwarePredicate player, DeserializationContext context) {
        return new Instance(player, json.has("amount") ? json.get("amount").getAsInt() : 0);
    }

    public void trigger(ServerPlayer player, int amount) {
        this.trigger(player, instance -> amount >= instance.amount);
    }

    public static class Instance extends AbstractCriterionTriggerInstance {
        private final int amount;

        public Instance(ContextAwarePredicate player, int amount) {
            super(ID, player);
            this.amount = amount;
        }

        public static Criterion forValue(int amount) {
            return new Criterion(new Instance(ContextAwarePredicate.ANY, amount));
        }

        @Override
        public JsonObject serializeToJson(SerializationContext conditions) {
            JsonObject jsonObject = super.serializeToJson(conditions);
            jsonObject.addProperty("amount", this.amount);
            return jsonObject;
        }
    }
}
