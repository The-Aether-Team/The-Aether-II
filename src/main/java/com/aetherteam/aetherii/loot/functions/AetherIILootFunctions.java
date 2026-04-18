package com.aetherteam.aetherii.loot.functions;

import com.aetherteam.aetherii.AetherII;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIILootFunctions {
    public static final DeferredRegister<MapCodec<? extends LootItemFunction>> LOOT_FUNCTION_TYPES = DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, AetherII.MODID);

    public static final DeferredHolder<MapCodec<? extends LootItemFunction>, MapCodec<? extends LootItemFunction>> SPAWN_SKYROOT_LIZARD = LOOT_FUNCTION_TYPES.register("spawn_skyroot_lizard", () -> SpawnSkyrootLizard.CODEC);
    public static final DeferredHolder<MapCodec<? extends LootItemFunction>, MapCodec<? extends LootItemFunction>> GEL_DROPS = LOOT_FUNCTION_TYPES.register("gel_drops", () -> GelDropsFunction.CODEC);
    public static final DeferredHolder<MapCodec<? extends LootItemFunction>, MapCodec<? extends LootItemFunction>> SUGAR_DROPS = LOOT_FUNCTION_TYPES.register("sugar_drops", () -> SugarDropsFunction.CODEC);
}