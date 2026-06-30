package com.aetherteam.aetherii.advancement.predicate;

import com.aetherteam.aetherii.entity.passive.Sheepuff;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record SheepuffPredicate(Optional<Boolean> puffed, Optional<Boolean> sheared, Optional<Sheepuff.SheepuffColor> color) implements EntitySubPredicate {
    public static SheepuffPredicate fromJson(JsonObject jsonObject) {
        Optional<Boolean> puffed = jsonObject.has("puffed") ? Optional.of(GsonHelper.getAsBoolean(jsonObject, "puffed")) : Optional.empty();
        Optional<Boolean> sheared = jsonObject.has("sheared") ? Optional.of(GsonHelper.getAsBoolean(jsonObject, "sheared")) : Optional.empty();
        Optional<Sheepuff.SheepuffColor> color = jsonObject.has("color") ? Optional.of(getColor(GsonHelper.getAsString(jsonObject, "color"))) : Optional.empty();
        return new SheepuffPredicate(puffed, sheared, color);
    }

    @Override
    public Type type() {
        return AetherIIEntitySubPredicates.SHEEPUFF;
    }

    @Override
    public JsonObject serializeCustomData() {
        JsonObject jsonObject = new JsonObject();
        this.puffed().ifPresent(value -> jsonObject.addProperty("puffed", value));
        this.sheared().ifPresent(value -> jsonObject.addProperty("sheared", value));
        this.color().ifPresent(value -> jsonObject.addProperty("color", value.getSerializedName()));
        return jsonObject;
    }

    @Override
    public boolean matches(Entity entity, ServerLevel serverLevel, @Nullable Vec3 vec3) {
        if (entity instanceof Sheepuff sheepuff) {
            if (this.sheared.isPresent() && sheepuff.isSheared() == this.sheared.get()) {
                if (this.color.isPresent()) {
                    return this.color.get() == sheepuff.getColor();
                } else if (this.puffed.isPresent()) {
                    return this.puffed.get() == sheepuff.getPuffed();
                }
            }
        }
        return false;
    }

    public static SheepuffPredicate hasWool(Sheepuff.SheepuffColor color) {
        return new SheepuffPredicate(Optional.empty(), Optional.of(false), Optional.of(color));
    }

    public static SheepuffPredicate isPuffed(boolean puffed) {
        return new SheepuffPredicate(Optional.of(puffed), Optional.of(false), Optional.empty());
    }

    private static Sheepuff.SheepuffColor getColor(String name) {
        for (Sheepuff.SheepuffColor color : Sheepuff.SheepuffColor.values()) {
            if (color.getSerializedName().equalsIgnoreCase(name)) {
                return color;
            }
        }
        throw new IllegalArgumentException("Unknown sheepuff color: " + name);
    }
}
