package com.aetherteam.aetherii.advancement.predicate;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public record AlivePredicate() implements EntitySubPredicate {
    @Override
    public Type type() {
        return AetherIIEntitySubPredicates.ALIVE;
    }

    @Override
    public JsonObject serializeCustomData() {
        return new JsonObject();
    }

    @Override
    public boolean matches(Entity entity, ServerLevel level, @Nullable Vec3 position) {
        return entity.isAlive();
    }
}
