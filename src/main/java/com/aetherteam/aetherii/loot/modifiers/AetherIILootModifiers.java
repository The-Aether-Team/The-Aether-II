package com.aetherteam.aetherii.loot.modifiers;

import com.aetherteam.aetherii.AetherII;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AetherIILootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, AetherII.MODID);

    public static final RegistryObject<Codec<DoubleDropsModifier>> DOUBLE_DROPS = GLOBAL_LOOT_MODIFIERS.register("double_drops", () -> DoubleDropsModifier.CODEC);
}
