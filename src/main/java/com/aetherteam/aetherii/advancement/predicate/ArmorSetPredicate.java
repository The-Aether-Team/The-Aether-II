package com.aetherteam.aetherii.advancement.predicate;

import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public record ArmorSetPredicate(TagKey<Item> armor) implements EntitySubPredicate {
    public static ArmorSetPredicate fromJson(JsonObject jsonObject) {
        String id = GsonHelper.getAsString(jsonObject, "armor");
        if (id.startsWith("#")) {
            id = id.substring(1);
        }
        return new ArmorSetPredicate(TagKey.create(Registries.ITEM, new ResourceLocation(id)));
    }

    @Override
    public Type type() {
        return AetherIIEntitySubPredicates.ARMOR_SET;
    }

    @Override
    public JsonObject serializeCustomData() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("armor", this.armor().location().toString());
        return jsonObject;
    }

    @Override
    public boolean matches(Entity entity, ServerLevel level, @Nullable Vec3 position) {
        if (entity instanceof LivingEntity livingEntity) {
            return EquipmentUtil.hasArmorAbility(livingEntity, this.armor());
        }
        return false;
    }
}
