package com.aetherteam.aetherii.client.gui.screen.inventory.recipebook;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.recipebook.CraftingRecipeBookComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractCraftingMenu;

public class SkyrootCraftingTableRecipeBookComponent extends CraftingRecipeBookComponent {
    private static final WidgetSprites FILTER_SPRITES = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "recipe_book/skyroot_filter_enabled"),
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "recipe_book/skyroot_filter_disabled"),
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "recipe_book/skyroot_filter_enabled_highlighted"),
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "recipe_book/skyroot_filter_disabled_highlighted"));

    public SkyrootCraftingTableRecipeBookComponent(AbstractCraftingMenu menu) {
        super(menu);
    }

    @Override
    protected void initFilterButtonTextures() {
        this.filterButton.initTextureValues(FILTER_SPRITES);
    }
}
