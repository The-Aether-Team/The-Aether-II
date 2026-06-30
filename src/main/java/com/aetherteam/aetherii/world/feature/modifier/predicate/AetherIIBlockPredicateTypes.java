package com.aetherteam.aetherii.world.feature.modifier.predicate;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class AetherIIBlockPredicateTypes {
    public static final DeferredRegister<BlockPredicateType<?>> BLOCK_PREDICATE_TYPES = DeferredRegister.create(Registries.BLOCK_PREDICATE_TYPE, AetherII.MODID);

    public static RegistryObject<BlockPredicateType<ScanPredicate>> SCAN = BLOCK_PREDICATE_TYPES.register("scan", () -> () -> ScanPredicate.CODEC.codec());
    public static RegistryObject<BlockPredicateType<SearchPredicate>> SEARCH = BLOCK_PREDICATE_TYPES.register("search", () -> () -> SearchPredicate.CODEC.codec());
    public static RegistryObject<BlockPredicateType<MossyPredicate>> MOSSY = BLOCK_PREDICATE_TYPES.register("mossy", () -> () -> MossyPredicate.CODEC.codec());
}
