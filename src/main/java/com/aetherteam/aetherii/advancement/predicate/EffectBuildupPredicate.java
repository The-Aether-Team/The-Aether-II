package com.aetherteam.aetherii.advancement.predicate;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupInstance;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record EffectBuildupPredicate(Holder<MobEffect> effect, Optional<Integer> buildupLimit) implements EntitySubPredicate {
    public static EffectBuildupPredicate fromJson(JsonObject jsonObject) {
        ResourceLocation effectId = new ResourceLocation(GsonHelper.getAsString(jsonObject, "effect"));
        Holder<MobEffect> effect = BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(ResourceKey.create(Registries.MOB_EFFECT, effectId));
        Optional<Integer> buildupLimit = jsonObject.has("buildup_limit") ? Optional.of(GsonHelper.getAsInt(jsonObject, "buildup_limit")) : Optional.empty();
        return new EffectBuildupPredicate(effect, buildupLimit);
    }

    @Override
    public Type type() {
        return AetherIIEntitySubPredicates.EFFECT_BUILDUP;
    }

    @Override
    public JsonObject serializeCustomData() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("effect", this.effect().unwrapKey().orElseThrow().location().toString());
        this.buildupLimit().ifPresent(limit -> jsonObject.addProperty("buildup_limit", limit));
        return jsonObject;
    }

    @Override
    public boolean matches(Entity entity, ServerLevel level, @Nullable Vec3 position) {
        EffectBuildupInstance instance = AetherIIDataAttachments.get(entity, AetherIIDataAttachments.EFFECTS_SYSTEM).getActiveBuildups().get(this.effect());
        if (instance != null) {
            if (this.buildupLimit().isPresent()) {
                return instance.getBuildup() >= this.buildupLimit().get();
            }
            return true;
        }
        return false;
    }
}
