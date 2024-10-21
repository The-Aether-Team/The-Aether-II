package com.aetherteam.aetherii.loot.functions;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIILootFunctions {
    public static final DeferredRegister<LootItemFunctionType<?>> LOOT_FUNCTION_TYPES = DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, AetherII.MODID);

    public static final DeferredHolder<LootItemFunctionType<?>, LootItemFunctionType<?>> SPAWN_SKYROOT_LIZARD = LOOT_FUNCTION_TYPES.register("spawn_skyroot_lizard", () -> new LootItemFunctionType<>(SpawnSkyrootLizard.CODEC));
}