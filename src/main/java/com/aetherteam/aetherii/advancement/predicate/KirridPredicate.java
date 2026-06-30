package com.aetherteam.aetherii.advancement.predicate;

import com.aetherteam.aetherii.entity.passive.Kirrid;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record KirridPredicate(Optional<Boolean> sheared, Optional<Kirrid.KirridColor> color) implements EntitySubPredicate {
    public static KirridPredicate fromJson(JsonObject jsonObject) {
        Optional<Boolean> sheared = jsonObject.has("sheared") ? Optional.of(GsonHelper.getAsBoolean(jsonObject, "sheared")) : Optional.empty();
        Optional<Kirrid.KirridColor> color = jsonObject.has("color") ? Optional.of(getColor(GsonHelper.getAsString(jsonObject, "color"))) : Optional.empty();
        return new KirridPredicate(sheared, color);
    }

    @Override
    public Type type() {
        return AetherIIEntitySubPredicates.KIRRID;
    }

    @Override
    public JsonObject serializeCustomData() {
        JsonObject jsonObject = new JsonObject();
        this.sheared().ifPresent(value -> jsonObject.addProperty("sheared", value));
        this.color().ifPresent(value -> jsonObject.addProperty("color", value.getSerializedName()));
        return jsonObject;
    }

    @Override
    public boolean matches(Entity entity, ServerLevel serverLevel, @Nullable Vec3 vec3) {
        if (entity instanceof Kirrid kirrid) {
            if (this.sheared.isPresent() && kirrid.isSheared() != this.sheared.get()) {
                return false;
            } else if (this.color.isPresent() && kirrid.getColor().isPresent()) {
                return this.color.get() == kirrid.getColor().get();
            } else {
                return this.color.isEmpty() && kirrid.getColor().isEmpty();
            }
        }
        return false;
    }

    public static KirridPredicate hasWool(Kirrid.KirridColor color) {
        return new KirridPredicate(Optional.of(false), Optional.of(color));
    }

    public static KirridPredicate hasWool() {
        return new KirridPredicate(Optional.of(false), Optional.empty());
    }

    private static Kirrid.KirridColor getColor(String name) {
        for (Kirrid.KirridColor color : Kirrid.KirridColor.values()) {
            if (color.getSerializedName().equalsIgnoreCase(name)) {
                return color;
            }
        }
        throw new IllegalArgumentException("Unknown kirrid color: " + name);
    }
}
