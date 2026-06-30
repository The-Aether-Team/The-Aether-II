package com.aetherteam.aetherii.loot.functions;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class AetherIILootFunctions {
    public static final DeferredRegister<LootItemFunctionType> LOOT_FUNCTION_TYPES = DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, AetherII.MODID);

    public static final RegistryObject<LootItemFunctionType> SPAWN_SKYROOT_LIZARD = LOOT_FUNCTION_TYPES.register("spawn_skyroot_lizard", () -> new LootItemFunctionType(new SpawnSkyrootLizard.Serializer()));
    public static final RegistryObject<LootItemFunctionType> GEL_DROPS = LOOT_FUNCTION_TYPES.register("gel_drops", () -> new LootItemFunctionType(new GelDropsFunction.Serializer()));
    public static final RegistryObject<LootItemFunctionType> SUGAR_DROPS = LOOT_FUNCTION_TYPES.register("sugar_drops", () -> new LootItemFunctionType(new SugarDropsFunction.Serializer()));
    public static final RegistryObject<LootItemFunctionType> SET_AETHER_II_COMPONENT = LOOT_FUNCTION_TYPES.register("set_aether_ii_component", () -> new LootItemFunctionType(new SetAetherIIComponent.Serializer()));
}
