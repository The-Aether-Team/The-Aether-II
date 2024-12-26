package com.aetherteam.aetherii.recipe.set;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipePropertySet;

public class AetherIIRecipePropertySets {
    public static final ResourceKey<RecipePropertySet> ALTAR_INPUT = register("altar_input");

    private static ResourceKey<RecipePropertySet> register(String id) {
        return ResourceKey.create(RecipePropertySet.TYPE_KEY, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, id));
    }
}
