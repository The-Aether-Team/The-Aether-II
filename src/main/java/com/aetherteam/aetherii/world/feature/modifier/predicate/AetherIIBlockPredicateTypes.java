package com.aetherteam.aetherii.world.feature.modifier.predicate;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIBlockPredicateTypes {
    public static final DeferredRegister<BlockPredicateType<?>> BLOCK_PREDICATE_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_PREDICATE_TYPE, AetherII.MODID);

    public static DeferredHolder<BlockPredicateType<?>, BlockPredicateType<ScanPredicate>> SCAN = BLOCK_PREDICATE_TYPES.register("scan", () -> () -> ScanPredicate.CODEC);
    public static DeferredHolder<BlockPredicateType<?>, BlockPredicateType<SearchPredicate>> SEARCH = BLOCK_PREDICATE_TYPES.register("search", () -> () -> SearchPredicate.CODEC);
}
