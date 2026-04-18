package com.aetherteam.aetherii.loot.conditions;

import com.aetherteam.aetherii.entity.PlantCuttingMob;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public record PlayerGrownCondition() implements LootItemCondition {
    public static final MapCodec<PlayerGrownCondition> CODEC = MapCodec.unit(PlayerGrownCondition::new);

    @Override
    public boolean test(LootContext context) {
        Entity entity = context.getOptionalParameter(LootContextParams.THIS_ENTITY);
        if (entity instanceof PlantCuttingMob plantCuttingMob) {
            return plantCuttingMob.isPlayerGrown();
        }
        return false;
    }

    @Override
    public MapCodec<? extends LootItemCondition> codec() {
        return CODEC;
    }
}
