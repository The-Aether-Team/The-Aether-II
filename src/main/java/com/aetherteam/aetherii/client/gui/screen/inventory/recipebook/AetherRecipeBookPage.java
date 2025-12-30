package com.aetherteam.aetherii.client.gui.screen.inventory.recipebook;

import com.aetherteam.aetherii.mixin.mixins.client.accessor.RecipeBookPageAccessor;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeBookPage;
import net.minecraft.client.gui.screens.recipebook.SlotSelectTime;

public class AetherRecipeBookPage extends RecipeBookPage {
    public AetherRecipeBookPage(RecipeBookComponent<?> parent, SlotSelectTime slotSelectTime) {
        super(parent, slotSelectTime, false);
        RecipeBookPageAccessor pageAccessor = (RecipeBookPageAccessor) this;
        pageAccessor.aether_ii$setOverlay(new AetherOverlayRecipeComponent(parent, slotSelectTime, false));
    }
}
