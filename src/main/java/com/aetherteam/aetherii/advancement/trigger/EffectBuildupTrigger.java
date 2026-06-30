package com.aetherteam.aetherii.advancement.trigger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EffectBuildupTrigger extends SimpleCriterionTrigger<EffectBuildupTrigger.Instance> {
    public static final ResourceLocation ID = AetherIIAdvancementTriggers.id("effect_buildup");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected Instance createInstance(JsonObject json, ContextAwarePredicate player, DeserializationContext context) {
        Optional<EntityPredicate> directSource = json.has("direct_source") ? Optional.of(EntityPredicate.fromJson(json.get("direct_source"))) : Optional.empty();
        Optional<EntityPredicate> target = json.has("target") ? Optional.of(EntityPredicate.fromJson(json.get("target"))) : Optional.empty();
        return new Instance(player, directSource, target, effectsFromJson(json), GsonHelper.getAsBoolean(json, "triggered", false));
    }

    public void trigger(ServerPlayer player, Entity source, Entity target, Holder<MobEffect> effect, boolean triggered) {
        this.trigger(player, instance -> instance.test(player, source, target, effect, triggered));
    }

    public static class Instance extends AbstractCriterionTriggerInstance {
        private final Optional<EntityPredicate> directSource;
        private final Optional<EntityPredicate> target;
        private final HolderSet<MobEffect> mobEffects;
        private final boolean triggered;

        public Instance(ContextAwarePredicate player, Optional<EntityPredicate> directSource, Optional<EntityPredicate> target, HolderSet<MobEffect> mobEffects, boolean triggered) {
            super(ID, player);
            this.directSource = directSource;
            this.target = target;
            this.mobEffects = mobEffects;
            this.triggered = triggered;
        }

        public static Criterion effect(Optional<EntityPredicate> directSource, Optional<EntityPredicate> target, HolderSet<MobEffect> mobEffects, boolean triggered) {
            return new Criterion(new Instance(ContextAwarePredicate.ANY, directSource, target, mobEffects, triggered));
        }

        public boolean test(ServerPlayer player, Entity directSource, Entity target, Holder<MobEffect> effect, boolean triggered) {
            if (this.directSource.isPresent() && !this.directSource.get().matches(player, directSource)) {
                return false;
            }
            if (this.target.isPresent() && !this.target.get().matches(player, target)) {
                return false;
            }
            if (this.mobEffects != null && !this.mobEffects.contains(effect)) {
                return false;
            }
            return this.triggered == triggered;
        }

        @Override
        public JsonObject serializeToJson(SerializationContext conditions) {
            JsonObject jsonObject = super.serializeToJson(conditions);
            this.directSource.ifPresent(directSource -> jsonObject.add("direct_source", directSource.serializeToJson()));
            this.target.ifPresent(target -> jsonObject.add("target", target.serializeToJson()));
            if (this.mobEffects != null) {
                JsonArray effects = new JsonArray();
                for (Holder<MobEffect> holder : this.mobEffects) {
                    ResourceLocation location = holder.unwrapKey()
                            .map(ResourceKey::location)
                            .orElseGet(() -> BuiltInRegistries.MOB_EFFECT.getKey(holder.value()));
                    effects.add(location.toString());
                }
                jsonObject.add("effects", effects);
            }
            jsonObject.addProperty("triggered", this.triggered);
            return jsonObject;
        }
    }

    private static HolderSet<MobEffect> effectsFromJson(JsonObject json) {
        if (!json.has("effects")) {
            return null;
        }
        JsonElement element = json.get("effects");
        List<Holder<MobEffect>> effects = new ArrayList<>();
        if (element.isJsonArray()) {
            for (JsonElement effect : element.getAsJsonArray()) {
                effects.add(effectFromJson(effect));
            }
        } else {
            effects.add(effectFromJson(element));
        }
        return HolderSet.direct(effects);
    }

    private static Holder<MobEffect> effectFromJson(JsonElement element) {
        ResourceLocation effectId = new ResourceLocation(GsonHelper.convertToString(element, "effect"));
        return BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(ResourceKey.create(Registries.MOB_EFFECT, effectId));
    }
}
