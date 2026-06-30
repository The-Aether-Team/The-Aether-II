package com.aetherteam.aetherii.advancement.trigger;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class ForgingCharmTrigger extends SimpleCriterionTrigger<ForgingCharmTrigger.Instance> {
    public static final ResourceLocation ID = AetherIIAdvancementTriggers.id("forging_charm");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected Instance createInstance(JsonObject json, ContextAwarePredicate player, DeserializationContext context) {
        return new Instance(player, json.has("item") ? ItemPredicate.fromJson(json.get("item")) : null);
    }

    public void trigger(ServerPlayer player, ItemStack stack) {
        this.trigger(player, instance -> instance.test(stack));
    }

    public static class Instance extends AbstractCriterionTriggerInstance {
        private final ItemPredicate item;

        public Instance(ContextAwarePredicate player, ItemPredicate item) {
            super(ID, player);
            this.item = item;
        }

        public static Criterion charm() {
            return new Criterion(new Instance(ContextAwarePredicate.ANY, null));
        }

        public static Criterion charmItem(ItemPredicate item) {
            return new Criterion(new Instance(ContextAwarePredicate.ANY, item));
        }

        public boolean test(ItemStack stack) {
            return this.item == null || this.item.matches(stack);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext conditions) {
            JsonObject jsonObject = super.serializeToJson(conditions);
            if (this.item != null) {
                jsonObject.add("item", this.item.serializeToJson());
            }
            return jsonObject;
        }
    }
}
