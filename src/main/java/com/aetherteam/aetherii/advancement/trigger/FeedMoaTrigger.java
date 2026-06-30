package com.aetherteam.aetherii.advancement.trigger;

import com.google.gson.JsonObject;
import java.util.Optional;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;

public class FeedMoaTrigger extends SimpleCriterionTrigger<FeedMoaTrigger.Instance> {
    public static final ResourceLocation ID = AetherIIAdvancementTriggers.id("feed_moa");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected Instance createInstance(JsonObject json, ContextAwarePredicate player, DeserializationContext context) {
        ItemPredicate item = json.has("item") ? ItemPredicate.fromJson(json.get("item")) : null;
        ContextAwarePredicate entity = json.has("entity") ? EntityPredicate.fromJson(json, "entity", context) : null;
        return new Instance(player, item, entity);
    }

    public void trigger(ServerPlayer player, ItemStack item, Entity entity) {
        LootContext lootContext = EntityPredicate.createContext(player, entity);
        this.trigger(player, instance -> instance.matches(item, lootContext));
    }

    public static class Instance extends AbstractCriterionTriggerInstance {
        private final ItemPredicate item;
        private final ContextAwarePredicate entity;

        public Instance(ContextAwarePredicate player, ItemPredicate item, ContextAwarePredicate entity) {
            super(ID, player);
            this.item = item;
            this.entity = entity;
        }

        public static Criterion itemUsedOnEntity(Optional<ContextAwarePredicate> player, ItemPredicate.Builder item, Optional<ContextAwarePredicate> entity) {
            return new Criterion(new Instance(player.orElse(ContextAwarePredicate.ANY), item.build(), entity.orElse(null)));
        }

        public static Criterion itemUsedOnEntity(ItemPredicate.Builder item, Optional<ContextAwarePredicate> entity) {
            return itemUsedOnEntity(Optional.empty(), item, entity);
        }

        public static Criterion itemUsedOnEntity(ItemPredicate.Builder item) {
            return itemUsedOnEntity(Optional.empty(), item, Optional.empty());
        }

        public boolean matches(ItemStack item, LootContext lootContext) {
            return (this.item == null || this.item.matches(item)) && (this.entity == null || this.entity.matches(lootContext));
        }

        @Override
        public JsonObject serializeToJson(SerializationContext conditions) {
            JsonObject jsonObject = super.serializeToJson(conditions);
            if (this.item != null) {
                jsonObject.add("item", this.item.serializeToJson());
            }
            if (this.entity != null) {
                jsonObject.add("entity", this.entity.toJson(conditions));
            }
            return jsonObject;
        }
    }
}
