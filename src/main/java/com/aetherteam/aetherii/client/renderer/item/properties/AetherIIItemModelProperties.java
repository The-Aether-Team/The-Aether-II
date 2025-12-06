package com.aetherteam.aetherii.client.renderer.item.properties;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.item.properties.conditional.BetterIsUsingItem;
import com.aetherteam.aetherii.client.renderer.item.properties.conditional.HasBlockState;
import com.aetherteam.aetherii.client.renderer.item.properties.conditional.LassoThrow;
import com.aetherteam.aetherii.client.renderer.item.properties.range.*;
import com.aetherteam.aetherii.client.renderer.item.properties.select.SelectFeatherColor;
import com.aetherteam.aetherii.client.renderer.item.properties.select.SelectMoaEggType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterConditionalItemModelPropertyEvent;
import net.neoforged.neoforge.client.event.RegisterRangeSelectItemModelPropertyEvent;
import net.neoforged.neoforge.client.event.RegisterSelectItemModelPropertyEvent;

public class AetherIIItemModelProperties {
    public static void registerConditionalProperties(RegisterConditionalItemModelPropertyEvent event) {
        event.register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "using_item"), BetterIsUsingItem.MAP_CODEC);
        event.register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "has_block_state"), HasBlockState.MAP_CODEC);
        event.register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "lasso_throw"), LassoThrow.MAP_CODEC);
    }

    public static void registerSelectProperties(RegisterSelectItemModelPropertyEvent event) {
        event.register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "feather_color"), SelectFeatherColor.TYPE);
        event.register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "moa_egg/feather_color"), SelectMoaEggType.FeatherColor.TYPE);
        event.register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "moa_egg/feather_shape"), SelectMoaEggType.FeatherShape.TYPE);
        event.register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "moa_egg/eye_color"), SelectMoaEggType.EyeColor.TYPE);
        event.register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "moa_egg/keratin_color"), SelectMoaEggType.KeratinColor.TYPE);
    }

    public static void registerRangeSelectProperties(RegisterRangeSelectItemModelPropertyEvent event) {
        event.register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "reinforcement_tier"), ReinforcementTierRange.MAP_CODEC);
        event.register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "crossbow/pull"), TieredCrossbowPullRange.MAP_CODEC);
        event.register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "darts_loaded"), DartsLoadedRange.MAP_CODEC);
        event.register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "healing_stone_charges"), HealingStoneChargeRange.MAP_CODEC);
        event.register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "parachuting"), ParachutingRange.MAP_CODEC);
        event.register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "dull_ability"), DullAbilityRange.MAP_CODEC);
    }
}
