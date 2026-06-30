package com.aetherteam.aetherii.loot.conditions;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class AetherIILootConditions {
    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITION_TYPES = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, AetherII.MODID);

    public static final RegistryObject<LootItemConditionType> PLAYER_GROWN = LOOT_CONDITION_TYPES.register("player_grown", () -> new LootItemConditionType(new PlayerGrownCondition.Serializer()));
    public static final RegistryObject<LootItemConditionType> TIER_COMPARE = LOOT_CONDITION_TYPES.register("tier_compare", () -> new LootItemConditionType(new TierCompare.Serializer()));
}
