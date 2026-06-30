package com.aetherteam.aetherii.recipe;

import com.aetherteam.aetherii.AetherII;
import com.google.common.collect.ImmutableMap;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public final class AetherIIRecipePriorities {
    private static final int AETHER_PRIORITY = 5;
    private static final Map<ResourceLocation, Integer> PRIORITIES = ImmutableMap.<ResourceLocation, Integer>builder()
            .put(aether("beast_pelt_boots"), AETHER_PRIORITY)
            .put(aether("beast_pelt_chestplate"), AETHER_PRIORITY)
            .put(aether("beast_pelt_gloves"), AETHER_PRIORITY)
            .put(aether("beast_pelt_helmet"), AETHER_PRIORITY)
            .put(aether("beast_pelt_leggings"), AETHER_PRIORITY)
            .put(aether("burrukai_plate_boots"), AETHER_PRIORITY)
            .put(aether("burrukai_plate_chestplate"), AETHER_PRIORITY)
            .put(aether("burrukai_plate_gloves"), AETHER_PRIORITY)
            .put(aether("burrukai_plate_helmet"), AETHER_PRIORITY)
            .put(aether("burrukai_plate_leggings"), AETHER_PRIORITY)
            .put(aether("cloudwool"), AETHER_PRIORITY)
            .put(aether("hide_bundle"), AETHER_PRIORITY)
            .put(aether("holystone_axe"), AETHER_PRIORITY)
            .put(aether("holystone_furnace"), AETHER_PRIORITY)
            .put(aether("holystone_hammer"), AETHER_PRIORITY)
            .put(aether("holystone_pickaxe"), AETHER_PRIORITY)
            .put(aether("holystone_shortsword"), AETHER_PRIORITY)
            .put(aether("holystone_shovel"), AETHER_PRIORITY)
            .put(aether("holystone_pike"), AETHER_PRIORITY)
            .put(aether("holystone_trowel"), AETHER_PRIORITY)
            .put(aether("skyroot_axe"), AETHER_PRIORITY)
            .put(aether("skyroot_barrel"), AETHER_PRIORITY)
            .put(aether("skyroot_bookshelf"), AETHER_PRIORITY)
            .put(aether("skyroot_bucket"), AETHER_PRIORITY)
            .put(aether("skyroot_chest"), AETHER_PRIORITY)
            .put(aether("skyroot_crafting_table"), AETHER_PRIORITY)
            .put(aether("skyroot_hammer"), AETHER_PRIORITY)
            .put(aether("skyroot_ladder"), AETHER_PRIORITY)
            .put(aether("skyroot_pickaxe"), AETHER_PRIORITY)
            .put(aether("skyroot_shovel"), AETHER_PRIORITY)
            .put(aether("skyroot_pike"), AETHER_PRIORITY)
            .put(aether("skyroot_stick"), AETHER_PRIORITY)
            .put(aether("skyroot_shortsword"), AETHER_PRIORITY)
            .put(aether("skyroot_trowel"), AETHER_PRIORITY)
            .build();

    private AetherIIRecipePriorities() {
    }

    public static int get(ResourceLocation recipeId) {
        return PRIORITIES.getOrDefault(recipeId, 0);
    }

    private static ResourceLocation aether(String path) {
        return new ResourceLocation(AetherII.MODID, path);
    }
}
