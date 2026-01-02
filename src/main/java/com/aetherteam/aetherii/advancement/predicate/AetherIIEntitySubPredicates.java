package com.aetherteam.aetherii.advancement.predicate;

import com.aetherteam.aetherii.AetherII;
import com.mojang.serialization.MapCodec;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIEntitySubPredicates {
    public static final DeferredRegister<MapCodec<? extends EntitySubPredicate>> ENTITY_SUB_PREDICATES = DeferredRegister.create(BuiltInRegistries.ENTITY_SUB_PREDICATE_TYPE, AetherII.MODID);

    public static final DeferredHolder<MapCodec<? extends EntitySubPredicate>, MapCodec<KirridPredicate>> KIRRID = ENTITY_SUB_PREDICATES.register("kirrid", () -> KirridPredicate.CODEC);
    public static final DeferredHolder<MapCodec<? extends EntitySubPredicate>, MapCodec<SheepuffPredicate>> SHEEPUFF = ENTITY_SUB_PREDICATES.register("sheepuff", () -> SheepuffPredicate.CODEC);
}
