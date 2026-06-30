package com.aetherteam.aetherii.world.feature.modifier.filter;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class AetherIIPlacementModifierTypes {
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIER_TYPES = DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, AetherII.MODID);

    public static final RegistryObject<PlacementModifierType<StructureBlacklistFilter>> STRUCTURE_BLACKLIST_FILTER = PLACEMENT_MODIFIER_TYPES.register("structure_blacklist_filter", () -> () -> StructureBlacklistFilter.CODEC.codec());
    public static final RegistryObject<PlacementModifierType<ElevationFilter>> ELEVATION_FILTER = PLACEMENT_MODIFIER_TYPES.register("elevation_filter", () -> () -> ElevationFilter.CODEC.codec());
    public static final RegistryObject<PlacementModifierType<ImprovedLayerPlacementModifier>> IMPROVED_LAYER_PLACEMENT = PLACEMENT_MODIFIER_TYPES.register("improved_layer_placement", () -> () -> ImprovedLayerPlacementModifier.CODEC.codec());
    public static final RegistryObject<PlacementModifierType<LakePlacementModifier>> LAKE_PLACEMENT = PLACEMENT_MODIFIER_TYPES.register("lake_placement", () -> () -> LakePlacementModifier.CODEC.codec());
}
