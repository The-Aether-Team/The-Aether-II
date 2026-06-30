package com.aetherteam.aetherii.advancement.trigger;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class IncubationTrigger extends SimpleCriterionTrigger<IncubationTrigger.Instance> {
    public static final ResourceLocation ID = AetherIIAdvancementTriggers.id("incubation");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected Instance createInstance(JsonObject json, ContextAwarePredicate player, DeserializationContext context) {
        return new Instance(player, json.has("entity") ? EntityPredicate.fromJson(json.get("entity")) : null);
    }

    public void trigger(ServerPlayer player, Entity entity) {
        this.trigger(player, instance -> instance.test(player, entity));
    }

    public static class Instance extends AbstractCriterionTriggerInstance {
        private final EntityPredicate entity;

        public Instance(ContextAwarePredicate player, EntityPredicate entity) {
            super(ID, player);
            this.entity = entity;
        }

        public static Criterion incubate() {
            return new Criterion(new Instance(ContextAwarePredicate.ANY, null));
        }

        public static Criterion incubateEntity(EntityPredicate entity) {
            return new Criterion(new Instance(ContextAwarePredicate.ANY, entity));
        }

        public boolean test(ServerPlayer player, Entity entity) {
            return this.entity == null || this.entity.matches(player, entity);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext conditions) {
            JsonObject jsonObject = super.serializeToJson(conditions);
            if (this.entity != null) {
                jsonObject.add("entity", this.entity.serializeToJson());
            }
            return jsonObject;
        }
    }
}
