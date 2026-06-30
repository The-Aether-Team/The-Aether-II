package com.aetherteam.aetherii.loot.conditions;

import com.aetherteam.aetherii.entity.PlantCuttingMob;
import com.google.gson.JsonObject;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonSerializationContext;

public record PlayerGrownCondition() implements LootItemCondition {
    @Override
    public boolean test(LootContext context) {
        Entity entity = context.getParamOrNull(LootContextParams.THIS_ENTITY);
        if (entity instanceof PlantCuttingMob plantCuttingMob) {
            return plantCuttingMob.isPlayerGrown();
        }
        return false;
    }

    @Override
    public LootItemConditionType getType() {
        return AetherIILootConditions.PLAYER_GROWN.get();
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<PlayerGrownCondition> {
        @Override
        public void serialize(JsonObject jsonObject, PlayerGrownCondition condition, JsonSerializationContext context) {
        }

        @Override
        public PlayerGrownCondition deserialize(JsonObject jsonObject, JsonDeserializationContext context) {
            return new PlayerGrownCondition();
        }
    }
}
