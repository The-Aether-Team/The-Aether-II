package com.aetherteam.aetherii.advancement.predicate;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public record AndPredicate(EntitySubPredicate first, EntitySubPredicate second) implements EntitySubPredicate {
    public static AndPredicate fromJson(JsonObject jsonObject) {
        return new AndPredicate(EntitySubPredicate.fromJson(jsonObject.get("first")), EntitySubPredicate.fromJson(jsonObject.get("second")));
    }

    @Override
    public Type type() {
        return AetherIIEntitySubPredicates.AND;
    }

    @Override
    public JsonObject serializeCustomData() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("first", this.first().serialize());
        jsonObject.add("second", this.second().serialize());
        return jsonObject;
    }

    @Override
    public boolean matches(Entity entity, ServerLevel level, @Nullable Vec3 position) {
        return this.first().matches(entity, level, position) && this.second().matches(entity, level, position);
    }
}
