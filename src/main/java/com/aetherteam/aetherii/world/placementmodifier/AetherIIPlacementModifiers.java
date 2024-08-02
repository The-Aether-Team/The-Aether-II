package com.aetherteam.aetherii.world.placementmodifier;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIPlacementModifiers {
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS = DeferredRegister.create(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE, AetherII.MODID);

    public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<StructureBlacklistFilter>> STRUCTURE_BLACKLIST_FILTER = PLACEMENT_MODIFIERS.register("structure_blacklist_filter", () -> () -> StructureBlacklistFilter.CODEC);
}