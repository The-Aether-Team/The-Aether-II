package com.aetherteam.aetherii.data.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeProvider;

public abstract class AetherIIRecipeProvider extends RecipeProvider {
    private static String ID;

    public AetherIIRecipeProvider(PackOutput output, String id) {
        super(output);
        ID = id;
    }
}
