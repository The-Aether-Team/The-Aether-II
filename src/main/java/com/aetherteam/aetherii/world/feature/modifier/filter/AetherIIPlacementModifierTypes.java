package com.aetherteam.aetherii.world.feature.modifier.filter;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIPlacementModifierTypes {
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIER_TYPES = DeferredRegister.create(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE, AetherII.MODID);

    public static DeferredHolder<PlacementModifierType<?>, PlacementModifierType<ElevationFilter>> ELEVATION_FILTER = PLACEMENT_MODIFIER_TYPES.register("elevation_filter", () -> () -> ElevationFilter.CODEC);
}
