package com.aetherteam.aetherii.mixin.mixins.client.accessor;

import net.minecraft.client.gui.screens.recipebook.OverlayRecipeComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeBookPage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RecipeBookPage.class)
public interface RecipeBookPageAccessor {
    @Accessor("overlay")
    OverlayRecipeComponent aether_ii$getOverlay();

    @Mutable
    @Accessor("overlay")
    void aether_ii$setOverlay(OverlayRecipeComponent overlay);
}
