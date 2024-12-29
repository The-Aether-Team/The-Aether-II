package com.aetherteam.aetherii.client.renderer.item.properties;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterRangeSelectItemModelPropertyEvent;
import net.neoforged.neoforge.client.event.RegisterSelectItemModelPropertyEvent;

public class AetherIIItemModelProperties {
    public static void registerSelectProperties(RegisterSelectItemModelPropertyEvent event) {
        event.register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "feather_color"), SelectFeatherColor.TYPE);
        event.register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "moa_egg_type"), SelectMoaEggType.TYPE);
    }

    public static void registerRangeSelectProperties(RegisterRangeSelectItemModelPropertyEvent event) {
        event.register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "reinforcement_tier"), ReinforcementTierRange.MAP_CODEC);
        event.register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "crossbow/pull"), TieredCrossbowPullRange.MAP_CODEC);
        event.register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "healing_stone_charges"), HealingStoneChargeRange.MAP_CODEC);
    }
}
