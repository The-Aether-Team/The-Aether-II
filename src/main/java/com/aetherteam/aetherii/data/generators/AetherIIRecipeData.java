package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.data.providers.AetherIIRecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;

public class AetherIIRecipeData extends AetherIIRecipeProvider {
    public AetherIIRecipeData(PackOutput output) {
        super(output, AetherII.MODID);
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {

    }
}
