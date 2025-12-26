package com.aetherteam.aetherii.mixin.mixins.client.accessor;

import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeBookPage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RecipeBookComponent.class)
public interface RecipeBookComponentAccessor {
    @Accessor("time")
    float aether_ii$getTime();

    @Mutable
    @Accessor("recipeBookPage")
    void aether_ii$setRecipeBookPage(RecipeBookPage page);
}
