package com.aetherteam.aetherii.client.gui.screen.inventory.recipebook;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.recipebook.CraftingRecipeBookComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.AbstractCraftingMenu;

public class SkyrootCraftingTableRecipeBookComponent extends CraftingRecipeBookComponent {
    private static final WidgetSprites FILTER_SPRITES = new WidgetSprites(
            Identifier.fromNamespaceAndPath(AetherII.MODID, "recipe_book/skyroot_filter_enabled"),
            Identifier.fromNamespaceAndPath(AetherII.MODID, "recipe_book/skyroot_filter_disabled"),
            Identifier.fromNamespaceAndPath(AetherII.MODID, "recipe_book/skyroot_filter_enabled_highlighted"),
            Identifier.fromNamespaceAndPath(AetherII.MODID, "recipe_book/skyroot_filter_disabled_highlighted"));

    public SkyrootCraftingTableRecipeBookComponent(AbstractCraftingMenu menu) {
        super(menu);
    }

    @Override
    protected WidgetSprites getFilterButtonTextures() {
        return FILTER_SPRITES;
    }
}
